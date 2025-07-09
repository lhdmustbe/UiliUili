<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: 'App',
  components: {},
  data() {
    return {}
  }
}
</script>

<style>
html, body {
  margin: 0;
  padding: 0;
  overflow-y: hidden
}

#app {
  background-color: #0d1117;
  min-height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;
}
</style>
