<script setup lang="ts">
import { computed, reactive, ref } from "vue";

type LoginFormData = {
  email: string;
  password: string;
  rememberMe: boolean;
};

const emit = defineEmits<{
  submit: [data: LoginFormData];
  googleLogin: [];
  appleLogin: [];
}>();

const form = reactive<LoginFormData>({
  email: "",
  password: "",
  rememberMe: false,
});

const errors = reactive({
  email: "",
  password: "",
});

const showPassword = ref(false);
const isSubmitting = ref(false);

const canSubmit = computed(() => {
  return (
    form.email.trim().length > 0 &&
    form.password.length > 0 &&
    !isSubmitting.value
  );
});

function validateForm() {
  errors.email = "";
  errors.password = "";

  const email = form.email.trim();

  if (!email) {
    errors.email = "請輸入電子郵件";
  } else if (
    !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  ) {
    errors.email = "電子郵件格式不正確";
  }

  if (!form.password) {
    errors.password = "請輸入密碼";
  } else if (form.password.length < 8) {
    errors.password = "密碼至少需要 8 個字元";
  }

  return !errors.email && !errors.password;
}

async function handleSubmit() {
  if (!validateForm()) return;

  isSubmitting.value = true;

  try {
    emit("submit", {
      email: form.email.trim(),
      password: form.password,
      rememberMe: form.rememberMe,
    });
  } finally {
    isSubmitting.value = false;
  }
}
</script>

<template>
  <section class="login-form">
    <header class="login-form__header">
      <div class="login-form__header-icon">
        <i class="bi bi-person-fill"></i>
      </div>

      <div class="login-form__header-content">
        <h1>帳號登入</h1>
        <p>歡迎使用 MealGo 帳號登入</p>
      </div>
    </header>

    <form
      class="login-form__body"
      novalidate
      @submit.prevent="handleSubmit"
    >
      <div class="login-form__field">
        <label for="login-email">
          電子郵件
        </label>

        <div
          class="login-form__input"
          :class="{
            'login-form__input--error': errors.email,
          }"
        >
          <i class="bi bi-envelope"></i>

          <input
            id="login-email"
            v-model="form.email"
            type="email"
            autocomplete="email"
            placeholder="電子郵件地址"
            @input="errors.email = ''"
          />
        </div>

        <p
          v-if="errors.email"
          class="login-form__error"
        >
          {{ errors.email }}
        </p>
      </div>

      <div class="login-form__field">
        <label for="login-password">
          密碼
        </label>

        <div
          class="login-form__input"
          :class="{
            'login-form__input--error':
              errors.password,
          }"
        >
          <i class="bi bi-lock"></i>

          <input
            id="login-password"
            v-model="form.password"
            :type="showPassword ? 'text' : 'password'"
            autocomplete="current-password"
            placeholder="輸入密碼"
            @input="errors.password = ''"
          />

          <button
            type="button"
            class="login-form__input-action"
            :aria-label="
              showPassword ? '隱藏密碼' : '顯示密碼'
            "
            @click="showPassword = !showPassword"
          >
            <i
              :class="
                showPassword
                  ? 'bi bi-eye-slash'
                  : 'bi bi-eye'
              "
            ></i>
          </button>
        </div>

        <p
          v-if="errors.password"
          class="login-form__error"
        >
          {{ errors.password }}
        </p>
      </div>

      <div class="login-form__options">
        <label class="login-form__remember">
          <input
            v-model="form.rememberMe"
            type="checkbox"
          />

          <span class="login-form__checkbox">
            <i class="bi bi-check"></i>
          </span>

          <span>記住我</span>
        </label>

        <RouterLink
          to="/forgot-password"
          class="login-form__forgot"
        >
          忘記密碼？
        </RouterLink>
      </div>

      <button
        type="submit"
        class="login-form__submit"
        :disabled="!canSubmit"
      >
        <span
          v-if="isSubmitting"
          class="login-form__spinner"
        ></span>

        {{ isSubmitting ? "登入中..." : "登入" }}
      </button>

      <div class="login-form__divider">
        <span>或使用以下方式登入</span>
      </div>

      <div class="login-form__socials">
        <button
          type="button"
          class="login-form__social"
          @click="emit('googleLogin')"
        >
          <span class="login-form__google">G</span>
          <span>使用 Google 帳號登入</span>
        </button>

        <button
          type="button"
          class="login-form__social"
          @click="emit('appleLogin')"
        >
          <i class="bi bi-apple"></i>
          <span>使用 Apple 帳號登入</span>
        </button>
      </div>

      <p class="login-form__register">
        還沒有帳號嗎？

        <RouterLink to="/register">
          立即註冊
        </RouterLink>
      </p>
    </form>
  </section>
