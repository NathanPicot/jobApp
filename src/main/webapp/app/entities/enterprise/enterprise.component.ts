import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EnterpriseService from './enterprise.service';
import { type IEnterprise } from '@/shared/model/enterprise.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Enterprise',
  setup() {
    const { t: t$ } = useI18n();
    const enterpriseService = inject('enterpriseService', () => new EnterpriseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const enterprises: Ref<IEnterprise[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEnterprises = async () => {
      isFetching.value = true;
      try {
        const res = await enterpriseService().retrieve();
        enterprises.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEnterprises();
    };

    onMounted(async () => {
      await retrieveEnterprises();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEnterprise) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEnterprise = async () => {
      try {
        await enterpriseService().delete(removeId.value);
        const message = t$('jobApp.enterprise.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEnterprises();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      enterprises,
      handleSyncList,
      isFetching,
      retrieveEnterprises,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEnterprise,
      t$,
    };
  },
});
