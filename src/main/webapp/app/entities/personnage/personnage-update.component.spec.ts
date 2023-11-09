/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import PersonnageUpdate from './personnage-update.vue';
import PersonnageService from './personnage.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type PersonnageUpdateComponentType = InstanceType<typeof PersonnageUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const personnageSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PersonnageUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Personnage Management Update Component', () => {
    let comp: PersonnageUpdateComponentType;
    let personnageServiceStub: SinonStubbedInstance<PersonnageService>;

    beforeEach(() => {
      route = {};
      personnageServiceStub = sinon.createStubInstance<PersonnageService>(PersonnageService);
      personnageServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          personnageService: () => personnageServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(PersonnageUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PersonnageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.personnage = personnageSample;
        personnageServiceStub.update.resolves(personnageSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personnageServiceStub.update.calledWith(personnageSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        personnageServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PersonnageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.personnage = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(personnageServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        personnageServiceStub.find.resolves(personnageSample);
        personnageServiceStub.retrieve.resolves([personnageSample]);

        // WHEN
        route = {
          params: {
            personnageId: '' + personnageSample.id,
          },
        };
        const wrapper = shallowMount(PersonnageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.personnage).toMatchObject(personnageSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        personnageServiceStub.find.resolves(personnageSample);
        const wrapper = shallowMount(PersonnageUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
