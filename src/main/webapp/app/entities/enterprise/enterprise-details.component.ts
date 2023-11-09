import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EnterpriseService from './enterprise.service';
import { type IEnterprise } from '@/shared/model/enterprise.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EnterpriseDetails',
  setup() {
    const enterpriseService = inject('enterpriseService', () => new EnterpriseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const enterprise: Ref<IEnterprise> = ref({});

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

    return {
      alertService,
      enterprise,

      previousState,
      t$: useI18n().t,
    };
  },
});
