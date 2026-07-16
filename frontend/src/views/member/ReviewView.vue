<script setup lang="ts">
import { computed, ref, watch } from "vue";
import ReviewCard, {
  type ReviewItem,
  type ReviewStatus,
} from "@/components/member/review/ReviewCard.vue";

type ReviewFilter = "ALL" | ReviewStatus;
type ReviewSort = "LATEST" | "OLDEST" | "HIGHEST";

const activeFilter = ref<ReviewFilter>("ALL");
const activeSort = ref<ReviewSort>("LATEST");

const currentPage = ref(1);
const pageSize = ref(3);

const reviews = ref<ReviewItem[]>([
  {
    id: 1,
    shopName: "晨光早餐",
    shopImage: "https://picsum.photos/500/500?random=101",
    orderDate: "2026/07/15 09:20",
    orderNumber: "MG202607150128",
    products: ["厚切豬排蛋吐司", "薯餅", "奶茶"],
    status: "REVIEWED",
    rating: 4,
    content:
      "吐司酥香、豬排口感不錯，整體份量足夠。奶茶甜度剛好，但薯餅如果再熱一點會更好。",
    shopReply:
      "謝謝你的回饋，我們會再注意餐點保溫，期待再次為你服務！",
  },
  {
    id: 2,
    shopName: "川味小館",
    shopImage: "https://picsum.photos/500/500?random=102",
    orderDate: "2026/07/10 12:35",
    orderNumber: "MG202607100935",
    products: ["麻辣牛肉麵", "涼拌小黃瓜", "酸梅湯"],
    status: "REVIEWED",
    rating: 5,
    content:
      "湯頭麻香夠味，牛肉很嫩，份量也很足夠，小黃瓜清爽解膩，之後還會再回購。",
  },
  {
    id: 3,
    shopName: "義式廚房 Italian Kitchen",
    shopImage: "https://picsum.photos/500/500?random=103",
    orderDate: "2026/07/08 18:45",
    orderNumber: "MG202607081845",
    products: ["奶油培根義大利麵", "義式香草麵包", "檸檬紅茶"],
    status: "PENDING",
  },
  {
    id: 4,
    shopName: "日光便當",
    shopImage: "https://picsum.photos/500/500?random=104",
    orderDate: "2026/07/03 11:50",
    orderNumber: "MG202607031150",
    products: ["招牌雞腿便當", "味噌湯"],
    status: "REVIEWED",
    rating: 5,
    content:
      "雞腿外皮酥脆、肉質很多汁，配菜不會太油，整體份量很剛好。",
  },
  {
    id: 5,
    shopName: "元氣早午餐",
    shopImage: "https://picsum.photos/500/500?random=105",
    orderDate: "2026/06/28 08:40",
    orderNumber: "MG202606280840",
    products: ["鮪魚蛋餅", "蘿蔔糕", "豆漿"],
    status: "PENDING",
  },
  {
    id: 6,
    shopName: "好味水餃",
    shopImage: "https://picsum.photos/500/500?random=106",
    orderDate: "2026/06/22 19:10",
    orderNumber: "MG202606221910",
    products: ["高麗菜水餃", "酸辣湯"],
    status: "REVIEWED",
    rating: 4,
    content:
      "水餃皮薄餡多，酸辣湯味道也不錯，整體價格很實惠。",
  },
  {
    id: 7,
    shopName: "大口吃漢堡",
    shopImage: "https://picsum.photos/500/500?random=107",
    orderDate: "2026/06/18 12:20",
    orderNumber: "MG202606181220",
    products: ["雙層牛肉堡", "脆薯", "可樂"],
    status: "REVIEWED",
    rating: 5,
    content:
      "漢堡肉很多汁，薯條也很酥脆，餐點送到時還是熱的。",
  },
]);

