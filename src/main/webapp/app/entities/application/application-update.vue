<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jobApp.application.home.createOrEditLabel"
          data-cy="ApplicationCreateUpdateHeading"
          v-text="t$('jobApp.application.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="application.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="application.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.application.name3')" for="application-name3"></label>
            <input
              type="text"
              class="form-control"
              name="name3"
              id="application-name3"
              data-cy="name3"
              :class="{ valid: !v$.name3.$invalid, invalid: v$.name3.$invalid }"
              v-model="v$.name3.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.application.enterprise')" for="application-enterprise"></label>
            <select
              class="form-control"
              id="application-enterprise"
              data-cy="enterprise"
              name="enterprise"
              v-model="application.enterprise"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  application.enterprise && enterpriseOption.id === application.enterprise.id ? application.enterprise : enterpriseOption
                "
                v-for="enterpriseOption in enterprises"
                :key="enterpriseOption.id"
              >
                {{ enterpriseOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./application-update.component.ts"></script>
