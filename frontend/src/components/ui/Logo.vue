<script setup lang="ts">
import { RouterLink } from "vue-router";
import MgLogoIcon from "@/components/icons/LogoIcon.vue";

withDefaults(
  defineProps<{
    size?: number;
    textSize?: number;
    iconColor?: string;
    mealColor?: string;
    goColor?: string;
  }>(),
  {
    size: 32,
    textSize: 28,
    iconColor: "#ff5a1f",
    mealColor: "#252525",
    goColor: "#ff5a1f",
  },
);
</script>

<template>
  <RouterLink
    to="/"
    class="mealgo-logo"
  >
    <MgLogoIcon
      :size="size"
      :color="iconColor"
    />

    <span
      class="brand-text"
      :style="{
        fontSize: `${textSize}px`,
      }"
    >
      <span :style="{ color: mealColor }">
        Meal
      </span>

      <span :style="{ color: goColor }">
        Go
      </span>
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
  display: inline-flex;
  align-items: center;
  font-weight: 800;
  line-height: 1;
  letter-spacing: -0.5px;
}
</style>