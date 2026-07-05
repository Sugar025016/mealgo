<script setup lang="ts">
withDefaults(
  defineProps<{
    variant?: "primary" | "soft" | "outline" | "ghost" | "pickup" | "delivery";
    size?: "sm" | "md" | "lg";
    rounded?: "md" | "pill";
    type?: "button" | "submit" | "reset";
    disabled?: boolean;
  }>(),
  {
    variant: "primary",
    size: "md",
    rounded: "md",
    type: "button",
    disabled: false,
  }
);
</script>

<template>
  <button
    :type="type"
    :disabled="disabled"
    class="btn btn-mg"
    :class="[
      `btn-mg-${variant}`,
      `btn-mg-${size}`,
      `btn-mg-rounded-${rounded}`,
    ]"
  >
    <span v-if="$slots.icon" class="btn-mg__icon">
      <slot name="icon" />
    </span>

    <span class="btn-mg__text">
      <slot />
    </span>
  </button>
</template>

<style scoped lang="scss">
.btn-mg {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;

  font-weight: 700;
  white-space: nowrap;

  transition:
    background-color 0.2s ease,
    color 0.2s ease,
    border-color 0.2s ease,
    box-shadow 0.2s ease,
    transform 0.2s ease;

  &:hover:not(:disabled) {
    transform: translateY(-1px);
  }

  &:active:not(:disabled) {
    transform: translateY(0);
  }

  &:disabled {
    opacity: 0.55;
    cursor: not-allowed;
  }
}

/* radius */
.btn-mg-rounded-md {
  border-radius: 14px;
}

.btn-mg-rounded-pill {
  border-radius: 9999px;
}

/* size */
.btn-mg-sm {
  padding: 0.45rem 0.85rem;
  font-size: 0.875rem;
}

.btn-mg-md {
  padding: 0.65rem 1.1rem;
  font-size: 1rem;
}

.btn-mg-lg {
  padding: 0.85rem 1.5rem;
  font-size: 1.05rem;
}

/* variant */
.btn-mg-primary,
.btn-mg-delivery {
  background: var(--btn-bg);
  color: var(--btn-text);
  border: 1px solid var(--btn-bg);
  box-shadow:
    0 2px 6px rgba(0, 0, 0, 0.08),
    0 8px 20px rgba(0, 0, 0, 0.12);

  &:hover:not(:disabled) {
    background: var(--btn-hover);
    border-color: var(--btn-hover);
    color: var(--btn-text);
    box-shadow:
      0 3px 8px rgba(0, 0, 0, 0.12),
      0 12px 26px rgba(0, 0, 0, 0.18);
  }

  &:active:not(:disabled) {
    background: var(--btn-active);
    border-color: var(--btn-active);
    box-shadow:
      inset 0 2px 4px rgba(0, 0, 0, 0.12),
      0 2px 6px rgba(0, 0, 0, 0.08);
  }
}

.btn-mg-soft {
  background: var(--btn-soft);
  color: var(--btn-bg);
  border: 1px solid transparent;

  &:hover:not(:disabled) {
    background: var(--btn-soft-hover);
    color: var(--btn-bg);
  }
}

.btn-mg-outline {
  background: transparent;
  color: var(--btn-bg);
  border: 1px solid var(--btn-bg);

  &:hover:not(:disabled) {
    background: var(--btn-soft);
    color: var(--btn-bg);
  }
}

.btn-mg-ghost {
  background: transparent;
  color: var(--btn-bg);
  border: 1px solid transparent;
  box-shadow: none;

  &:hover:not(:disabled) {
    background: var(--btn-soft);
    color: var(--btn-bg);
  }
}

.btn-mg-pickup {
  background: var(--mg-pickup-main);
  color: #fff;
  border: 1px solid var(--mg-pickup-main);

  &:hover:not(:disabled) {
    background: var(--mg-pickup-hover);
    border-color: var(--mg-pickup-hover);
    color: #fff;
  }

  &:active:not(:disabled) {
    background: var(--mg-pickup-active);
    border-color: var(--mg-pickup-active);
  }
}

.btn-mg__icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
}
</style>