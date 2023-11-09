/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Enterprise from './enterprise.vue';
import EnterpriseService from './enterprise.service';
import AlertService from '@/shared/alert/alert.service';

type EnterpriseComponentType = InstanceType<typeof Enterprise>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Enterprise Management Component', () => {
    let enterpriseServiceStub: SinonStubbedInstance<EnterpriseService>;
    let mountOptions: MountingOptions<EnterpriseComponentType>['global'];

    beforeEach(() => {
      enterpriseServiceStub = sinon.createStubInstance<EnterpriseService>(EnterpriseService);
      enterpriseServiceStub.retrieve.resolves({ headers: {} });

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
          enterpriseService: () => enterpriseServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        enterpriseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Enterprise, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(enterpriseServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.enterprises[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: EnterpriseComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Enterprise, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        enterpriseServiceStub.retrieve.reset();
        enterpriseServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        enterpriseServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeEnterprise();
        await comp.$nextTick(); // clear components

        // THEN
        expect(enterpriseServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(enterpriseServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
