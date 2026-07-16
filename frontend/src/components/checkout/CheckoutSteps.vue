<!-- src/components/checkout/CheckoutSteps.vue -->
<script setup lang="ts">
type CheckoutStep = "cart" | "checkout" | "completed";

withDefaults(
  defineProps<{
    currentStep?: CheckoutStep;
  }>(),
  {
    currentStep: "checkout",
  },
);

const steps: Array<{
  key: CheckoutStep;
  label: string;
}> = [
  {
    key: "cart",
    label: "購物車",
  },
  {
    key: "checkout",
    label: "結帳",
  },
  {
    key: "completed",
    label: "完成",
  },
];
</script>

<template>
  <nav class="checkout-steps" aria-label="結帳進度">
    <ol class="checkout-steps__list">
      <li
        v-for="(step, index) in steps"
        :key="step.key"
        class="checkout-steps__item"
        :class="{
          'checkout-steps__item--active': currentStep === step.key,
        }"
      >
        <span class="checkout-steps__label">
          {{ step.label }}
        </span>

        <span
          v-if="index < steps.length - 1"
          class="checkout-steps__separator"
          aria-hidden="true"
        >
          /
        </span>
      </li>
    </ol>
  </nav>
</template>

<style scoped lang="scss">
.checkout-steps {
  width: 100%;
}

.checkout-steps__list {
  display: flex;
  align-items: center;
  gap: 14px;
  margin: 0;
  padding: 0;
  list-style: none;
}

.checkout-steps__item {
  display: flex;
  align-items: center;
  gap: 14px;
  color: #9ca3af;
  font-size: 14px;
  font-weight: 500;
}

.checkout-steps__label {
  line-height: 1;
  white-space: nowrap;
}

.checkout-steps__separator {
  color: #d1d5db;
  font-weight: 400;
}

.checkout-steps__item--active {
  color: #ff5a00;
  font-weight: 700;
}

@media (max-width: 576px) {
  .checkout-steps__list {
    gap: 10px;
  }

  .checkout-steps__item {
    gap: 10px;
    font-size: 13px;
  }
}
</style>