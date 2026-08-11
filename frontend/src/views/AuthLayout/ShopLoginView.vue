<script setup lang="ts">
import { reactive } from "vue";
import AuthLayout from "@/layouts/AuthLayout.vue";
import Logo from "@/components/ui/Logo.vue";
import shopImage from "@/assets/images/auth/shop-login.png";

const form = reactive({
  account: "",
  password: "",
  remember: false,
});

function submitShopLogin() {
  // TODO：串接商家登入 API，成功後導向商家後台
}
</script>

<template>
  <AuthLayout>
    <template #visual>
      <div class="shop-visual">
        <Logo :size="40" :text-size="30" />
        <h1>歡迎回來，<br /><span>MealGo 合作商家</span></h1>
        <p>管理訂單、餐點與營業資訊，讓每一份美味準時送達。</p>
        <img :src="shopImage" alt="MealGo 合作商家" />
      </div>
    </template>

    <form class="shop-card" @submit.prevent="submitShopLogin">
      <header>
        <span class="shop-card__badge">商家專區</span>
        <h2>商家登入</h2>
        <p>登入後進入 MealGo 商家管理後台</p>
      </header>

      <label>
        商家帳號
        <input v-model.trim="form.account" autocomplete="username" required />
      </label>

      <label>
        密碼
        <input
          v-model="form.password"
          type="password"
          autocomplete="current-password"
          required
        />
      </label>

      <div class="shop-card__options">
        <label class="shop-card__check">
          <input v-model="form.remember" type="checkbox" />
          記住我
        </label>
        <RouterLink to="/auth/shop-forgot-password">忘記密碼？</RouterLink>
      </div>

      <button type="submit">登入商家後台</button>

      <div class="shop-card__apply">
        <p>還不是 MealGo 合作商家？</p>
        <RouterLink to="/merchant/apply">申請成為合作商家</RouterLink>
      </div>

      <RouterLink class="shop-card__member" to="/auth/login">
        返回會員登入
      </RouterLink>
    </form>
  </AuthLayout>
</template>

<style scoped lang="scss">
.shop-visual {
  position: relative;
  height: 100%;

  h1 {
    margin: 24px 0 10px;
    font-size: clamp(36px, 3.7vw, 52px);
    font-weight: 900;
    line-height: 1.12;
  }

  h1 span {
    color: #ff5f07;
  }

  p {
    width: min(440px, 82%);
    color: #687180;
    line-height: 1.7;
  }

  img {
    position: absolute;
    bottom: -2%;
    left: 47%;
    width: 112%;
    max-height: 72%;
    object-fit: contain;
    transform: translateX(-50%);
  }
}

.shop-card {
  display: grid;
  gap: 20px;
  padding: clamp(28px, 4vw, 48px);
  border: 1px solid #f0e9e3;
  border-radius: 28px;
  background: rgb(255 255 255 / 94%);
  box-shadow: 0 22px 60px rgb(77 45 23 / 10%);

  &__badge {
    display: inline-flex;
    padding: 6px 11px;
    border-radius: 999px;
    color: #d94e00;
    background: #fff0e7;
    font-size: 13px;
    font-weight: 800;
  }

  h2 {
    margin: 12px 0 0;
    font-size: 32px;
  }

  header p,
  &__apply p {
    margin: 7px 0 0;
    color: #7c8491;
  }

  > label {
    display: grid;
    gap: 8px;
    font-weight: 700;
  }

  input:not([type="checkbox"]) {
    width: 100%;
    height: 50px;
    padding: 0 14px;
    border: 1px solid #dfe2e6;
    border-radius: 12px;
    box-sizing: border-box;
    font: inherit;
  }

  &__options {
    display: flex;
    justify-content: space-between;
  }

  &__check {
    display: flex;
    align-items: center;
    gap: 7px;
  }

  button {
    height: 52px;
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

  &__apply {
    padding: 16px;
    border-radius: 14px;
    text-align: center;
    background: #fff8f3;
  }

  &__member {
    text-align: center;
  }
}
</style>
