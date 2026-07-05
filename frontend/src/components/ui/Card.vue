<!-- src/components/ui/MgCard.vue -->
<template>
  <div class="mg-card">
    <slot />
  </div>
</template>

<style scoped lang="scss">
.mg-card {
  background: var(--mg-surface, #ffffff);
  border: 1px solid var(--mg-border, #e5e7eb);
  border-radius: 18px;
  padding: 16px;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.08);
  transition:
    box-shadow 0.2s ease,
    transform 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 14px 32px rgba(15, 23, 42, 0.12);
  }
}
</style>