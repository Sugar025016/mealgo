<script setup lang="ts">
defineProps({
  image: {
    type: String,
    default: "",
  },
  name: {
    type: String,
    default: "店家名稱",
  },
  description: {
    type: String,
    default: "店家描述",
  },
  deliveryFee: {
    type: Number,
    default: 30,
  },
  distance: {
    type: String,
    default: "1.2 km",
  },

  rating: {
    type: Number,
    default: 4.8,
  },

  reviewCount: {
    type: Number,
    default: 235,
  },

  deliveryTime: {
    type: String,
    default: "25~35 分",
  },
});
</script>

<template>
  <div class="shop-card">
    <div class="shop-card__image">
      <img :src="image" :alt="name" />

      <button class="shop-card__favorite">
        <i class="bi bi-heart"></i>
      </button>
    </div>

    <div class="shop-card__body">
      <h5 class="shop-card__title">
        {{ name }}
      </h5>

      <div class="shop-card__rating">
        <span class="star"> ★ {{ rating }} </span>

        <span> ({{ reviewCount }}) </span>

        <span class="time">
          <i class="bi bi-clock"></i>
          {{ deliveryTime }}
        </span>
      </div>

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
</template>

<style scoped lang="scss">
.shop-card {
  background: white;
  border-radius: 20px;
  overflow: hidden;

  transition: 0.25s;

  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.08);

  cursor: pointer;

  &:hover {
    transform: translateY(-5px);

    box-shadow: 0 14px 30px rgba(0, 0, 0, 0.12);
  }
}

.shop-card__image {
  position: relative;

  img {
    width: 100%;

    aspect-ratio: 16/10;

    object-fit: cover;

    display: block;

    transition: 0.3s;
  }
}

.shop-card:hover img {
  transform: scale(1.05);
}

.shop-card__favorite {
  position: absolute;

  top: 12px;

  right: 12px;

  width: 38px;

  height: 38px;

  border: none;

  border-radius: 50%;

  background: rgba(255, 255, 255, 0.92);

  color: var(--mg-primary);

  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);

  transition: 0.2s;

  &:hover {
    background: var(--mg-primary);

    color: white;
  }
}

.shop-card__body {
  padding: 18px;
}

.shop-card__title {
  font-size: 1.15rem;
  font-weight: 800;
  color: #222;
  margin-bottom: 10px;
}

.shop-card__rating {
  display: flex;

  align-items: center;

  gap: 10px;

  font-size: 0.9rem;

  color: #666;

  margin-bottom: 10px;
}

.star {
  color: #f59e0b;

  font-weight: 800;
}

.time {
  margin-left: auto;
}

.shop-card__description {
  color: #666;

  font-size: 0.92rem;

  line-height: 1.5;

  min-height: 44px;

  margin-bottom: 14px;
}

.shop-card__footer {
  display: flex;

  justify-content: space-between;

  font-size: 0.92rem;

  color: #444;
}

.shop-card__footer i {
  color: var(--mg-primary);
}
</style>
