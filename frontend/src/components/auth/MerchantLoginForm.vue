<script setup lang="ts">
import { reactive } from "vue";
import AuthInput from "./AuthInput.vue";
import type { LoginPayload } from "@/types/auth";

withDefaults(defineProps<{ loading?: boolean }>(), { loading: false });

const emit = defineEmits<{ submit: [data: LoginPayload] }>();
const form = reactive<LoginPayload>({ email: "", password: "", rememberMe: false });
const errors = reactive({ email: "", password: "" });

function submit() {
  errors.email = !form.email.trim()
    ? "請輸入電子郵件"
    : !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)
      ? "電子郵件格式不正確"
      : "";
  errors.password = form.password ? "" : "請輸入密碼";
  if (errors.email || errors.password) return;
  emit("submit", { ...form, email: form.email.trim() });
}
</script>

<template>
  <section class="auth-form" aria-labelledby="merchant-login-title">
    <header class="auth-form__header">
      <p class="auth-form__brand">Meal<strong>Go</strong></p>
      <h2 id="merchant-login-title">商家後台登入</h2>
      <p>登入管理你的 MealGo 店舖</p>
    </header>

    <form class="auth-form__body" novalidate @submit.prevent="submit">
      <AuthInput id="merchant-login-email" v-model="form.email" label="電子郵件" type="email" icon="bi-envelope" autocomplete="email" placeholder="請輸入電子郵件" :error="errors.email" @input="errors.email = ''" />
      <AuthInput id="merchant-login-password" v-model="form.password" label="密碼" type="password" icon="bi-lock" autocomplete="current-password" placeholder="請輸入密碼" :error="errors.password" @input="errors.password = ''" />

      <div class="auth-form__options">
        <label><input v-model="form.rememberMe" type="checkbox" />記住我</label>
        <RouterLink to="/merchant/forgot-password">忘記密碼？</RouterLink>
      </div>

      <button class="auth-form__submit" type="submit" :disabled="loading">
        {{ loading ? "登入中…" : "登入商家後台" }}
      </button>
    </form>

    <p class="auth-form__footer">
      還不是合作商家？<RouterLink to="/merchant-register">申請成為合作商家</RouterLink>
    </p>
    <p class="auth-form__secondary-link"><RouterLink to="/login">返回一般會員登入</RouterLink></p>
  </section>
</template>

<style scoped lang="scss" src="./auth-form.scss"></style>
