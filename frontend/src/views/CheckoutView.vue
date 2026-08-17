<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";

import CheckoutForm from "@/components/checkout/CheckoutForm.vue";
import OrderSummary from "@/components/checkout/OrderSummary.vue";

const router = useRouter();

const shop = {
  id: 1,
  name: "晨光早餐",
};

const cartItems = ref([
  {
    id: 1,
    name: "花生培根蛋吐司",
    image: "https://picsum.photos/160/160?random=21",
    quantity: 2,
    price: 95,
    options: ["吐司烤酥", "不要洋蔥"],
  },
  {
    id: 2,
    name: "香煎雞腿早午餐",
    image: "https://picsum.photos/160/160?random=22",
    quantity: 1,
    price: 180,
    options: ["荷包蛋全熟"],
  },
]);

const isSubmitting = ref(false);

const submitOrder = async () => {
  if (isSubmitting.value || !cartItems.value.length) return;

  isSubmitting.value = true;

  try {
    // 之後改成建立訂單 API
    const orderId = 18;

    await router.push(`/OrderDetail/${orderId}`);
  } catch (error) {
    console.error("送出訂單失敗：", error);
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<template>
  <main class="checkout-page">
    <div class="checkout-page__container">
      <section class="checkout-page__form">
        <CheckoutForm />
      </section>

      <aside class="checkout-page__summary">
        <OrderSummary
          :shop-id="shop.id"
          :shop-name="shop.name"
          :items="cartItems"
          :delivery-fee="40"
          :discount="0"
          :submitting="isSubmitting"
          @submit="submitOrder"
        />
      </aside>
    </div>
  </main>
</template>

<style scoped lang="scss">
.checkout-page {
  min-height: 100vh;
  padding: 24px 0 60px;
  background: #f8f9fa;
}

.checkout-page__container {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(320px, 390px);
  align-items: start;
  gap: 22px;
  width: min(calc(100% - 48px), 1320px);
  margin: 0 auto;
}

.checkout-page__form {
  min-width: 0;
}

.checkout-page__summary {
  position: sticky;
  top: 96px;
  min-width: 0;
}

@media (max-width: 992px) {
  .checkout-page__container {
    grid-template-columns: minmax(0, 1fr);
  }

  .checkout-page__summary {
    position: static;
  }
}

@media (max-width: 576px) {
  .checkout-page {
    padding: 16px 0 40px;
  }

  .checkout-page__container {
    width: min(calc(100% - 24px), 1320px);
    gap: 16px;
  }
}
</style>