</template>

<style scoped lang="scss">
.login-form {
  width: 100%;
  max-width: 560px;
  padding: 36px 42px 30px;
  border: 1px solid #e3e7ec;
  border-radius: 26px;
  background: rgb(255 255 255 / 97%);
  box-shadow:
    0 22px 54px rgb(45 34 25 / 10%),
    inset 0 1px 0 rgb(255 255 255 / 88%);
}

/* 標題 */

.login-form__header {
  display: flex;
  align-items: center;
  gap: 17px;
  margin-bottom: 27px;
}

.login-form__header-icon {
  display: grid;
  flex: 0 0 auto;
  width: 60px;
  height: 60px;
  place-items: center;
  border-radius: 50%;
  color: #f26722;
  background: #fff0e7;
  font-size: 26px;
}

.login-form__header-content {
  min-width: 0;
}

.login-form__header h1 {
  margin: 0 0 5px;
  color: #202938;
  font-size: 29px;
  font-weight: 800;
  line-height: 1.25;
}

.login-form__header p {
  margin: 0;
  color: #a0a7b2;
  font-size: 13px;
}

/* 欄位 */

.login-form__body {
  display: flex;
  flex-direction: column;
}

.login-form__field {
  margin-bottom: 18px;
}

.login-form__field label {
  display: block;
  margin-bottom: 8px;
  color: #283142;
  font-size: 14px;
  font-weight: 700;
}

.login-form__input {
  display: flex;
  height: 56px;
  align-items: center;
  gap: 12px;
  padding: 0 17px;
  border: 1px solid #dfe3e9;
  border-radius: 11px;
  background: #ffffff;
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.login-form__input:focus-within {
  border-color: #ff5a00;
  box-shadow: 0 0 0 4px rgb(255 90 0 / 9%);
}

.login-form__input--error {
  border-color: #dc3545;
}

.login-form__input > i {
  flex: 0 0 auto;
  color: #8993a3;
  font-size: 18px;
}

.login-form__input input {
  min-width: 0;
  flex: 1;
  border: 0;
  outline: 0;
  color: #283142;
  background: transparent;
  font: inherit;
  font-size: 14px;
}

.login-form__input input::placeholder {
  color: #b8bec8;
}

.login-form__input-action {
  display: grid;
  flex: 0 0 auto;
  width: 34px;
  height: 34px;
  padding: 0;
  place-items: center;
  border: 0;
  color: #8b95a5;
  background: transparent;
  font-size: 18px;
  cursor: pointer;
}

.login-form__input-action:hover {
  color: #ff5a00;
}

.login-form__error {
  margin: 6px 0 0;
  color: #dc3545;
  font-size: 12px;
}

/* 選項 */

.login-form__options {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  margin: 0 0 21px;
}

.login-form__remember {
  display: inline-flex;
  align-items: center;
  gap: 9px;
  color: #737c8a;
  font-size: 13px;
  cursor: pointer;
}

.login-form__remember input {
  position: absolute;
  width: 1px;
  height: 1px;
  opacity: 0;
}

.login-form__checkbox {
  display: grid;
  width: 20px;
  height: 20px;
  place-items: center;
  border: 1px solid #cfd5dd;
  border-radius: 5px;
  color: transparent;
  background: #ffffff;
  font-size: 13px;
}

.login-form__remember input:checked +
.login-form__checkbox {
  border-color: #ff5a00;
  color: #ffffff;
  background: #ff5a00;
}

.login-form__forgot {
  color: #ff5a00;
  font-size: 13px;
  font-weight: 600;
  text-decoration: none;
}

.login-form__forgot:hover {
  text-decoration: underline;
}

/* 登入按鈕 */

.login-form__submit {
  display: flex;
  width: 100%;
  height: 54px;
  align-items: center;
  justify-content: center;
  gap: 8px;
  border: 0;
  border-radius: 11px;
  color: #ffffff;
  background: linear-gradient(
    90deg,
    #ff5a00,
    #ff6a19
  );
  font-size: 16px;
  font-weight: 700;
  box-shadow: 0 9px 20px rgb(255 90 0 / 22%);
  cursor: pointer;
  transition:
    background 0.2s ease,
    box-shadow 0.2s ease,
    opacity 0.2s ease;
}

.login-form__submit:hover:not(:disabled) {
  background: linear-gradient(
    90deg,
    #ed5400,
    #f75d0a
  );
  box-shadow: 0 11px 24px rgb(255 90 0 / 28%);
}

.login-form__submit:active:not(:disabled) {
  background: #dc4e00;
  box-shadow: none;
}

.login-form__submit:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  box-shadow: none;
}

