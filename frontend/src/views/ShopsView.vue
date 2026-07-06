<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
import ShopFilterPanel from "@/components/shop/ShopFilterPanel.vue";
import ShopList from "@/components/shop/ShopList.vue";
import ShopListToolbar from "@/components/shop/ShopListToolbar.vue";

const isFilterStopped = ref(false);

function handleScroll() {
  const footer = document.querySelector(".mg-footer");

  if (!footer) return;

  const footerTop = footer.getBoundingClientRect().top;
  const filterHeight = window.innerHeight - 120;

  isFilterStopped.value = footerTop <= filterHeight + 120;
}

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
  handleScroll();
});

onBeforeUnmount(() => {
  window.removeEventListener("scroll", handleScroll);
});
</script>

<template>
  <main class="shops-view">
    <div class="shops-view__container">
      <aside
        class="shops-view__sidebar"
        :class="{ 'is-stopped': isFilterStopped }"
      >
        <ShopFilterPanel />
      </aside>

      <section class="shops-view__content">
        <div class="shops-view__toolbar-sticky">
          <ShopListToolbar />
        </div>

        <div class="shops-view__shops-list">
          <ShopList />
        </div>
      </section>
    </div>
  </main>
</template>

<style scoped lang="scss">
.shops-view {
  --shops-max-width: 1440px;
  --shops-filter-width: 260px;
  --shops-gap: 22px;
  --shops-side-space: 32px;

  position: relative;
  min-height: 100vh;
  background: #fffaf6;
}

.shops-view__container {
  width: min(100% - 64px, var(--shops-max-width));
  margin-inline: auto;
  padding: 12px 0 10px;
}

/* 左側篩選固定 */
.shops-view__sidebar {
  position: fixed;
  top: 84px;

  left: max(
    var(--shops-side-space),
    calc((100vw - var(--shops-max-width)) / 2)
  );

  width: var(--shops-filter-width);
  z-index: 20;
}

/* 碰到 Footer 前停住 */
.shops-view__sidebar.is-stopped {
  position: absolute;
  top: auto;
  bottom: 32px;
}

/* 右側內容避開左側篩選 */
.shops-view__content {
  padding-left: calc(var(--shops-filter-width) + var(--shops-gap));
  min-width: 0;
}

/* 右側分類工具列固定 */
.shops-view__toolbar-sticky {
  position: sticky;
  top: 72px;
  z-index: 50;

//   padding: 0 0 14px;
  background: #fffaf6;
}

.shops-view__shops-list {
  padding: 0 8px;
}

@media (max-width: 992px) {
  .shops-view__container {
    width: 100%;
    padding: 20px;
  }

  .shops-view__sidebar {
    display: none;
  }

  .shops-view__content {
    padding-left: 0;
  }

  .shops-view__toolbar-sticky {
    top: 72px;
    padding-bottom: 12px;
  }

  .shops-view__shops-list {
    padding: 0;
  }
}
</style>