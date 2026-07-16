<script setup lang="ts">
import AuthLayout from "@/layouts/AuthLayout.vue";
import RegisterForm, {
  type RegisterFormData,
} from "@/components/auth/RegisterForm.vue";

async function handleRegister(data: RegisterFormData) {
  console.log("註冊資料：", data);
}

function handleGoogleRegister() {
  window.location.href =
    "http://localhost:8080/oauth2/authorization/google";
}

function handleAppleRegister() {
  console.log("Apple 註冊");
}
</script>

<template>
  <AuthLayout mode="register">
    <RegisterForm
      @submit="handleRegister"
      @google-register="handleGoogleRegister"
      @apple-register="handleAppleRegister"
    />
  </AuthLayout>
</template>