/* 載入 */

.login-form__spinner {
  width: 17px;
  height: 17px;
  border: 2px solid rgb(255 255 255 / 40%);
  border-top-color: #ffffff;
  border-radius: 50%;
  animation: login-spin 0.8s linear infinite;
}

/* 分隔線 */

.login-form__divider {
  display: flex;
  align-items: center;
  gap: 14px;
  margin: 23px 0 17px;
  color: #959daa;
  font-size: 12px;
  white-space: nowrap;
}

.login-form__divider::before,
.login-form__divider::after {
  height: 1px;
  flex: 1;
  background: #e4e7eb;
  content: "";
}

/* 第三方登入 */

.login-form__socials {
  display: flex;
  flex-direction: column;
  gap: 11px;
}

.login-form__social {
  display: flex;
  width: 100%;
  height: 50px;
  align-items: center;
  justify-content: center;
  gap: 10px;
  border: 1px solid #dfe3e8;
  border-radius: 10px;
  color: #273142;
  background: #ffffff;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition:
    border-color 0.2s ease,
    background 0.2s ease,
    box-shadow 0.2s ease;
}

.login-form__social:hover {
  border-color: #ffb58d;
  background: #fffaf7;
  box-shadow: 0 5px 14px rgb(15 23 42 / 5%);
}

.login-form__social:active {
  background: #fff4ed;
  box-shadow: none;
}

.login-form__social i {
  font-size: 21px;
}

.login-form__google {
  color: #4285f4;
  font-size: 21px;
  font-weight: 800;
}

/* 註冊 */

.login-form__register {
  margin: 19px 0 0;
  color: #8d95a2;
  font-size: 13px;
  text-align: center;
}

.login-form__register a {
  margin-left: 6px;
  color: #ff5a00;
  font-weight: 700;
  text-decoration: none;
}

.login-form__register a:hover {
  text-decoration: underline;
}

@keyframes login-spin {
  to {
    transform: rotate(360deg);
  }
}

/* 只有螢幕真的很矮才縮小 */

@media (max-height: 720px) and (min-width: 821px) {
  .login-form {
    padding: 24px 30px 20px;
  }

  .login-form__header {
    margin-bottom: 17px;
  }

  .login-form__header-icon {
    width: 48px;
    height: 48px;
    font-size: 21px;
  }

  .login-form__header h1 {
    font-size: 23px;
  }

  .login-form__field {
    margin-bottom: 12px;
  }

  .login-form__input {
    height: 45px;
  }

  .login-form__options {
    margin-bottom: 14px;
  }

  .login-form__submit {
    height: 45px;
  }

  .login-form__divider {
    margin: 14px 0 10px;
  }

  .login-form__socials {
    gap: 8px;
  }

  .login-form__social {
    height: 42px;
  }

  .login-form__register {
    margin-top: 11px;
  }
}

/* 手機 */

@media (max-width: 576px) {
  .login-form {
    max-width: none;
    padding: 27px 19px 23px;
    border-radius: 18px;
    box-shadow: 0 12px 34px rgb(15 23 42 / 8%);
  }

  .login-form__header {
    align-items: flex-start;
  }

  .login-form__header-icon {
    width: 48px;
    height: 48px;
    font-size: 21px;
  }

  .login-form__header h1 {
    font-size: 22px;
  }

  .login-form__input {
    height: 51px;
  }

  .login-form__submit {
    height: 51px;
  }

  .login-form__social {
    height: 48px;
  }
}
</style>