/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Personnage from './personnage.vue';
import PersonnageService from './personnage.service';
import AlertService from '@/shared/alert/alert.service';

type PersonnageComponentType = InstanceType<typeof Personnage>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Personnage Management Component', () => {
    let personnageServiceStub: SinonStubbedInstance<PersonnageService>;
    let mountOptions: MountingOptions<PersonnageComponentType>['global'];

    beforeEach(() => {
      personnageServiceStub = sinon.createStubInstance<PersonnageService>(PersonnageService);
      personnageServiceStub.retrieve.resolves({ headers: {} });

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
          personnageService: () => personnageServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        personnageServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Personnage, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(personnageServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.personnages[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: PersonnageComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Personnage, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        personnageServiceStub.retrieve.reset();
        personnageServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        personnageServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removePersonnage();
        await comp.$nextTick(); // clear components

        // THEN
        expect(personnageServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(personnageServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
