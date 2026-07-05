<script setup lang="ts">
import HelloWorld from "./components/HelloWorld.vue";
import MgButton from "./components/ui/Button.vue";
import MgSwitche from "./components/ui/Switche.vue";
import MgLogo from "./components/ui/Logo.vue";
import ShopCard from "./components/shop/ShopCard.vue";
import HorizontalScroll from "./components/ui/HorizontalScroll.vue";
import MgCategory from "./components/category/MgCategory.vue";

import All from "@/assets/images/category/All.png";
import Pizza from "@/assets/images/category/Pizza.png";
import Noodles from "@/assets/images/category/Noodles.png";
import Dumplings from "@/assets/images/category/Dumplings.png";
import Breakfast from "@/assets/images/category/Breakfast.png";
import ShavedIce from "@/assets/images/category/ShavedIce.png";
import Bento from "@/assets/images/category/Bento.png";
import Steak from "@/assets/images/category/Steak.png";
import Burger from "@/assets/images/category/Burger.png";
import Drink from "@/assets/images/category/Drink.png";
import HotPot from "@/assets/images/category/HotPot.png";
import Dessert from "@/assets/images/category/Dessert.png";
import Sushi from "@/assets/images/category/Sushi.png";
import Rice from "@/assets/images/category/Rice.png";
import Cutlery from "@/assets/images/category/Cutlery2.png";

import HomeHero from "./components/home/HomeHero.vue";
import AppHeader from "./components/common/Header.vue";
import SectionTitle from "./components/ui/SectionTitle.vue";
import Footer from "./components/common/Footer.vue";

// import MgLogoIcon from "./archive/icons/MgLogo喇叭.vue";

// import MgLogoIcon2 from "./archive/icons/MgLogoGood速蓋.vue";
// import MgLogoIcon3 from "./archive/icons/MgLogoGood速2.vue";
// import MgLogoIcon4 from "./archive/icons/MgLogoGood速蓋2.vue";

// import MgLogoIcon from "./archive/icons/MgLogo喇叭.vue";

import { ref, watch } from "vue";

// const theme = ref("theme-orange");
const theme = ref(localStorage.getItem("theme") || "theme-orange");

const orderType = ref<"pickup" | "delivery">("delivery");
function changeTheme(name: string) {
  theme.value = name;
  localStorage.setItem("theme", name);

  watch(orderType, (value) => {
    theme.value = value === "pickup" ? "theme-green" : "theme-orange";

    localStorage.setItem("theme", theme.value);
  });
}

const categories = [
  { id: 1, name: "全部", image: Cutlery },
  { id: 2, name: "便當", image: Bento },
  { id: 3, name: "麵食", image: Noodles },
  { id: 4, name: "早餐", image: Breakfast },
  { id: 5, name: "壽司", image: Sushi },
  { id: 6, name: "便當", image: Bento },
  { id: 7, name: "火鍋", image: HotPot },
  { id: 8, name: "披薩", image: Pizza },
  { id: 9, name: "漢堡", image: Burger },
  { id: 10, name: "甜點", image: Dessert },
  { id: 11, name: "米食", image: Rice },
  { id: 12, name: "飲料", image: Drink },
];
</script>

