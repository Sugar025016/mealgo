<script setup lang="ts">
import type { Order } from "@/types/order.ts";

defineProps<{
  payment: Order["payment"];
}>();
</script>

<template>
  <section class="order-info-card">
    <h2>付款資訊</h2>
    <p>
      <i class="bi bi-credit-card"></i>
      <span
        >{{ payment.method
        }}<template v-if="payment.detail"
          >　{{ payment.detail }}</template
        ></span
      >
    </p>
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
//   h2 {
//     margin: 0;
//     color: #222;
//     font-size: 18px;
//     font-weight: 600;
//     white-space: nowrap;
//   }

  p {
    display: flex;
    gap: 10px;
    margin: 0;
  }

  i {
    color: #ff5f07;
  }
}
</style>
