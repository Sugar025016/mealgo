<script setup lang="ts">
import { computed, reactive, ref } from "vue";

export type RegisterFormData = {
  name: string;
  email: string;
  phone: string;
  password: string;
  confirmPassword: string;
  agreed: boolean;
};

const emit = defineEmits<{
  submit: [data: RegisterFormData];
  googleRegister: [];
  appleRegister: [];
}>();

const form = reactive<RegisterFormData>({
  name: "",
  email: "",
  phone: "",
  password: "",
  confirmPassword: "",
  agreed: false,
});

const errors = reactive({
  name: "",
  email: "",
  phone: "",
  password: "",
  confirmPassword: "",
  agreed: "",
});

const showPassword = ref(false);
const showConfirmPassword = ref(false);
const isSubmitting = ref(false);

const passwordStrength = computed(() => {
  const password = form.password;

  if (!password) {
    return {
      score: 0,
      label: "",
      className: "",
    };
  }

  let score = 0;

  if (password.length >= 8) score += 1;
  if (/[A-Z]/.test(password)) score += 1;
  if (/[a-z]/.test(password)) score += 1;
  if (/\d/.test(password)) score += 1;
  if (/[^A-Za-z0-9]/.test(password)) score += 1;

  if (score <= 2) {
    return {
      score: 1,
      label: "弱",
      className: "register-form__strength--weak",
    };
  }

  if (score <= 4) {
    return {
      score: 2,
      label: "中等",
      className: "register-form__strength--medium",
    };
  }

  return {
    score: 3,
    label: "強",
    className: "register-form__strength--strong",
  };
});

const canSubmit = computed(() => {
  return (
    form.name.trim().length > 0 &&
    form.email.trim().length > 0 &&
    form.password.length > 0 &&
    form.confirmPassword.length > 0 &&
    form.agreed &&
    !isSubmitting.value
  );
});

function clearError(field: keyof typeof errors) {
  errors[field] = "";
}

function validateForm() {
  Object.keys(errors).forEach((key) => {
    errors[key as keyof typeof errors] = "";
  });

  const name = form.name.trim();
  const email = form.email.trim();
  const phone = form.phone
    .trim()
    .replace(/[-\s]/g, "");

  if (!name) {
    errors.name = "請輸入姓名";
  } else if (name.length < 2) {
    errors.name = "姓名至少需要 2 個字";
  }

  if (!email) {
    errors.email = "請輸入電子郵件";
  } else if (
    !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  ) {
    errors.email = "電子郵件格式不正確";
  }

  if (phone && !/^09\d{8}$/.test(phone)) {
    errors.phone = "請輸入正確的台灣手機號碼";
  }

  if (!form.password) {
    errors.password = "請輸入密碼";
  } else if (form.password.length < 8) {
    errors.password = "密碼至少需要 8 個字元";
  } else if (form.password.length > 16) {
    errors.password = "密碼最多 16 個字元";
  }

  if (!form.confirmPassword) {
    errors.confirmPassword = "請再次輸入密碼";
  } else if (
    form.confirmPassword !== form.password
  ) {
    errors.confirmPassword = "兩次輸入的密碼不一致";
  }

  if (!form.agreed) {
    errors.agreed = "請先同意服務條款與隱私政策";
  }

  return !Object.values(errors).some(Boolean);
}

async function handleSubmit() {
  if (!validateForm()) return;

  isSubmitting.value = true;

  try {
    emit("submit", {
      name: form.name.trim(),
      email: form.email.trim(),
      phone: form.phone
        .trim()
        .replace(/[-\s]/g, ""),
      password: form.password,
      confirmPassword: form.confirmPassword,
      agreed: form.agreed,
    });
  } finally {
    isSubmitting.value = false;
  }
}
</script>

