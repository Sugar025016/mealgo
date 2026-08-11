<script setup lang="ts">
import ShopHero from "@/components/shop/ShopHero.vue";
import ShopMenuSection from "@/components/shop/ShopMenuSection.vue";
import ShopCartLayout from "@/layouts/ShopCartLayout.vue";

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

const breadcrumbItems = [
  { label: "首頁", to: "/" },
  { label: "店家", to: "/shops" },
  { label: shop.name },
];
</script>

<template>
  <main class="shop-detail-view">
    <div class="container">
      <ShopCartLayout
        :breadcrumb-items="breadcrumbItems"
        cart-width="390px"
      >
        <div class="shop-detail-view__content">
          <ShopHero v-bind="shop" />
          <ShopMenuSection />
        </div>
      </ShopCartLayout>
    </div>
  </main>
</template>

<style scoped lang="scss">
.shop-detail-view {
  padding: 10px 0 26px;
}

.shop-detail-view__content {
  min-width: 0;
}

@media (max-width: 576px) {
  .shop-detail-view {
    padding-bottom: 32px;
  }
}
</style>