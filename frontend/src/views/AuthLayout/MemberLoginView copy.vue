<script setup lang="ts">
import AuthLayout from "@/layouts/AuthLayout.vue";
import LoginForm from "@/components/auth/LoginForm.vue";
import Logo from "@/components/ui/Logo.vue";
import loginImage from "@/assets/images/auth/auth-login1.png";

type LoginData = {
  email: string;
  password: string;
  rememberMe: boolean;
};

function handleLogin(data: LoginData) {
  console.log(data);
}

function handleGoogleLogin() {
  window.location.href = "http://localhost:8080/oauth2/authorization/google";
}

function handleAppleLogin() {
  console.log("Apple 登入");
}
</script>

<template>
  <AuthLayout>
    <template #visual>
      <section class="member-login-visual">
        <header class="member-login-visual__header">
          <Logo
            :size="40"
            :text-size="30"
            icon-color="#ff5a1f"
            meal-color="#202938"
            go-color="#ff5a1f"
          />

          <div class="member-login-visual__intro">
            <h1>歡迎回來</h1>
            <p>
              登入 MealGo，繼續享受美味餐點
              <br />
              與快速外送服務。
            </p>
            <span aria-hidden="true"></span>
          </div>
        </header>

        <div class="member-login-visual__image-wrap">
          <img :src="loginImage" alt="MealGo 美食外送" />
        </div>

        <div class="member-login-features">
          <article class="member-login-feature">
            <span class="member-login-feature__icon">
              <i class="bi bi-cup-hot"></i>
            </span>
            <div>
              <strong>多樣美食</strong>
              <small>豐富餐點選擇</small>
              <small>滿足你的味蕾</small>
            </div>
          </article>

          <article class="member-login-feature">
            <span class="member-login-feature__icon">
              <i class="bi bi-bicycle"></i>
            </span>
            <div>
              <strong>快速外送</strong>
              <small>高效配送服務</small>
              <small>美味立即送達</small>
            </div>
          </article>

          <article class="member-login-feature">
            <span class="member-login-feature__icon">
              <i class="bi bi-shield-check"></i>
            </span>
            <div>
              <strong>安全付款</strong>
              <small>多種付款保障</small>
              <small>交易安心無憂</small>
            </div>
          </article>
        </div>
      </section>
    </template>

    <LoginForm
      @submit="handleLogin"
      @google-login="handleGoogleLogin"
      @apple-login="handleAppleLogin"
    />
  </AuthLayout>
</template>

<style scoped lang="scss">
.member-login-visual {
  --auth-primary: #ff5a1f;

  display: grid;
  grid-template-rows: 245px minmax(160px, 1fr) 64px;
  min-width: 0;
  min-height: 100%;
}

.member-login-visual__header {
  position: relative;
  z-index: 2;
  min-width: 0;
}

.member-login-visual__intro {
  margin-top: 18px;

  h1 {
    margin: 0;
    color: #222;
    font-size: clamp(38px, 3.6vw, 54px);
    font-weight: 900;
    line-height: 1.08;
    letter-spacing: -0.05em;
  }

  p {
    margin: 14px 0 0;
    color: #687180;
    font-size: clamp(14px, 1.1vw, 16px);
    line-height: 1.65;
  }

  > span {
    display: block;
    width: 48px;
    height: 4px;
    margin-top: 14px;
    border-radius: 999px;
    background: var(--auth-primary);
  }
}

.member-login-visual__image-wrap {
  position: relative;
  min-width: 0;
  min-height: 0;
    z-index: 0;

  &::before {
    position: absolute;
    z-index: 0;
    right: 8%;
    bottom: 8%;
    width: 64%;
    height: 56%;
    border-radius: 50%;
    background: radial-gradient(
      circle,
      rgb(255 181 135 / 25%),
      transparent 68%
    );
    content: "";
    filter: blur(14px);
  }

  img {
    position: absolute;
    z-index: 0;
    // bottom: -8%;
    top: -25%;
    left: 50%;
    display: block;
    width: 128%;
    min-width: 500px;
    max-width: 920px;
    height: auto;
    object-fit: contain;
    transform: translateX(-50%);
    
  }
}

.member-login-features {
  position: relative;
  z-index: 2;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  align-items: center;
  gap: 16px;
  min-width: 0;
}

.member-login-feature {
  display: flex;
  min-width: 0;
  align-items: center;
  gap: 11px;

  &:not(:last-child) {
    padding-right: 14px;
    border-right: 1px solid rgb(210 197 187 / 65%);
  }

  > div {
    display: flex;
    min-width: 0;
    flex-direction: column;
    gap: 2px;
  }

  strong {
    color: #30343b;
    font-size: 13px;
    font-weight: 800;
  }

  small {
    overflow: hidden;
    color: #9299a4;
    font-size: 10px;
    line-height: 1.35;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

.member-login-feature__icon {
  display: grid;
  flex: 0 0 42px;
  width: 42px;
  height: 42px;
  place-items: center;
  border-radius: 50%;
  color: var(--auth-primary);
  background: #fff1e8;
  font-size: 18px;
  box-shadow: 0 6px 16px rgb(255 90 0 / 8%);
}

@media (max-width: 960px) {
  .member-login-visual {
    grid-template-rows: 215px minmax(300px, 1fr) auto;
  }

  .member-login-visual__intro {
    margin-top: 14px;

    h1 {
      font-size: 38px;
    }

    p {
      margin-top: 10px;
      font-size: 13px;
    }
  }

  .member-login-visual__image-wrap img {
    bottom: 4%;
    width: 125%;
    max-width: 720px;
  }

  .member-login-features {
    grid-template-columns: 1fr;
    align-content: center;
    gap: 7px;
  }

  .member-login-feature {
    &:not(:last-child) {
      padding-right: 0;
      border-right: 0;
    }

    small:last-child {
      display: none;
    }
  }
}

@media (max-height: 760px) and (min-width: 821px) {
  .member-login-visual {
    grid-template-rows: 185px minmax(270px, 1fr) 52px;
  }

  .member-login-visual__intro {
    margin-top: 9px;

    h1 {
      font-size: 36px;
    }

    p {
      margin-top: 7px;
      font-size: 12px;
      line-height: 1.5;
    }

    > span {
      margin-top: 8px;
    }
  }

  .member-login-visual__image-wrap img {
    bottom: 2%;
    width: 108%;
    max-width: 650px;
  }

  .member-login-feature__icon {
    flex-basis: 36px;
    width: 36px;
    height: 36px;
    font-size: 15px;
  }

  .member-login-feature {
    strong {
      font-size: 12px;
    }

    small {
      font-size: 9px;
    }
  }
}
</style>
