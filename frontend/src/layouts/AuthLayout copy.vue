<script setup lang="ts">
import authHeroImage from "@/assets/images/auth/auth-food3.png";
import MgLogo from "@/components/ui/Logo.vue";

type AuthMode = "login" | "register";

withDefaults(
  defineProps<{
    mode?: AuthMode;
    image?: string;
  }>(),
  {
    mode: "login",
    image: authHeroImage,
  },
);
</script>

<template>
  <main class="auth-layout" :class="`auth-layout--${mode}`">
    <div class="auth-layout__container">
      <!-- 左側品牌區 -->
      <section class="auth-layout__brand">
        <div class="auth-layout__brand-top">
          <RouterLink to="/" class="auth-layout__logo" aria-label="返回首頁">
            <MgLogo />
          </RouterLink>

          <div class="auth-layout__intro">
            <template v-if="mode === 'login'">
              <h1>歡迎回來</h1>

              <p>
                登入 MealGo，繼續享受美味餐點
                <br />
                與快速外送服務。
              </p>
            </template>

            <template v-else>
              <h1>
                註冊 <span>MealGo</span>
                <br />
                開啟美味新生活
              </h1>

              <p>
                輕鬆註冊會員，立即探索更多美食，
                <br />
                享受快速、便利的外送服務。
              </p>
            </template>

            <span class="auth-layout__accent"></span>
          </div>
        </div>

        <!-- 左側圖片 -->
        <div class="auth-layout__visual">
          <img :src="image" alt="MealGo 美食外送" />
        </div>

        <!-- 左側特色 -->
        <div class="auth-layout__features">
          <article class="auth-feature">
            <span class="auth-feature__icon">
              <i class="bi bi-cup-hot"></i>
            </span>

            <div class="auth-feature__content">
              <strong>多樣美食</strong>
              <small>豐富餐點選擇</small>
              <small>滿足你的味蕾</small>
            </div>
          </article>

          <article class="auth-feature">
            <span class="auth-feature__icon">
              <i class="bi bi-bicycle"></i>
            </span>

            <div class="auth-feature__content">
              <strong>快速外送</strong>
              <small>高效配送服務</small>
              <small>美味立即送達</small>
            </div>
          </article>

          <article class="auth-feature">
            <span class="auth-feature__icon">
              <i class="bi bi-shield-check"></i>
            </span>

            <div class="auth-feature__content">
              <strong>安全付款</strong>
              <small>多種付款保障</small>
              <small>交易安心無憂</small>
            </div>
          </article>
        </div>
      </section>

      <!-- 右側表單 -->
      <section class="auth-layout__form-area">
        <slot />
      </section>
    </div>
  </main>
</template>

