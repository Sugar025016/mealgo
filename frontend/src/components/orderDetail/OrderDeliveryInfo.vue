<script setup lang="ts">
import type { FulfillmentType, Order } from "@/types/order";

defineProps<{
  fulfillmentType: FulfillmentType;
  shop: Order["shop"] & {
    phone?: string;
  };
  receiver: Order["receiver"];
  createdAt: string;
}>();
</script>

<template>
  <section class="order-info-card">
    <header class="order-info-card__header">
      <h2>
        {{ fulfillmentType === "DELIVERY" ? "配送資訊" : "取餐資訊" }}
      </h2>

      <span
        class="order-info-card__type"
        :class="{
          'order-info-card__type--pickup':
            fulfillmentType === 'PICKUP',
        }"
      >
        <i
          class="bi"
          :class="
            fulfillmentType === 'DELIVERY'
              ? 'bi-scooter'
              : 'bi-bag-check'
          "
        ></i>

        {{
          fulfillmentType === "DELIVERY"
            ? "外送訂單"
            : "自取訂單"
        }}
      </span>
    </header>

    <!-- 外送資訊 -->
    <div
      v-if="fulfillmentType === 'DELIVERY'"
      class="order-info-card__content"
    >
      <div class="order-info-card__row">
        <i class="bi bi-geo-alt"></i>

        <span>{{ receiver.address }}</span>
      </div>

      <div class="order-info-card__row">
        <i class="bi bi-person"></i>

        <span>
          {{ receiver.name }}
          <span class="order-info-card__space"></span>
          {{ receiver.phone }}
        </span>
      </div>

      <div class="order-info-card__row">
        <i class="bi bi-clock"></i>

        <span>
          <span class="order-info-card__label">訂購時間</span>
          {{ createdAt }}
        </span>
      </div>
    </div>

    <!-- 自取資訊 -->
    <div
      v-else
      class="order-info-card__content"
    >
      <div class="order-info-card__row">
        <i class="bi bi-geo-alt"></i>

        <span>{{ shop.address }}</span>
      </div>

      <div
        v-if="shop.phone"
        class="order-info-card__row"
      >
        <i class="bi bi-telephone"></i>

        <span>{{ shop.phone }}</span>
      </div>

      <div class="order-info-card__row">
        <i class="bi bi-clock"></i>

        <span>
          <span class="order-info-card__label">訂購時間</span>
          {{ createdAt }}
        </span>
      </div>
    </div>
  </section>
</template>

<style scoped lang="scss">
.order-info-card {
  padding: 22px;
  border: 1px solid #eee;
  border-radius: 14px;
  background: #fff;
  box-shadow: 0 4px 16px rgb(0 0 0 / 5%);
}

.order-info-card__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 20px;

  h2 {
    margin: 0;
    color: #222;
    font-size: 18px;
    font-weight: 700;
    white-space: nowrap;
  }
  
}

.order-info-card__type {
  display: inline-flex;
  flex: 0 0 auto;
  align-items: center;
  justify-content: center;
  gap: 7px;

  min-height: 32px;
  padding: 6px 13px;

  border-radius: 999px;
  background: #ff5f07;
  color: #fff;

  font-size: 14px;
  font-weight: 600;
  line-height: 1;
  white-space: nowrap;

  i {
    color: inherit;
    font-size: 15px;
  }
}

.order-info-card__type--pickup {
  background: #495057;
}

.order-info-card__content {
  display: grid;
  gap: 16px;
}

.order-info-card__row {
  display: grid;
  grid-template-columns: 20px minmax(0, 1fr);
  align-items: start;
  gap: 10px;

  color: #3f3f3f;
  font-size: 1rem;
  line-height: 1.6;

  > i {
    margin-top: 2px;
    color: #ff5f07;
    font-size: 18px;
    line-height: 1.4;
  }
}

.order-info-card__label {
  margin-right: 12px;
  color: #555;
  font-weight: 500;
}

.order-info-card__space {
  display: inline-block;
  width: 8px;
}

@media (max-width: 575.98px) {
  .order-info-card {
    padding: 18px;
  }

  .order-info-card__header {
    margin-bottom: 16px;
  }

  .order-info-card__type {
    padding: 6px 11px;
    font-size: 14px;
  }

  .order-info-card__row {
    font-size: 14px;
  }
}
</style>