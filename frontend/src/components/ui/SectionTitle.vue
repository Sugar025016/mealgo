<script setup lang="ts">
import { RouterLink } from "vue-router";
defineProps<{
  title: string;
  icon?: string;
  more?: boolean;
}>();
</script>

<template>
  <!-- <RouterLink to="/" class="mg-section-title"> -->
  <div class="mg-section-title">
    <div class="mg-section-title__left">
      <i v-if="icon" :class="['bi', icon]"></i>
      <h2>{{ title }}</h2>
    </div>

    <RouterLink v-if="more" to="/shops" class="mg-section-title__more">
      查看全部
      <i class="bi bi-chevron-right"></i>
    </RouterLink>
  </div>
</template>
<style scoped lang="scss">
.mg-section-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //   margin-bottom: 20px;
}

//   display: inline-flex;
//   align-items: center;
//   gap: 8px;
//   text-decoration: non

.mg-section-title__left {
  display: flex;
  align-items: center;
  gap: 10px;

  h2 {
    margin: 0;
    font-size: 1.5rem;
    font-weight: 900;
    color: #222;
  }

  i {
    color: var(--mg-primary);
    font-size: 1.35rem;
  }
}

.mg-section-title__more {
  display: inline-flex;
  align-items: center;
  gap: 4px;

  border: 0;
  background: transparent;
  color: var(--mg-primary, #ff5a1f);
  font-weight: 700;
  text-decoration: none;
  cursor: pointer;
}
</style>
