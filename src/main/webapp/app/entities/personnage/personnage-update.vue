<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="jobApp.personnage.home.createOrEditLabel"
          data-cy="PersonnageCreateUpdateHeading"
          v-text="t$('jobApp.personnage.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="personnage.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="personnage.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.personnage.name')" for="personnage-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="personnage-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.personnage.age')" for="personnage-age"></label>
            <input
              type="number"
              class="form-control"
              name="age"
              id="personnage-age"
              data-cy="age"
              :class="{ valid: !v$.age.$invalid, invalid: v$.age.$invalid }"
              v-model.number="v$.age.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.personnage.birthday')" for="personnage-birthday"></label>
            <div class="d-flex">
              <input
                id="personnage-birthday"
                data-cy="birthday"
                type="datetime-local"
                class="form-control"
                name="birthday"
                :class="{ valid: !v$.birthday.$invalid, invalid: v$.birthday.$invalid }"
                :value="convertDateTimeFromServer(v$.birthday.$model)"
                @change="updateInstantField('birthday', $event)"
              />
            </div>
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
<script lang="ts" src="./personnage-update.component.ts"></script>