const filteredReviews = computed(() => {
  let result = [...reviews.value];

  if (activeFilter.value !== "ALL") {
    result = result.filter(
      (review) => review.status === activeFilter.value,
    );
  }

  if (activeSort.value === "LATEST") {
    result.sort(
      (a, b) =>
        parseOrderDate(b.orderDate) -
        parseOrderDate(a.orderDate),
    );
  }

  if (activeSort.value === "OLDEST") {
    result.sort(
      (a, b) =>
        parseOrderDate(a.orderDate) -
        parseOrderDate(b.orderDate),
    );
  }

  if (activeSort.value === "HIGHEST") {
    result.sort(
      (a, b) => (b.rating ?? 0) - (a.rating ?? 0),
    );
  }

  return result;
});

const totalItems = computed(() => filteredReviews.value.length);

const totalPages = computed(() => {
  return Math.max(
    1,
    Math.ceil(totalItems.value / pageSize.value),
  );
});

const paginatedReviews = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;

  return filteredReviews.value.slice(start, end);
});

const visiblePages = computed(() => {
  const pages: number[] = [];
  const maxVisiblePages = 5;

  let startPage = Math.max(
    1,
    currentPage.value - Math.floor(maxVisiblePages / 2),
  );

  let endPage = Math.min(
    totalPages.value,
    startPage + maxVisiblePages - 1,
  );

  if (endPage - startPage + 1 < maxVisiblePages) {
    startPage = Math.max(
      1,
      endPage - maxVisiblePages + 1,
    );
  }

  for (let page = startPage; page <= endPage; page += 1) {
    pages.push(page);
  }

  return pages;
});

const pageStart = computed(() => {
  if (totalItems.value === 0) return 0;

  return (currentPage.value - 1) * pageSize.value + 1;
});

const pageEnd = computed(() => {
  return Math.min(
    currentPage.value * pageSize.value,
    totalItems.value,
  );
});

const reviewCount = computed(() => {
  return reviews.value.filter(
    (review) => review.status === "REVIEWED",
  ).length;
});

const pendingCount = computed(() => {
  return reviews.value.filter(
    (review) => review.status === "PENDING",
  ).length;
});

function parseOrderDate(date: string) {
  return new Date(date.replace(" ", "T")).getTime();
}

function goToPage(page: number) {
  if (page < 1 || page > totalPages.value) return;

  currentPage.value = page;
  scrollToTop();
}

function goToPreviousPage() {
  goToPage(currentPage.value - 1);
}

function goToNextPage() {
  goToPage(currentPage.value + 1);
}

function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
}

function handleCreateReview(review: ReviewItem) {
  console.log("立即評價", review);
}

function handleEditReview(review: ReviewItem) {
  console.log("編輯評價", review);
}

function handleReorder(review: ReviewItem) {
  console.log("再次訂購", review);
}

watch([activeFilter, activeSort, pageSize], () => {
  currentPage.value = 1;
});

watch(totalPages, (pages) => {
  if (currentPage.value > pages) {
    currentPage.value = pages;
  }
});
</script>

