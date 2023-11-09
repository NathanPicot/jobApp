/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EnterpriseUpdate from './enterprise-update.vue';
import EnterpriseService from './enterprise.service';
import AlertService from '@/shared/alert/alert.service';

type EnterpriseUpdateComponentType = InstanceType<typeof EnterpriseUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const enterpriseSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EnterpriseUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Enterprise Management Update Component', () => {
    let comp: EnterpriseUpdateComponentType;
    let enterpriseServiceStub: SinonStubbedInstance<EnterpriseService>;

    beforeEach(() => {
      route = {};
      enterpriseServiceStub = sinon.createStubInstance<EnterpriseService>(EnterpriseService);
      enterpriseServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          enterpriseService: () => enterpriseServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EnterpriseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.enterprise = enterpriseSample;
        enterpriseServiceStub.update.resolves(enterpriseSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(enterpriseServiceStub.update.calledWith(enterpriseSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        enterpriseServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EnterpriseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.enterprise = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(enterpriseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        enterpriseServiceStub.find.resolves(enterpriseSample);
        enterpriseServiceStub.retrieve.resolves([enterpriseSample]);

        // WHEN
        route = {
          params: {
            enterpriseId: '' + enterpriseSample.id,
          },
        };
        const wrapper = shallowMount(EnterpriseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.enterprise).toMatchObject(enterpriseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        enterpriseServiceStub.find.resolves(enterpriseSample);
        const wrapper = shallowMount(EnterpriseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