<style scoped lang="scss">
.auth-layout {
  --auth-primary: #ff5a00;
  --auth-primary-dark: #e95000;
  --auth-text: #242424;
  --auth-muted: #7c8491;

  width: 100%;
  height: 100dvh;
  min-height: 100dvh;
  overflow-x: hidden;
  overflow-y: auto;

  color: var(--auth-text);
  background:
    radial-gradient(circle at 10% 10%, rgb(255 255 255 / 92%), transparent 32%),
    radial-gradient(circle at 92% 16%, rgb(255 225 207 / 42%), transparent 30%),
    linear-gradient(135deg, #f7f4f0, #fffaf6);
}

.auth-layout__container {
  display: grid;
  grid-template-columns:
    minmax(0, 1.12fr)
    minmax(400px, 0.88fr);
  align-items: stretch;
  gap: clamp(30px, 5vw, 76px);
  width: min(1380px, 100%);
  height: 100%;
  margin: auto 0;
  padding: clamp(24px, 3.5vh, 52px) clamp(28px, 4vw, 64px);
  //   padding: auto ;
}
// .auth-layout__container {
//   display: grid;
//   grid-template-columns:
//     minmax(0, 1.06fr)
//     minmax(500px, 0.94fr);
//   align-items: center;
//   gap: clamp(30px, 4vw, 60px);

//   width: min(1480px, 100%);
//   height: 100%;
//   min-height: 0;
//   margin: 0 auto;
//   padding:
//     clamp(16px, 2.4vh, 28px)
//     clamp(28px, 4vw, 64px);
// }

/* 左側 */

.auth-layout__brand {
  position: relative;
  display: flex;
  min-width: 0;
  min-height: 0;
  flex-direction: column;
}

.auth-layout__brand-top {
  position: relative;
  z-index: 3;
  flex: 0 0 auto;
}

.auth-layout__logo {
  display: inline-flex;
  width: fit-content;
  align-items: center;
  color: inherit;
  text-decoration: none;
}

.auth-layout__logo :deep(svg),
.auth-layout__logo :deep(img) {
  display: block;
  width: auto;
  height: clamp(48px, 4vh, 58px);
}

/* 標題 */

.auth-layout__intro {
  margin-top: clamp(16px, 2.6vh, 28px);
  text-shadow:
    -2px -2px 0 #fff,
    2px -2px 0 #fff,
    -2px 2px 0 #fff,
    2px 2px 0 #fff;
}

.auth-layout__intro h1 {
  margin: 0;
  color: #222222;
  font-size: clamp(38px, 4vw, 58px);
  font-weight: 900;
  line-height: 1.1;
  letter-spacing: -0.05em;
}

.auth-layout__intro h1 span {
  color: var(--auth-primary);
}

.auth-layout__intro p {
  margin: 14px 0 0;
  color: #687180;
  font-size: clamp(14px, 1.2vw, 17px);
  line-height: 1.65;
}

.auth-layout__accent {
  display: block;
  width: 48px;
  height: 4px;
  margin-top: 14px;
  border-radius: 999px;
  background: var(--auth-primary);
}

/* 圖片 */

.auth-layout__visual {
  position: relative;
  min-width: 0;
  min-height: 0;
  flex: 1;
  overflow: visible;
}

.auth-layout__visual::before {
  position: absolute;
  right: 8%;
  bottom: 8%;
  width: 64%;
  height: 56%;
  border-radius: 50%;
  background: radial-gradient(circle, rgb(255 181 135 / 25%), transparent 68%);
  content: "";
  filter: blur(14px);
}

// .auth-layout__visual img {
//   position: absolute;
//   z-index: 1;
//   right: 0;
//   bottom: 0;
//   display: block;
//   width: min(920px, 108%);
//   height: 100%;
//   max-height: 800px;
//   object-fit: contain;
//   object-position: center bottom;
// }
.auth-layout__visual img {
  position: absolute;
  z-index: 0;
  left: 50.2%;

  bottom: -20%;
  display: block;

  width: 180%;
  max-width: 1320px;
  height: auto;

  object-fit: contain;
  transform: translateX(-50%);
}

/* 特色 */

.auth-layout__features {
  position: relative;
  z-index: 3;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  flex: 0 0 auto;
  gap: 16px;
  margin-top: 8px;
}

.auth-feature {
  display: flex;
  min-width: 0;
  align-items: center;
  gap: 11px;
}

.auth-feature:not(:last-child) {
  padding-right: 14px;
  border-right: 1px solid rgb(210 197 187 / 65%);
}

.auth-feature__icon {
  display: grid;
  flex: 0 0 auto;
  width: 42px;
  height: 42px;
  place-items: center;
  border-radius: 50%;
  color: var(--auth-primary);
  background: #fff1e8;
  font-size: 18px;
  box-shadow: 0 6px 16px rgb(255 90 0 / 8%);
}

.auth-feature__content {
  display: flex;
  min-width: 0;
  flex-direction: column;
  gap: 2px;
}

.auth-feature strong {
  color: #30343b;
  font-size: 13px;
  font-weight: 800;
}

.auth-feature small {
  overflow: hidden;
  color: #9299a4;
  font-size: 10px;
  line-height: 1.35;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 右側 */

.auth-layout__form-area {
  display: flex;
  min-width: 0;
  min-height: 0;
  align-items: center;
  justify-content: center;
  padding: 13px 0;
  z-index: 1;
}

.auth-layout__form-area :deep(> *) {
  width: 100%;
  max-width: 560px;
}

.auth-layout--register .auth-layout__form-area {
  align-items: center;
}

/* 較矮的桌面螢幕 */

@media (min-width: 821px) {
  .auth-layout__container {
    padding-top: 10px;
    padding-bottom: 20px;
  }

  .auth-layout__logo :deep(svg),
  .auth-layout__logo :deep(img) {
    height: 34px;
  }

  .auth-layout__intro {
    margin-top: 16px;
  }

  .auth-layout__intro h1 {
    font-size: 38px;
  }

  .auth-layout__intro p {
    margin-top: 10px;
    font-size: 14px;
  }

  .auth-layout__accent {
    margin-top: 10px;
  }

  //   .auth-layout__visual img {
  //     max-height: 320px;
  //   }

  .auth-layout__features {
    margin-top: 4px;
  }
}

/* 平板 */

@media (max-width: 1100px) {
  .auth-layout__container {
    grid-template-columns:
      minmax(0, 1fr)
      minmax(390px, 460px);
    gap: 34px;
    padding-right: 32px;
    padding-left: 32px;
  }

  .auth-layout__intro h1 {
    font-size: 44px;
  }

  .auth-layout__features {
    grid-template-columns: 1fr;
    gap: 8px;
  }

  .auth-feature:not(:last-child) {
    padding-right: 0;
    border-right: 0;
  }

  .auth-feature small:last-child {
    display: none;
  }
}

/* 手機與小平板 */

@media (max-width: 820px) {
  .auth-layout {
    height: auto;
    min-height: 100dvh;
    overflow-y: auto;
    background: #fffaf6;
  }

  .auth-layout__container {
    display: block;
    width: 100%;
    height: auto;
    min-height: 100dvh;
    padding: 20px 14px 32px;
  }

  .auth-layout__brand {
    display: none;
  }

  .auth-layout__form-area {
    display: block;
    width: min(520px, 100%);
    max-height: none;
    margin: 0 auto;
  }

  .auth-layout__form-area :deep(> *) {
    max-width: none;
    max-height: none;
  }
}
</style>
