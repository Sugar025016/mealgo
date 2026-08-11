<script setup lang="ts">
import { reactive, ref } from "vue";
import AuthInput from "./AuthInput.vue";
import type { MerchantRegisterPayload } from "@/types/auth";

withDefaults(defineProps<{ loading?: boolean }>(), { loading: false });

const emit = defineEmits<{ submit: [data: MerchantRegisterPayload] }>();

const form = reactive({
  shopName: "",
  ownerName: "",
  phone: "",
  email: "",
  password: "",
  confirmPassword: "",
});
const agreed = ref(false);
const agreementError = ref("");
const errors = reactive({ shopName: "", ownerName: "", phone: "", email: "", password: "", confirmPassword: "" });

function submit() {
  errors.shopName = form.shopName.trim() ? "" : "請輸入店家名稱";
  errors.ownerName = form.ownerName.trim() ? "" : "請輸入負責人姓名";
  errors.phone = /^09\d{8}$/.test(form.phone) ? "" : "請輸入正確的手機號碼";
  errors.email = !form.email.trim()
    ? "請輸入電子郵件"
    : !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)
      ? "電子郵件格式不正確"
      : "";
  errors.password = form.password.length >= 6 ? "" : "密碼至少需要 6 個字元";
  errors.confirmPassword = form.confirmPassword === form.password ? "" : "兩次輸入的密碼不一致";
  agreementError.value = agreed.value ? "" : "請先同意商家服務條款與隱私權政策";

  if (Object.values(errors).some(Boolean) || agreementError.value) return;
  emit("submit", {
    shopName: form.shopName.trim(),
    ownerName: form.ownerName.trim(),
    phone: form.phone,
    email: form.email.trim(),
    password: form.password,
  });
}
</script>

<template>
  <section class="auth-form auth-form--compact" aria-labelledby="merchant-register-title">
    <header class="auth-form__header">
      <p class="auth-form__brand">Meal<strong>Go</strong></p>
      <h2 id="merchant-register-title">申請成為合作商家</h2>
      <p>建立商家帳號，開始設定你的店舖</p>
    </header>

    <form class="auth-form__body" novalidate @submit.prevent="submit">
      <AuthInput id="merchant-shop-name" v-model="form.shopName" label="店家名稱" icon="bi-shop" autocomplete="organization" placeholder="請輸入店家名稱" :error="errors.shopName" @input="errors.shopName = ''" />

      <div class="auth-form__row">
        <AuthInput id="merchant-owner-name" v-model="form.ownerName" label="負責人姓名" icon="bi-person" autocomplete="name" placeholder="請輸入負責人姓名" :error="errors.ownerName" @input="errors.ownerName = ''" />
        <AuthInput id="merchant-phone" v-model="form.phone" label="聯絡電話" type="tel" icon="bi-telephone" autocomplete="tel" placeholder="0912345678" :error="errors.phone" @input="errors.phone = ''" />
      </div>

      <AuthInput id="merchant-email" v-model="form.email" label="電子郵件" type="email" icon="bi-envelope" autocomplete="email" placeholder="請輸入電子郵件" :error="errors.email" @input="errors.email = ''" />

      <div class="auth-form__row">
        <AuthInput id="merchant-password" v-model="form.password" label="密碼" type="password" icon="bi-lock" autocomplete="new-password" placeholder="請輸入密碼" :error="errors.password" @input="errors.password = ''" />
        <AuthInput id="merchant-confirm-password" v-model="form.confirmPassword" label="確認密碼" type="password" icon="bi-lock" autocomplete="new-password" placeholder="請再次輸入密碼" :error="errors.confirmPassword" @input="errors.confirmPassword = ''" />
      </div>

      <label class="auth-form__agreement">
        <input v-model="agreed" type="checkbox" @change="agreementError = ''" />
        <span>我同意<RouterLink to="/merchant/terms">商家服務條款</RouterLink>與<RouterLink to="/privacy">隱私權政策</RouterLink></span>
      </label>
      <small v-if="agreementError" class="auth-form__error">{{ agreementError }}</small>

      <button class="auth-form__submit" type="submit" :disabled="loading">
        {{ loading ? "建立中…" : "建立商家帳號" }}
      </button>
    </form>

    <p class="auth-form__footer">已經是合作商家？<RouterLink to="/merchant-login">登入商家後台</RouterLink></p>
  </section>
</template>

<style scoped lang="scss" src="./auth-form.scss"></style>
