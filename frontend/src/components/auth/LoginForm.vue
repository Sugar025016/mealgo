<script setup lang="ts">
import { reactive } from "vue";
import AuthInput from "./AuthInput.vue";
import SocialLoginButtons from "./SocialLoginButtons.vue";
import type { LoginPayload } from "@/types/auth";

withDefaults(defineProps<{ loading?: boolean }>(), { loading: false });

const emit = defineEmits<{
  submit: [data: LoginPayload];
  "google-login": [];
  "apple-login": [];
}>();

const form = reactive<LoginPayload>({
  email: "",
  password: "",
  rememberMe: false,
});
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
  <section class="auth-form" aria-labelledby="member-login-title">
    <header class="auth-form__header">
      <!-- <p class="auth-form__brand">Meal<strong>Go</strong></p> -->
      <h2 id="member-login-title">登入 MealGo</h2>
      <p>歡迎回來，登入後繼續探索美味</p>
    </header>

    <form class="auth-form__body" novalidate @submit.prevent="submit">
      <AuthInput
        id="member-login-email"
        v-model="form.email"
        label="電子郵件"
        type="email"
        icon="bi-envelope"
        autocomplete="email"
        placeholder="請輸入電子郵件"
        :error="errors.email"
        @input="errors.email = ''"
      />
      <AuthInput
        id="member-login-password"
        v-model="form.password"
        label="密碼"
        type="password"
        icon="bi-lock"
        autocomplete="current-password"
        placeholder="請輸入密碼"
        :error="errors.password"
        @input="errors.password = ''"
      />

      <div class="auth-form__options">
        <label><input v-model="form.rememberMe" type="checkbox" />記住我</label>
        <RouterLink to="/forgot-password">忘記密碼？</RouterLink>
      </div>

      <button class="auth-form__submit" type="submit" :disabled="loading">
        {{ loading ? "登入中…" : "登入" }}
      </button>
    </form>

    <SocialLoginButtons
      @google="emit('google-login')"
      @apple="emit('apple-login')"
    />

    <p class="auth-form__footer">
      還沒有帳號？<RouterLink to="/register">立即註冊</RouterLink>
    </p>
  </section>
</template>

<style scoped lang="scss">
.auth-form__header {
  margin-bottom: 26px;

  h2 {
    margin: 0 ;
    color: #171310;
    font-size: clamp(28px, 3vw, 38px);
    font-weight: 800;
    line-height: 1.22;
  }

  > p:last-child {
    margin: 0 0 0 2px;
    color: #746a64;
    font-size: 14px;
  }
}

.auth-form__brand {
  margin: 0;
  color: #28221e;
  font-size: 36px;
  font-weight: 800;
  letter-spacing: -0.05em;

  strong {
    color: #ff5f07;
  }
}

.auth-form__body {
  display: grid;
  gap: 16px;
}

.auth-form--compact {
  .auth-form__header {
    margin-bottom: 20px;
  }

  .auth-form__body {
    gap: 12px;
  }
}

.auth-form__row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.auth-form__options {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;

  label {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #544a44;
    cursor: pointer;
  }

  a {
    color: #ff5f07;
    font-weight: 700;
    text-decoration: none;
  }
}

.auth-form__agreement {
  display: flex;
  align-items: flex-start;
  gap: 9px;
  color: #514741;
  font-size: 13px;
  line-height: 1.55;
  cursor: pointer;

  a {
    color: #ff5f07;
    text-decoration: none;
  }
}

.auth-form__options input,
.auth-form__agreement input {
  width: 17px;
  height: 17px;
  flex: 0 0 17px;
  margin-top: 1px;
  accent-color: #ff5f07;
}

.auth-form__submit {
  min-height: 52px;
  border: 0;
  border-radius: 10px;
  background: #ff5f07;
  color: #fff;
  font: inherit;
  font-size: 16px;
  font-weight: 800;
  cursor: pointer;

  &:hover:not(:disabled) {
    background: #e95406;
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.65;
  }
}

.auth-form__error {
  color: #c92d35;
  font-size: 12px;
}

.auth-form__footer,
.auth-form__secondary-link {
  margin: 24px 0 0;
  color: #655b55;
  font-size: 14px;
  text-align: center;

  a {
    margin-left: 7px;
    color: #ff5f07;
    font-weight: 800;
    text-decoration: none;
  }
}

.auth-form__secondary-link {
  margin-top: 14px;
  padding-top: 18px;
  border-top: 1px solid #ded7d1;
}

@media (max-width: 479.98px) {
  .auth-form__row {
    grid-template-columns: 1fr;
  }

  .auth-form__brand {
    font-size: 32px;
  }

  .auth-form__header h2 {
    font-size: 29px;
  }
}
</style>
