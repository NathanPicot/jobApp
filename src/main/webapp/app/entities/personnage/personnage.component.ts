import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PersonnageService from './personnage.service';
import { type IPersonnage } from '@/shared/model/personnage.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Personnage',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const personnageService = inject('personnageService', () => new PersonnageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const personnages: Ref<IPersonnage[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePersonnages = async () => {
      isFetching.value = true;
      try {
        const res = await personnageService().retrieve();
        personnages.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePersonnages();
    };

    onMounted(async () => {
      await retrievePersonnages();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPersonnage) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePersonnage = async () => {
      try {
        await personnageService().delete(removeId.value);
        const message = t$('jobApp.personnage.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePersonnages();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      personnages,
      handleSyncList,
      isFetching,
      retrievePersonnages,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePersonnage,
      t$,
    };
  },
});