<template>
  <div :class="theme">
    <AppHeader></AppHeader>
    <!-- <HorizontalScroll></HorizontalScroll> -->
    <HomeHero> </HomeHero>
    <div class="category-scroll">
      <HorizontalScroll :cardWidth="100" :keepCards="1">
        <MgCategory
          v-for="item in categories"
          :key="item.id"
          :title="item.name"
          :image="item.image"
        />
      </HorizontalScroll>
    </div>

    <div class="hot-shops">
      <SectionTitle title="熱門店家" icon="bi-fire" more />
      <!-- <HorizontalScroll class=" hot-shops-cards"> -->
      <HorizontalScroll :cardWidth="100" :keepCards="1" class="px-0 px-md-1">
        <div class="px-2 px-lg-3 col-9 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard image="https://picsum.photos/300/200?random=123" />
        </div>
        <div class="px-2 px-lg-3 col-9 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard image="https://picsum.photos/300/200?random=456" />
        </div>
        <div class="px-2 px-lg-3 col-9 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard image="https://picsum.photos/300/200?random=101" />
        </div>
        <div class="px-2 px-lg-3 col-9 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard image="https://picsum.photos/300/200?random=102" />
        </div>
        <div class="px-2 px-lg-3 col-9 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard image="https://picsum.photos/300/200?random=103" />
        </div>
        <div class="px-2 px-lg-3 col-9 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard image="https://picsum.photos/300/200?random=104" />
        </div>
      </HorizontalScroll>
    </div>

    <!-- <div class="px-1">
      <div class="row g-3">
        <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
        <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
        <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
        <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
        <div class="col-12 col-sm-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
      </div>
    </div> -->
    <!-- <a href="https://vitejs.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a> -->
    <!-- <div class="container-fluid px-4 shop-cards">
      <div class="row g-3">
        <div class="col-12 col-md-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
        <div class="col-12 col-md-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
        <div class="col-12 col-md-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
        <div class="col-12 col-md-6 col-lg-4 col-xl-3">
          <ShopCard />
        </div>
      </div>
    </div> -->

    <!-- <div class="col-sm-6 col-md-4 col-lg-3">
      <ShopCard class="shop-card"></ShopCard>
    </div> -->

    <!-- @/assets/images/category/Chinese.png -->
    <!-- <img
      src="@/assets/images/category/Pizza.png"
      alt="MealGo"
      style="width: 300px; height: auto"
    />
    <img
      src="@/assets/icons/MgLogo.png"
      alt="MealGo"
      style="width: 300px; height: auto"
    /> -->
    <!-- <img
      src="@/assets/icons/MgLogo.svg"
      alt="MealGo"
      style="width: 300px; height: auto"
    /> -->

    <!-- <MgLogoGood :size="50" :text-size="18" color="var(--mg-primary)"/> -->
    <!-- <div style="border: 10px solid red">
      <MgLogoIcon :size="160" />
    </div>
    <div style="border: 10px solid red">
      <MgLogoIcon2 :size="160" />
    </div>
    <div style="border: 10px solid red">
      <MgLogoIcon3 :size="160" />
    </div>
    <div style="border: 10px solid red">
      <MgLogoIcon4 :size="160" />
    </div> -->
    <!-- <MgLogo :size="500" :text-size="58" color="var(--mg-primary)" /> -->
    <!-- <MgLogo :size="500" :text-size="28" color="var(--mg-primary)" /> -->
    <!-- <MgLogo color="#22c55e" :size="60" /> -->
    <!-- <button class="btn btn-mg btn-pickup" @click="changeTheme('theme-green')">
      自取
    </button>
    <button
      class="btn btn-mg btn-delivery"
      @click="changeTheme('theme-orange')"
    >
      外送
    </button>
    <button class="btn btn-mg" @click="changeTheme('theme-orange')">
      橘色
    </button>

    <button class="btn btn-mg" @click="changeTheme('theme-green')">綠色</button> -->
    <!-- <MgButton>加入購物車</MgButton>
    <MgButton variant="soft">外送到府</MgButton>
    <MgButton variant="outline">取消</MgButton>
    <MgButton variant="ghost">返回</MgButton>
    <MgButton variant="pickup">返回</MgButton> -->
    <!-- <MgButton>
      <template #icon>
        <i class="bi bi-bicycle"></i>
      </template>

      外送
    </MgButton>

    <MgSwitche></MgSwitche>

    <MgSwitche v-model="orderType" /> -->

    <!-- <MgSwitche
      v-model="orderType"
      @update:modelValue="
        theme = $event === 'pickup' ? 'theme-green' : 'theme-orange'
      "
    /> -->
    <Footer />
  </div>
  <!-- <HelloWorld msg="Vite + Vue" /> -->
</template>

<style scoped>
.hot-shops {
  padding: 0 32px;
  margin: 10px 0;
}
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
.btn-mg {
  border: 0;
  border-radius: 14px;
  padding: 0.65rem 1.1rem;
  font-weight: 600;
  transition:
    background-color 0.2s ease,
    box-shadow 0.2s ease,
    color 0.2s ease;
}

.btn-mg-primary {
  background: var(--btn-bg);
  color: var(--btn-text);
  box-shadow:
    0 2px 6px rgba(0, 0, 0, 0.08),
    0 8px 20px rgba(0, 0, 0, 0.12);

  &:hover {
    background: var(--btn-hover);
    box-shadow:
      0 6px 12px rgba(0, 0, 0, 0.12),
      0 16px 30px rgba(0, 0, 0, 0.18);
  }

  &:active {
    background: var(--btn-active);
    box-shadow:
      inset 0 2px 4px rgba(0, 0, 0, 0.12),
      0 2px 6px rgba(0, 0, 0, 0.08);
  }
}

.btn-mg-soft {
  background: var(--btn-soft);
  color: var(--btn-bg);

  &:hover {
    background: var(--btn-soft-hover);
  }
}

.btn-mg-outline {
  background: transparent;
  color: var(--btn-bg);
  border: 1px solid var(--btn-bg);

  &:hover {
    background: var(--btn-soft);
  }
}

.btn-mg-ghost {
  background: transparent;
  color: var(--btn-bg);

  &:hover {
    background: var(--btn-soft);
  }
}

.category-scroll {
  position: relative;
  z-index: 2;
  margin-top: -40px;
  padding: 0 32px;
}

/* .category-scroll {
  width: min(100% - 32px, 1280px);
  margin: -42px auto 0;
  position: relative;
  z-index: 3;

  background: #fff;
  border-radius: 24px;
  box-shadow: 0 12px 32px rgb(0 0 0 / 0.12);
  padding: 14px 0 10px;
} */

@media (max-width: 768px) {
  .category-scroll {
    margin-top: -45px;
    padding: 0;
  }
  .hot-shops {
    padding: 0 8px;
  }
}
@media (max-width: 576px) {
  .category-scroll {
    margin-top: -35px;
  }
}
</style>
