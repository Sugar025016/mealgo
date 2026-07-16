<script setup lang="ts">
import {
  computed,
  nextTick,
  onBeforeUnmount,
  onMounted,
  ref,
  watch,
} from "vue";
import ShopCard from "@/components/shop/ShopCard.vue";

type FavoriteShop = {
  id: number;
  name: string;
  image: string;
  category: string;
  rating: number;
  reviewCount: number;
  deliveryTime: string;
  deliveryFee: number;
  distance: string;
  isFavorite: boolean;
};

const allFavoriteShops = ref<FavoriteShop[]>([
  {
    id: 1,
    name: "健康少油餐盒",
    image: "https://picsum.photos/600/400?random=101",
    category: "健康餐盒・低卡・少油",
    rating: 4.8,
    reviewCount: 156,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.2 km",
    isFavorite: true,
  },
  {
    id: 2,
    name: "好味雞肉飯",
    image: "https://picsum.photos/600/400?random=102",
    category: "便當・飯類・台式",
    rating: 4.6,
    reviewCount: 243,
    deliveryTime: "25 分鐘",
    deliveryFee: 25,
    distance: "1.6 km",
    isFavorite: true,
  },
  {
    id: 3,
    name: "川味麻辣燙",
    image: "https://picsum.photos/600/400?random=103",
    category: "麻辣燙・辣味・小吃",
    rating: 4.7,
    reviewCount: 189,
    deliveryTime: "40 分鐘",
    deliveryFee: 35,
    distance: "2.1 km",
    isFavorite: true,
  },
  {
    id: 4,
    name: "日式咖哩專賣",
    image: "https://picsum.photos/600/400?random=104",
    category: "日式・咖哩・炸物",
    rating: 4.9,
    reviewCount: 312,
    deliveryTime: "35 分鐘",
    deliveryFee: 30,
    distance: "1.8 km",
    isFavorite: true,
  },
  {
    id: 5,
    name: "牛肉麵食館",
    image: "https://picsum.photos/600/400?random=105",
    category: "牛肉麵・麵食・湯品",
    rating: 4.7,
    reviewCount: 278,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "2.5 km",
    isFavorite: true,
  },
  {
    id: 6,
    name: "鐵板燒便當",
    image: "https://picsum.photos/600/400?random=106",
    category: "便當・鐵板燒・酥排",
    rating: 4.5,
    reviewCount: 168,
    deliveryTime: "30 分鐘",
    deliveryFee: 25,
    distance: "1.4 km",
    isFavorite: true,
  },
  {
    id: 7,
    name: "晨光早餐",
    image: "https://picsum.photos/600/400?random=107",
    category: "早餐・早午餐・飲料",
    rating: 4.8,
    reviewCount: 1258,
    deliveryTime: "20 分鐘",
    deliveryFee: 20,
    distance: "0.8 km",
    isFavorite: true,
  },
  {
    id: 8,
    name: "義式手作披薩",
    image: "https://picsum.photos/600/400?random=108",
    category: "披薩・義大利麵・焗烤",
    rating: 4.6,
    reviewCount: 426,
    deliveryTime: "40 分鐘",
    deliveryFee: 45,
    distance: "3.2 km",
    isFavorite: true,
  },
  {
    id: 9,
    name: "幸福鍋物",
    image: "https://picsum.photos/600/400?random=109",
    category: "火鍋・鍋燒・湯品",
    rating: 4.7,
    reviewCount: 356,
    deliveryTime: "35 分鐘",
    deliveryFee: 35,
    distance: "2.7 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
  {
    id: 10,
    name: "港式燒臘便當",
    image: "https://picsum.photos/600/400?random=110",
    category: "燒臘・便當・港式",
    rating: 4.5,
    reviewCount: 294,
    deliveryTime: "30 分鐘",
    deliveryFee: 30,
    distance: "1.9 km",
    isFavorite: true,
  },
  {
    id: 11,
    name: "鮮味壽司",
    image: "https://picsum.photos/600/400?random=111",
    category: "壽司・生魚片・日式",
    rating: 4.8,
    reviewCount: 521,
    deliveryTime: "45 分鐘",
    deliveryFee: 40,
    distance: "3.5 km",
    isFavorite: true,
  },
  {
    id: 12,
    name: "甜心下午茶",
    image: "https://picsum.photos/600/400?random=112",
    category: "甜點・蛋糕・飲料",
    rating: 4.9,
    reviewCount: 187,
    deliveryTime: "25 分鐘",
    deliveryFee: 20,
    distance: "1.1 km",
    isFavorite: true,
  },
]);

const keyword = ref("");
const page = ref(1);
const pageSize = 6;
const isLoading = ref(false);
const loadMoreTarget = ref<HTMLElement | null>(null);

let observer: IntersectionObserver | null = null;

const filteredShops = computed(() => {
  const normalizedKeyword = keyword.value.trim().toLowerCase();

  if (!normalizedKeyword) {
    return allFavoriteShops.value;
  }

  return allFavoriteShops.value.filter((shop) => {
    return (
      shop.name.toLowerCase().includes(normalizedKeyword) ||
      shop.category.toLowerCase().includes(normalizedKeyword)
    );
  });
});

const visibleShops = computed(() => {
  return filteredShops.value.slice(0, page.value * pageSize);
});

const hasMore = computed(() => {
  return visibleShops.value.length < filteredShops.value.length;
});

async function loadMore() {
  if (isLoading.value || !hasMore.value) return;

  isLoading.value = true;

  // 模擬 API 載入時間，串接後端後可刪除
  await new Promise((resolve) => setTimeout(resolve, 500));

  page.value += 1;
  isLoading.value = false;
}

function removeFavorite(shopId: number) {
  allFavoriteShops.value = allFavoriteShops.value.filter(
    (shop) => shop.id !== shopId,
  );
}

function createObserver() {
  observer?.disconnect();

  if (!loadMoreTarget.value) return;

  observer = new IntersectionObserver(
    (entries) => {
      const entry = entries[0];

      if (entry?.isIntersecting) {
        loadMore();
      }
    },
    {
      root: null,
      rootMargin: "200px 0px",
      threshold: 0.1,
    },
  );

  observer.observe(loadMoreTarget.value);
}

watch(keyword, async () => {
  page.value = 1;

  await nextTick();
  createObserver();
});

watch(
  () => visibleShops.value.length,
  async () => {
    await nextTick();
    createObserver();
  },
);

onMounted(async () => {
  await nextTick();
  createObserver();
});

onBeforeUnmount(() => {
  observer?.disconnect();
});
</script>

<template>
  <main class="favorite-page">
    <header class="favorite-header">
      <div>
        <h1 class="favorite-header__title">我的最愛</h1>

        <p class="favorite-header__description">
          您收藏的店家將會顯示在這裡，方便快速找到喜愛的美食
        </p>
      </div>

      <label class="favorite-search">
        <i class="bi bi-search"></i>

        <input
          v-model="keyword"
          type="search"
          placeholder="搜尋店家名稱"
          aria-label="搜尋收藏店家"
        />
      </label>
    </header>

    <div class="favorite-summary">
      共 {{ filteredShops.length }} 間店家
    </div>

    <div
      v-if="visibleShops.length"
      class="favorite-grid"
    >
      <ShopCard
        v-for="shop in visibleShops"
        :key="shop.id"
        :id="shop.id"
        :name="shop.name"
        :image="shop.image"
        :category="shop.category"
        :rating="shop.rating"
        :review-count="shop.reviewCount"
        :delivery-time="shop.deliveryTime"
        :delivery-fee="shop.deliveryFee"
        :distance="shop.distance"
        :is-favorite="shop.isFavorite"
        @toggle-favorite="removeFavorite(shop.id)"
      />
    </div>

    <div
      v-else
      class="favorite-empty"
    >
      <div class="favorite-empty__icon">
        <i class="bi bi-heart"></i>
      </div>

      <h2>
        {{ keyword ? "找不到符合的收藏店家" : "目前還沒有收藏店家" }}
      </h2>

      <p>
        {{
          keyword
            ? "請嘗試輸入其他店家名稱"
            : "看到喜歡的店家時，點擊愛心即可加入我的最愛。"
        }}
      </p>
    </div>

    <!-- 滾動載入偵測位置 -->
    <div
      v-if="visibleShops.length"
      ref="loadMoreTarget"
      class="load-more-target"
    >
      <div
        v-if="isLoading"
        class="loading-state"
      >
        <span class="loading-state__spinner"></span>
        <span>載入更多店家中...</span>
      </div>

      <p
        v-else-if="!hasMore"
        class="loaded-all"
      >
        已經顯示全部收藏店家
      </p>
    </div>
  </main>
</template>

<style scoped lang="scss">
$primary: #f2692e;
$text-main: #252525;
$text-secondary: #858585;
$border: #e8e8e8;

.favorite-page {
  width: 100%;
  padding: 30px 36px 40px;
  border: 1px solid #f0ebe7;
  border-radius: 18px;
  background: #ffffff;
  box-shadow: 0 8px 28px rgb(0 0 0 / 4%);
}

.favorite-header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 24px;
  padding-bottom: 24px;
  border-bottom: 1px solid $border;
}

