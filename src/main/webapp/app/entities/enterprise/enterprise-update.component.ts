import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EnterpriseService from './enterprise.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IEnterprise, Enterprise } from '@/shared/model/enterprise.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EnterpriseUpdate',
  setup() {
    const enterpriseService = inject('enterpriseService', () => new EnterpriseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const enterprise: Ref<IEnterprise> = ref(new Enterprise());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveEnterprise = async enterpriseId => {
      try {
        const res = await enterpriseService().find(enterpriseId);
        enterprise.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.enterpriseId) {
      retrieveEnterprise(route.params.enterpriseId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name2: {},
      nbEmployee: {},
      international: {},
      jobs: {},
    };
    const v$ = useVuelidate(validationRules, enterprise as any);
    v$.value.$validate();

    return {
      enterpriseService,
      alertService,
      enterprise,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.enterprise.id) {
        this.enterpriseService()
          .update(this.enterprise)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jobApp.enterprise.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.enterpriseService()
          .create(this.enterprise)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jobApp.enterprise.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
