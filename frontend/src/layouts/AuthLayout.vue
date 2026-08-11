<script setup lang="ts">
import type { AuthFeature } from "@/types/auth";
import Logo from "@/components/ui/Logo.vue";

defineProps<{
  backgroundImage: string;
  headline: string;
  description: string;
  features: AuthFeature[];
}>();
</script>

<template>
  <main
    class="auth-layout"
    :style="{ '--auth-background': `url(${backgroundImage})` }"
  >
    <section class="auth-layout__intro" aria-labelledby="auth-intro-title">
      <Logo
        class="auth-layout__logo"
        :size="66"
        :text-size="38"
        icon-color="#ff5f07"
        meal-color="#ffffff"
        go-color="#ff5f07"
      />

      <div class="auth-layout__message">
        <h1 id="auth-intro-title">{{ headline }}</h1>
        <span class="auth-layout__accent" aria-hidden="true"></span>
        <p>{{ description }}</p>

        <ul class="auth-layout__features">
          <li v-for="feature in features" :key="feature.title">
            <span class="auth-layout__feature-icon">
              <i class="bi" :class="feature.icon" aria-hidden="true"></i>
            </span>
            <span>
              <strong>{{ feature.title }}</strong>
              <small>{{ feature.description }}</small>
            </span>
          </li>
        </ul>
      </div>
    </section>

    <section class="auth-layout__form-area" aria-label="帳號表單">
      <div class="auth-layout__panel">
        <slot />
      </div>
    </section>
  </main>
</template>

<style scoped lang="scss">
:global(html),
:global(body),
:global(#app) {
  min-height: 100%;
}

:global(body) {
  margin: 0;
}

.auth-layout {
  position: relative;
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(500px, 42%);
  height: 100dvh;
  min-height: 680px;
  overflow: hidden;
  background: #16120f var(--auth-background) center / cover no-repeat;

  &::before {
    position: absolute;
    z-index: 0;
    inset: 0;
    background:
      linear-gradient(
        90deg,
        rgba(6, 6, 6, 0.9) 0%,
        rgba(6, 6, 6, 0.58) 38%,
        rgba(6, 6, 6, 0.08) 69%
      ),
      linear-gradient(0deg, rgba(6, 6, 6, 0.18), transparent 46%);
    content: "";
    pointer-events: none;
  }
}

.auth-layout__intro,
.auth-layout__form-area {
  position: relative;
  z-index: 1;
}

.auth-layout__intro {
  box-sizing: border-box;
  display: flex;
  min-width: 0;
  flex-direction: column;
  padding: clamp(32px, 5vw, 72px);
  color: #fff;
}

.auth-layout__logo {
  width: fit-content;
  flex-shrink: 0;
  transform-origin: left top;
}

.auth-layout__message {
  width: min(100%, 680px);
  margin: auto 0;
  gap: 12px;

  h1 {
    max-width: 620px;
    margin: 0 0 50px 0;
    font-size: clamp(42px, 5vw, 72px);
    font-weight: 500;
    letter-spacing: 0.03em;
    line-height: 1.2;
  }

  > p {
    max-width: 610px;
    margin: 26px 0 0;
    color: rgba(255, 255, 255, 0.8);
    font-size: clamp(15px, 1.35vw, 18px);
    line-height: 1.85;
  }
}

.auth-layout__accent {
  display: block;
  width: 46px;
  height: 4px;
  margin-top: 23px;
  border-radius: 99px;
  background: #ff5f07;
}

.auth-layout__features {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 20px;
  margin: 40px 0 0;
  padding: 0;
  list-style: none;

  li {
    display: flex;
    min-width: 0;
    align-items: center;
    gap: 11px;
  }

  strong,
  small {
    display: block;
  }

  strong {
    font-size: 15px;
  }

  small {
    margin-top: 4px;
    color: rgba(255, 255, 255, 0.68);
    font-size: 12px;
  }
}

.auth-layout__feature-icon {
  display: grid;
  width: 46px;
  height: 46px;
  flex: 0 0 46px;
  border: 1px solid rgba(255, 95, 7, 0.8);
  border-radius: 50%;
  background: rgba(255, 95, 7, 0.16);
  color: #ff6c1a;
  font-size: 20px;
  place-items: center;
}

.auth-layout__form-area {
  box-sizing: border-box;
  display: flex;
  min-width: 0;
  height: 100dvh;
  align-items: center;
  justify-content: center;
  overflow-y: auto;
  padding: 32px clamp(24px, 3vw, 52px);
  scrollbar-gutter: stable;
}

.auth-layout__panel {
  box-sizing: border-box;
  width: min(100%, 620px);
  margin: auto;
  padding: clamp(32px, 2.2vw, 46px) clamp(30px, 3.3vw, 52px);
  border: 1px solid rgba(255, 255, 255, 0.68);
  border-radius: 22px;
  background: rgba(255, 253, 250, 0.95);
  box-shadow: 0 28px 80px rgba(0, 0, 0, 0.25);
  backdrop-filter: blur(14px);
}

@media (max-width: 1099.98px) {
  .auth-layout {
    grid-template-columns: minmax(300px, 40%) minmax(0, 60%);
  }

  .auth-layout__intro {
    padding: 34px 28px;
  }

  .auth-layout__logo {
    transform: scale(0.9);
    margin-top: 36px;
  }

  .auth-layout__message h1 {
    font-size: clamp(34px, 5vw, 50px);
  }

  .auth-layout__features {
    grid-template-columns: 1fr;
    gap: 13px;
    margin-top: 28px;
  }

  .auth-layout__feature-icon {
    width: 40px;
    height: 40px;
    flex-basis: 40px;
  }

  .auth-layout__form-area {
    padding: 24px 20px;
  }

  .auth-layout__panel {
    padding: 32px 34px;
  }
}

@media (max-width: 699.98px) {
  .auth-layout {
    display: block;
    height: auto;
    min-height: 100dvh;
    overflow: visible;
    background: #fffdfb;

    &::before {
      display: none;
    }
  }

  .auth-layout__intro {
    min-height: 220px;
    padding: 24px 20px 28px;
    background:
      linear-gradient(90deg, rgba(5, 5, 5, 0.86), rgba(5, 5, 5, 0.28)),
      var(--auth-background) center 42% / cover no-repeat;
  }

  .auth-layout__logo {
    transform: scale(0.78);
  }

  .auth-layout__message {
    margin: 42px 0 0;

    h1 {
      font-size: 33px;
    }

    > p,
    .auth-layout__accent,
    .auth-layout__features {
      display: none;
    }
  }

  .auth-layout__form-area {
    height: auto;
    min-height: auto;
    overflow: visible;
    padding: 0;
    background: #fffdfb;
  }

  .auth-layout__panel {
    width: 100%;
    max-height: none;
    overflow: visible;
    padding: 32px 20px 40px;
    border: 0;
    border-radius: 0;
    background: #fffdfb;
    box-shadow: none;
    backdrop-filter: none;
  }
}
</style>
