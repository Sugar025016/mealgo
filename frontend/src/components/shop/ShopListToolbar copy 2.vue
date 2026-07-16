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
  </div>
</template>

<style scoped lang="scss">
.shop-list-toolbar {
  position: sticky;
  top: 84px;
  z-index: 50;

  display: flex;
  align-items: center;
  gap: 18px;

  width: 100%;
  padding: 12px 22px;

  border-radius: 0 0 22px 22px;
  background: #fffaf6;
}

.shop-list-toolbar__title {
  flex: 0 0 auto;

  h1 {
    margin: 0;
    color: #111827;
    // font-size: 30px;
    font-size: 28px;
    font-weight: 900;
    line-height: 1.05;
  }

  p {
    margin: 4px 0 0;
    color: #6b7280;
    font-size: 14px;
    font-weight: 600;
  }
}

.shop-list-toolbar__categories {
  display: flex;
  align-items: center;
  gap: 8px;

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

  height: 34px;
  padding: 0 20px;

  border: 1px solid #d9dee6;
  border-radius: 999px;
  background: #ffffff;
  color: #111827;

  font-size: 14px;
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
    box-shadow: 0 6px 16px rgba(255, 90, 31, 0.22);
  }
}
</style>