<template>
  <section class="review-view">
    <header class="review-view__header">
      <div>
        <h1 class="review-view__title">我的評價</h1>

        <p class="review-view__description">
          查看你的所有訂單評價與店家回覆。
        </p>
      </div>
    </header>

    <div class="review-view__toolbar">
      <div class="review-view__filters">
        <button
          type="button"
          class="review-view__filter"
          :class="{
            'review-view__filter--active':
              activeFilter === 'ALL',
          }"
          @click="activeFilter = 'ALL'"
        >
          全部
          <span>{{ reviews.length }}</span>
        </button>

        <button
          type="button"
          class="review-view__filter"
          :class="{
            'review-view__filter--active':
              activeFilter === 'PENDING',
          }"
          @click="activeFilter = 'PENDING'"
        >
          待評價
          <span>{{ pendingCount }}</span>
        </button>

        <button
          type="button"
          class="review-view__filter"
          :class="{
            'review-view__filter--active':
              activeFilter === 'REVIEWED',
          }"
          @click="activeFilter = 'REVIEWED'"
        >
          已評價
          <span>{{ reviewCount }}</span>
        </button>
      </div>

      <div class="review-view__sort">
        <i class="bi bi-sort-down" />

        <select
          v-model="activeSort"
          aria-label="評價排序"
        >
          <option value="LATEST">最新評價</option>
          <option value="OLDEST">最早評價</option>
          <option value="HIGHEST">最高評分</option>
        </select>
      </div>
    </div>

    <template v-if="filteredReviews.length">
      <div class="review-view__list">
        <ReviewCard
          v-for="review in paginatedReviews"
          :key="review.id"
          :review="review"
          @review="handleCreateReview"
          @edit="handleEditReview"
          @reorder="handleReorder"
        />
      </div>

      <footer class="review-view__pagination-area">
        <p class="review-view__pagination-info">
          顯示第 {{ pageStart }}－{{ pageEnd }} 筆，
          共 {{ totalItems }} 筆
        </p>

        <nav
          class="review-pagination"
          aria-label="評價分頁"
        >
          <button
            type="button"
            class="review-pagination__button review-pagination__button--arrow"
            :disabled="currentPage === 1"
            aria-label="上一頁"
            @click="goToPreviousPage"
          >
            <i class="bi bi-chevron-left" />
          </button>

          <button
            v-for="page in visiblePages"
            :key="page"
            type="button"
            class="review-pagination__button"
            :class="{
              'review-pagination__button--active':
                currentPage === page,
            }"
            :aria-current="
              currentPage === page ? 'page' : undefined
            "
            @click="goToPage(page)"
          >
            {{ page }}
          </button>

          <button
            type="button"
            class="review-pagination__button review-pagination__button--arrow"
            :disabled="currentPage === totalPages"
            aria-label="下一頁"
            @click="goToNextPage"
          >
            <i class="bi bi-chevron-right" />
          </button>
        </nav>

        <label class="review-view__page-size">
          <span>每頁顯示</span>

          <select v-model="pageSize">
            <option :value="3">3 筆</option>
            <option :value="5">5 筆</option>
            <option :value="10">10 筆</option>
          </select>
        </label>
      </footer>
    </template>

    <div
      v-else
      class="review-view__empty"
    >
      <div class="review-view__empty-icon">
        <i class="bi bi-chat-square-text" />
      </div>

      <h2>目前沒有評價</h2>

      <p>
        完成訂單後，就可以在這裡留下你的用餐心得。
      </p>
    </div>
  </section>
</template>

<style scoped lang="scss">
.review-view {
  min-height: calc(100vh - 116px);
  padding: 24px;

  border: 1px solid #ededed;
  border-radius: 14px;

  background: #fff;
  box-shadow: 0 5px 20px rgb(0 0 0 / 4%);
}

.review-view__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
}

.review-view__title {
  margin: 0;
  color: #242424;
  font-size: 30px;
  font-weight: 700;
  line-height: 1.35;
}

.review-view__description {
  margin: 6px 0 0;
  color: #888;
  font-size: 15px;
}

.review-view__toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;

  margin-top: 24px;
  padding-bottom: 18px;

  border-bottom: 1px solid #ededed;
}

