<script setup lang="ts">
withDefaults(
  defineProps<{
    image: string;
    imageAlt?: string;
    imagePosition?: string;
  }>(),
  {
    imageAlt: "MealGo 美食外送服務",
    imagePosition: "center",
  },
);
</script>

<template>
  <main class="login-layout">
    <section class="login-layout__visual" aria-label="MealGo 品牌形象">
      <img
        class="login-layout__image"
        :src="image"
        :alt="imageAlt"
        :style="{ objectPosition: imagePosition }"
      />
      <div class="login-layout__shade" aria-hidden="true"></div>
    </section>

    <section class="login-layout__content">
      <div class="login-layout__form">
        <slot />
      </div>
    </section>
  </main>
</template>

<style scoped lang="scss">
.login-layout {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(520px, 1fr);
  min-height: 100dvh;
  background: #fffdfb;
}

.login-layout__visual {
  position: sticky;
  top: 0;
  height: 100dvh;
  min-height: 640px;
  overflow: hidden;
  background: #efe7dc;
}

.login-layout__image {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.login-layout__shade {
  position: absolute;
  inset: 0;
  background:
    linear-gradient(90deg, rgba(37, 28, 23, 0.08), transparent 35%),
    linear-gradient(0deg, rgba(37, 28, 23, 0.08), transparent 28%);
  pointer-events: none;
}

.login-layout__content {
  display: flex;
  min-width: 0;
  min-height: 100dvh;
  align-items: center;
  justify-content: center;
  padding: clamp(36px, 5vw, 80px);
}

.login-layout__form {
  width: min(100%, 440px);
}

@media (max-width: 1199.98px) {
  .login-layout {
    grid-template-columns: minmax(320px, 42%) minmax(0, 58%);
  }

  .login-layout__content {
    padding: 48px 36px;
  }
}

@media (max-width: 899.98px) {
  .login-layout {
    grid-template-columns: minmax(240px, 36%) minmax(0, 64%);
  }

  .login-layout__visual {
    min-height: 560px;
  }

  .login-layout__content {
    padding: 40px 28px;
  }
}

@media (max-width: 699.98px) {
  .login-layout {
    display: block;
  }

  .login-layout__visual {
    display: none;
  }

  .login-layout__content {
    min-height: 100dvh;
    padding: 28px 20px;
  }

  .login-layout__form {
    width: min(100%, 460px);
  }
}

@media (max-height: 720px) and (min-width: 700px) {
  .login-layout__content {
    align-items: flex-start;
  }
}
</style>
