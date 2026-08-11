<script setup lang="ts">
import aboutHero from "@/assets/images/about/about-hero.png";

type Feature = {
  icon: string;
  title: string;
  description: string;
};

const heroStyle = {
  backgroundImage: `url(${aboutHero})`,
};

const features: Feature[] = [
  {
    icon: "bi-stopwatch",
    title: "快速便利",
    description: "輕鬆搜尋餐廳，快速完成點餐",
  },
  {
    icon: "bi-cup-hot",
    title: "多元選擇",
    description: "探索早餐、便當、麵食與更多美味",
  },
  {
    icon: "bi-shield-check",
    title: "安心品質",
    description: "清楚的訂單資訊與可靠的外送流程",
  },
];
</script>

<template>
  <div class="about-page">
    <!-- Hero -->
    <section class="about-hero" :style="heroStyle">
      <div class="about-hero__container">
        <div class="about-hero__content">
          <h1 class="about-hero__title">讓每一餐，都更簡單</h1>

          <p class="about-hero__description">
            MealGo 致力於打造快速、方便的線上點餐體驗，
            <br />
            連結在地餐廳與每一位喜愛美食的你。
          </p>
        </div>
      </div>
    </section>

    <!-- 關於 MealGo -->
    <section class="about-content">
      <div class="about-content__container">
        <header class="about-content__header">
          <h2 class="about-content__title">關於 MealGo</h2>

          <p class="about-content__description">
            MealGo 是一個連結在地餐廳與消費者的線上點餐平台，
            我們相信科技能讓日常生活更有效率，也能讓美食體驗更貼近生活。
            我們持續優化服務，陪伴你在忙碌的生活中，也能享受每一餐的美好。
          </p>
        </header>

        <!-- 特色 -->
        <div class="about-features">
          <article
            v-for="feature in features"
            :key="feature.title"
            class="about-feature-card"
          >
            <div class="about-feature-card__icon">
              <i class="bi" :class="feature.icon"></i>
            </div>

            <div class="about-feature-card__content">
              <h3 class="about-feature-card__title">
                {{ feature.title }}
              </h3>

              <p class="about-feature-card__description">
                {{ feature.description }}
              </p>
            </div>
          </article>
        </div>

        <!-- 理念 -->
        <div class="about-mission">
          <div class="about-mission__icon">
            <i class="bi bi-heart-fill"></i>
          </div>

          <div class="about-mission__content">
            <h2 class="about-mission__title">我們的理念</h2>

            <p class="about-mission__description">用科技縮短美食與你的距離</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped lang="scss">
.about-page {
  background: #ffffff;
}

/* Hero */
.about-hero {
  min-height: 300px;
  background-color: #fff6ec;
  background-position: center 40%;
  background-repeat: no-repeat;
  background-size: cover;
}

.about-hero__container {
  display: flex;
  align-items: center;

  width: min(100% - 64px, 1440px);
  height: 100%;
  min-height: 260px;
  margin: 0 auto;
}

.about-hero__content {
  width: 44%;
}

.about-hero__title {
  margin: 0;
  color: #172033;
  font-size: clamp(38px, 4vw, 60px);
  font-weight: 800;
  line-height: 1.25;
  letter-spacing: 1px;
}

.about-hero__description {
  margin: 22px 0 0;
  color: #505866;
  font-size: 20px;
  line-height: 1.9;
}

/* 主要內容 */
.about-content {
  padding: 36px 0 40px;
  background: #ffffff;
}

.about-content__container {
  width: min(100% - 64px, 1300px);
  margin: 0 auto;
}

.about-content__header {
  max-width: 900px;
  margin: 0 auto 30px;
  text-align: center;
}

.about-content__title {
  margin: 0;
  color: #172033;
  font-size: 32px;
  font-weight: 700;
}

.about-content__description {
  margin: 12px 0 0;
  color: #626a78;
  font-size: 16px;
  line-height: 1.8;
}

