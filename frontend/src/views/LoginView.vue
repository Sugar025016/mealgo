<script setup lang="ts">
import AuthLayout from "@/layouts/AuthLayout.vue";
import LoginForm from "@/components/auth/LoginForm.vue";

type LoginData = {
  email: string;
  password: string;
  rememberMe: boolean;
};

function handleLogin(data: LoginData) {
  console.log(data);
}

function handleGoogleLogin() {
  window.location.href =
    "http://localhost:8080/oauth2/authorization/google";
}

function handleAppleLogin() {
  console.log("Apple 登入");
}
</script>

<template>
  <AuthLayout mode="login">
    <LoginForm
      @submit="handleLogin"
      @google-login="handleGoogleLogin"
      @apple-login="handleAppleLogin"
    />
  </AuthLayout>
</template>