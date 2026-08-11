<script setup lang="ts">
import type { FulfillmentType, Order } from "@/types/order.ts";

defineProps<{
  amounts: Order["amounts"];
  fulfillmentType: FulfillmentType;
}>();
</script>

<template>
  <section class="order-info-card order-amount">
    <h2>金額明細</h2>

    <dl>
      <div>
        <dt>餐點小計</dt>
        <dd>NT$ {{ amounts.subtotal }}</dd>
      </div>
      <div v-if="fulfillmentType === 'DELIVERY'">
        <dt>外送費</dt>
        <dd>NT$ {{ amounts.deliveryFee }}</dd>
      </div>
      <div v-if="amounts.discount > 0" class="order-amount__discount">
        <dt>優惠折抵</dt>
        <dd>−NT$ {{ amounts.discount }}</dd>
      </div>
      <div class="order-amount__total">
        <dt>總計</dt>
        <dd>NT$ {{ amounts.total }}</dd>
      </div>
    </dl>
  </section>
</template>

<style scoped lang="scss">
.order-info-card {
  padding: 20px;
  border: 1px solid #eee;
  border-radius: 14px;
  background: #fff;
  box-shadow: 0 4px 16px rgb(0 0 0 / 5%);

  h2 {
    margin: 0 0 14px;
    font-size: 18px;
  }
}

.order-amount {
  dl {
    margin: 0;
  }

  dl > div {
    display: flex;
    justify-content: space-between;
    gap: 20px;

    + div {
      margin-top: 12px;
    }
  }

  dt {
    font-weight: 400;
  }

  dd {
    margin: 0;
    white-space: nowrap;
  }

  &__discount {
    color: #198754;
  }

  &__total {
    margin-top: 16px !important;
    padding-top: 16px;
    border-top: 1px solid #ddd;
    font-weight: 700;

    dd {
      color: #ff5f07;
      font-size: 22px;
    }
  }
}
</style>
