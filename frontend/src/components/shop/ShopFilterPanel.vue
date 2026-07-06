<script setup lang="ts">
const sortOptions = [
  { label: "推薦排序", value: "recommend" },
  { label: "評價最高", value: "rating" },
  { label: "距離最近", value: "distance" },
  { label: "外送費最低", value: "deliveryFee" },
];

const timeOptions = [
  { label: "20 分鐘內", value: "20" },
  { label: "20–30 分鐘", value: "20-30" },
  { label: "30–40 分鐘", value: "30-40" },
  { label: "40 分鐘以上", value: "40" },
];

const feeOptions = [
  { label: "免外送費", value: "free" },
  { label: "$20 以下", value: "under20" },
  { label: "$20–$40", value: "20-40" },
  { label: "$40 以上", value: "over40" },
];

const ratingOptions = [
  { label: "4 以上", value: "4" },
  { label: "4.5 以上", value: "4.5" },
];
</script>

<template>
  <aside class="shop-filter-panel">
    <div class="shop-filter-panel__header">
      <h3>篩選條件</h3>
      <button type="button">清除所有</button>
    </div>

    <div class="shop-filter-panel__group">
      <h4>排序方式</h4>

      <label
        v-for="item in sortOptions"
        :key="item.value"
        class="shop-filter-panel__option"
      >
        <input
          type="radio"
          name="sort"
          :value="item.value"
          :checked="item.value === 'recommend'"
        />
        <span>{{ item.label }}</span>
      </label>
    </div>

    <div class="shop-filter-panel__group">
      <h4>外送時間</h4>

      <label
        v-for="item in timeOptions"
        :key="item.value"
        class="shop-filter-panel__option"
      >
        <input type="checkbox" :value="item.value" />
        <span>{{ item.label }}</span>
      </label>
    </div>

    <div class="shop-filter-panel__group">
      <h4>外送費</h4>

      <label
        v-for="item in feeOptions"
        :key="item.value"
        class="shop-filter-panel__option"
      >
        <input type="checkbox" :value="item.value" />
        <span>{{ item.label }}</span>
      </label>
    </div>

    <div class="shop-filter-panel__group">
      <h4>評分</h4>

      <div class="shop-filter-panel__rating-list">
        <button
          v-for="item in ratingOptions"
          :key="item.value"
          type="button"
          class="shop-filter-panel__rating"
        >
          <i class="bi bi-star-fill"></i>
          {{ item.label }}
        </button>
      </div>
    </div>

    <button type="button" class="shop-filter-panel__submit">
      顯示結果（128）
    </button>
  </aside>
</template>

<style scoped lang="scss">
// .shop-filter-panel {
//   width: 280px;
//   padding: 24px;
//   border-radius: 24px;
//   background: #ffffff;
//   box-shadow: 0 12px 34px rgba(17, 24, 39, 0.08);
// }
.shop-filter-panel {
  position: sticky;
//   top: 104px;
  align-self: start;

  max-height: calc(100vh - 98px);
  overflow-y: auto;

  padding: 24px;
  border-radius: 24px;
  background: #ffffff;
  box-shadow: 0 12px 34px rgba(17, 24, 39, 0.08);
}

.shop-filter-panel__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 18px;
  border-bottom: 1px solid #eeeeee;

  h3 {
    margin: 0;
    font-size: 22px;
    font-weight: 900;
    color: #1f2937;
  }

  button {
    border: 0;
    background: transparent;
    color: #ff5a1f;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;
  }
}

.shop-filter-panel__group {
  padding: 22px 0;
  border-bottom: 1px solid #eeeeee;

  h4 {
    margin: 0 0 14px;
    font-size: 17px;
    font-weight: 800;
    color: #1f2937;
  }
}

.shop-filter-panel__option {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 14px;
  color: #4b5563;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;

  &:last-child {
    margin-bottom: 0;
  }

  input {
    width: 20px;
    height: 20px;
    accent-color: #ff5a1f;
    cursor: pointer;
  }
}

.shop-filter-panel__rating-list {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.shop-filter-panel__rating {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 18px;
  border: 1px solid #eeeeee;
  border-radius: 12px;
  background: #ffffff;
  color: #374151;
  font-size: 15px;
  font-weight: 700;
  cursor: pointer;

  i {
    color: #ff5a1f;
  }

  &:hover {
    border-color: #ffb799;
    background: #fff4ef;
  }
}

.shop-filter-panel__submit {
  width: 100%;
  height: 54px;
  margin-top: 22px;
  border: 0;
  border-radius: 14px;
  background: #ff5a1f;
  color: #ffffff;
  font-size: 17px;
  font-weight: 900;
  cursor: pointer;
  box-shadow: 0 10px 24px rgba(255, 90, 31, 0.28);

  &:hover {
    background: #f04d14;
  }

  &:active {
    background: #dc3f0b;
    box-shadow: inset 0 3px 8px rgba(0, 0, 0, 0.16);
  }
}
</style>