.favorite-header__title {
  margin: 0 0 8px;
  color: $text-main;
  font-size: 28px;
  font-weight: 700;
  line-height: 1.3;
}

.favorite-header__description {
  margin: 0;
  color: $text-secondary;
  font-size: 15px;
  line-height: 1.6;
}

.favorite-search {
  display: flex;
  flex: 0 0 260px;
  align-items: center;
  gap: 10px;
  height: 44px;
  padding: 0 14px;
  border: 1px solid #dedede;
  border-radius: 8px;
  background: #ffffff;
  color: #777777;
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;

  &:focus-within {
    border-color: $primary;
    box-shadow: 0 0 0 3px rgb(242 105 46 / 12%);
  }

  input {
    width: 100%;
    min-width: 0;
    border: 0;
    outline: 0;
    background: transparent;
    color: $text-main;
    font-size: 14px;

    &::placeholder {
      color: #aaaaaa;
    }

    &::-webkit-search-cancel-button {
      cursor: pointer;
    }
  }
}

.favorite-summary {
  margin: 22px 0 18px;
  color: #666666;
  font-size: 14px;
}

.favorite-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 22px;
}

/* 空狀態 */

.favorite-empty {
  display: flex;
  min-height: 420px;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 50px 20px;
  text-align: center;
}

