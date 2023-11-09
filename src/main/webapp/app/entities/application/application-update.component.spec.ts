/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ApplicationUpdate from './application-update.vue';
import ApplicationService from './application.service';
import AlertService from '@/shared/alert/alert.service';

import EnterpriseService from '@/entities/enterprise/enterprise.service';

type ApplicationUpdateComponentType = InstanceType<typeof ApplicationUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const applicationSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ApplicationUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Application Management Update Component', () => {
    let comp: ApplicationUpdateComponentType;
    let applicationServiceStub: SinonStubbedInstance<ApplicationService>;

    beforeEach(() => {
      route = {};
      applicationServiceStub = sinon.createStubInstance<ApplicationService>(ApplicationService);
      applicationServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          applicationService: () => applicationServiceStub,
          enterpriseService: () =>
            sinon.createStubInstance<EnterpriseService>(EnterpriseService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ApplicationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.application = applicationSample;
        applicationServiceStub.update.resolves(applicationSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(applicationServiceStub.update.calledWith(applicationSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        applicationServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ApplicationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.application = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(applicationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        applicationServiceStub.find.resolves(applicationSample);
        applicationServiceStub.retrieve.resolves([applicationSample]);

        // WHEN
        route = {
          params: {
            applicationId: '' + applicationSample.id,
          },
        };
        const wrapper = shallowMount(ApplicationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.application).toMatchObject(applicationSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        applicationServiceStub.find.resolves(applicationSample);
        const wrapper = shallowMount(ApplicationUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
