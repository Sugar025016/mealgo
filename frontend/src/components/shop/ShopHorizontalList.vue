<script setup lang="ts">
import ShopCard from "@/components/shop/ShopCard.vue";

const shops = [
  { id: 1, image: "https://picsum.photos/300/200?random=123" },
  { id: 2, image: "https://picsum.photos/300/200?random=456" },
  { id: 3, image: "https://picsum.photos/300/200?random=101" },
  { id: 4, image: "https://picsum.photos/300/200?random=102" },
  { id: 5, image: "https://picsum.photos/300/200?random=103" },
  { id: 6, image: "https://picsum.photos/300/200?random=104" },
];
</script>

<template>
  <div class="shop-cards">
    <div v-for="shop in shops" :key="shop.id" class="shop-cards__item">
      <ShopCard :image="shop.image" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.shop-cards {
  display: flex;
  flex-wrap: nowrap;
  gap: 24px;
}

.shop-cards__item {
  flex: 0 0 320px;
}

@media (max-width: 768px) {
  .shop-cards__item {
    flex-basis: 300px;
  }

  .shop-cards {
    gap: 16px;
  }
}

@media (max-width: 576px) {
  .shop-cards__item {
    flex-basis: 280px;
  }
  .shop-cards {
    gap: 8px;
  }
}
</style>
