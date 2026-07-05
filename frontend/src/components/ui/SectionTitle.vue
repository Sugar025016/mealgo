<script setup lang="ts">
defineProps<{
  title: string;
  icon?: string;
  more?: boolean;
}>();
</script>

<template>
  <div class="mg-section-title">
    <div class="mg-section-title__left">
      <i v-if="icon" :class="['bi', icon]"></i>
      <h2>{{ title }}</h2>
    </div>

    <button v-if="more" class="mg-section-title__more">
      查看全部
      <i class="bi bi-chevron-right"></i>
    </button>
  </div>
</template>
<style scoped lang="scss">
.mg-section-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //   margin-bottom: 20px;
}

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
  border: 0;
  background: transparent;
  color: var(--mg-primary);
  font-weight: 700;
  cursor: pointer;
  font-size: 0.95rem;

  display: flex;
  align-items: center;
  gap: 4px;
  //   .button{
  padding: 0;
  //   }
}
</style>
