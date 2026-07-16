<script setup lang="ts">
type CompletedStatus = "COMPLETED" | "CANCELLED";

type Order = {
  id: number;
  orderNumber: string;
  shopName: string;
  shopImage: string;
  orderTime: string;
  totalPrice: number;
  status: CompletedStatus;
};

defineProps<{
  order: Order;
}>();

const emit = defineEmits<{
  view: [orderId: number];
}>();

function getStatusText(status: CompletedStatus) {
  return status === "COMPLETED" ? "已完成" : "已取消";
}
</script>

<template>
  <article class="completed-order-row">
    <img
      class="completed-order-row__image"
      :src="order.shopImage"
      :alt="order.shopName"
    />

    <h3 class="completed-order-row__shop">
      {{ order.shopName }}
    </h3>

    <span class="completed-order-row__number">
      訂單編號：{{ order.orderNumber }}
    </span>

    <time class="completed-order-row__time">
      {{ order.orderTime }}
    </time>

    <strong class="completed-order-row__price">
      NT$ {{ order.totalPrice }}
    </strong>

    <span
      class="completed-order-row__status"
      :class="{
        'completed-order-row__status--cancelled':
          order.status === 'CANCELLED',
      }"
    >
      {{ getStatusText(order.status) }}
    </span>

    <button
      type="button"
      class="completed-order-row__detail"
      aria-label="查看訂單詳情"
      @click="emit('view', order.id)"
    >
      <i class="bi bi-chevron-down"></i>
    </button>
  </article>
</template>

<style scoped lang="scss">
.completed-order-row {
  display: grid;
  grid-template-columns:
    48px
    minmax(130px, 1fr)
    minmax(180px, 1fr)
    150px
    90px
    80px
    30px;
  gap: 16px;
  align-items: center;

  min-height: 64px;
  padding: 8px 14px;
  border-bottom: 1px solid #eee;
  background: #fff;
}

.completed-order-row:last-child {
  border-bottom: 0;
}

.completed-order-row__image {
  width: 48px;
  height: 42px;
  border-radius: 7px;
  object-fit: cover;
}

.completed-order-row__shop {
  margin: 0;
  font-size: 15px;
}

.completed-order-row__number,
.completed-order-row__time {
  color: #777;
  font-size: 13px;
}

.completed-order-row__price {
  color: #ff5f07;
  font-size: 14px;
}

.completed-order-row__status {
  color: #20a74a;
  font-size: 14px;
  font-weight: 700;
}

.completed-order-row__status--cancelled {
  color: #dc3545;
}

.completed-order-row__detail {
  border: 0;
  background: transparent;
  color: #777;
  cursor: pointer;
}

@media (max-width: 767.98px) {
  .completed-order-row {
    grid-template-columns: 56px minmax(0, 1fr) auto;
    gap: 12px;
    margin-bottom: 10px;
    border: 1px solid #eee;
    border-radius: 10px;
  }

  .completed-order-row__number,
  .completed-order-row__time {
    display: none;
  }

  .completed-order-row__price {
    grid-column: 2;
  }

  .completed-order-row__status {
    grid-column: 3;
    grid-row: 1;
  }

  .completed-order-row__detail {
    grid-column: 3;
    grid-row: 2;
  }
}
</style>