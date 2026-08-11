<script setup lang="ts">
type ShopHeroProps = {
  image: string;
  name: string;
  category: string;
  rating: number;
  reviewCount: number;
  deliveryTime: string;
  deliveryFee: number;
  distance: string;
  openTime: string;
  description: string;
  isOpen: boolean;
  isFavorite?: boolean;
};

const props = withDefaults(defineProps<ShopHeroProps>(), {
  isFavorite: false,
});
</script>

<template>
  <section class="shop-hero">
    <!-- 麵包屑 -->
    <!-- <nav class="shop-hero__breadcrumb">
      <RouterLink to="/">首頁</RouterLink>
      <span>/</span>
      <RouterLink to="/shops">店家</RouterLink>
      <span>/</span>
      <span>{{ props.name }}</span>
    </nav> -->

    <div class="shop-hero__main">
      <!-- 左邊圖片 -->
      <div class="shop-hero__image-wrap">
        <img class="shop-hero__image" :src="props.image" :alt="props.name" />

        <button class="shop-hero__favorite" type="button" aria-label="收藏店家">
          <i
            class="bi"
            :class="props.isFavorite ? 'bi-heart-fill' : 'bi-heart'"
          ></i>
        </button>
      </div>

      <!-- 右邊資訊 -->
      <div class="shop-hero__info">
        <span class="shop-hero__tag">
          {{ props.category }}
        </span>

        <h1 class="shop-hero__title">
          {{ props.name }}
        </h1>

        <div class="shop-hero__rating">
          <i class="bi bi-star-fill"></i>
          <strong>{{ props.rating }}</strong>
          <span>({{ props.reviewCount.toLocaleString() }} 則評價)</span>
        </div>

        <div class="shop-hero__meta">
          <div class="shop-hero__meta-item">
            <i class="bi bi-clock"></i>

            <div>
              <strong>{{ props.deliveryTime }}</strong>
              <span>外送時間</span>
            </div>
          </div>

          <div class="shop-hero__meta-item">
            <i class="bi bi-bicycle"></i>

            <div>
              <strong>NT$ {{ props.deliveryFee }}</strong>
              <span>外送費</span>
            </div>
          </div>

          <div class="shop-hero__meta-item">
            <i class="bi bi-geo-alt"></i>

            <div>
              <strong>{{ props.distance }}</strong>
              <span>距離</span>
            </div>
          </div>
        </div>

        <div class="shop-hero__status">
          <span
            class="shop-hero__open"
            :class="{ 'shop-hero__open--closed': !props.isOpen }"
          >
            {{ props.isOpen ? "營業中" : "休息中" }}
          </span>

          <span>營業時間：{{ props.openTime }}</span>
        </div>

        <p class="shop-hero__desc">
          {{ props.description }}
        </p>
      </div>
    </div>
  </section>
</template>

<!-- <style scoped lang="scss">
.shop-hero {
  width: 100%;
  max-width: 100%;
  padding-top: 20px;
}

.shop-hero__breadcrumb {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 18px;
  color: #6b7280;
  font-size: 15px;

  a {
    color: #4b5563;
    text-decoration: none;

    &:hover {
      color: #ff5a00;
    }
  }
}

/* 圖片加大，文字區吃剩下空間 */
.shop-hero__main {
  display: grid;
  grid-template-columns: 420px minmax(0, 1fr);
  gap: 28px;
  align-items: start;
  width: 100%;
  max-width: 100%;
}

.shop-hero__image-wrap {
  position: relative;
  width: 100%;
  aspect-ratio: 16 / 9;
  overflow: hidden;
  border-radius: 14px;
  background: #f3f4f6;
}

