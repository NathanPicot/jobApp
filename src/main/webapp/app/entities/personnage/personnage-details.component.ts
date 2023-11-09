import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PersonnageService from './personnage.service';
import { useDateFormat } from '@/shared/composables';
import { type IPersonnage } from '@/shared/model/personnage.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PersonnageDetails',
  setup() {
    const dateFormat = useDateFormat();
    const personnageService = inject('personnageService', () => new PersonnageService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const personnage: Ref<IPersonnage> = ref({});

    const retrievePersonnage = async personnageId => {
      try {
        const res = await personnageService().find(personnageId);
        personnage.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.personnageId) {
      retrievePersonnage(route.params.personnageId);
    }

    return {
      ...dateFormat,
      alertService,
      personnage,

      previousState,
      t$: useI18n().t,
    };
  },
});
