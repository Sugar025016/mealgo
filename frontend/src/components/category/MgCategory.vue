<script setup lang="ts">
defineProps({
  image: {
    type: String,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
});
</script>
<template>
  <button class="mg-category">
    <div class="mg-category__image">
      <img :src="image" :alt="title" />
    </div>

    <div class="mg-category__title">
      {{ title }}
    </div>
  </button>
</template>

<style scoped lang="scss">
.mg-category {
  flex: 0 0 var(--mg-category-size);
  width: var(--mg-category-size);
  min-width: 80px;
  margin:  0px 16px;
  border: 0;
  background: transparent;

}

// .mg-category::hover {
//   background: var(--mg-primary-soft);
//   border-radius: 8px;
//   border: 1px solid var(--mg-primary);
// }

.mg-category__image {
  position: relative;

  width: 100%;
  aspect-ratio: 1/0.8;
  display: flex;
  justify-content: center;
  align-items: center;
}

.mg-category__image::before {
  content: "";
  position: absolute;

  width: 90%;
  height: 90%;

  border-radius: 90%;
  //   background: var(--mg-primary-soft); // 底色
  //   background: rgba(255, 106, 0, 0.08); // 底色
  //   background: #fff3eb;
  background: #fff5ef;
//   background: #fff5efa2;
    background: #ffffff;
  z-index: 0;
}

.mg-category__image img {
  position: relative;
  z-index: 1;
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.mg-category__title {
  height: 22px;
  line-height: 22px;
  text-align: center;
  font-size: 1.2rem;
  font-weight: 700;
  color: var(--mg-primary);
  color: #444;
}
.mg-category:hover {
  .mg-category__title {
    color: var(--mg-primary);
    font-weight: 900;
  }
  .mg-category__image::before {
    background: #ffffff;
  background: #ffefe7a0;
  }
}

@media (max-width: 768px) {
  .mg-category {
    margin: 0;
  }
}
</style>
