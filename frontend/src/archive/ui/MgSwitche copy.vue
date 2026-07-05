<script setup lang="ts">
const model = defineModel<"pickup" | "delivery">({
  default: "pickup",
});
</script>

<template>
  <div class="mg-switch" type="checkbox">
    <button
      class="mg-switch__item"
      :class="{ active: model === 'pickup' }"
      @click="model = 'pickup'"
    >
      🚶 自取
    </button>

    <button
      class="mg-switch__item"
      :class="{ active: model === 'delivery' }"
      @click="model = 'delivery'"
    >
      🛵 外送
    </button>
  </div>
</template>
<style scoped lang="scss">
.mg-switch {
  display: inline-flex;
  background: #f3f4f6;
  border-radius: 999px;
  padding: 4px;
  gap: 4px;

  &__item {
    border: 0;
    background: transparent;
    border-radius: 999px;

    display: flex;
    align-items: center;
    gap: 6px;

    padding: 8px 14px;

    color: #64748b;
    font-weight: 600;

    transition: all 0.2s ease;

    &.active {
      background: white;
    background: var(--btn-bg) !important;
    color: var(--btn-text);
      color: #1f2937;

      box-shadow:
        0 2px 8px rgba(0, 0, 0, 0.08),
        0 6px 18px rgba(0, 0, 0, 0.08);
    }
  }
}
</style>