.shop-hero__image {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.shop-hero__favorite {
  position: absolute;
  top: 12px;
  right: 12px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 42px;
  height: 42px;
  border: 0;
  border-radius: 999px;
  color: #374151;
  background: rgba(255, 255, 255, 0.92);
  box-shadow: 0 6px 16px rgba(15, 23, 42, 0.16);
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease,
    box-shadow 0.2s ease;

  i {
    font-size: 20px;
  }

  &:hover {
    color: #ff5a00;
    background: #fff;
    box-shadow: 0 8px 20px rgba(255, 90, 0, 0.2);
  }

  &:active {
    color: #ea580c;
  }
}

.shop-hero__info {
  min-width: 0;
  max-width: 100%;
  padding-top: 4px;
  gap: 6px;
}

.shop-hero__tag {
  display: inline-flex;
  align-items: center;
  margin-bottom: 6px;
  padding: 2px 10px;
  border-radius: 999px;
  color: #ff5a00;
  background: #fff1e8;
  font-size: 13px;
  font-weight: 700;
}

.shop-hero__title {
  margin: 0 0 6px;
  color: #111827;
  font-size: 30px;
  font-weight: 800;
  line-height: 1.2;
}

.shop-hero__rating {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 7px;
  margin-bottom: 18px;
  font-size: 15px;

  i,
  strong {
    color: #ff9500;
  }

  span {
    color: #6b7280;
  }
}

.shop-hero__meta {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
  margin-bottom: 20px;
}

.shop-hero__meta-item {
  display: flex;
  align-items: flex-start;
  min-width: 0;
  gap: 8px;

  i {
    flex: 0 0 auto;
    color: #111827;
    font-size: 20px;
    line-height: 1;
  }

  div {
    min-width: 0;
  }

  strong {
    display: block;
    margin-bottom: 4px;
    color: #111827;
    font-size: 15px;
    font-weight: 800;
    white-space: nowrap;
  }

  span {
    display: block;
    color: #6b7280;
    font-size: 12px;
    white-space: nowrap;
  }
}

.shop-hero__status {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 10px;
  color: #6b7280;
  font-size: 14px;
}

.shop-hero__open {
  flex: 0 0 auto;
  padding: 4px 10px;
  border-radius: 999px;
  color: #16a34a;
  background: #dcfce7;
  font-size: 13px;
  font-weight: 700;
}

.shop-hero__open--closed {
  color: #dc2626;
  background: #fee2e2;
}

.shop-hero__desc {
  max-width: 100%;
  margin: 0;
  color: #4b5563;
  font-size: 14px;
  line-height: 1.75;
}

/* 你的右側有購物車，1300 以下圖片稍微縮回來 */
@media (max-width: 1300px) {
  .shop-hero__main {
    grid-template-columns: 380px minmax(0, 1fr);
    gap: 24px;
  }

  .shop-hero__title {
    font-size: 28px;
  }

  .shop-hero__meta {
    gap: 12px;
  }

  .shop-hero__meta-item {
    i {
      font-size: 19px;
    }

    strong {
      font-size: 14px;
    }
  }
}

@media (max-width: 1200px) {
  .shop-hero__main {
    grid-template-columns: 1fr;
    gap: 18px;
  }

  .shop-hero__image-wrap {
    max-width: 560px;
  }

  .shop-hero__title {
    font-size: 30px;
  }
}

@media (max-width: 768px) {
  .shop-hero {
    padding-top: 16px;
  }

  .shop-hero__title {
    font-size: 28px;
  }

  .shop-hero__meta {
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 10px;
  }

  .shop-hero__meta-item {
    gap: 8px;

    i {
      font-size: 18px;
    }

    strong {
      font-size: 13px;
    }

    span {
      font-size: 12px;
    }
  }
}

@media (max-width: 576px) {
  .shop-hero__breadcrumb {
    gap: 8px;
    font-size: 14px;
  }

  .shop-hero__title {
    font-size: 26px;
  }

  .shop-hero__meta {
    grid-template-columns: 1fr;
    gap: 14px;
  }

  .shop-hero__status {
    align-items: flex-start;
    flex-direction: column;
    gap: 8px;
  }
}
</style> -->

<style scoped lang="scss">
.shop-hero {
  width: 100%;
  max-width: 100%;
  padding-top: 5px;
}

.shop-hero__breadcrumb {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 18px;
  color: #6b7280;
  font-size: 15px;

  a {
    color: #4b5563;
    text-decoration: none;

    &:hover {
      color: #ff5a00;
    }
  }
}

/* 圖片加大，文字區吃剩下空間 */
.shop-hero__main {
  display: grid;
  grid-template-columns: 420px minmax(0, 1fr);
  grid-template-columns: minmax(320px, 420px) minmax(0, 1fr);
  grid-template-columns: clamp(360px, 38vw, 460px) minmax(280px, 360px);
  grid-template-columns: minmax(0, 48%) minmax(0, 52%);
  gap: 22px;
  align-items: start;
  width: 100%;
  max-width: 100%;
}

.shop-hero__image-wrap {
  position: relative;
  width: 100%;
  aspect-ratio: 16 / 9;
  overflow: hidden;
  border-radius: 14px;
  background: #f3f4f6;
}

.shop-hero__image {
  display: block;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.shop-hero__favorite {
  position: absolute;
  top: 12px;
  right: 12px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 42px;
  height: 42px;
  border: 0;
  border-radius: 999px;
  color: #374151;
  background: rgba(255, 255, 255, 0.92);
  box-shadow: 0 6px 16px rgba(15, 23, 42, 0.16);
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease,
    box-shadow 0.2s ease;

  i {
    font-size: 20px;
  }

  &:hover {
    color: #ff5a00;
    background: #fff;
    box-shadow: 0 8px 20px rgba(255, 90, 0, 0.2);
  }

  &:active {
    color: #ea580c;
  }
}

.shop-hero__info {
  display: flex;
  flex-direction: column;
  gap: 6px;

  min-width: 0;
  max-width: 100%;
  padding-top: 4px;
}

.shop-hero__tag {
  display: inline-flex;
  align-items: center;
  width: fit-content;
  margin-bottom: 0;
  padding: 3px 10px;
  border-radius: 999px;
  color: #ff5a00;
  background: #fff1e8;
  font-size: 14px;
  font-weight: 800;
}

.shop-hero__title {
  margin: 0;
  color: #111827;
  font-size: 30px;
  font-weight: 800;
  line-height: 1.2;
}

.shop-hero__rating {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 7px;
  margin-bottom: 0;
  font-size: 15px;

  i,
  strong {
    color: #ff9500;
  }

  span {
    color: #6b7280;
  }
}

.shop-hero__meta {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
  margin-bottom: 0;
}

.shop-hero__meta-item {
  display: flex;
  align-items: flex-start;
  min-width: 0;
  gap: 8px;

  i {
    flex: 0 0 auto;
    color: #111827;
    font-size: 20px;
    line-height: 1;
  }

  div {
    min-width: 0;
  }

  strong {
    display: block;
    margin-bottom: 4px;
    color: #111827;
    font-size: 15px;
    font-weight: 800;
    white-space: nowrap;
  }

  span {
    display: block;
    color: #6b7280;
    font-size: 12px;
    white-space: nowrap;
  }
}

.shop-hero__status {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 0;
}

.shop-hero__open {
  flex: 0 0 auto;
  padding: 3px 10px;
  border-radius: 999px;
  color: #16a34a;
  background: #dcfce7;
  font-size: 14px;
  font-weight: 700;
}

.shop-hero__open--closed {
  color: #dc2626;
  background: #fee2e2;
}

.shop-hero__desc {
  max-width: 100%;
  margin: 0;
  color: #4b5563;
  font-size: 14px;
  line-height: 1.75;
}

/* 你的右側有購物車，1300 以下圖片稍微縮回來 */
@media (max-width: 1300px) {
  .shop-hero__main {
    grid-template-columns: 360px minmax(0, 1fr);
    gap: 24px;
  }

  .shop-hero__title {
    font-size: 28px;
  }

  .shop-hero__meta {
    gap: 12px;
  }

  .shop-hero__meta-item {
    i {
      font-size: 19px;
    }

    strong {
      font-size: 14px;
    }
  }
}

@media (max-width: 1200px) {
  .shop-hero__main {
    grid-template-columns: 1fr;
    gap: 18px;
  }

  .shop-hero__image-wrap {
    max-width: 560px;
  }

  .shop-hero__title {
    font-size: 30px;
  }
}

@media (max-width: 768px) {
  //   .shop-hero {
  //     padding-top: 16px;
  //   }

  .shop-hero__title {
    font-size: 28px;
  }

  .shop-hero__meta {
    grid-template-columns: repeat(3, minmax(0, 1fr));
    gap: 10px;
  }

  .shop-hero__meta-item {
    gap: 8px;

    i {
      font-size: 18px;
    }

    strong {
      font-size: 13px;
    }

    span {
      font-size: 12px;
    }
  }
}

@media (max-width: 576px) {
  .shop-hero__breadcrumb {
    gap: 8px;
    font-size: 14px;
  }

  .shop-hero__title {
    font-size: 26px;
  }

  .shop-hero__meta {
    grid-template-columns: 1fr;
    gap: 14px;
  }

  .shop-hero__status {
    align-items: flex-start;
    flex-direction: column;
    gap: 8px;
  }
}
</style>
