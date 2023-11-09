/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EnterpriseDetails from './enterprise-details.vue';
import EnterpriseService from './enterprise.service';
import AlertService from '@/shared/alert/alert.service';

type EnterpriseDetailsComponentType = InstanceType<typeof EnterpriseDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const enterpriseSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Enterprise Management Detail Component', () => {
    let enterpriseServiceStub: SinonStubbedInstance<EnterpriseService>;
    let mountOptions: MountingOptions<EnterpriseDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      enterpriseServiceStub = sinon.createStubInstance<EnterpriseService>(EnterpriseService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          enterpriseService: () => enterpriseServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        enterpriseServiceStub.find.resolves(enterpriseSample);
        route = {
          params: {
            enterpriseId: '' + 123,
          },
        };
        const wrapper = shallowMount(EnterpriseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.enterprise).toMatchObject(enterpriseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        enterpriseServiceStub.find.resolves(enterpriseSample);
        const wrapper = shallowMount(EnterpriseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
