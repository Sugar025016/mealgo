<script setup lang="ts">
const categories = ["全部", "便當", "麵食", "早餐", "壽司", "火鍋"];

const activeCategory = "全部";
</script>

<template>
  <div class="shop-list-toolbar">
    <div class="shop-list-toolbar__title">
      <h1>所有店家</h1>
      <p>找到 128 間附近店家</p>
    </div>

    <div class="shop-list-toolbar__categories">
      <button
        v-for="category in categories"
        :key="category"
        type="button"
        class="shop-list-toolbar__category"
        :class="{ active: category === activeCategory }"
      >
        {{ category }}
      </button>
    </div>

    <!-- <button type="button" class="shop-list-toolbar__sort">
      <i class="bi bi-sliders"></i>
      <span>推薦排序</span>
      <i class="bi bi-chevron-down"></i>
    </button> -->
  </div>
</template>

<style scoped lang="scss">
.shop-list-toolbar {
  display: flex;
  align-items: center;
  gap: 24px;

  width: 100%;
  padding: 22px 28px;
  margin-bottom: 24px;

  border-radius: 24px;
  background: #ffffff;
  box-shadow: 0 10px 28px rgba(17, 24, 39, 0.07);
}

.shop-list-toolbar__title {
  flex: 0 0 auto;

  h1 {
    margin: 0;
    color: #111827;
    font-size: 34px;
    font-weight: 900;
    line-height: 1.1;
  }

  p {
    margin: 6px 0 0;
    color: #6b7280;
    font-size: 16px;
    font-weight: 600;
  }
}

.shop-list-toolbar__categories {
  display: flex;
  align-items: center;
  gap: 12px;

  flex: 1;
  min-width: 0;

  overflow-x: auto;
  white-space: nowrap;

  &::-webkit-scrollbar {
    display: none;
  }
}

.shop-list-toolbar__category {
  flex: 0 0 auto;

  height: 42px;
  padding: 0 24px;

  border: 1px solid #d9dee6;
  border-radius: 999px;
  background: #ffffff;
  color: #111827;

  font-size: 16px;
  font-weight: 800;
  cursor: pointer;

  transition:
    background-color 0.2s ease,
    color 0.2s ease,
    border-color 0.2s ease,
    box-shadow 0.2s ease;

  &:hover {
    border-color: #ffb799;
    background: #fff4ef;
    color: #ff5a1f;
  }

  &.active {
    border-color: #ff5a1f;
    background: #ff5a1f;
    color: #ffffff;
    box-shadow: 0 8px 20px rgba(255, 90, 31, 0.24);
  }
}

.shop-list-toolbar__sort {
  flex: 0 0 auto;

  height: 42px;
  padding: 0 18px;

  border: 1px solid #d9dee6;
  border-radius: 999px;
  background: #ffffff;
  color: #111827;

  display: inline-flex;
  align-items: center;
  gap: 10px;

  font-size: 16px;
  font-weight: 800;
  cursor: pointer;

  &:hover {
    border-color: #ffb799;
    background: #fff4ef;
    color: #ff5a1f;
  }
}

@media (max-width: 1200px) {
  .shop-list-toolbar {
    align-items: flex-start;
    flex-direction: column;
    gap: 16px;
  }

  .shop-list-toolbar__categories {
    width: 100%;
  }
}

@media (max-width: 576px) {
  .shop-list-toolbar {
    padding: 18px;
    border-radius: 20px;
  }

  .shop-list-toolbar__title h1 {
    font-size: 28px;
  }

  .shop-list-toolbar__title p {
    font-size: 14px;
  }

  .shop-list-toolbar__category,
  .shop-list-toolbar__sort {
    height: 38px;
    font-size: 14px;
  }
}
</style>