<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="application">
        <h2 class="jh-entity-heading" data-cy="applicationDetailsHeading">
          <span v-text="t$('jobApp.application.detail.title')"></span> {{ application.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jobApp.application.name3')"></span>
          </dt>
          <dd>
            <span>{{ application.name3 }}</span>
          </dd>
          <dt>
            <span v-text="t$('jobApp.application.enterprise')"></span>
          </dt>
          <dd>
            <div v-if="application.enterprise">
              <router-link :to="{ name: 'EnterpriseView', params: { enterpriseId: application.enterprise.id } }">{{
                application.enterprise.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="application.id"
          :to="{ name: 'ApplicationEdit', params: { applicationId: application.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./application-details.component.ts"></script>
