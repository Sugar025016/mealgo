<script setup lang="ts">
defineProps({
  image: { type: String, default: "" },
  shopId: { type: Number, default: 1 },
  name: { type: String, default: "店家名稱" },
  description: { type: String, default: "店家描述" },
  deliveryFee: { type: Number, default: 30 },
  distance: { type: String, default: "1.2 km" },
  rating: { type: Number, default: 4.8 },
  reviewCount: { type: Number, default: 235 },
  deliveryTime: { type: String, default: "25~35 分" },
  tags: {
    type: Array,
    default: () => ["便當", "健康餐", "人氣"],
  },
});
</script>

<template>
  <RouterLink :to="`/shops/${shopId}`" class="shop-card">
    <div class="shop-card">
      <div class="shop-card__image">
        <img :src="image" :alt="name" />

        <span class="shop-card__badge">
          <i class="bi bi-fire"></i>
          熱門
        </span>

        <button class="shop-card__favorite" type="button">
          <i class="bi bi-heart"></i>
        </button>
      </div>

      <div class="shop-card__body">
        <h5 class="shop-card__title">
          {{ name }}
        </h5>

        <div class="shop-card__meta">
          <span class="shop-card__rating">
            <i class="bi bi-star-fill"></i>
            {{ rating }}
          </span>

          <span class="shop-card__review">({{ reviewCount }})</span>

          <span class="shop-card__time">
            <i class="bi bi-clock"></i>
            {{ deliveryTime }}
          </span>
        </div>

        <!-- <div class="shop-card__tags">
        <span
          v-for="tag in tags"
          :key="String(tag)"
          class="shop-card__tag"
        >
          {{ tag }}
        </span>
      </div> -->

        <p class="shop-card__description">
          {{ description }}
        </p>

        <div class="shop-card__footer">
          <span>
            <i class="bi bi-bicycle"></i>
            ${{ deliveryFee }}
          </span>

          <span>
            <i class="bi bi-geo-alt"></i>
            {{ distance }}
          </span>
        </div>
      </div>
    </div>
  </RouterLink>
</template>

<style scoped lang="scss">
.shop-card {
  background: #fff;
  border-radius: 20px;
  overflow: hidden;
  cursor: pointer;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.075);
  transition:
    transform 0.22s ease,
    box-shadow 0.22s ease;

  margin-top: 4px;
  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 12px 26px rgba(0, 0, 0, 0.11);
  }
}

.shop-card__image {
  position: relative;
  aspect-ratio: 16 / 9;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    transition: transform 0.35s ease;
  }
}

.shop-card:hover .shop-card__image img {
  transform: scale(1.04);
}

.shop-card__badge {
  position: absolute;
  top: 10px;
  left: 10px;
  height: 26px;
  padding: 0 10px;
  border-radius: 999px;
  background: rgba(255, 90, 0, 0.95);
  color: #fff;
  font-size: 0.72rem;
  font-weight: 800;
  display: inline-flex;
  align-items: center;
  gap: 5px;
}

.shop-card__favorite {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 34px;
  height: 34px;
  border: 0;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.94);
  color: var(--mg-primary);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.14);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition:
    background-color 0.2s ease,
    color 0.2s ease;

  &:hover {
    background: var(--mg-primary);
    color: #fff;
  }
}

.shop-card__body {
  padding: 8px 16px 12px;
}

.shop-card__title {
  margin: 0 0 4px;
  color: #222;
  font-size: 1.08rem;
  font-weight: 900;
  line-height: 1.25;
}

.shop-card__meta {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-bottom: 4px;
  color: #666;
  font-size: 0.88rem;
}

.shop-card__rating {
  color: #f59e0b;
  font-weight: 800;

  i {
    font-size: 0.8rem;
  }
}

.shop-card__review {
  color: #777;
}

.shop-card__time {
  margin-left: auto;
  color: #666;
  white-space: nowrap;
}

.shop-card__description {
  margin: 0 0 7px;
  color: #666;
  font-size: 0.9rem;
  line-height: 1.35;
}

.shop-card__footer {
  display: flex;
  justify-content: space-between;
  color: #444;
  font-size: 0.9rem;

  span {
    display: inline-flex;
    align-items: center;
    gap: 4px;
  }

  i {
    color: var(--mg-primary);
  }
}
</style>
