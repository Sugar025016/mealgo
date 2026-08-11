<script setup lang="ts">
import HorizontalScroll from "@/components/ui/HorizontalScroll.vue";

const categories = ["全部", "便當", "麵食", "早餐", "壽司", "火鍋"];

const activeCategory = "全部";
</script>

<template>
  <div class="shop-list-toolbar">
    <div class="shop-list-toolbar__title">
      <h1>所有店家</h1>
      <p>找到 128 間附近店家</p>
    </div>

    <HorizontalScroll
      class="shop-list-toolbar__scroll"
      :card-width="96"
      :keep-cards="1"
    >
      <button
        v-for="category in categories"
        :key="category"
        type="button"
        class="shop-list-toolbar__category"
        :class="{ active: category === activeCategory }"
      >
        {{ category }}
      </button>
    </HorizontalScroll>
  </div>
</template>

<style scoped lang="scss">
.shop-list-toolbar {
  position: sticky;
  top: 72px; // Header 固定時，這裡要等於 Header 高度
  z-index: 50;

  display: flex;
  align-items: center;
  gap: 18px;

  width: 100%;
  padding: 12px 22px 10px;

  border-radius: 0 0 22px 22px;
  background: #fffaf6;
}

.shop-list-toolbar__title {
  flex: 0 0 auto;

  h1 {
    margin: 0;
    color: #111827;
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

.shop-list-toolbar__scroll {
  flex: 1;
  min-width: 0;
}

/* 修改 HorizontalScroll 裡面的內容 */
.shop-list-toolbar__scroll :deep(.mg-scroll-content) {
  gap: 8px;
  //   padding: 6px 8px;
}

/* 箭頭縮小一點 */
.shop-list-toolbar__scroll :deep(.mg-scroll-arrow) {
  width: 34px;
  height: 34px;
  font-size: 0.85rem;
}

.shop-list-toolbar__scroll :deep(.mg-scroll-arrow.left) {
  left: 4px;
}

.shop-list-toolbar__scroll :deep(.mg-scroll-arrow.right) {
  right: 4px;
}

.shop-list-toolbar__category {
  flex: 0 0 auto;

  height: 34px;
  min-width: 88px;
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

@media (max-width: 768px) {
  .shop-list-toolbar {
    top: 64px;
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
    padding: 12px 16px;
  }

  .shop-list-toolbar__title h1 {
    font-size: 24px;
  }

  .shop-list-toolbar__scroll {
    width: 100%;
  }

  .shop-list-toolbar__scroll :deep(.mg-scroll-content) {
    // padding: 6px 38px;
  }

  .shop-list-toolbar__category {
    min-width: 78px;
    height: 32px;
    padding: 0 16px;
    font-size: 13px;
  }
}
</style>
