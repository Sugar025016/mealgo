<script setup lang="ts">
import ShopFilterPanel from "@/components/shop/ShopFilterPanel.vue";
import ShopList from "@/components/shop/ShopList.vue";
</script>

<template>
  <main class="shops-view">
    <div class="shops-view__container">
      <ShopFilterPanel />

      <section class="shops-view__main">
        <div class="shops-view__header">
          <div>
            <h1>所有店家</h1>
            <p>找到附近可外送的美食店家</p>
          </div>

          <button type="button" class="shops-view__filter-btn">
            <i class="bi bi-sliders"></i>
            篩選
          </button>
        </div>

        <div class="shops-view__sort">
          <button type="button" class="active">推薦排序</button>
          <button type="button">評價最高</button>
          <button type="button">距離最近</button>
          <button type="button">外送費最低</button>
        </div>

        <div class="shops-view__category">
          <button type="button" class="active">全部</button>
          <button type="button">便當</button>
          <button type="button">麵食</button>
          <button type="button">早餐</button>
          <button type="button">壽司</button>
          <button type="button">火鍋</button>
          <button type="button">披薩</button>
          <button type="button">飲料</button>
        </div>

        <ShopList />
      </section>
    </div>
  </main>
</template>

<style scoped lang="scss">
.shops-view {
  min-height: 100vh;
  padding: 32px;
  background: #fffaf6;
}

.shops-view__container {
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 28px;
  width: min(100%, 1440px);
  margin-inline: auto;
}

.shops-view__main {
  min-width: 0;
}

.shops-view__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 22px;

  h1 {
    margin: 0;
    font-size: 36px;
    font-weight: 900;
    color: #1f2937;
  }

  p {
    margin: 8px 0 0;
    color: #6b7280;
  }
}

.shops-view__filter-btn {
  display: none;
  align-items: center;
  gap: 8px;
  padding: 11px 18px;
  border: 1px solid #ffd6c2;
  border-radius: 14px;
  background: #ffffff;
  color: #1f2937;
  font-weight: 800;
}

.shops-view__sort,
.shops-view__category {
  display: flex;
  gap: 12px;
  overflow-x: auto;
  padding-bottom: 8px;
  margin-bottom: 16px;
}

.shops-view__sort button,
.shops-view__category button {
  flex: 0 0 auto;
  padding: 10px 18px;
  border: 1px solid #eeeeee;
  border-radius: 999px;
  background: #ffffff;
  color: #374151;
  font-weight: 800;
  cursor: pointer;
}

.shops-view__sort button.active,
.shops-view__category button.active {
  border-color: #ff5a1f;
  background: #ff5a1f;
  color: #ffffff;
  box-shadow: 0 8px 20px rgba(255, 90, 31, 0.22);
}

@media (max-width: 992px) {
  .shops-view {
    padding: 20px;
  }

  .shops-view__container {
    grid-template-columns: 1fr;
  }

  .shops-view__filter-btn {
    display: inline-flex;
  }
}

@media (max-width: 576px) {
  .shops-view {
    padding: 16px;
  }

  .shops-view__header {
    align-items: flex-start;
    gap: 12px;
  }

  .shops-view__header h1 {
    font-size: 30px;
  }
}
</style>
