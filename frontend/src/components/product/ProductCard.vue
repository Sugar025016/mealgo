<script setup lang="ts">
type Product = {
  id: number;
  name: string;
  description: string;
  price: number;
  image: string;
  category: string;
};

defineProps<{
  product: Product;
}>();

const emit = defineEmits<{
  addToCart: [product: Product];
}>();
</script>

<template>
  <article class="shop-product-card">
    <img
      class="shop-product-card__image"
      :src="product.image"
      :alt="product.name"
    />

    <div class="shop-product-card__body">
      <div class="shop-product-card__top">
        <div>
          <h3 class="shop-product-card__name">
            {{ product.name }}
          </h3>

          <p class="shop-product-card__desc">
            {{ product.description }}
          </p>
        </div>
      </div>

      <div class="shop-product-card__bottom">
        <strong class="shop-product-card__price">
          NT$ {{ product.price }}
        </strong>

        <button
          type="button"
          class="shop-product-card__add"
          @click="emit('addToCart', product)"
        >
          <i class="bi bi-plus-lg"></i>
          加入
        </button>
      </div>
    </div>
  </article>
</template>

<style scoped lang="scss">
.shop-product-card {
  display: grid;
  grid-template-columns: 120px 1fr;
  min-height: 96px;
  overflow: hidden;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  background: #fff;
  box-shadow: 0 4px 12px rgba(15, 23, 42, 0.04);
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease,
    transform 0.2s ease;

  &:hover {
    border-color: #fed7aa;
    box-shadow: 0 8px 20px rgba(15, 23, 42, 0.08);
  }
}

.shop-product-card__image {
  width: 120px;
  //   height: 96px;

  aspect-ratio: 16/14;
  object-fit: cover;
}

.shop-product-card__body {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-width: 0;
  padding: 10px 12px;
}

.shop-product-card__top {
  display: flex;
  justify-content: space-between;
  gap: 8px;
}

.shop-product-card__name {
  margin: 0 0 4px;
  color: #111827;
  font-size: 15px;
  font-weight: 800;
  line-height: 1.35;
}

.shop-product-card__desc {
  display: -webkit-box;
  overflow: hidden;
  margin: 0;
  color: #6b7280;
  font-size: 12px;
  line-height: 1.4;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.shop-product-card__bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  margin-top: 8px;
}

.shop-product-card__price {
  color: #ff5a00;
  font-size: 15px;
  font-weight: 800;
}

.shop-product-card__add {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  height: 32px;
  padding: 0 12px;
  border: 1px solid #ff5a00;
  border-radius: 8px;
  color: #ff5a00;
  background: #fff;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease,
    box-shadow 0.2s ease;

  &:hover {
    color: #fff;
    background: #ff5a00;
    box-shadow: 0 4px 10px rgba(255, 90, 0, 0.24);
  }

  &:active {
    background: #ea580c;
  }

  i {
    font-size: 13px;
  }
}

@media (max-width: 576px) {
  .shop-product-card {
    grid-template-columns: 100px 1fr;
  }

  .shop-product-card__image {
    width: 100px;
    height: 96px;
  }
}
</style>
