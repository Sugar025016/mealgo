<script setup lang="ts">
import { ref } from "vue";
import AuthLayout from "@/layouts/AuthLayout.vue";
import LoginForm from "@/components/auth/LoginForm.vue";
import memberBackground from "@/assets/images/auth/member-auth-bg.png";
import type { AuthFeature, LoginPayload } from "@/types/auth";

const loading = ref(false);
const features: AuthFeature[] = [
  { icon: "bi-shop", title: "精選餐廳", description: "嚴選優質店家" },
  { icon: "bi-lightning-charge", title: "快速抵達", description: "即時外送追蹤" },
  { icon: "bi-shield-check", title: "安心可靠", description: "多重支付保障" },
];

async function handleLogin(data: LoginPayload) {
  loading.value = true;
  try {
    // TODO：await authStore.login(data)
    console.log("會員登入", data);
  } finally {
    loading.value = false;
  }
}

function googleLogin() {
  window.location.href = "http://localhost:8080/oauth2/authorization/google";
}

function appleLogin() {
  // TODO：換成 Apple OAuth 授權網址
  console.log("Apple 登入");
}
</script>

<template>
  <AuthLayout
    :background-image="memberBackground"
    headline="美味，即刻抵達"
    description="MealGo 結合在地餐廳與即時外送，讓你探索美味、快速下單，享受即刻抵達的幸福。"
    :features="features"
  >
    <LoginForm :loading="loading" @submit="handleLogin" @google-login="googleLogin" @apple-login="appleLogin" />
  </AuthLayout>
</template>
