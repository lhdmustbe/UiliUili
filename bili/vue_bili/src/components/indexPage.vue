<template>
  <div id="container">
    <Header class="custom-header"/>
    <Nav/>
    <MainPage/>
  </div>
</template>


<script>
import Nav from "@/components/NavPage";
import Header from "@/components/HeaderPage";
import MainPage from "@/components/MainPage";

export default {
  name: "indexPage",
  components: {
    Nav,
    Header,
    MainPage,
  }
}
</script>

<style scoped>
.custom-header {
  /*height: 155px;*/
  border: 1px solid #30363d;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  /*background-image:url("../assets/background_Header.png");*/
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}
</style>