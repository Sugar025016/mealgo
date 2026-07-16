<script setup lang="ts">
import MgSwitche from "@/components/ui/Switche.vue";
import MgLogo from "@/components/ui/Logo.vue";
import { useBreakpoints } from "@vueuse/core";
import MgButton from "@/components/ui/Button.vue";
import { useRouter } from "vue-router";

const router = useRouter();

function goToLogin() {
  router.push("/login");
}

const breakpoints = useBreakpoints({
  md: 768,
  lg: 992,
});

const isMobile = breakpoints.smaller("lg");

const orderType = defineModel<"pickup" | "delivery">({
  default: "delivery",
});

const isLogin = false; // 之後改 Pinia：authStore.isLogin
</script>

<template>
  <header class="app-header">
    <div class="mg-container app-header__inner">
      <MgLogo
        :size="50"
        :text-size="23"
        meal-color="var(--mg-primary)"
        go-color="var(--mg-primary)"
        class="app-header__logo"
      />

      <!-- 外送地址：下拉選單 -->
      <button class="app-header__location">
        <i class="bi bi-geo-alt"></i>
        <div>
          <strong>外送到哪裡？</strong>
          <span>請選擇外送地址</span>
        </div>
        <i class="bi bi-chevron-down"></i>
      </button>
      <div class="app-header__search">
        <input type="text" placeholder="搜尋餐廳、料理或商品" />
        <button>
          <i class="bi bi-search"></i>
        </button>
      </div>

      <!-- 未登入 -->
      <button
        v-if="!isLogin"
        type="button"
        class="app-header__nav-btn"
        @click="goToLogin"
      >
        <i class="bi bi-person"></i>
        <span>登入</span>
      </button>

      <!-- 登入成功：會員中心下拉 -->

      <template v-else>
        <button class="app-header__nav-btn">
          <i class="bi bi-person"></i>
          <span>會員中心</span>
        </button>
        <button class="app-header__nav-btn">
          <i class="bi bi-clock-history"></i>
          <span>歷史訂單</span>
        </button>
      </template>

      <button class="app-header__nav-btn">
        <i class="bi bi-cart3"></i>
        <span>購物車</span>
      </button>

      <!-- <button class="app-header__nav-btn">
        <i class="bi bi-cart3"></i>
        <span>購物車</span>
      </button> -->

      <MgSwitche
        v-model="orderType"
        size="sm"
        :icon-only="isMobile"
        icon-size="14px"
        height="34px"
      />
      <!-- <MgSwitche v-model="orderType" size="sm" :icon-only="isMobile"  /> -->
      <!-- <MgSwitche v-model="orderType"  size="sm" class="app-header__switch" /> -->
    </div>
  </header>
</template>

<style scoped lang="scss">
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;

  height: var(--header-height);

  background: #fff;
  border-bottom: 1px solid var(--mg-border);

  z-index: 1000;
}

.app-header__inner {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.app-header__location {
  height: 48px;
  padding: 0 16px;
  border: 1px solid #ffe2d2;
  border-radius: 999px;
  background: #fff8f4;
  color: #222;
  display: flex;
  align-items: center;
  gap: 8px;
  white-space: nowrap;
  cursor: pointer;

  i {
    color: var(--mg-primary);
  }

  div {
    display: flex;
    flex-direction: column;
    line-height: 1.1;
  }

  strong {
    font-size: 0.9rem;
    font-weight: 900;
  }

  span {
    font-size: 0.72rem;
    color: #777;
  }
}

.app-header__search {
  flex: 1;
  display: flex;
  max-width: 520px;

  flex: 1 1 260px;
  min-width: 240px;
  max-width: 420px;

  height: 42px;
  border: 1px solid var(--mg-border);
  border-radius: 12px;
  overflow: hidden;
  background: #fff;
  //   min-width: 230px; /* 讓 flex:1 的元素在寬度不足時可以縮小 */
}

.app-header__search input {
  flex: 1;
  border: 0;
  padding: 0 0 0 16px;
  outline: none;
}

.app-header__search button {
  width: 52px;
  border: 0;
  background: var(--mg-primary);
  color: #fff;
}

.app-header__nav-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  border: 0;
  background: transparent;
  font-weight: 900;
  white-space: nowrap;
  font-size: 0.95rem;
  cursor: pointer;

  //   color: #ff7c01;
}

@media (max-width: 992px) {
  .app-header__search {
    flex: 1 1 100%;
    max-width: 220px;
    min-width: 160px;
  }
  .app-header__search input {
    font-size: 0.9rem;
    min-width: 120px;
    padding: 0 0 0 12px;
  }
  .app-header__search button {
    width: 50px;
  }
  .app-header__inner {
    gap: 10px;
  }
  .app-header__nav-btn span {
    display: none;
  }

  .app-header__nav-btn {
    width: 42px;
    height: 42px;
    min-width: 42px;
    padding: 0;
    justify-content: center;
    gap: 0;
    border-radius: 999px;
    background: #ffffff;
    box-shadow:
      0 2px 8px var(--btn-shadow-sm),
      0 8px 22px var(--btn-shadow-lg);
  }

  .app-header__nav-btn i {
    font-size: 1.2rem;
    color: var(--mg-primary);
  }

  //   .app-header__location strong,
  //   .app-header__location span {
  //     display: none;
  //   }

  .app-header__location {
    width: auto;
    height: 40px;
    padding: 5px;
    justify-content: center;
  }
}

// @media (max-width: 991px) {
//   .app-header__location,
//   .app-header__member,
//   .app-header__car,
//   .app-header__history {
//     display: none;
//   }

//   //   .app-header__search {
//   //     order: 10;
//   //     flex-basis: 100%;
//   //     max-width: none;
//   //   }
// }

@media (max-width: 768px) {
  .app-header {
    height: auto;
    padding: 12px 0;
  }

  .app-header__inner {
    flex-wrap: wrap;
    gap: 12px;
  }

  .app-header__search {
    order: 10;
    flex-basis: 100%;
    max-width: none;
  }

  .app-header__location,
  .app-header__logo {
    display: none;
  }
}
</style>
