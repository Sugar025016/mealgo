<script setup lang="ts">
import HomeHero from "@/components/home/HomeHero.vue";
import HorizontalScroll from "@/components/ui/HorizontalScroll.vue";
import MgCategory from "@/components/category/MgCategory.vue";
import SectionTitle from "@/components/ui/SectionTitle.vue";
import ShopCard from "@/components/shop/ShopCard.vue";

// import ShopSection from "@/components/shop/ShopSection.vue";

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
import ShopHorizontalList from "@/components/shop/ShopHorizontalList.vue";

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
  <main>
    <HomeHero> </HomeHero>
    <div class="category-scroll mg-scroll-container">
      <HorizontalScroll :cardWidth="100" :keepCards="1">
        <MgCategory
          v-for="item in categories"
          :key="item.id"
          :title="item.name"
          :image="item.image"
        />
      </HorizontalScroll>
    </div>

    <div class="hot-shops mg-scroll-container">
      <SectionTitle
        title="熱門店家"
        icon="bi-fire"
        class="hot-shops-cards"
        more
      />
      <!-- <HorizontalScroll class=" hot-shops-cards"> -->
      <HorizontalScroll :cardWidth="100" :keepCards="1">
        <ShopHorizontalList />
      </HorizontalScroll>
    </div>
  </main>
</template>

<style scoped lang="scss">
main {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
  //   gap: 56px;

  .category-scroll {
    position: relative;
    z-index: 2;
    margin-top: -40px;
    // padding: 0 32px;
  }
  .home-hot-shops__shop-card {
    position: relative;
    z-index: 2;
    width: 100%;
    padding: 0 32px;
  }
  .shop-scroll-list {
    display: flex;
    flex-wrap: nowrap;
    gap: 24px;
  }
}
</style>
