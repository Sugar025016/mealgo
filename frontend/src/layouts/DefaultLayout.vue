<script setup lang="ts">
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
</script>

<template>
  <Header />

  <main class="default-layout">
    <RouterView />
  </main>

  <Footer />
</template>

<style scoped lang="scss">
.default-layout {
  min-height: 100vh;
  padding-top: var(--header-height);
}
</style>
