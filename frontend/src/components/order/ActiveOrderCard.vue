<script setup lang="ts">
type OrderStatus =
  | "PENDING"
  | "ACCEPTED"
  | "COOKING"
  | "ON_THE_WAY";

type Order = {
  id: number;
  orderNumber: string;
  shopName: string;
  shopImage: string;
  orderTime: string;
  products: string[];
  totalPrice: number;
  status: OrderStatus;
  estimatedTime?: string;
};

defineProps<{
  order: Order;
}>();

const emit = defineEmits<{
  view: [orderId: number];
  support: [orderId: number];
}>();

function getStatusText(status: OrderStatus) {
  const statusMap: Record<OrderStatus, string> = {
    PENDING: "等待店家確認",
    ACCEPTED: "店家已接單",
    COOKING: "餐點準備中",
    ON_THE_WAY: "外送中",
  };

  return statusMap[status];
}
</script>

<template>
  <article class="active-order-card">
    <div class="active-order-card__image-wrap">
      <img
        class="active-order-card__image"
        :src="order.shopImage"
        :alt="order.shopName"
      />

      <span class="active-order-card__tag">外送</span>
    </div>

    <div class="active-order-card__content">
      <h2 class="active-order-card__shop-name">
        {{ order.shopName }}
      </h2>

      <p>訂單編號：{{ order.orderNumber }}</p>
      <p>下單時間：{{ order.orderTime }}</p>

      <p class="active-order-card__products">
        {{ order.products.join("、") }}
      </p>

      <strong class="active-order-card__total">
        總金額：NT$ {{ order.totalPrice }}
      </strong>
    </div>

    <div class="active-order-card__side">
      <div class="active-order-card__status">
        <strong>{{ getStatusText(order.status) }}</strong>

        <span v-if="order.estimatedTime">
          預計送達 {{ order.estimatedTime }}
        </span>
      </div>

      <div class="active-order-card__actions">
        <button
          type="button"
          class="active-order-card__button active-order-card__button--primary"
          @click="emit('view', order.id)"
        >
          查看訂單
        </button>

        <button
          type="button"
          class="active-order-card__button active-order-card__button--outline"
          @click="emit('support', order.id)"
        >
          聯絡客服
        </button>
      </div>
    </div>
  </article>
</template>

<style scoped lang="scss">
.active-order-card {
  display: grid;
  grid-template-columns: 250px minmax(0, 1fr) 180px;
  gap: 24px;
  padding: 14px;
  border: 1px solid #e8e8e8;
  border-radius: 12px;
  background: #fff;
}

.active-order-card__image-wrap {
  position: relative;
  overflow: hidden;
  min-height: 170px;
  border-radius: 10px;
}

.active-order-card__image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.active-order-card__tag {
  position: absolute;
  top: 10px;
  left: 10px;
  padding: 5px 9px;
  border-radius: 6px;
  background: #fff;
  color: #ff5f07;
  font-size: 13px;
  font-weight: 700;
}

.active-order-card__content {
  align-self: center;
  min-width: 0;
}

.active-order-card__shop-name {
  margin: 0 0 10px;
  font-size: 20px;
}

.active-order-card__content p {
  margin: 6px 0;
  color: #777;
  font-size: 15px;
}

.active-order-card__products {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.active-order-card__total {
  display: block;
  margin-top: 14px;
  color: #ff5f07;
}

.active-order-card__side {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  flex-direction: column;
  gap: 16px;
  padding: 8px 0;
}

.active-order-card__status {
  text-align: right;
}

.active-order-card__status strong {
  display: block;
  color: #ff5f07;
}

.active-order-card__status span {
  display: block;
  margin-top: 6px;
  color: #888;
  font-size: 14px;
}

.active-order-card__actions {
  display: flex;
  flex-direction: column;
  gap: 9px;
  width: 100%;
}

.active-order-card__button {
  min-height: 40px;
  border-radius: 8px;
  font-weight: 700;
  cursor: pointer;
}

.active-order-card__button--primary {
  border: 1px solid #ff5f07;
  background: #ff5f07;
  color: #fff;
}

.active-order-card__button--outline {
  border: 1px solid #bbb;
  background: #fff;
  color: #555;
}

@media (max-width: 767.98px) {
  .active-order-card {
    grid-template-columns: 1fr;
  }

  .active-order-card__image-wrap {
    aspect-ratio: 16 / 9;
    min-height: auto;
  }

  .active-order-card__products {
    white-space: normal;
  }

  .active-order-card__side {
    align-items: stretch;
  }

  .active-order-card__status {
    text-align: left;
  }

  .active-order-card__actions {
    flex-direction: row;
  }

  .active-order-card__button {
    flex: 1;
  }
}
</style>