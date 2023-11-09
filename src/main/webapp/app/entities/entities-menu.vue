<template>
  <div>
    <b-dropdown-item to="/personnage">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.personnage')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/application">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.application')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/job">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.job')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/enterprise">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.enterprise')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