<template>
  <section class="register-form">
    <header class="register-form__header">
      <div class="register-form__header-icon">
        <i class="bi bi-person-plus-fill"></i>
      </div>

      <div class="register-form__header-content">
        <h1>建立您的 MealGo 帳號</h1>
        <p>請填寫以下資訊完成註冊</p>
      </div>
    </header>

    <form
      class="register-form__body"
      novalidate
      @submit.prevent="handleSubmit"
    >
      <!-- 姓名 -->
      <div class="register-form__field">
        <label for="register-name">
          姓名
        </label>

        <div
          class="register-form__input"
          :class="{
            'register-form__input--error':
              errors.name,
          }"
        >
          <i class="bi bi-person"></i>

          <input
            id="register-name"
            v-model="form.name"
            type="text"
            autocomplete="name"
            placeholder="請輸入您的姓名"
            @input="clearError('name')"
          />
        </div>

        <p
          v-if="errors.name"
          class="register-form__error"
        >
          {{ errors.name }}
        </p>
      </div>

      <!-- 電子郵件 -->
      <div class="register-form__field">
        <label for="register-email">
          電子郵件
        </label>

        <div
          class="register-form__input"
          :class="{
            'register-form__input--error':
              errors.email,
          }"
        >
          <i class="bi bi-envelope"></i>

          <input
            id="register-email"
            v-model="form.email"
            type="email"
            autocomplete="email"
            placeholder="請輸入您的電子郵件"
            @input="clearError('email')"
          />
        </div>

        <p
          v-if="errors.email"
          class="register-form__error"
        >
          {{ errors.email }}
        </p>
      </div>

      <!-- 手機號碼 -->
      <div class="register-form__field">
        <label for="register-phone">
          手機號碼
          <span class="register-form__optional">
            （選填）
          </span>
        </label>

        <div
          class="register-form__input"
          :class="{
            'register-form__input--error':
              errors.phone,
          }"
        >
          <i class="bi bi-telephone"></i>

          <input
            id="register-phone"
            v-model="form.phone"
            type="tel"
            autocomplete="tel"
            inputmode="tel"
            maxlength="12"
            placeholder="例如：0912-345-678"
            @input="clearError('phone')"
          />
        </div>

        <p
          v-if="errors.phone"
          class="register-form__error"
        >
          {{ errors.phone }}
        </p>
      </div>

      <!-- 密碼 -->
      <div class="register-form__field">
        <label for="register-password">
          密碼
        </label>

        <div
          class="register-form__input"
          :class="{
            'register-form__input--error':
              errors.password,
          }"
        >
          <i class="bi bi-lock"></i>

          <input
            id="register-password"
            v-model="form.password"
            :type="
              showPassword ? 'text' : 'password'
            "
            autocomplete="new-password"
            maxlength="16"
            placeholder="請輸入 8–16 位元的密碼"
            @input="clearError('password')"
          />

          <button
            type="button"
            class="register-form__input-action"
            :aria-label="
              showPassword
                ? '隱藏密碼'
                : '顯示密碼'
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
          class="register-form__error"
        >
          {{ errors.password }}
        </p>

        <div
          v-if="form.password"
          class="register-form__strength"
          :class="passwordStrength.className"
        >
          <span>密碼強度</span>

          <div class="register-form__strength-bars">
            <span
              v-for="index in 3"
              :key="index"
              :class="{
                'register-form__strength-bar--active':
                  index <= passwordStrength.score,
              }"
            ></span>
          </div>

          <strong>
            {{ passwordStrength.label }}
          </strong>
        </div>
      </div>

      <!-- 確認密碼 -->
      <div class="register-form__field">
        <label for="register-confirm-password">
          確認密碼
        </label>

        <div
          class="register-form__input"
          :class="{
            'register-form__input--error':
              errors.confirmPassword,
          }"
        >
          <i class="bi bi-lock"></i>

          <input
            id="register-confirm-password"
            v-model="form.confirmPassword"
            :type="
              showConfirmPassword
                ? 'text'
                : 'password'
            "
            autocomplete="new-password"
            maxlength="16"
            placeholder="請再次輸入密碼"
            @input="
              clearError('confirmPassword')
            "
          />

          <button
            type="button"
            class="register-form__input-action"
            :aria-label="
              showConfirmPassword
                ? '隱藏密碼'
                : '顯示密碼'
            "
            @click="
              showConfirmPassword =
                !showConfirmPassword
            "
          >
            <i
              :class="
                showConfirmPassword
                  ? 'bi bi-eye-slash'
                  : 'bi bi-eye'
              "
            ></i>
          </button>
        </div>

        <p
          v-if="errors.confirmPassword"
          class="register-form__error"
        >
          {{ errors.confirmPassword }}
        </p>
      </div>

      <!-- 同意條款 -->
      <div class="register-form__agreement">
        <label class="register-form__agreement-label">
          <input
            v-model="form.agreed"
            type="checkbox"
            @change="clearError('agreed')"
          />

          <span class="register-form__checkbox">
            <i class="bi bi-check"></i>
          </span>

          <span>
            我已閱讀並同意 MealGo 的

            <RouterLink to="/terms">
              服務條款
            </RouterLink>

            與

            <RouterLink to="/privacy">
              隱私政策
            </RouterLink>
          </span>
        </label>

        <p
          v-if="errors.agreed"
          class="register-form__error"
        >
          {{ errors.agreed }}
        </p>
      </div>

      <!-- 註冊按鈕 -->
      <button
        type="submit"
        class="register-form__submit"
        :disabled="!canSubmit"
      >
        <span
          v-if="isSubmitting"
          class="register-form__spinner"
        ></span>

        {{
          isSubmitting
            ? "註冊中..."
            : "註冊會員"
        }}
      </button>

      <!-- 分隔線 -->
      <div class="register-form__divider">
        <span>或使用以下方式註冊</span>
      </div>

      <!-- 第三方註冊 -->
      <div class="register-form__socials">
        <button
          type="button"
          class="register-form__social"
          @click="emit('googleRegister')"
        >
          <span class="register-form__google">
            G
          </span>

          <span>使用 Google 註冊</span>
        </button>

        <button
          type="button"
          class="register-form__social"
          @click="emit('appleRegister')"
        >
          <i class="bi bi-apple"></i>

          <span>使用 Apple 註冊</span>
        </button>
      </div>

      <!-- 登入 -->
      <p class="register-form__login">
        已經有帳號了？

        <RouterLink to="/login">
          立即登入
        </RouterLink>
      </p>
    </form>
  </section>
