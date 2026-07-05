<script setup lang="ts">
import MgSwitche from "@/components/ui/Switche.vue";
import MgLogo from "@/components/ui/Logo.vue";

const orderType = defineModel<"pickup" | "delivery">({
  default: "delivery",
});
</script>

<template>
  <header class="app-header">
    <div class="mg-container app-header__inner">
      <!-- <div class="app-header__logo">
        <i class="bi bi-shop"></i>
        <span>MealGo</span>
      </div> -->
      <MgLogo :size="50" :text-size="23" color="var(--mg-primary)" class="app-header__logo" />

      <div class="app-header__location">
        <i class="bi bi-geo-alt"></i>
        <span>台北市 中山區</span>
        <i class="bi bi-chevron-down"></i>
      </div>

      <div class="app-header__search">
        <input type="text" placeholder="搜尋餐廳、料理或商品" />
        <button>
          <i class="bi bi-search"></i>
        </button>
      </div>

      <button class="app-header__login">
        <i class="bi bi-person"></i>
        登入
      </button>

      <button class="app-header__cart">
        <i class="bi bi-cart3"></i>
        購物車
      </button>

      <MgSwitche v-model="orderType" class="app-header__switch"/>
    </div>
  </header>
</template>

<style scoped lang="scss">
.app-header {
  height: 72px;
  background: #fff;
  border-bottom: 1px solid var(--mg-border);
  position: sticky;
  top: 0;
  z-index: 20;
}

.app-header__inner {
  height: 100%;
  display: flex;
  align-items: center;
  gap: 24px;
}

// .app-header__logo {
//   display: flex;
//   align-items: center;
//   gap: 8px;
//   color: var(--mg-primary);
//   font-size: 1.2rem;
//   font-weight: 900;
//   white-space: nowrap;
// }

.app-header__location,
.app-header__login,
.app-header__cart {
  display: flex;
  align-items: center;
  gap: 6px;
  border: 0;
  background: transparent;
  font-weight: 900;
  white-space: nowrap;
  font-size: 1rem;
}

.app-header__search {
  flex: 1;
  display: flex;
  max-width: 520px;
  height: 42px;
  border: 1px solid var(--mg-border);
  border-radius: 12px;
  overflow: hidden;
  background: #fff;
}

.app-header__search input {
  flex: 1;
  border: 0;
  padding: 0 16px;
  outline: none;
}

.app-header__search button {
  width: 52px;
  border: 0;
  background: var(--mg-primary);
  color: #fff;
}

.app-header__switch{
    // height: 24px;

}
// MealGo2 0702
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
  .app-header__cart {
    display: none;
  }
}
</style>