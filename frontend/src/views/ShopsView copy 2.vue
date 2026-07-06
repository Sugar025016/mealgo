<script setup lang="ts">
import ShopFilterPanel from "@/components/shop/ShopFilterPanel.vue";
import ShopList from "@/components/shop/ShopList.vue";
</script>

<template>
  <main class="shops-view">
    <aside class="shops-view__filter">
      <ShopFilterPanel />
    </aside>

    <section class="shops-view__content">
      <ShopList />
    </section>
  </main>
</template>

<style scoped lang="scss">
.shops-view {
  min-height: 100vh;
  background: #fffaf6;
  padding-top: 32px;
}

/* 左側固定不動 */
.shops-view__filter {
  position: fixed;
  left: 48px;
  top: 120px;
  width: 280px;
  z-index: 20;
}

/* 右側內容要避開左側固定區 */
.shops-view__content {
  margin-left: 360px;
  padding-right: 48px;
  padding-bottom: 80px;
}

/* 平板以下先隱藏左側篩選 */
@media (max-width: 992px) {
  .shops-view__filter {
    display: none;
  }

  .shops-view__content {
    margin-left: 0;
    padding: 20px;
  }
}


// .shops-view {
//   min-height: 100vh;
//   background: #fffaf6;
// }

// .shops-view__container {
//   display: grid;
//   grid-template-columns: 280px 1fr;
//   gap: 28px;
//   align-items: start;

//   width: min(100% - 64px, 1440px);
//   margin-inline: auto;
//   padding: 32px 0 80px;
// }

// .shops-view__sidebar {
//   position: sticky;
//   top: 104px;
//   align-self: start;
// }

// .shops-view__main {
//   min-width: 0;
// }
</style>