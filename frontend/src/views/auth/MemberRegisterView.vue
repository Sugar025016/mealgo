<script setup lang="ts">
import { ref } from "vue";
import AuthLayout from "@/layouts/AuthLayout.vue";
import RegisterForm from "@/components/auth/RegisterForm.vue";
import memberBackground from "@/assets/images/auth/member-auth-bg.png";
import type { AuthFeature, RegisterPayload } from "@/types/auth";

const loading = ref(false);
const features: AuthFeature[] = [
  { icon: "bi-shop", title: "精選餐廳", description: "探索附近美食" },
  { icon: "bi-lightning-charge", title: "快速抵達", description: "即時掌握進度" },
  { icon: "bi-shield-check", title: "安心可靠", description: "安全付款保障" },
];

async function handleRegister(data: RegisterPayload) {
  loading.value = true;
  try {
    // TODO：await authStore.register(data)
    console.log("會員註冊", data);
  } finally {
    loading.value = false;
  }
}

function googleRegister() {
  window.location.href = "http://localhost:8080/oauth2/authorization/google";
}

function appleRegister() {
  // TODO：換成 Apple OAuth 授權網址
  console.log("Apple 註冊");
}
</script>

<template>
  <AuthLayout :background-image="memberBackground" headline="美味，即刻抵達" description="建立 MealGo 帳號，收藏喜愛店家並輕鬆追蹤每一筆訂單。" :features="features">
    <RegisterForm :loading="loading" @submit="handleRegister" @google-register="googleRegister" @apple-register="appleRegister" />
  </AuthLayout>
</template>
