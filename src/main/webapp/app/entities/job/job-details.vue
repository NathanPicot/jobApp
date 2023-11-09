<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="job">
        <h2 class="jh-entity-heading" data-cy="jobDetailsHeading"><span v-text="t$('jobApp.job.detail.title')"></span> {{ job.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jobApp.job.name1')"></span>
          </dt>
          <dd>
            <span>{{ job.name1 }}</span>
          </dd>
          <dt>
            <span v-text="t$('jobApp.job.salary')"></span>
          </dt>
          <dd>
            <span>{{ job.salary }}</span>
          </dd>
          <dt>
            <span v-text="t$('jobApp.job.task')"></span>
          </dt>
          <dd>
            <span v-for="(task, i) in job.tasks" :key="task.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'EnterpriseView', params: { enterpriseId: task.id } }">{{ task.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('jobApp.job.personnage')"></span>
          </dt>
          <dd>
            <div v-if="job.personnage">
              <router-link :to="{ name: 'PersonnageView', params: { personnageId: job.personnage.id } }">{{
                job.personnage.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="job.id" :to="{ name: 'JobEdit', params: { jobId: job.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./job-details.component.ts"></script>
