<template>
  <div>
    <h2 id="page-heading" data-cy="EnterpriseHeading">
      <span v-text="t$('jobApp.enterprise.home.title')" id="enterprise-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jobApp.enterprise.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'EnterpriseCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-enterprise"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jobApp.enterprise.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && enterprises && enterprises.length === 0">
      <span v-text="t$('jobApp.enterprise.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="enterprises && enterprises.length > 0">
      <table class="table table-striped" aria-describedby="enterprises">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jobApp.enterprise.name2')"></span></th>
            <th scope="row"><span v-text="t$('jobApp.enterprise.nbEmployee')"></span></th>
            <th scope="row"><span v-text="t$('jobApp.enterprise.international')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="enterprise in enterprises" :key="enterprise.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EnterpriseView', params: { enterpriseId: enterprise.id } }">{{ enterprise.id }}</router-link>
            </td>
            <td>{{ enterprise.name2 }}</td>
            <td>{{ enterprise.nbEmployee }}</td>
            <td>{{ enterprise.international }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EnterpriseView', params: { enterpriseId: enterprise.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'EnterpriseEdit', params: { enterpriseId: enterprise.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(enterprise)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="jobApp.enterprise.delete.question" data-cy="enterpriseDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-enterprise-heading" v-text="t$('jobApp.enterprise.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-enterprise"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeEnterprise()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./enterprise.component.ts"></script>
