<script setup lang="ts">
withDefaults(
  defineProps<{
    variant?:
      | "primary"
      | "soft"
      | "outline"
      | "ghost"
      | "pickup"
      | "delivery";

    size?: "sm" | "md" | "lg";

    rounded?: "md" | "pill";

    fullWidth?: boolean;

    disabled?: boolean;

    type?: "button" | "submit" | "reset";
  }>(),
  {
    variant: "primary",
    size: "md",
    rounded: "pill",
    fullWidth: false,
    disabled: false,
    type: "button",
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
      rounded === 'pill' ? 'rounded-pill' : 'rounded-4',
      {
        'w-100': fullWidth,
      },
    ]"
  >
    <span
      v-if="$slots.icon"
      class="btn-mg__icon"
    >
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
    background-color .2s,
    color .2s,
    border-color .2s,
    box-shadow .2s,
    transform .15s;

  box-shadow:
    0 2px 6px rgba(0, 0, 0, .08),
    0 8px 20px rgba(0, 0, 0, .12);

  &:hover:not(:disabled) {
    transform: translateY(-1px);
  }

  &:active:not(:disabled) {
    transform: translateY(0);
  }

  &:disabled {
    opacity: .55;
    cursor: not-allowed;
    box-shadow: none;
  }
}

/* ---------- Size ---------- */

.btn-mg-sm {
  min-height: 34px;
  padding: 0 14px;
  font-size: .875rem;
}

.btn-mg-md {
  min-height: 42px;
  padding: 0 18px;
  font-size: .95rem;
}

.btn-mg-lg {
  min-height: 48px;
  padding: 0 22px;
  font-size: 1rem;
}

/* ---------- Primary ---------- */

.btn-mg-primary,
.btn-mg-delivery {
  background: var(--btn-bg);
  color: var(--btn-text);
  border: 1px solid var(--btn-bg);

  &:hover:not(:disabled) {
    background: var(--btn-hover);
    border-color: var(--btn-hover);
    color: var(--btn-text);

    box-shadow:
      0 4px 10px rgba(0,0,0,.12),
      0 14px 28px rgba(0,0,0,.18);
  }

  &:active:not(:disabled) {
    background: var(--btn-active);
    border-color: var(--btn-active);

    box-shadow:
      inset 0 2px 4px rgba(0,0,0,.12);
  }
}

/* ---------- Soft ---------- */

.btn-mg-soft {
  background: var(--btn-soft);
  color: var(--btn-bg);
  border: 1px solid transparent;

  &:hover:not(:disabled) {
    background: var(--btn-soft-hover);
    color: var(--btn-bg);
  }
}

/* ---------- Outline ---------- */

.btn-mg-outline {
  background: transparent;
  color: var(--btn-bg);

  border: 1px solid var(--btn-bg);

  box-shadow: none;

  &:hover:not(:disabled) {
    background: var(--btn-soft);
    color: var(--btn-bg);
  }
}

/* ---------- Ghost ---------- */

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

/* ---------- Pickup ---------- */

.btn-mg-pickup {
  background: var(--mg-pickup-main);

  color: white;

  border: 1px solid var(--mg-pickup-main);

  &:hover:not(:disabled) {
    background: var(--mg-pickup-hover);
    border-color: var(--mg-pickup-hover);
    color: white;
  }

  &:active:not(:disabled) {
    background: var(--mg-pickup-active);
    border-color: var(--mg-pickup-active);
  }
}

/* ---------- Icon ---------- */

.btn-mg__icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  font-size: 1rem;
}
</style>