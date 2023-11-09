<template>
  <div>
    <h2 id="page-heading" data-cy="ApplicationHeading">
      <span v-text="t$('jobApp.application.home.title')" id="application-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jobApp.application.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'ApplicationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-application"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jobApp.application.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && applications && applications.length === 0">
      <span v-text="t$('jobApp.application.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="applications && applications.length > 0">
      <table class="table table-striped" aria-describedby="applications">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jobApp.application.name3')"></span></th>
            <th scope="row"><span v-text="t$('jobApp.application.enterprise')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="application in applications" :key="application.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApplicationView', params: { applicationId: application.id } }">{{ application.id }}</router-link>
            </td>
            <td>{{ application.name3 }}</td>
            <td>
              <div v-if="application.enterprise">
                <router-link :to="{ name: 'EnterpriseView', params: { enterpriseId: application.enterprise.id } }">{{
                  application.enterprise.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ApplicationView', params: { applicationId: application.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ApplicationEdit', params: { applicationId: application.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(application)"
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
        <span id="jobApp.application.delete.question" data-cy="applicationDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-application-heading" v-text="t$('jobApp.application.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-application"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeApplication()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./application.component.ts"></script>
