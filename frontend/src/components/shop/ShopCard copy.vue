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
});
</script>
<template>
  <div class="card shop-card h-100">
    <img :src="image" class="card-img-top" :alt="name" />

    <div class="card-body">
      <h5 class="card-title">
        {{ name }}
      </h5>

      <p class="card-text">
        {{ description }}
      </p>

      <div class="d-flex justify-content-between">
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

<style lang="scss">
.shop-card {
  border: 0px;
  //   border: 1px solid var(--mg-border);
  border-radius: 20px;
  overflow: hidden;

  transition: all 0.2s ease;
  max-width: 480px;
//   min-width: 280px;
  margin-left: auto;
  margin-right: auto;
  box-shadow:
0 6px 20px rgba(0,0,0,.08);

  &:hover {
    border-color: var(--mg-primary-border);
    box-shadow: var(--mg-shadow-md);
  }

  .card-img-top {
    // height: 160px;
    width: 100%;
    aspect-ratio: 16 / 9;
    object-fit: cover;
  }
}
</style>
