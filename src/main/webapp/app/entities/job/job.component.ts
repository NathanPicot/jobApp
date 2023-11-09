import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import JobService from './job.service';
import { type IJob } from '@/shared/model/job.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Job',
  setup() {
    const { t: t$ } = useI18n();
    const jobService = inject('jobService', () => new JobService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const jobs: Ref<IJob[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveJobs = async () => {
      isFetching.value = true;
      try {
        const res = await jobService().retrieve();
        jobs.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveJobs();
    };

    onMounted(async () => {
      await retrieveJobs();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IJob) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeJob = async () => {
      try {
        await jobService().delete(removeId.value);
        const message = t$('jobApp.job.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveJobs();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      jobs,
      handleSyncList,
      isFetching,
      retrieveJobs,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeJob,
      t$,
    };
  },
});
