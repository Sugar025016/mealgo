<script setup lang="ts">
import MgDelivery from "@/components/icons/MgDelivery.vue";
defineProps<{
  size?: "sm" | "md";
}>();
const model = defineModel<"pickup" | "delivery">({
  default: "delivery",
});
</script>

<template>
  <div class="mg-segmented" :class="`mg-segmented--${size || 'md'}`">
    <button
      type="button"
      class="mg-segmented__item"
      :class="{ active: model === 'delivery' }"
      @click="model = 'delivery'"
    >
      <!-- <i class="bi bi-scooter"></i> -->
      <i class="bi bi-bicycle"></i>
      <!-- <MgDelivery class="bi bi-bicycle"></MgDelivery> -->
      <span>外送</span>
    </button>
    <button
      type="button"
      class="mg-segmented__item"
      :class="{ active: model === 'pickup' }"
      @click="model = 'pickup'"
    >
      <i class="bi bi-person-walking"></i>
      <span>自取</span>
    </button>
  </div>
</template>

<style scoped lang="scss">
.mg-segmented {
  display: inline-flex;
  align-items: center;
  padding: 5px;
  border-radius: 999px;
  background: #d1d1d160;
  box-shadow:
    inset 0 1px 2px rgba(255, 255, 255, 0.9),
    0 4px 14px rgba(0, 0, 0, 0.08);
}

.mg-segmented__item {
  border: 0;
  background: transparent;
  color: #7c8899;

  min-width: 96px;
  height: 44px;
  padding: 0 18px;

  border-radius: 999px;

  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;

  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;

  position: relative;
  z-index: 1;

  transition:
    background-color 0.2s ease,
    color 0.2s ease,
    box-shadow 0.2s ease;

  i {
    font-size: 1.15rem;
  }

  &:hover:not(.active) {
    background: rgba(255, 255, 255, 0.806);
    color: #717a89;
  }
}

/* 第二顆往左重疊 */
.mg-segmented__item + .mg-segmented__item {
  margin-left: -14px;
}

/* 選中的浮上來 */
.mg-segmented__item.active {
  background: #fff;
  z-index: 10;
}

/* 自取 active */
.mg-segmented__item.active:first-child {
  color: var(--btn-bg) !important;
  box-shadow:
    0 2px 8px var(--btn-shadow-sm),
    0 8px 22px var(--btn-shadow-lg);
}

/* 外送 active */
.mg-segmented__item.active:last-child {
  color: var(--btn-bg) !important;
  box-shadow:
    0 2px 8px var(--btn-shadow-sm),
    0 8px 22px var(--btn-shadow-lg);
}

.mg-segmented--sm {
  padding: 4px;

  .mg-segmented__item {
    min-width: 72px;
    height: 34px;
    padding: 0 12px;
    font-size: 0.85rem;
    gap: 5px;

    i {
      font-size: 0.95rem;
    }
  }

  .mg-segmented__item + .mg-segmented__item {
    margin-left: -10px;
  }
}
</style>
