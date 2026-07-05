<script setup lang="ts">
import MgButton from "@/components/ui/Button.vue";
</script>
<template>
  <section class="home-hero">
    <div class="mg-container hero-content">
      <div class="hero-text">
        <MgButton variant="delivery" rounded="pill" type="button">
          <template #icon>
            <i class="bi bi-bicycle"></i>
          </template>
          外送到府
        </MgButton>
        <h1>
          美味即刻送達<br />
          <span>MealGo</span> 讓你吃得更好！
        </h1>

        <p>多樣美食・快速外送・安心品質</p>

        <div class="hero-actions">
          <MgButton>立即訂餐</MgButton>
          <!-- <button class="btn btn-primary">立即訂餐</button> -->
          <!-- <button class="btn btn-outline-secondary">查看附近店家</button> -->
          <MgButton variant="outline">查看附近店家</MgButton>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped lang="scss">
.home-hero {
  min-height: 380px;

  display: flex;
  //   align-items: center;
  opacity: 0.97;
  background-position: 65% center;
  background: url("@/assets/images/hero-bg.png") right center / cover no-repeat;
  padding: 32px 0 66px;
}

.hero-content {
  display: flex;
  align-items: center;
  //   margin: 0 42px;
}

.hero-text {
  color: #222;
  text-shadow:
    -2px -2px 0 #fff,
    2px -2px 0 #fff,
    -2px 2px 0 #fff,
    2px 2px 0 #fff;
}
.hero-badge {
  display: inline-flex;
  gap: 8px;
  padding: 8px 16px;
  border-radius: 999px !important;
  background: var(--mg-primary);
  font-weight: 700;
}

.hero-text h1 {
  margin-top: 20px;
  font-size: clamp(1.8rem, 4vw, 4rem);
  font-weight: 900;
  line-height: 1.15;
}

.hero-text h1 span {
  color: var(--mg-primary);
}

.hero-actions {
  display: flex;
  gap: 16px;
  margin-top: 24px;
}

@media (max-width: 768px) {
  .home-hero {
    min-height: 320px;
    padding: 32px 0 50px;
    background-position: 65% center;
    background: url("@/assets/images/hero-bg.png") right center / cover
      no-repeat;
  }

  .hero-actions {
    flex-direction: column;
    max-width: 220px;
  }

  .hero-title,
  .hero-subtitle,
  .hero-desc {
    color: #222;
    text-shadow:
      -1px -1px 0 #fff,
      1px -1px 0 #fff,
      -1px 1px 0 #fff,
      1px 1px 0 #fff;
  }
}

@media (max-width: 768px) {
  .home-hero {
    padding: 16px 0 36px;
    background-position: 90% center;
  }
  .hero-text h1 {
    // font-size: 1.8rem;
    line-height: 1.15;
  }
}
</style>
