<script setup lang="ts">
import ShopHero from "@/components/shop/ShopHero.vue";
import ShopMenuSection from "@/components/shop/ShopMenuSection.vue";
import ProductCartPanel from "@/components/cart/ProductCartPanel.vue";

const shop = {
  image: "https://picsum.photos/800/500?random=11",
  name: "晨光早餐",
  category: "早午餐",
  rating: 4.8,
  reviewCount: 1258,
  deliveryTime: "15–25 分鐘",
  deliveryFee: 40,
  distance: "1.2 公里",
  openTime: "06:00 – 14:00",
  description:
    "用心製作每一份早餐，嚴選新鮮食材，給你一天滿滿的活力！美味早午餐、手作餐點與香濃飲品，開啟美好的一天。",
  isOpen: true,
  isFavorite: false,
};
</script>

<template>
  <main class="shop-detail-view">
    <div class="shop-detail-view__container">
      <section class="shop-detail-view__content">
        <ShopHero v-bind="shop" />
        <ShopMenuSection />
      </section>

      <aside class="shop-detail-view__cart">
        <ProductCartPanel />
      </aside>
    </div>
  </main>
</template>

<style scoped lang="scss">
.shop-detail-view {
  padding: 10px 0 26px;
}

.shop-detail-view__container {
  display: grid;
  grid-template-columns: minmax(0, 1fr) 390px;
  align-items: start;
  gap: 24px;

  width: min(calc(100% - 48px), 1320px);
  margin: 0 auto;
}

.shop-detail-view__content {
  min-width: 0;
}

.shop-detail-view__cart {
  position: sticky;
  top: 96px;
  min-width: 0;
}

@media (max-width: 992px) {
  .shop-detail-view__container {
    grid-template-columns: minmax(0, 1fr);
  }

  .shop-detail-view__cart {
    position: static;
  }
}

@media (max-width: 576px) {
  .shop-detail-view {
    padding-bottom: 32px;
  }

  .shop-detail-view__container {
    width: min(calc(100% - 24px), 1320px);
    gap: 16px;
  }
}
</style>