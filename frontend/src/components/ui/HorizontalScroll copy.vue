<script setup lang="ts">
import { ref } from "vue";

const scrollRef = ref<HTMLDivElement | null>(null);

// function scroll(direction: "prev" | "next") {
//   const el = scrollRef.value;
//   if (!el) return;

//   const distance = el.clientWidth * 0.8;

//   el.scrollBy({
//     left: direction === "next" ? distance : -distance,
//     behavior: "smooth",
//   });
// }
function scroll(direction: "prev" | "next") {
  const el = scrollRef.value;
  if (!el) return;

  el.scrollBy({
    left: direction === "next" ? el.clientWidth * 0.8 : -el.clientWidth * 0.8,
    behavior: "smooth",
  });
}
</script>

<template>
  <div class="mg-horizontal-scroll">
    <button class="mg-scroll-arrow left" @click="scroll('prev')">
      <i class="bi bi-chevron-left"></i>
    </button>

    <div ref="scrollRef" class="mg-scroll-content">
      <slot />
    </div>

    <button class="mg-scroll-arrow right" @click="scroll('next')">
      <i class="bi bi-chevron-right"></i>
    </button>
  </div>
</template>

<style scoped lang="scss">
.mg-horizontal-scroll {
  position: relative;
  height: auto;
  width: 100%;
  max-width: 100%;
  overflow: hidden;
}

.mg-scroll-content {
  display: flex;
  overflow-x: auto;
  scroll-behavior: smooth;
  scrollbar-width: none;
  padding: 8px 0px; // 原本可能是 0 或 8px
//   padding-inline: 22px;
//   gap: 12px;

  &::-webkit-scrollbar {
    display: none;
  }
}

.mg-scroll-arrow {
  position: absolute;
  top: 50%;
  z-index: 5;
  transform: translateY(-50%);

  width: 44px;
  height: 44px;
  border: 0;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.16);
}

@media (max-width: 768px) {
  .mg-scroll-arrow {
    width: 32px;
    height: 32px;
    opacity: 0.6;
  }
}

.mg-scroll-arrow.left {
  left: 8px;
  opacity: 0.7;
}

.mg-scroll-arrow.right {
  right: 8px;
  opacity: 0.7;
}
</style>
