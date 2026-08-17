<script setup lang="ts">
import { computed } from "vue";

interface OrderSummaryItem {
  id: number;
  name: string;
  image: string;
  quantity: number;
  price: number;
  options?: string[];
}

const props = withDefaults(
  defineProps<{
    shopId: number;
    shopName: string;
    items: OrderSummaryItem[];
    deliveryFee?: number;
    discount?: number;
    submitting?: boolean;
  }>(),
  {
    deliveryFee: 0,
    discount: 0,
    submitting: false,
  },
);

const emit = defineEmits<{
  submit: [];
}>();

const subtotal = computed(() =>
  props.items.reduce(
    (total, item) => total + item.price * item.quantity,
    0,
  ),
);

const totalPrice = computed(() =>
  Math.max(0, subtotal.value + props.deliveryFee - props.discount),
);

const submitOrder = () => {
  if (!props.items.length || props.submitting) return;

  emit("submit");
};
</script>

<template>
  <aside class="order-summary">
    <header class="order-summary__header">
      <div class="order-summary__heading">
        <h2 class="order-summary__title">訂單摘要</h2>
        <p class="order-summary__shop">
          {{ shopName }}
        </p>
      </div>

      <RouterLink
        :to="`/shops/${shopId}`"
        class="order-summary__edit"
      >
        <i class="bi bi-pencil" aria-hidden="true"></i>
        修改餐點
      </RouterLink>
    </header>

    <div v-if="items.length" class="order-summary__items">
      <article
        v-for="item in items"
        :key="item.id"
        class="order-summary__item"
      >
        <img
          :src="item.image"
          :alt="item.name"
          class="order-summary__image"
        />

        <div class="order-summary__item-content">
          <div class="order-summary__item-header">
            <h3 class="order-summary__item-name">
              {{ item.name }}
            </h3>

            <span class="order-summary__item-price">
              NT$ {{ item.price * item.quantity }}
            </span>
          </div>

          <p
            v-if="item.options?.length"
            class="order-summary__options"
          >
            {{ item.options.join("、") }}
          </p>

          <span class="order-summary__quantity">
            數量：{{ item.quantity }}
          </span>
        </div>
      </article>
    </div>

    <div v-else class="order-summary__empty">
      <i class="bi bi-bag-x" aria-hidden="true"></i>
      <p>尚未選擇餐點</p>

      <RouterLink
        :to="`/shops/${shopId}`"
        class="order-summary__back"
      >
        返回店家選購
      </RouterLink>
    </div>

    <div class="order-summary__amount">
      <div class="order-summary__amount-row">
        <span>餐點小計</span>
        <span>NT$ {{ subtotal }}</span>
      </div>

      <div class="order-summary__amount-row">
        <span>外送費</span>
        <span>
          {{ deliveryFee ? `NT$ ${deliveryFee}` : "免費" }}
        </span>
      </div>

      <div
        v-if="discount > 0"
        class="order-summary__amount-row order-summary__amount-row--discount"
      >
        <span>優惠折抵</span>
        <span>－NT$ {{ discount }}</span>
      </div>

      <div class="order-summary__total">
        <span>總金額</span>
        <strong>NT$ {{ totalPrice }}</strong>
      </div>
    </div>

    <button
      type="button"
      class="order-summary__submit"
      :disabled="!items.length || submitting"
      @click="submitOrder"
    >
      <span>
        {{ submitting ? "訂單送出中..." : "確認送出訂單" }}
      </span>

      <span v-if="!submitting">
        NT$ {{ totalPrice }}
      </span>
    </button>
  </aside>
</template>

<style scoped lang="scss">
.order-summary {
  overflow: hidden;
  width: 100%;
  border: 1px solid #eeeeee;
  border-radius: 16px;
  background: #ffffff;
  box-shadow: 0 4px 18px rgb(0 0 0 / 5%);
}

.order-summary__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
  padding: 20px;
  border-bottom: 1px solid #eeeeee;
}

.order-summary__heading {
  min-width: 0;
}

.order-summary__title {
  margin: 0;
  color: #202938;
  font-size: 20px;
  font-weight: 700;
}

.order-summary__shop {
  overflow: hidden;
  margin: 5px 0 0;
  color: #777777;
  font-size: 14px;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.order-summary__edit {
  display: inline-flex;
  flex-shrink: 0;
  align-items: center;
  gap: 5px;
  color: #ff5f07;
  font-size: 14px;
  font-weight: 600;
  text-decoration: none;

  &:hover,
  &:focus {
    color: #e95400;
    text-decoration: none;
  }
}

.order-summary__items {
  max-height: 390px;
  overflow-y: auto;
  padding: 0 20px;
}

.order-summary__item {
  display: flex;
  gap: 12px;
  padding: 18px 0;
  border-bottom: 1px solid #eeeeee;

  &:last-child {
    border-bottom: 0;
  }
}

.order-summary__image {
  flex: 0 0 68px;
  width: 68px;
  height: 68px;
  border-radius: 10px;
  object-fit: cover;
}

.order-summary__item-content {
  flex: 1;
  min-width: 0;
}

.order-summary__item-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 12px;
}

.order-summary__item-name {
  overflow: hidden;
  margin: 0;
  color: #252525;
  font-size: 15px;
  font-weight: 700;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.order-summary__item-price {
  flex-shrink: 0;
  color: #252525;
  font-size: 14px;
  font-weight: 700;
}

.order-summary__options {
  margin: 5px 0;
  color: #888888;
  font-size: 13px;
  line-height: 1.5;
}

.order-summary__quantity {
  color: #666666;
  font-size: 13px;
}

.order-summary__empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 42px 20px;
  color: #999999;
  text-align: center;

  > i {
    margin-bottom: 8px;
    font-size: 30px;
  }

  > p {
    margin: 0 0 12px;
  }
}

.order-summary__back {
  color: #ff5f07;
  font-size: 14px;
  font-weight: 600;
  text-decoration: none;

  &:hover,
  &:focus {
    color: #e95400;
    text-decoration: none;
  }
}

.order-summary__amount {
  padding: 18px 20px;
  border-top: 1px solid #eeeeee;
}

.order-summary__amount-row,
.order-summary__total {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.order-summary__amount-row {
  margin-bottom: 11px;
  color: #666666;
  font-size: 14px;
}

.order-summary__amount-row--discount {
  color: #1b8d55;
}

.order-summary__total {
  margin-top: 14px;
  padding-top: 15px;
  border-top: 1px dashed #dddddd;
  color: #202938;
  font-size: 17px;
  font-weight: 700;

  strong {
    color: #ff5f07;
    font-size: 22px;
  }
}

.order-summary__submit {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: calc(100% - 40px);
  min-height: 50px;
  margin: 0 20px 20px;
  padding: 0 18px;
  border: 0;
  border-radius: 10px;
  color: #ffffff;
  background: #ff5f07;
  font-size: 15px;
  font-weight: 700;
  cursor: pointer;
  transition:
    background-color 0.2s ease,
    opacity 0.2s ease;

  &:hover:not(:disabled) {
    background: #e95400;
  }

  &:focus-visible {
    outline: 3px solid rgb(255 95 7 / 25%);
    outline-offset: 2px;
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.55;
  }
}

@media (max-width: 992px) {
  .order-summary__items {
    max-height: none;
  }
}

@media (max-width: 576px) {
  .order-summary__header,
  .order-summary__amount {
    padding-right: 16px;
    padding-left: 16px;
  }

  .order-summary__items {
    padding: 0 16px;
  }

  .order-summary__submit {
    width: calc(100% - 32px);
    margin-right: 16px;
    margin-left: 16px;
  }
}
</style>