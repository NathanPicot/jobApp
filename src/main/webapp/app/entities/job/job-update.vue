<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="jobApp.job.home.createOrEditLabel" data-cy="JobCreateUpdateHeading" v-text="t$('jobApp.job.home.createOrEditLabel')"></h2>
        <div>
          <div class="form-group" v-if="job.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="job.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.job.name1')" for="job-name1"></label>
            <input
              type="text"
              class="form-control"
              name="name1"
              id="job-name1"
              data-cy="name1"
              :class="{ valid: !v$.name1.$invalid, invalid: v$.name1.$invalid }"
              v-model="v$.name1.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.job.salary')" for="job-salary"></label>
            <input
              type="number"
              class="form-control"
              name="salary"
              id="job-salary"
              data-cy="salary"
              :class="{ valid: !v$.salary.$invalid, invalid: v$.salary.$invalid }"
              v-model.number="v$.salary.$model"
            />
          </div>
          <div class="form-group">
            <label v-text="t$('jobApp.job.task')" for="job-task"></label>
            <select
              class="form-control"
              id="job-tasks"
              data-cy="task"
              multiple
              name="task"
              v-if="job.tasks !== undefined"
              v-model="job.tasks"
            >
              <option
                v-bind:value="getSelected(job.tasks, enterpriseOption)"
                v-for="enterpriseOption in enterprises"
                :key="enterpriseOption.id"
              >
                {{ enterpriseOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('jobApp.job.personnage')" for="job-personnage"></label>
            <select class="form-control" id="job-personnage" data-cy="personnage" name="personnage" v-model="job.personnage">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="job.personnage && personnageOption.id === job.personnage.id ? job.personnage : personnageOption"
                v-for="personnageOption in personnages"
                :key="personnageOption.id"
              >
                {{ personnageOption.id }}
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
<script lang="ts" src="./job-update.component.ts"></script>