</template>

<style scoped lang="scss">
.register-form {
  width: 100%;
  max-width: 540px;
  padding: 20px 34px 18px;
  border: 1px solid #e3e7ec;
  border-radius: 24px;
  background: rgb(255 255 255 / 97%);
  box-shadow:
    0 20px 48px rgb(45 34 25 / 9%),
    inset 0 1px 0 rgb(255 255 255 / 88%);
}

/* 標題 */

.register-form__header {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 15px;
}

.register-form__header-icon {
  display: grid;
  flex: 0 0 auto;
  width: 55px;
  height: 55px;
  place-items: center;
  border-radius: 50%;
  color: #f26722;
  background: #fff0e7;
  font-size: 21px;
}

.register-form__header-content {
  min-width: 0;

}

.register-form__header h1 {
  margin: 0 0 2px;
  color: #202938;
  font-size: 23px;
  font-weight: 800;
  line-height: 1.2;
}

.register-form__header p {
  margin: 0;
  color: #a0a7b2;
  font-size: 13px;
}

/* 表單 */

.register-form__body {
  display: flex;
  flex-direction: column;
}

.register-form__field {
  margin-bottom: 9px;
}

.register-form__field label {
  display: block;
  margin-bottom: 4px;
  color: #283142;
  font-size: 14px;
  font-weight: 700;
}

.register-form__optional {
  color: #9aa1ad;
  font-size: 11px;
  font-weight: 400;
}

.register-form__input {
  display: flex;
  height: 42px;
  align-items: center;
  gap: 10px;
  padding: 0 13px;
  border: 1px solid #dfe3e9;
  border-radius: 9px;
  background: #ffffff;
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.register-form__input:focus-within {
  border-color: #ff5a00;
  box-shadow: 0 0 0 3px rgb(255 90 0 / 9%);
}

.register-form__input--error {
  border-color: #dc3545;
}

.register-form__input > i {
  flex: 0 0 auto;
  color: #8993a3;
  font-size: 15px;
}

.register-form__input input {
  min-width: 0;
  flex: 1;
  border: 0;
  outline: 0;
  color: #283142;
  background: transparent;
  font: inherit;
  font-size: 14px;
}

.register-form__input input::placeholder {
  color: #b8bec8;
}

.register-form__input-action {
  display: grid;
  flex: 0 0 auto;
  width: 30px;
  height: 30px;
  padding: 0;
  place-items: center;
  border: 0;
  color: #8b95a5;
  background: transparent;
  font-size: 16px;
  cursor: pointer;
}

.register-form__input-action:hover {
  color: #ff5a00;
}

.register-form__error {
  margin: 3px 0 0;
  color: #dc3545;
  font-size: 12px;
}

/* 密碼強度 */

.register-form__strength {
  display: flex;
  align-items: center;
  gap: 7px;
  margin-top: 5px;
  color: #8d95a2;
  font-size: 12px;
}

.register-form__strength-bars {
  display: grid;
  width: 82px;
  grid-template-columns: repeat(3, 1fr);
  gap: 4px;
}

.register-form__strength-bars span {
  height: 3px;
  border-radius: 999px;
  background: #e4e7eb;
}

.register-form__strength strong {
  font-size: 12px;
}

.register-form__strength--weak {
  color: #dc3545;
}

.register-form__strength--weak
  .register-form__strength-bar--active {
  background: #dc3545;
}

.register-form__strength--medium {
  color: #f59e0b;
}

.register-form__strength--medium
  .register-form__strength-bar--active {
  background: #f59e0b;
}

.register-form__strength--strong {
  color: #22a06b;
}

.register-form__strength--strong
  .register-form__strength-bar--active {
  background: #22a06b;
}

/* 條款 */

.register-form__agreement {
  margin: 1px 0 10px;
}

.register-form__agreement-label {
  display: flex;
  align-items: flex-start;
  gap: 8px;
  color: #737c8a;
  font-size: 14px;
  line-height: 1.45;
  cursor: pointer;
}

.register-form__agreement-label input {
  position: absolute;
  width: 1px;
  height: 1px;
  opacity: 0;
}

.register-form__checkbox {
  display: grid;
  flex: 0 0 auto;
  width: 17px;
  height: 17px;
  margin-top: 1px;
  place-items: center;
  border: 1px solid #cfd5dd;
  border-radius: 4px;
  color: transparent;
  background: #ffffff;
  font-size: 13px;
}

.register-form__agreement-label input:checked +
.register-form__checkbox {
  border-color: #ff5a00;
  color: #ffffff;
  background: #ff5a00;
}

.register-form__agreement a {
  color: #ff5a00;
  font-weight: 700;
  text-decoration: none;
}

.register-form__agreement a:hover {
  text-decoration: underline;
}

/* 註冊按鈕 */

.register-form__submit {
  display: flex;
  width: 100%;
  height: 44px;
  align-items: center;
  justify-content: center;
  gap: 7px;
  border: 0;
  border-radius: 9px;
  color: #ffffff;
  background: linear-gradient(
    90deg,
    #ff5a00,
    #ff6a19
  );
  font-size: 14px;
  font-weight: 700;
  box-shadow: 0 7px 16px rgb(255 90 0 / 20%);
  cursor: pointer;
  transition:
    background 0.2s ease,
    box-shadow 0.2s ease,
    opacity 0.2s ease;
}

.register-form__submit:hover:not(:disabled) {
  background: linear-gradient(
    90deg,
    #ed5400,
    #f75d0a
  );
  box-shadow: 0 9px 20px rgb(255 90 0 / 26%);
}

.register-form__submit:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  box-shadow: none;
}

