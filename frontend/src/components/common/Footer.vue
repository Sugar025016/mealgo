<script setup lang="ts">
import MgLogo from "@/components/ui/Logo.vue";
</script>

<template>
  <footer class="mg-footer">
    <div class="mg-container">
      <div class="mg-footer__top">
        <div class="mg-footer__brand">
          <MgLogo
            :size="56"
            :text-size="42"
            color="var(--mg-primary)"
            meal-color="#ffffff"
          />

          <p class="mg-footer__description">
            MealGo 致力於打造快速、方便的線上點餐體驗。<br />
            多樣餐廳・快速外送・安心品質
          </p>

          <div class="mg-footer__tech-tags">
            <span>Vue 3</span>
            <span>TypeScript</span>
            <span>Spring Boot</span>
            <span>MySQL</span>
            <span>REST API</span>
            <span>JWT</span>
          </div>
        </div>

        <div class="mg-footer__links">
          <div class="mg-footer__column">
            <h5>關於 MealGo</h5>
            <RouterLink to="/about">關於我們</RouterLink>
            <a href="#">最新消息</a>
            <RouterLink to="/terms">使用條款</RouterLink>
            <RouterLink to="/privacy">隱私權政策</RouterLink>
            <!-- <a href="#">隱私權政策</a> -->
          </div>

          <div class="mg-footer__column">
            <h5>商家專區</h5>
            <RouterLink to="/merchant-login">商家登入</RouterLink>
            <RouterLink to="/merchant-register">成為合作商家</RouterLink>
            <RouterLink to="/merchant-login">商家後台</RouterLink>
            
          </div>

          <div class="mg-footer__column">
            <h5>技術資訊</h5>
            <span>Vue 3</span>
            <span>TypeScript</span>
            <span>Spring Boot</span>
            <span>REST API</span>
            <span>Docker</span>

            <!-- <a
              class="mg-footer__github"
              href="https://github.com/Sugar025016/mealgo"
              target="_blank"
              rel="noopener noreferrer"
            >
              <i class="bi bi-github"></i>
              GitHub 原始碼
            </a> -->
            <a
              class="mg-footer__github"
              href="＃"
              target="_blank"
              rel="noopener noreferrer"
            >
              <i class="bi bi-github"></i>
              GitHub 原始碼
            </a>
          </div>
        </div>
      </div>

      <div class="mg-footer__bottom">
        <span>© 2026 MealGo. All rights reserved.</span>
        <span>Built with Vue 3 + Spring Boot</span>
      </div>
    </div>
  </footer>
</template>

<style scoped lang="scss">
.mg-footer {
  //   margin-top: 64px;
  //   padding: 56px 0 24px;
  background:
    radial-gradient(
      circle at 15% 20%,
      rgba(255, 90, 31, 0.12),
      transparent 28%
    ),
    linear-gradient(135deg, #111827 0%, #1f2937 100%);
  color: #cbd5e1;

  //   margin-top: 56px;
  padding: 48px 0 22px;
}

.mg-footer__top {
  display: grid;
  grid-template-columns: 1.2fr 1.8fr;
  gap: 72px;
}

.mg-footer__brand {
  max-width: 460px;
}

.mg-footer__description {
  margin: 24px 0 0;
  color: #cbd5e1;
  font-size: 0.95rem;
  line-height: 1.8;
}

.mg-footer__tech-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 24px;

  span {
    display: inline-flex;
    align-items: center;
    height: 34px;
    padding: 0 14px;
    border-radius: 999px;
    background: rgba(255, 255, 255, 0.06);
    border: 1px solid rgba(255, 255, 255, 0.08);
    color: #e5e7eb;
    font-size: 0.82rem;
    font-weight: 700;
  }
}

.mg-footer__links {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 48px;
}

.mg-footer__column {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 14px;

  h5 {
    position: relative;
    margin: 0 0 14px;
    color: #fff;
    font-size: 1.1rem;
    font-weight: 900;

    &::after {
      content: "";
      position: absolute;
      left: 0;
      bottom: -10px;
      width: 34px;
      height: 3px;
      border-radius: 999px;
      background: var(--mg-primary);
    }
  }

  a,
  span {
    color: #cbd5e1;
    font-size: 0.95rem;
    text-decoration: none;
    transition:
      color 0.2s ease,
      transform 0.2s ease;
  }

  a:hover {
    color: var(--mg-primary);
    transform: translateX(4px);
  }
}

.mg-footer__github {
  margin-top: 8px;
  height: 38px;
  padding: 0 16px;
  border-radius: 999px;
  background: var(--mg-primary);
  color: #fff !important;
  font-weight: 800;
  display: inline-flex;
  align-items: center;
  gap: 8px;

  &:hover {
    color: #fff !important;
    transform: translateY(-2px) !important;
    box-shadow: 0 10px 22px rgba(255, 90, 31, 0.28);
  }

  height: 34px;
  padding: 0 14px;
  font-size: 0.86rem;
}

.mg-footer__bottom {
  margin-top: 52px;
  padding-top: 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.07);

  display: flex;
  justify-content: center;
  gap: 24px;
  flex-wrap: wrap;

  color: #94a3b8;
  font-size: 0.88rem;
}

@media (max-width: 991.98px) {
  .mg-footer {
    padding: 48px 0 24px;
  }

  .mg-footer__top {
    grid-template-columns: 1fr;
    gap: 42px;
  }

  .mg-footer__links {
    grid-template-columns: repeat(3, 1fr);
    gap: 28px;
  }
}

@media (max-width: 767.98px) {
  .mg-footer {
    margin-top: 40px;
    padding: 42px 0 22px;
  }

  .mg-footer__links {
    grid-template-columns: 1fr;
    gap: 32px;
  }

  .mg-footer__description {
    font-size: 0.9rem;
  }

  .mg-footer__bottom {
    justify-content: flex-start;
    gap: 8px;
    flex-direction: column;
  }
}
</style>
