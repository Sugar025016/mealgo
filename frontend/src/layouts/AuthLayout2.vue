<script setup lang="ts">
import authHeroImage from "@/assets/images/auth/shop-login.png";
// import authHeroImage from "@/assets/images/auth/auth-login1.png";
import MgLogo from "@/components/ui/Logo.vue";

type AuthMode = "login" | "register";
type AuthVisual = "delivery" | "shop";

withDefaults(
  defineProps<{
    mode?: AuthMode;
    image?: string;
    visual?: AuthVisual;
  }>(),
  {
    mode: "login",
    image: authHeroImage,
    visual: "delivery",
  },
);
</script>

<template>
  <main class="auth-layout" :class="`auth-layout--${mode}`">
    <div class="auth-layout__container">
      <!-- 左側品牌與主視覺 -->
      <section class="auth-layout__brand">
        <!-- Logo 與文案 -->
        <div class="auth-layout__brand-top">
          <div class="auth-layout__logo">
            <MgLogo
              :size="40"
              :text-size="30"
              icon-color="#ff5a1f"
              meal-color="#202938"
              go-color="#ff5a1f"
            />
          </div>

          <div class="auth-layout__intro">
            <!-- 登入文案 -->
            <template v-if="mode === 'login'">
              <h1>歡迎回來</h1>

              <p>
                登入 MealGo，繼續享受美味餐點
                <br />
                與快速外送服務。
              </p>
            </template>

            <!-- 註冊文案 -->
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

        <!-- 主視覺圖片 -->
        <div class="auth-layout__visual">
          <img
            :src="image"
            :class="[
              'auth-layout__visual-image',
              `auth-layout__visual-image--${visual}`,
            ]"
            alt="MealGo 美食外送"
          />
        </div>

        <!-- 特色資訊 -->
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

      <!-- 右側登入或註冊表單 -->
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
  min-height: 100dvh;
  overflow-x: hidden;
  color: var(--auth-text);

  background:
    radial-gradient(circle at 10% 10%, rgb(255 255 255 / 94%), transparent 32%),
    radial-gradient(circle at 92% 16%, rgb(255 225 207 / 42%), transparent 30%),
    linear-gradient(135deg, #f7f4f0, #fffaf6);
}

/* =========================================
   主要左右欄
========================================= */

.auth-layout__container {
  display: grid;
  grid-template-columns:
    minmax(0, 1.08fr)
    minmax(500px, 0.92fr);
  align-items: center;
  gap: clamp(32px, 4vw, 64px);

  width: min(1480px, 100%);
  min-height: 100dvh;
  margin: 0 auto;

  padding: clamp(36px, 2.8vh, 52px) clamp(88px, 4vw, 164px);

  box-sizing: border-box;
}

/* =========================================
   左側區域
========================================= */

.auth-layout__brand {
  display: grid;
  min-width: 0;
  min-height: 0;
  align-self: stretch;

  /*
   * 固定為三個區域：
   * 1. Logo 與標題
   * 2. 主視覺圖片
   * 3. 特色資訊
   *
   * 因此註冊標題多一行時，
   * 不會改變圖片與特色的位置。
   */
  grid-template-rows:
    245px
    minmax(360px, 1fr)
    64px;
}

/* =========================================
   Logo 與標題
========================================= */

.auth-layout__brand-top {
  position: relative;
  z-index: 3;
  min-width: 0;
  min-height: 0;
}

.auth-layout__logo {
  display: inline-flex;
  width: fit-content;
  align-items: center;
}

.auth-layout__intro {
  margin-top: 18px;
}

.auth-layout__intro h1 {
  margin: 0;
  color: #222222;
  font-size: clamp(38px, 3.6vw, 54px);
  font-weight: 900;
  line-height: 1.08;
  letter-spacing: -0.05em;
}

.auth-layout__intro h1 span {
  color: var(--auth-primary);
}

