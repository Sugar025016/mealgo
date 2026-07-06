<script setup lang="ts">
// import { ref, computed, nextTick } from "vue";
import { ref, computed, onMounted, onBeforeUnmount, nextTick } from "vue";
import ShopCard from "@/components/shop/ShopCard.vue";

const allShops = Array.from({ length: 60 }, (_, index) => ({
  id: index + 1,
  name: `店家名稱 ${index + 1}`,
  image: `https://picsum.photos/500/300?random=${index + 1}`,
  rating: 4.8,
  reviewCount: 235,
  deliveryTime: "25–35 分鐘",
  deliveryFee: 30,
  distance: "1.2 km",
}));

const page = ref(1);
const pageSize = 9;
const isLoading = ref(false);
const loadMoreTarget = ref<HTMLElement | null>(null);

const visibleShops = computed(() => {
  return allShops.slice(0, page.value * pageSize);
});

const hasMore = computed(() => {
  return visibleShops.value.length < allShops.length;
});

async function loadMore() {
  if (isLoading.value || !hasMore.value) return;

  const oldCount = visibleShops.value.length;

  isLoading.value = true;

  setTimeout(async () => {
    page.value += 1;
    isLoading.value = false;

    await nextTick();

    const firstNewCard = document.querySelector<HTMLElement>(
      `[data-shop-index="${oldCount}"]`
    );

    firstNewCard?.scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  }, 500);
}


function autoLoadMore() {
  if (isLoading.value || !hasMore.value) return;

  isLoading.value = true;

  setTimeout(() => {
    page.value += 1;
    isLoading.value = false;
  }, 500);
}

let observer: IntersectionObserver | null = null;
onMounted(() => {
  observer = new IntersectionObserver(
    (entries) => {
      const entry = entries[0];

      if (entry.isIntersecting) {
        autoLoadMore();
      }
    },
    {
      root: null,
      threshold: 0.2,
    },
  );

  if (loadMoreTarget.value) {
    observer.observe(loadMoreTarget.value);
  }
});

onBeforeUnmount(() => {
  if (observer) {
    observer.disconnect();
  }
});
</script>

<template>
  <section class="shop-list">
    <div class="shop-list__grid">
      <div
        v-for="(shop, index) in visibleShops"
        :key="shop.id"
        class="shop-list__item"
        :data-shop-index="index"
      >
        <ShopCard
          :image="shop.image"
          :name="shop.name"
          :rating="shop.rating"
          :review-count="shop.reviewCount"
          :delivery-time="shop.deliveryTime"
          :delivery-fee="shop.deliveryFee"
          :distance="shop.distance"
        />
      </div>
    </div>

    <div class="shop-list__load-more">
      <button
        v-if="hasMore"
        type="button"
        class="shop-list__load-more-btn"
        :disabled="isLoading"
        @click="loadMore"
      >
        <span v-if="isLoading">載入中...</span>
        <span v-else>載入更多店家</span>
      </button>

      <p v-else class="shop-list__no-more">
        已經沒有更多店家了
      </p>
    </div>
  </section>
</template>

<style scoped lang="scss">
.shop-list {
  width: 100%;
}

.shop-list__grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 22px;
}

.shop-list__item {
  scroll-margin-top: 150px;
}

.shop-list__load-more {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 90px;
  margin-top: 24px;
}

.shop-list__load-more-btn {
  height: 44px;
  padding: 0 28px;
  border: 0;
  border-radius: 999px;
  background: #ff5a1f;
  color: #ffffff;
  font-size: 15px;
  font-weight: 800;
  cursor: pointer;
  box-shadow: 0 8px 20px rgba(255, 90, 31, 0.24);

  &:hover {
    background: #f04d14;
  }

  &:active {
    background: #dc3f0b;
  }

  &:disabled {
    cursor: not-allowed;
    opacity: 0.7;
  }
}

.shop-list__no-more {
  margin: 0;
  color: #8a8a8a;
  font-size: 14px;
  font-weight: 700;
}

@media (max-width: 1200px) {
  .shop-list__grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 768px) {
  .shop-list__grid {
    grid-template-columns: 1fr;
  }
}
</style>