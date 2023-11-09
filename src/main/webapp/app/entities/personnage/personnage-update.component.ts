import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PersonnageService from './personnage.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IPersonnage, Personnage } from '@/shared/model/personnage.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PersonnageUpdate',
  setup() {
    const personnageService = inject('personnageService', () => new PersonnageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const personnage: Ref<IPersonnage> = ref(new Personnage());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePersonnage = async personnageId => {
      try {
        const res = await personnageService().find(personnageId);
        res.birthday = new Date(res.birthday);
        personnage.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.personnageId) {
      retrievePersonnage(route.params.personnageId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name: {},
      age: {},
      birthday: {},
      jobs: {},
    };
    const v$ = useVuelidate(validationRules, personnage as any);
    v$.value.$validate();

    return {
      personnageService,
      alertService,
      personnage,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      ...useDateFormat({ entityRef: personnage }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.personnage.id) {
        this.personnageService()
          .update(this.personnage)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jobApp.personnage.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.personnageService()
          .create(this.personnage)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jobApp.personnage.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
