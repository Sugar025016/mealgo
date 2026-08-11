<script setup lang="ts">
import { computed } from "vue";
import type {
  FulfillmentType,
  OrderStatus,
} from "@/types/order.ts";

const props = defineProps<{
  status: OrderStatus;
  fulfillmentType: FulfillmentType;
  estimatedArrival?: string;
}>();

type Step = {
  label: string;
  statuses: OrderStatus[];
};

const isDelivery = computed(() => props.fulfillmentType === "DELIVERY");

const steps = computed<Step[]>(() =>
  isDelivery.value
    ? [
        { label: "訂單已送出", statuses: ["PENDING"] },
        { label: "店家接單", statuses: ["ACCEPTED"] },
        { label: "準備餐點", statuses: ["COOKING", "READY_FOR_PICKUP"] },
        { label: "外送配送", statuses: ["ON_THE_WAY", "COMPLETED"] },
      ]
    : [
        { label: "訂單已送出", statuses: ["PENDING"] },
        { label: "店家接單", statuses: ["ACCEPTED"] },
        { label: "準備餐點", statuses: ["COOKING"] },
        { label: "可以取餐", statuses: ["READY_FOR_PICKUP", "COMPLETED"] },
      ],
);

const currentIndex = computed(() => {
  if (["CANCELLED", "STORE_NOT_ACCEPT", "STORE_REFUSED"].includes(props.status)) {
    return -1;
  }

  return Math.max(
    0,
    steps.value.findIndex((step) => step.statuses.includes(props.status)),
  );
});

const statusContent = computed(() => {
  const content: Partial<
    Record<OrderStatus, { title: string; description: string; icon: string }>
  > = {
    PENDING: {
      title: "等待店家接單",
      description: "店家將於 5 分鐘內回覆",
      icon: "bi-receipt-cutoff",
    },
    ACCEPTED: {
      title: "店家已接單",
      description: "店家即將開始準備餐點",
      icon: "bi-check-circle",
    },
    COOKING: {
      title: "餐點準備中",
      description: "店家正在用心製作餐點",
      icon: "bi-fire",
    },
    READY_FOR_PICKUP: {
      title: isDelivery.value ? "餐點等待外送員取餐" : "餐點可以取餐了",
      description: isDelivery.value
        ? "外送員即將前往店家"
        : "請前往店家櫃台取餐",
      icon: isDelivery.value ? "bi-scooter" : "bi-bag-check",
    },
    ON_THE_WAY: {
      title: "外送員配送中",
      description: props.estimatedArrival
        ? `預計 ${props.estimatedArrival} 送達`
        : "外送員正在前往送達地址",
      icon: "bi-scooter",
    },
    COMPLETED: {
      title: isDelivery.value ? "訂單已送達" : "訂單已取餐",
      description: "感謝你使用 MealGo",
      icon: "bi-check-circle",
    },
    CANCELLED: {
      title: "訂單已取消",
      description: "此訂單已取消",
      icon: "bi-x-circle",
    },
    STORE_NOT_ACCEPT: {
      title: "店家未接單",
      description: "店家未在時間內接受訂單",
      icon: "bi-clock-history",
    },
    STORE_REFUSED: {
      title: "店家無法接單",
      description: "款項將依原付款方式退回",
      icon: "bi-x-circle",
    },
  };

  return content[props.status] ?? content.PENDING!;
});

function stepClass(index: number) {
  return {
    "order-status__step--done": currentIndex.value >= 0 && index < currentIndex.value,
    "order-status__step--active": index === currentIndex.value,
  };
}
</script>

<template>
  <section
    class="order-status"
    :class="{ 'order-status--error': currentIndex === -1 }"
  >
    <div class="order-status__summary">
      <div class="order-status__icon">
        <i class="bi" :class="statusContent.icon"></i>
      </div>

      <div>
        <h2>{{ statusContent.title }}</h2>
        <p>{{ statusContent.description }}</p>
      </div>
    </div>

    <ol v-if="currentIndex !== -1" class="order-status__steps">
      <li
        v-for="(step, index) in steps"
        :key="step.label"
        class="order-status__step"
        :class="stepClass(index)"
      >
        <span class="order-status__dot">
          <i v-if="index < currentIndex" class="bi bi-check-lg"></i>
        </span>
        <span class="order-status__label">{{ step.label }}</span>
      </li>
    </ol>
  </section>
</template>

<style scoped lang="scss">
.order-status {
  display: grid;
  grid-template-columns: minmax(280px, 0.9fr) minmax(420px, 1.1fr);
  align-items: center;
  gap: 36px;
  padding: 24px 28px;
  border: 1px solid #eee;
  border-radius: 14px;
  background: #fff;
  box-shadow: 0 4px 16px rgb(0 0 0 / 5%);

  &__summary {
    display: flex;
    align-items: center;
    gap: 20px;

    h2 {
      margin: 0 0 6px;
      color: #ff5f07;
      font-size: 24px;
      font-weight: 700;
    }

    p {
      margin: 0;
      color: #777;
      font-size: 15px;
    }
  }

  &__icon {
    display: grid;
    flex: 0 0 76px;
    width: 76px;
    height: 76px;
    place-items: center;
    border: 2px solid #ff5f07;
    border-radius: 50%;
    color: #ff5f07;
    font-size: 34px;
  }

  &__steps {
    display: flex;
    margin: 0;
    padding: 0;
    list-style: none;
  }

  &__step {
    position: relative;
    display: flex;
    flex: 1;
    flex-direction: column;
    align-items: center;
    gap: 8px;
    color: #aaa;

    &:not(:last-child)::after {
      position: absolute;
      top: 10px;
      left: calc(50% + 11px);
      width: calc(100% - 22px);
      height: 2px;
      background: #ddd;
      content: "";
    }
  }

  &__dot {
    z-index: 1;
    display: grid;
    width: 22px;
    height: 22px;
    place-items: center;
    border: 2px solid #ccc;
    border-radius: 50%;
    background: #fff;
    font-size: 12px;
  }

  &__label {
    color: #555;
    font-size: 14px;
    font-weight: 600;
    white-space: nowrap;
  }

  &__step--done,
  &__step--active {
    color: #ff5f07;

    .order-status__dot {
      border-color: #ff5f07;
    }
  }

  &__step--done {
    .order-status__dot {
      background: #ff5f07;
      color: #fff;
    }

    &:not(:last-child)::after {
      background: #ff5f07;
    }
  }

  &__step--active .order-status__dot::after {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background: #ff5f07;
    content: "";
  }

  &--error {
    grid-template-columns: 1fr;

    .order-status__summary h2,
    .order-status__icon {
      border-color: #dc3545;
      color: #dc3545;
    }
  }
}

@media (max-width: 1220px) {
  .order-status {
    grid-template-columns: 1fr;
    gap: 24px;
  }
}

@media (max-width: 575.98px) {
  .order-status {
    padding: 20px 16px;

    &__icon {
      flex-basis: 56px;
      width: 56px;
      height: 56px;
      font-size: 25px;
    }

    &__summary {
      gap: 14px;

      h2 {
        font-size: 20px;
      }
    }

    &__label {
      font-size: 12px;
    }
  }
}
</style>