.favorite-empty__icon {
  display: flex;
  width: 82px;
  height: 82px;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  border-radius: 50%;
  background: #fff3ed;
  color: $primary;
  font-size: 38px;
}

.favorite-empty h2 {
  margin: 0 0 10px;
  color: $text-main;
  font-size: 21px;
}

.favorite-empty p {
  margin: 0;
  color: $text-secondary;
  font-size: 15px;
  line-height: 1.7;
}

/* 無限滾動 */

.load-more-target {
  display: flex;
  min-height: 90px;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
}

.loading-state {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  color: $text-secondary;
  font-size: 14px;
}

.loading-state__spinner {
  width: 20px;
  height: 20px;
  border: 2px solid #f3d4c5;
  border-top-color: $primary;
  border-radius: 50%;
  animation: loading-spin 0.7s linear infinite;
}

.loaded-all {
  margin: 0;
  color: #aaaaaa;
  font-size: 14px;
}

@keyframes loading-spin {
  to {
    transform: rotate(360deg);
  }
}

/* 小桌面 */

@media (max-width: 1199.98px) {
  .favorite-page {
    padding-inline: 28px;
  }

  .favorite-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

/* 平板 */

@media (max-width: 767.98px) {
  .favorite-page {
    padding: 24px 18px 32px;
    border-radius: 14px;
  }

  .favorite-header {
    align-items: stretch;
    flex-direction: column;
    gap: 18px;
  }

  .favorite-header__title {
    font-size: 24px;
  }

  .favorite-header__description {
    font-size: 14px;
  }

  .favorite-search {
    flex-basis: auto;
    width: 100%;
  }

  .favorite-grid {
    grid-template-columns: 1fr;
    gap: 18px;
  }
}
</style>