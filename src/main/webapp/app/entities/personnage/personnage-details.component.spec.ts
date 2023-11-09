/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PersonnageDetails from './personnage-details.vue';
import PersonnageService from './personnage.service';
import AlertService from '@/shared/alert/alert.service';

type PersonnageDetailsComponentType = InstanceType<typeof PersonnageDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const personnageSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Personnage Management Detail Component', () => {
    let personnageServiceStub: SinonStubbedInstance<PersonnageService>;
    let mountOptions: MountingOptions<PersonnageDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      personnageServiceStub = sinon.createStubInstance<PersonnageService>(PersonnageService);

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
          personnageService: () => personnageServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        personnageServiceStub.find.resolves(personnageSample);
        route = {
          params: {
            personnageId: '' + 123,
          },
        };
        const wrapper = shallowMount(PersonnageDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.personnage).toMatchObject(personnageSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        personnageServiceStub.find.resolves(personnageSample);
        const wrapper = shallowMount(PersonnageDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
