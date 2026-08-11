<script setup lang="ts">
import { ref } from "vue";
import AuthLayout from "@/layouts/AuthLayout.vue";
import MerchantLoginForm from "@/components/auth/MerchantLoginForm.vue";
import merchantBackground from "@/assets/images/auth/merchant-auth-bg.png";
import type { AuthFeature, LoginPayload } from "@/types/auth";

const loading = ref(false);
const features: AuthFeature[] = [
  { icon: "bi-clipboard-check", title: "即時接單", description: "快速掌握新訂單" },
  { icon: "bi-shop-window", title: "營運管理", description: "菜單與店舖一次管理" },
  { icon: "bi-bar-chart", title: "數據掌握", description: "清楚了解營運表現" },
];

async function handleLogin(data: LoginPayload) {
  loading.value = true;
  try {
    // TODO：await merchantAuthStore.login(data)
    console.log("商家登入", data);
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <AuthLayout :background-image="merchantBackground" headline="好生意，從這裡開始" description="集中管理訂單、菜單與營運資訊，讓每一筆訂單都能順利完成。" :features="features">
    <MerchantLoginForm :loading="loading" @submit="handleLogin" />
  </AuthLayout>
</template>
