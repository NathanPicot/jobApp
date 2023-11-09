import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import ApplicationService from './application.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import EnterpriseService from '@/entities/enterprise/enterprise.service';
import { type IEnterprise } from '@/shared/model/enterprise.model';
import { type IApplication, Application } from '@/shared/model/application.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ApplicationUpdate',
  setup() {
    const applicationService = inject('applicationService', () => new ApplicationService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const application: Ref<IApplication> = ref(new Application());

    const enterpriseService = inject('enterpriseService', () => new EnterpriseService());

    const enterprises: Ref<IEnterprise[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'en'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveApplication = async applicationId => {
      try {
        const res = await applicationService().find(applicationId);
        application.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.applicationId) {
      retrieveApplication(route.params.applicationId);
    }

    const initRelationships = () => {
      enterpriseService()
        .retrieve()
        .then(res => {
          enterprises.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      name3: {},
      enterprise: {},
    };
    const v$ = useVuelidate(validationRules, application as any);
    v$.value.$validate();

    return {
      applicationService,
      alertService,
      application,
      previousState,
      isSaving,
      currentLanguage,
      enterprises,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.application.id) {
        this.applicationService()
          .update(this.application)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('jobApp.application.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.applicationService()
          .create(this.application)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('jobApp.application.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