.auth-layout__intro p {
  margin: 14px 0 0;
  color: #687180;
  font-size: clamp(14px, 1.1vw, 16px);
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

/* =========================================
   主視覺圖片
========================================= */

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

.auth-layout__visual-image {
  position: absolute;
  z-index: 1;
  left: 50%;
  bottom: 0;
  display: block;
  height: auto;
  object-fit: contain;
  transform: translateX(-50%);
}

/* 外送機車圖：原圖較寬 */
.auth-layout__visual-image--delivery {
  bottom: 5%;
  width: 118%;
  max-width: 920px;
}

/* 商店圖：原圖主體較高、透明留白較多 */
.auth-layout__visual-image--shop {
  bottom: -14%;
  width: 150%;
  max-width: 1320px;
}

/* =========================================
   特色區
========================================= */

.auth-layout__features {
  position: relative;
  z-index: 3;

  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  align-items: center;
  gap: 16px;

  min-width: 0;
  min-height: 64px;
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

/* =========================================
   右側表單區
========================================= */

.auth-layout__form-area {
  display: flex;
  min-width: 0;
  min-height: 0;
  align-items: center;
  justify-content: center;
  z-index: 1;
}

.auth-layout__form-area :deep(> *) {
  width: 100%;
  max-width: 560px;
  margin: 0;
}

/* =========================================
   中型桌面
========================================= */

@media (max-width: 1280px) {
  .auth-layout__container {
    grid-template-columns:
      minmax(0, 1fr)
      minmax(480px, 0.95fr);

    gap: 30px;
    padding-right: 32px;
    padding-left: 32px;
  }

  .auth-layout__intro h1 {
    font-size: 46px;
  }

  .auth-layout__visual {
    min-height: 330px;
  }

}

/* =========================================
   較窄桌面與平板橫向
========================================= */

@media (max-width: 1100px) {
  .auth-layout__container {
    grid-template-columns:
      minmax(0, 1fr)
      minmax(430px, 490px);

    gap: 24px;
    padding-right: 26px;
    padding-left: 26px;
  }

  .auth-layout__brand {
    grid-template-rows:
      215px
      minmax(300px, 1fr)
      auto;
  }

  .auth-layout__intro {
    margin-top: 14px;
  }

  .auth-layout__intro h1 {
    font-size: 38px;
  }

  .auth-layout__intro p {
    margin-top: 10px;
    font-size: 13px;
  }

  .auth-layout__visual {
    min-height: 300px;
  }

  .auth-layout__visual-image--delivery {
    bottom: 4%;
    width: 125%;
    max-width: 720px;
  }

  .auth-layout__visual-image--shop {
    bottom: -8%;
    width: 158%;
    max-width: 900px;
  }

  .auth-layout__features {
    grid-template-columns: 1fr;
    align-content: center;
    gap: 7px;
  }

  .auth-feature:not(:last-child) {
    padding-right: 0;
    border-right: 0;
  }

  .auth-feature small:last-child {
    display: none;
  }
}

/* =========================================
   螢幕高度較低
========================================= */

@media (max-height: 760px) and (min-width: 821px) {
  .auth-layout__container {
    padding-top: 12px;
    padding-bottom: 12px;
  }

  .auth-layout__brand {
    grid-template-rows:
      185px
      minmax(270px, 1fr)
      52px;
  }

  .auth-layout__intro {
    margin-top: 9px;
  }

  .auth-layout__intro h1 {
    font-size: 36px;
  }

  .auth-layout__intro p {
    margin-top: 7px;
    font-size: 12px;
    line-height: 1.5;
  }

  .auth-layout__accent {
    margin-top: 8px;
  }

  .auth-layout__visual {
    min-height: 270px;
  }

  .auth-layout__visual-image--delivery {
    bottom: 2%;
    width: 108%;
    max-width: 650px;
  }

  .auth-layout__visual-image--shop {
    bottom: -8%;
    width: 132%;
    max-width: 760px;
  }

  .auth-feature__icon {
    width: 36px;
    height: 36px;
    font-size: 15px;
  }

  .auth-feature strong {
    font-size: 12px;
  }

  .auth-feature small {
    font-size: 9px;
  }
}

/* =========================================
   手機與小平板
========================================= */

@media (max-width: 820px) {
  .auth-layout {
    min-height: 100dvh;
    overflow-y: auto;
    background: #fffaf6;
  }

  .auth-layout__container {
    display: block;
    width: 100%;
    min-height: 100dvh;
    padding: 20px 14px 32px;
  }

  .auth-layout__brand {
    display: none;
  }

  .auth-layout__form-area {
    display: block;
    width: min(560px, 100%);
    margin: 0 auto;
  }

  .auth-layout__form-area :deep(> *) {
    max-width: none;
  }
}
</style>
