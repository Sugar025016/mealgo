<script setup lang="ts">
defineProps<{
  size?: "sm" | "md";
  iconOnly?: boolean;
}>();

const model = defineModel<"pickup" | "delivery">({
  default: "delivery",
});
</script>

<template>
  <div
    class="mg-segmented"
    :class="[
      `mg-segmented--${size || 'md'}`,
      `mg-segmented--${model}`,
      { 'mg-segmented--icon-only': iconOnly },
    ]"
  >
    <button
      type="button"
      class="mg-segmented__item"
      :class="{ active: model === 'delivery' }"
      @click="model = 'delivery'"
    >
      <i class="bi bi-bicycle"></i>
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
  --item-width: 96px;
  --item-height: 44px;
  --item-padding-x: 18px;
  --item-gap: 8px;
  --item-font-size: 1rem;
  --icon-size: 1.15rem;
  --slider-offset: 82px;

  position: relative;
  display: inline-flex;
  align-items: center;
  flex-shrink: 0;
  padding: 5px;
  border-radius: 999px;
  background: #d1d1d160;
  box-shadow:
    inset 0 1px 2px rgba(255, 255, 255, 0.9),
    0 4px 14px rgba(0, 0, 0, 0.08);
}

.mg-segmented::before {
  content: "";
  position: absolute;
  top: 5px;
  left: 5px;
  width: var(--item-width);
  height: var(--item-height);
  border-radius: 999px;
//   background: #fff;
  background: var(--mg-primary) !important;
  box-shadow:
    0 2px 8px var(--btn-shadow-sm),
    0 8px 22px var(--btn-shadow-lg);
  transition: transform 0.25s ease;
}

.mg-segmented--delivery::before {
  transform: translateX(0);
}

.mg-segmented--pickup::before {
  transform: translateX(var(--slider-offset));
}

.mg-segmented__item {
  border: 0;
  background: transparent;
  color: #7c8899;
  min-width: var(--item-width);
  height: var(--item-height);
  padding: 0 var(--item-padding-x);
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: var(--item-gap);
  font-size: var(--item-font-size);
  font-weight: 600;
  cursor: pointer;
  position: relative;
  z-index: 1;
  white-space: nowrap;
  flex-shrink: 0;
  transition: color 0.2s ease;

  i {
    font-size: var(--icon-size);
  }

  &:hover:not(.active) {
    color: #717a89;
  }
}

.mg-segmented__item + .mg-segmented__item {
  margin-left: -14px;
}

.mg-segmented__item.active {
//   color: var(--btn-bg) !important;
  color: #fff;
}

/* sm */
.mg-segmented--sm {
  --item-width: 72px;
  --item-height: 34px;
  --item-padding-x: 12px;
  --item-gap: 5px;
  --item-font-size: 0.85rem;
  --icon-size: 0.95rem;
  --slider-offset: 62px;

  padding: 4px;

  &::before {
    top: 4px;
    left: 4px;
  }

  .mg-segmented__item + .mg-segmented__item {
    margin-left: -10px;
  }
}

/* 只留 icon */
.mg-segmented--icon-only {
  --item-width: 40px;
  --item-padding-x: 0;
  --item-gap: 0;
  --slider-offset: 34px;
  --item-font-size: 0.1rem;

  span {
    display: none;
  }

  .mg-segmented__item + .mg-segmented__item {
    margin-left: -6px;
  }
}
</style>