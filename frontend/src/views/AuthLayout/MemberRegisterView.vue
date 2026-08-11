<script setup lang="ts">
import { reactive } from "vue";
import AuthLayout from "@/layouts/AuthLayout.vue";
import Logo from "@/components/ui/Logo.vue";
import registerImage from "@/assets/images/auth/auth-login1.png";

const form = reactive({
  name: "",
  email: "",
  password: "",
  confirmPassword: "",
  agreed: false,
});

function submitRegister() {
  // TODO：檢查兩次密碼並串接會員註冊 API
}
</script>

<template>
  <AuthLayout>
    <template #visual>
      <div class="register-visual">
        <Logo :size="40" :text-size="30" />
        <h1>註冊 <span>MealGo</span><br />開啟美味新生活</h1>
        <p>輕鬆註冊會員，立即探索更多美食與便利外送服務。</p>
        <img :src="registerImage" alt="MealGo 會員註冊" />
      </div>
    </template>

    <form class="register-card" @submit.prevent="submitRegister">
      <header>
        <h2>建立會員帳號</h2>
        <p>填寫資料即可開始使用 MealGo</p>
      </header>

      <label>姓名<input v-model.trim="form.name" autocomplete="name" required /></label>
      <label>
        電子信箱
        <input v-model.trim="form.email" type="email" autocomplete="email" required />
      </label>
      <label>
        密碼
        <input v-model="form.password" type="password" autocomplete="new-password" required />
      </label>
      <label>
        確認密碼
        <input
          v-model="form.confirmPassword"
          type="password"
          autocomplete="new-password"
          required
        />
      </label>

      <label class="register-card__check">
        <input v-model="form.agreed" type="checkbox" required />
        我同意使用條款與隱私權政策
      </label>

      <button type="submit">建立帳號</button>

      <p class="register-card__footer">
        已經有帳號？
        <RouterLink to="/auth/login">返回登入</RouterLink>
      </p>
    </form>
  </AuthLayout>
</template>

<style scoped lang="scss">
.register-visual {
  position: relative;
  height: 100%;

  h1 {
    margin: 24px 0 10px;
    font-size: clamp(36px, 3.7vw, 52px);
    font-weight: 900;
    line-height: 1.1;
  }

  h1 span {
    color: #ff5f07;
  }

  p {
    color: #687180;
  }

  img {
    position: absolute;
    bottom: 3%;
    left: 50%;
    width: 122%;
    max-height: 62%;
    object-fit: contain;
    transform: translateX(-50%);
  }
}

.register-card {
  display: grid;
  gap: 14px;
  padding: clamp(24px, 3vw, 40px);
  border: 1px solid #f0e9e3;
  border-radius: 28px;
  background: rgb(255 255 255 / 94%);
  box-shadow: 0 22px 60px rgb(77 45 23 / 10%);

  h2 {
    margin: 0;
    font-size: 30px;
  }

  header p,
  &__footer {
    margin: 6px 0 0;
    color: #7c8491;
  }

  > label:not(&__check) {
    display: grid;
    gap: 6px;
    font-weight: 700;
  }

  input:not([type="checkbox"]) {
    width: 100%;
    height: 46px;
    padding: 0 14px;
    border: 1px solid #dfe2e6;
    border-radius: 12px;
    box-sizing: border-box;
    font: inherit;
  }

  &__check {
    display: flex;
    align-items: center;
    gap: 7px;
    color: #626b78;
  }

  button {
    height: 50px;
    border: 0;
    border-radius: 13px;
    color: #fff;
    background: #ff5f07;
    font: inherit;
    font-weight: 800;
  }

  a {
    color: #ef5700;
    font-weight: 700;
    text-decoration: none;
  }

  &__footer {
    text-align: center;
  }
}
</style>