/* 特色卡片 */
.about-features {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 20px;
}

.about-feature-card {
  display: flex;
  align-items: center;
  gap: 20px;

  min-height: 128px;
  padding: 22px 28px;

  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: 0 4px 14px rgb(0 0 0 / 5%);
}

.about-feature-card__icon {
  display: flex;
  flex: 0 0 72px;
  align-items: center;
  justify-content: center;

  width: 72px;
  height: 72px;

  color: #ff5f07;
  font-size: 36px;

  background: #fff2ea;
  border-radius: 50%;
}

.about-feature-card__content {
  min-width: 0;
}

.about-feature-card__title {
  margin: 0;
  color: #172033;
  font-size: 21px;
  font-weight: 700;
}

.about-feature-card__description {
  margin: 8px 0 0;
  color: #697180;
  font-size: 15px;
  line-height: 1.6;
}

/* 我們的理念 */
.about-mission {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 18px;

  min-height: 86px;
  margin-top: 20px;
  padding: 14px 24px;

  background: #fff8f3;
  border: 1px solid #ffd8c2;
  border-radius: 12px;
}

.about-mission__icon {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 48px;
  height: 48px;

  color: #ff5f07;
  font-size: 21px;

  background: #ffffff;
  border: 1px solid #ffd8c2;
  border-radius: 50%;
}

.about-mission__content {
  text-align: center;
}

.about-mission__title {
  margin: 0;
  color: #ff5f07;
  font-size: 21px;
  font-weight: 700;
}

.about-mission__description {
  margin: 4px 0 0;
  color: #626a78;
  font-size: 14px;
}

/* 平板 */
@media (max-width: 991.98px) {
  .about-hero {
    min-height: 340px;
    background-position: 58% center;
  }

  .about-hero__container {
    min-height: 340px;
  }

  .about-hero__content {
    width: 50%;
  }

  .about-hero__title {
    font-size: 38px;
  }

  .about-hero__description {
    margin-top: 16px;
    font-size: 17px;
  }

  .about-features {
    grid-template-columns: 1fr;
  }
}

/* 手機 */
@media (max-width: 767.98px) {
  .about-hero {
    min-height: 520px;
    background-position: 78% center;
  }

  .about-hero::before {
    position: absolute;
    inset: 0;

    content: "";

    background: linear-gradient(
      to bottom,
      rgb(255 247 238 / 98%) 0%,
      rgb(255 247 238 / 88%) 38%,
      rgb(255 247 238 / 18%) 70%
    );
  }

  .about-hero {
    position: relative;
  }

  .about-hero__container {
    position: relative;
    z-index: 1;

    align-items: flex-start;

    width: min(100% - 32px, 560px);
    min-height: 520px;
    padding-top: 44px;
  }

  .about-hero__content {
    width: 100%;
    text-align: center;
  }

  .about-hero__title {
    font-size: 34px;
  }

  .about-hero__description {
    font-size: 16px;
  }

  .about-hero__description br {
    display: none;
  }

  .about-content {
    padding: 30px 0 36px;
  }

  .about-content__container {
    width: min(100% - 32px, 560px);
  }

  .about-content__title {
    font-size: 27px;
  }

  .about-content__description {
    font-size: 15px;
    text-align: left;
  }

  .about-feature-card {
    min-height: 110px;
    padding: 18px;
  }

  .about-feature-card__icon {
    flex-basis: 60px;
    width: 60px;
    height: 60px;
    font-size: 29px;
  }
}

@media (max-width: 399.98px) {
  .about-hero {
    min-height: 320px;
    background-position: 78% 10%;
  }


  .about-hero__container {

    width: min(100% - 32px, 360px);
    min-height: 300px;
    padding-top: 22px;
  }


  .about-hero__title {
    font-size: 29px;
  }

  .about-feature-card {
    gap: 14px;
  }

  .about-feature-card__title {
    font-size: 19px;
  }
}
</style>
