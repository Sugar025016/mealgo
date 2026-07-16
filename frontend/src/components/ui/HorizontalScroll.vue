<script setup lang="ts">
import { nextTick, onBeforeUnmount, onMounted, ref } from "vue";

const props = defineProps({
  cardWidth: {
    type: Number,
    required: true,
  },

  keepCards: {
    type: Number,
    default: 1,
  },
});

const scrollRef = ref<HTMLDivElement | null>(null);

const canScrollLeft = ref(false);
const canScrollRight = ref(false);

function updateScrollState() {
  const el = scrollRef.value;
  if (!el) return;

  const maxScrollLeft = el.scrollWidth - el.clientWidth;

  canScrollLeft.value = el.scrollLeft > 2;
  canScrollRight.value = el.scrollLeft < maxScrollLeft - 2;
}

function scroll(direction: "prev" | "next") {
  const el = scrollRef.value;
  if (!el) return;

  const visibleCards = Math.floor(el.clientWidth / props.cardWidth);
  const moveCards = Math.max(1, visibleCards - props.keepCards);
  const distance = moveCards * props.cardWidth;

  el.scrollBy({
    left: direction === "next" ? distance : -distance,
    behavior: "smooth",
  });

  setTimeout(updateScrollState, 350);
}

onMounted(async () => {
  await nextTick();
  updateScrollState();

  window.addEventListener("resize", updateScrollState);
});

onBeforeUnmount(() => {
  window.removeEventListener("resize", updateScrollState);
});
</script>

<template>
  <div class="mg-horizontal-scroll">
    <button
      v-show="canScrollLeft"
      class="mg-scroll-arrow left"
      type="button"
      @click="scroll('prev')"
    >
      <i class="bi bi-chevron-left"></i>
    </button>

    <div ref="scrollRef" class="mg-scroll-content" @scroll="updateScrollState">
      <slot />
    </div>

    <button
      v-show="canScrollRight"
      class="mg-scroll-arrow right"
      type="button"
      @click="scroll('next')"
    >
      <i class="bi bi-chevron-right"></i>
    </button>
  </div>
</template>

<style scoped lang="scss">
.mg-horizontal-scroll {
  position: relative;
  width: 100%;
  max-width: 100%;
  overflow: visible;
  padding: 0 8px;
}

.mg-scroll-content {
  display: flex;
  overflow-x: auto;
  scroll-behavior: smooth;
  scrollbar-width: none;
  padding: 8px 8px;

  &::-webkit-scrollbar {
    display: none;
  }
}

.mg-scroll-arrow {
  position: absolute;
  top: 50%;
  z-index: 10000;
  transform: translateY(-50%);

  width: 44px;
  height: 44px;
  border: 0;
  border-radius: 50%;

  background: #fff;
  //   background: red;
  color: #333;

  display: inline-flex;
  align-items: center;
  justify-content: center;

  box-shadow: 0 4px 14px rgba(0, 0, 0, 0.16);
  opacity: 0.75;

  transition:
    opacity 0.2s ease,
    transform 0.2s ease;

  &:hover {
    opacity: 1;
    transform: translateY(-50%) scale(1.04);
  }
}

.mg-scroll-arrow.left {
  left: -16px;
}

.mg-scroll-arrow.right {
  right: -16px;
}

@media (max-width: 768px) {
  .mg-horizontal-scroll {
    padding: 0 8px;
  }
  //   .mg-scroll-arrow {
  //     width: 32px;
  //     height: 32px;
  //     opacity: 0.65;
  //   }

  .mg-scroll-arrow.left {
    left: -10px;
  }

  .mg-scroll-arrow.right {
    right: -10px;
  }
}
</style>
