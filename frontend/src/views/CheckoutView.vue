<script setup lang="ts">
import CheckoutForm from "@/components/checkout/CheckoutForm.vue";
import ShopCartLayout from "@/layouts/ShopCartLayout.vue";

const shopName = "晨光早餐";

const breadcrumbItems = [
  { label: "首頁", to: "/" },
  { label: "店家", to: "/shops" },
  { label: shopName, to: "/shops/1" },
  { label: "結帳" },
];
</script>

<template>
  <main class="checkout-page">
    <div class="container">
      <ShopCartLayout :breadcrumb-items="breadcrumbItems">
        <CheckoutForm />
      </ShopCartLayout>
    </div>
  </main>
</template>

<style scoped lang="scss">
.checkout-page {
  min-height: 100vh;
  padding: 10px 0 60px;
  background: #f8f9fa;
}

@media (max-width: 576px) {
  .checkout-page {
    padding: 16px 0 40px;
  }
}
</style>