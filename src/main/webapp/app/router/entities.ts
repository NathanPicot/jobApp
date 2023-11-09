import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Personnage = () => import('@/entities/personnage/personnage.vue');
const PersonnageUpdate = () => import('@/entities/personnage/personnage-update.vue');
const PersonnageDetails = () => import('@/entities/personnage/personnage-details.vue');

const Job = () => import('@/entities/job/job.vue');
const JobUpdate = () => import('@/entities/job/job-update.vue');
const JobDetails = () => import('@/entities/job/job-details.vue');

const Enterprise = () => import('@/entities/enterprise/enterprise.vue');
const EnterpriseUpdate = () => import('@/entities/enterprise/enterprise-update.vue');
const EnterpriseDetails = () => import('@/entities/enterprise/enterprise-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'personnage',
      name: 'Personnage',
      component: Personnage,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'personnage/new',
      name: 'PersonnageCreate',
      component: PersonnageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'personnage/:personnageId/edit',
      name: 'PersonnageEdit',
      component: PersonnageUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'personnage/:personnageId/view',
      name: 'PersonnageView',
      component: PersonnageDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job',
      name: 'Job',
      component: Job,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/new',
      name: 'JobCreate',
      component: JobUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/:jobId/edit',
      name: 'JobEdit',
      component: JobUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'job/:jobId/view',
      name: 'JobView',
      component: JobDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'enterprise',
      name: 'Enterprise',
      component: Enterprise,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'enterprise/new',
      name: 'EnterpriseCreate',
      component: EnterpriseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'enterprise/:enterpriseId/edit',
      name: 'EnterpriseEdit',
      component: EnterpriseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'enterprise/:enterpriseId/view',
      name: 'EnterpriseView',
      component: EnterpriseDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
