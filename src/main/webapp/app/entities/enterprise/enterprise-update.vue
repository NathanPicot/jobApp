<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jobApp.enterprise.home.createOrEditLabel"
          data-cy="EnterpriseCreateUpdateHeading"
          v-text="t$('jobApp.enterprise.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="enterprise.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="enterprise.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.enterprise.name2')" for="enterprise-name2"></label>
            <input
              type="text"
              class="form-control"
              name="name2"
              id="enterprise-name2"
              data-cy="name2"
              :class="{ valid: !v$.name2.$invalid, invalid: v$.name2.$invalid }"
              v-model="v$.name2.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.enterprise.nbEmployee')" for="enterprise-nbEmployee"></label>
            <input
              type="number"
              class="form-control"
              name="nbEmployee"
              id="enterprise-nbEmployee"
              data-cy="nbEmployee"
              :class="{ valid: !v$.nbEmployee.$invalid, invalid: v$.nbEmployee.$invalid }"
              v-model.number="v$.nbEmployee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.enterprise.international')" for="enterprise-international"></label>
            <input
              type="checkbox"
              class="form-check"
              name="international"
              id="enterprise-international"
              data-cy="international"
              :class="{ valid: !v$.international.$invalid, invalid: v$.international.$invalid }"
              v-model="v$.international.$model"
            />
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
<script lang="ts" src="./enterprise-update.component.ts"></script>
