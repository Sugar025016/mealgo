<script setup lang="ts">
import { RouterLink } from "vue-router";
import MgLogoIcon from "@/components/icons/LogoIcon.vue";

defineProps({
  size: {
    type: Number,
    default: 32,
  },
  textSize: {
    type: Number,
    default: 28,
  },
  color: {
    type: String,
    default: "#ff5a1f",
  },
});
</script>

<template>
  <RouterLink to="/" class="mealgo-logo">
    <MgLogoIcon :size="size" :color="color" />

    <span
      class="brand-text"
      :style="{
        color,
        fontSize: `${textSize}px`,
      }"
    >
      MealGo
    </span>
  </RouterLink>
</template>

<style scoped lang="scss">
.mealgo-logo {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
  cursor: pointer;
}

.brand-text {
  font-weight: 800;
  line-height: 1;
  letter-spacing: -0.5px;
}
</style>