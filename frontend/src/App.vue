<script setup lang="ts">
import { useThemeStore } from "@/stores/theme";

useThemeStore();
</script>
<template>
  <RouterView :key="$route.fullPath"> </RouterView>
</template>

<style></style>
