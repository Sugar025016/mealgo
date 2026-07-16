<script setup lang="ts">
type CartItem = {
  id: number;
  name: string;
  price: number;
  qty: number;
  image: string;
  remark?: string;
};

defineProps<{
  item: CartItem;
}>();

const emit = defineEmits<{
  increase: [item: CartItem];
  decrease: [item: CartItem];
}>();
</script>

<template>
  <article class="cart-product-card">
    <img class="cart-product-card__image" :src="item.image" :alt="item.name" />

    <div class="cart-product-card__content">
      <h3 class="cart-product-card__name">
        {{ item.name }}
      </h3>

      <div class="cart-product-card__bottom">
        <p class="cart-product-card__price">NT$ {{ item.price }}</p>

        <div class="cart-product-card__qty">
          <button type="button" @click="emit('decrease', item)">
            <i class="bi bi-dash"></i>
          </button>

          <span>{{ item.qty }}</span>

          <button type="button" @click="emit('increase', item)">
            <i class="bi bi-plus"></i>
          </button>
        </div>

        <strong class="cart-product-card__total">
          NT$ {{ item.price * item.qty }}
        </strong>
      </div>
    </div>
  </article>
</template>

<style scoped lang="scss">
.cart-product-card {
  display: grid;
  grid-template-columns: 54px minmax(0, 1fr);
  gap: 12px;
  padding: 10px 0;
  border-bottom: 1px solid #f1f5f9;

  &:last-child {
    border-bottom: 0;
  }
}

.cart-product-card__image {
  width: 54px;
  height: 54px;
  border-radius: 12px;
  object-fit: cover;
  background: #f3f4f6;
}

.cart-product-card__content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-width: 0;
}

.cart-product-card__name {
  overflow: hidden;
  margin: 0 0 8px;
  color: #111827;
  font-size: 15px;
  font-weight: 800;
  line-height: 1.35;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.cart-product-card__bottom {
  display: grid;
  grid-template-columns: auto auto minmax(0, 1fr);
  align-items: center;
  gap: 10px;
}
.cart-product-card__bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
}

.cart-product-card__price {
  margin: 0;
  color: #111827;
  font-size: 13px;
  font-weight: 700;
  white-space: nowrap;
}

.cart-product-card__qty {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.cart-product-card__qty button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 26px;
  height: 26px;
  border: 1px solid #fed7aa;
  border-radius: 999px;
  color: #ff5a00;
  background: #fff7ed;
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease,
    box-shadow 0.2s ease;
}

.cart-product-card__qty button:hover {
  color: #fff;
  background: #ff5a00;
  box-shadow: 0 4px 10px rgba(255, 90, 0, 0.24);
}

.cart-product-card__qty i {
  font-size: 13px;
}

.cart-product-card__qty span {
  min-width: 16px;
  color: #111827;
  text-align: center;
  font-size: 14px;
  font-weight: 800;
}

.cart-product-card__total {
  justify-self: end;
  color: #111827;
  font-size: 14px;
  font-weight: 800;
  white-space: nowrap;
}
</style>
