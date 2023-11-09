<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="enterprise">
        <h2 class="jh-entity-heading" data-cy="enterpriseDetailsHeading">
          <span v-text="t$('jobApp.enterprise.detail.title')"></span> {{ enterprise.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jobApp.enterprise.name2')"></span>
          </dt>
          <dd>
            <span>{{ enterprise.name2 }}</span>
          </dd>
          <dt>
            <span v-text="t$('jobApp.enterprise.nbEmployee')"></span>
          </dt>
          <dd>
            <span>{{ enterprise.nbEmployee }}</span>
          </dd>
          <dt>
            <span v-text="t$('jobApp.enterprise.international')"></span>
          </dt>
          <dd>
            <span>{{ enterprise.international }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="enterprise.id"
          :to="{ name: 'EnterpriseEdit', params: { enterpriseId: enterprise.id } }"
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

<script lang="ts" src="./enterprise-details.component.ts"></script>