.register-form__spinner {
  width: 15px;
  height: 15px;
  border: 2px solid rgb(255 255 255 / 40%);
  border-top-color: #ffffff;
  border-radius: 50%;
  animation: register-spin 0.8s linear infinite;
}

/* 分隔線 */

.register-form__divider {
  display: flex;
  align-items: center;
  gap: 11px;
  margin: 11px 0 8px;
  color: #959daa;
  font-size: 12px;
  white-space: nowrap;
}

.register-form__divider::before,
.register-form__divider::after {
  height: 1px;
  flex: 1;
  background: #e4e7eb;
  content: "";
}

/* 第三方註冊 */

.register-form__socials {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 9px;
}

.register-form__social {
  display: flex;
  width: 100%;
  height: 40px;
  align-items: center;
  justify-content: center;
  gap: 8px;
  border: 1px solid #dfe3e8;
  border-radius: 9px;
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

.register-form__social:hover {
  border-color: #ffb58d;
  background: #fffaf7;
  box-shadow: 0 4px 12px rgb(15 23 42 / 5%);
}

.register-form__social i {
  font-size: 17px;
}

.register-form__google {
  color: #4285f4;
  font-size: 17px;
  font-weight: 800;
}

/* 登入 */

.register-form__login {
  margin: 9px 0 0;
  color: #8d95a2;
  font-size: 13px;
  text-align: center;
}

.register-form__login a {
  margin-left: 5px;
  color: #ff5a00;
  font-weight: 700;
  text-decoration: none;
}

.register-form__login a:hover {
  text-decoration: underline;
}

@keyframes register-spin {
  to {
    transform: rotate(360deg);
  }
}

/* 更矮桌面 */

@media (max-height: 760px) and (min-width: 821px) {
  .register-form {
    max-width: 520px;
    padding: 16px 28px 14px;
  }

  .register-form__header {
    margin-bottom: 11px;
  }

  .register-form__header-icon {
    width: 42px;
    height: 42px;
    font-size: 18px;
  }

  .register-form__header h1 {
    font-size: 20px;
  }

  .register-form__field {
    margin-bottom: 7px;
  }

  .register-form__input {
    height: 38px;
  }

  .register-form__agreement {
    margin-bottom: 8px;
  }

  .register-form__submit {
    height: 40px;
  }

  .register-form__divider {
    margin: 8px 0 6px;
  }

  .register-form__social {
    height: 36px;
  }

  .register-form__login {
    margin-top: 7px;
  }
}

/* 手機 */

@media (max-width: 576px) {
  .register-form {
    max-width: none;
    padding: 24px 18px 21px;
    border-radius: 18px;
    box-shadow: 0 12px 34px rgb(15 23 42 / 8%);
  }

  .register-form__header {
    align-items: flex-start;
  }

  .register-form__header-icon {
    width: 46px;
    height: 46px;
    font-size: 20px;
  }

  .register-form__header h1 {
    font-size: 21px;
  }

  .register-form__input {
    height: 47px;
  }

  .register-form__socials {
    grid-template-columns: 1fr;
  }

  .register-form__social {
    height: 44px;
  }
}
</style>