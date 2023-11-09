import { defineComponent, provide } from 'vue';

import PersonnageService from './personnage/personnage.service';
import JobService from './job/job.service';
import EnterpriseService from './enterprise/enterprise.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('personnageService', () => new PersonnageService());
    provide('jobService', () => new JobService());
    provide('enterpriseService', () => new EnterpriseService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
