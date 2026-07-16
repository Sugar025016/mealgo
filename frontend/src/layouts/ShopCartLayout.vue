<script setup lang="ts">
import Breadcrumb from "@/components/ui/Breadcrumb.vue";
import ProductCartPanel from "@/components/cart/ProductCartPanel.vue";

type BreadcrumbItem = {
  label: string;
  to?: string;
};

withDefaults(
  defineProps<{
    breadcrumbItems?: BreadcrumbItem[];
    cartWidth?: string;
    stickyTop?: string;
  }>(),
  {
    breadcrumbItems: () => [],
    cartWidth: "420px",
    stickyTop: "86px",
  },
);
</script>

<template>
  <div
    class="shop-cart-layout"
    :style="{
      '--shop-cart-width': cartWidth,
      '--shop-cart-sticky-top': stickyTop,
    }"
  >
    <!-- 左側：Breadcrumb + 頁面主要內容 -->
    <section class="shop-cart-layout__main">
      <Breadcrumb
        v-if="breadcrumbItems.length"
        class="shop-cart-layout__breadcrumb"
        :items="breadcrumbItems"
      />

      <div class="shop-cart-layout__body">
        <slot />
      </div>
    </section>

    <!-- 右側：購物車 -->
    <aside class="shop-cart-layout__cart">
      <slot name="cart">
        <ProductCartPanel />
      </slot>
    </aside>
  </div>
</template>

<style scoped lang="scss">
.shop-cart-layout {
  display: grid;
//   grid-template-columns:
//     minmax(0, 1fr)
//     var(--shop-cart-width);
  grid-template-columns:
    minmax(0, 1fr)
    390px;
  align-items: start;
  gap: 20px;
  width: 100%;
}

.shop-cart-layout__main {
  display: flex;
  min-width: 0;
  flex-direction: column;
  gap: 20px;
}

.shop-cart-layout__breadcrumb {
  margin: 0;
}

.shop-cart-layout__body {
  min-width: 0;
}

.shop-cart-layout__cart {
  position: sticky;
  top: var(--shop-cart-sticky-top);
  min-width: 0;
  align-self: start;
}

@media (max-width: 992px) {
  .shop-cart-layout {
    grid-template-columns: minmax(0, 1fr);
  }

  .shop-cart-layout__cart {
    position: static;
  }
}

@media (max-width: 576px) {
  .shop-cart-layout {
    gap: 16px;
  }

  .shop-cart-layout__main {
    gap: 16px;
  }
}
</style>