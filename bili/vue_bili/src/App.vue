<template>
  <IndexPage/>
</template>

<script>
import IndexPage from "@/components/indexPage";

export default {
  name: 'App',
  components: {
    IndexPage
  }
}
</script>

<style>
html, body {
  margin: 0;
  padding: 0;

}

#app {
  background-color: #0d1117;
  min-height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;
}
</style>
