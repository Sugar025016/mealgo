<script setup lang="ts">
defineProps<{
  modelValue: string;
  categories: string[];
}>();

const emit = defineEmits<{
  selectCategory: [category: string];
}>();
</script>

<template>
  <div class="shop-category-tabs">
    <button
      v-for="category in categories"
      :key="category"
      type="button"
      class="shop-category-tabs__item"
      :class="{ active: modelValue === category }"
      @click="emit('selectCategory', category)"
    >
      <i
        v-if="category === '推薦餐點'"
        class="bi bi-fire"
      ></i>

      <i
        v-else-if="category === '早餐'"
        class="bi bi-egg-fried"
      ></i>

      <i
        v-else-if="category === '漢堡'"
        class="bi bi-shop"
      ></i>

      <i
        v-else-if="category === '吐司'"
        class="bi bi-basket"
      ></i>

      <i
        v-else-if="category === '飲品'"
        class="bi bi-cup-straw"
      ></i>

      <i
        v-else
        class="bi bi-hand-thumbs-up"
      ></i>

      <span>{{ category }}</span>
    </button>
  </div>
</template>

<style scoped lang="scss">
.shop-category-tabs {
  display: flex;
  align-items: center;
  gap: 8px;
  overflow-x: auto;
  scrollbar-width: none;

  &::-webkit-scrollbar {
    display: none;
  }
}

.shop-category-tabs__item {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  flex: 0 0 auto;
  height: 44px;
  padding: 0 18px;
  border: 1px solid transparent;
  border-radius: 10px;
  color: #6b7280;
  background: #fff;
  font-size: 15px;
  font-weight: 700;
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease,
    border-color 0.2s ease,
    box-shadow 0.2s ease;

  i {
    font-size: 17px;
  }

  &:hover {
    color: #ff5a00;
    background: #fff7ed;
  }

  &.active {
    color: #ff5a00;
    border-color: #ff5a00;
    background: #fff7ed;
    box-shadow: 0 4px 12px rgba(255, 90, 0, 0.12);
  }
}
</style>