.review-view__filters {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.review-view__filter {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 7px;

  min-width: 88px;
  height: 40px;
  padding: 0 16px;

  border: 1px solid #dedede;
  border-radius: 9px;

  background: #fff;
  color: #555;

  font-size: 14px;
  font-weight: 600;

  cursor: pointer;
  transition:
    border-color 0.2s ease,
    background-color 0.2s ease,
    color 0.2s ease;
}

.review-view__filter span {
  display: inline-grid;
  min-width: 20px;
  height: 20px;
  place-items: center;

  border-radius: 999px;
  background: #f1f1f1;

  color: #777;
  font-size: 12px;
}

.review-view__filter:hover {
  border-color: #ffb380;
  color: #ff5f07;
}

.review-view__filter--active {
  border-color: #ff5f07;
  background: #fff2ea;
  color: #ff5f07;
}

.review-view__filter--active span {
  background: #ff5f07;
  color: #fff;
}

.review-view__sort {
  position: relative;
  display: flex;
  align-items: center;

  min-width: 145px;
  height: 40px;

  border: 1px solid #dedede;
  border-radius: 9px;
  background: #fff;
}

.review-view__sort > i {
  position: absolute;
  left: 12px;
  color: #777;
  pointer-events: none;
}

.review-view__sort select {
  width: 100%;
  height: 100%;
  padding: 0 34px 0 38px;

  border: 0;
  border-radius: inherit;
  outline: none;

  background: transparent;
  color: #555;

  font-size: 14px;
  font-weight: 600;

  cursor: pointer;
}

.review-view__list {
  display: grid;
  gap: 16px;
  margin-top: 18px;
}

.review-view__pagination-area {
  display: grid;
  grid-template-columns: minmax(180px, 1fr) auto minmax(180px, 1fr);
  align-items: center;
  gap: 20px;

  margin-top: 24px;
  padding-top: 20px;

  border-top: 1px solid #ededed;
}

.review-view__pagination-info {
  margin: 0;
  color: #888;
  font-size: 14px;
}

.review-pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

.review-pagination__button {
  display: inline-grid;
  width: 38px;
  height: 38px;
  place-items: center;

  border: 1px solid #e1e1e1;
  border-radius: 8px;

  background: #fff;
  color: #555;

  font-size: 14px;
  font-weight: 600;

  cursor: pointer;
  transition:
    border-color 0.2s ease,
    background-color 0.2s ease,
    color 0.2s ease;
}

.review-pagination__button:hover:not(:disabled) {
  border-color: #ffb380;
  background: #fff2ea;
  color: #ff5f07;
}

.review-pagination__button--active {
  border-color: #ff5f07;
  background: #ff5f07;
  color: #fff;
}

.review-pagination__button--active:hover:not(:disabled) {
  border-color: #e65500;
  background: #e65500;
  color: #fff;
}

.review-pagination__button:disabled {
  background: #f7f7f7;
  color: #c2c2c2;
  cursor: not-allowed;
}

.review-pagination__button--arrow {
  font-size: 13px;
}

.review-view__page-size {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 8px;

  color: #777;
  font-size: 14px;
}

.review-view__page-size select {
  height: 38px;
  padding: 0 30px 0 12px;

  border: 1px solid #dedede;
  border-radius: 8px;
  outline: none;

  background: #fff;
  color: #555;

  cursor: pointer;
}

.review-view__page-size select:focus {
  border-color: #ff5f07;
}

.review-view__empty {
  display: grid;
  min-height: 420px;
  place-items: center;
  align-content: center;

  padding: 40px 20px;
  text-align: center;
}

.review-view__empty-icon {
  display: grid;
  width: 72px;
  height: 72px;
  place-items: center;

  border-radius: 50%;
  background: #fff2ea;
  color: #ff5f07;

  font-size: 30px;
}

.review-view__empty h2 {
  margin: 18px 0 6px;
  color: #333;
  font-size: 21px;
}

.review-view__empty p {
  margin: 0;
  color: #888;
  font-size: 14px;
}

@media (max-width: 1199.98px) {
  .review-view {
    min-height: auto;
    padding: 20px;
  }

  .review-view__pagination-area {
    grid-template-columns: 1fr;
  }

  .review-view__pagination-info {
    text-align: center;
  }

  .review-view__page-size {
    justify-content: center;
  }
}

@media (max-width: 767.98px) {
  .review-view {
    padding: 16px;
    border-radius: 12px;
  }

  .review-view__title {
    font-size: 25px;
  }

  .review-view__toolbar {
    align-items: stretch;
    flex-direction: column;
  }

  .review-view__filters {
    display: grid;
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }

  .review-view__filter {
    min-width: 0;
    padding: 0 8px;
  }

  .review-view__sort {
    width: 100%;
  }
}

@media (max-width: 575.98px) {
  .review-view {
    padding: 14px;
    border: 0;
    box-shadow: none;
  }

  .review-view__title {
    font-size: 23px;
  }

  .review-view__description {
    font-size: 14px;
  }

  .review-view__filter {
    gap: 4px;
    font-size: 13px;
  }

  .review-view__pagination-area {
    gap: 14px;
  }

  .review-pagination {
    gap: 4px;
  }

  .review-pagination__button {
    width: 34px;
    height: 34px;
  }
}
</style>