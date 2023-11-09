/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Job from './job.vue';
import JobService from './job.service';
import AlertService from '@/shared/alert/alert.service';

type JobComponentType = InstanceType<typeof Job>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Job Management Component', () => {
    let jobServiceStub: SinonStubbedInstance<JobService>;
    let mountOptions: MountingOptions<JobComponentType>['global'];

    beforeEach(() => {
      jobServiceStub = sinon.createStubInstance<JobService>(JobService);
      jobServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          jobService: () => jobServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        jobServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Job, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(jobServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.jobs[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: JobComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Job, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        jobServiceStub.retrieve.reset();
        jobServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        jobServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeJob();
        await comp.$nextTick(); // clear components

        // THEN
        expect(jobServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(jobServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
