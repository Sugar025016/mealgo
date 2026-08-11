<script setup lang="ts">
import type { OrderItem } from "@/types/order.ts";

defineProps<{
  shopName: string;
  shopLogo: string;
  items: OrderItem[];
}>();

function itemTotal(item: OrderItem) {
  return item.price * item.qty;
}
</script>

<template>
  <section class="order-card order-items">
    <header class="order-items__shop">
      <img :src="shopLogo" :alt="shopName" />
      <h2>{{ shopName }}</h2>
    </header>

    <ul class="order-items__list">
      <li v-for="item in items" :key="item.id" class="order-items__item">
        <img :src="item.image" :alt="item.name" />

        <div class="order-items__content">
          <div class="order-items__line">
            <span>{{ item.name }} × {{ item.qty }}</span>
            <strong>NT$ {{ itemTotal(item) }}</strong>
          </div>
          <small v-if="item.remark">{{ item.remark }}</small>
        </div>
      </li>
    </ul>
  </section>
</template>

<style scoped lang="scss">
.order-card {
  overflow: hidden;
  border: 1px solid #eee;
  border-radius: 14px;
  background: #fff;
  box-shadow: 0 4px 16px rgb(0 0 0 / 5%);
}

.order-items {
  &__shop {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 16px 20px;

    img {
      width: 48px;
      height: 48px;
      border-radius: 8px;
      object-fit: cover;
    }

    h2 {
      margin: 0;
      font-size: 20px;
    }
  }

  &__list {
    margin: 0 20px 20px;
    padding: 0;
    overflow: hidden;
    border: 1px solid #eee;
    border-radius: 10px;
    list-style: none;
  }

  &__item {
    display: flex;
    align-items: center;
    gap: 16px;
    padding: 10px 14px;

    &:not(:last-child) {
      border-bottom: 1px solid #eee;
    }

    > img {
      width: 72px;
      height: 56px;
      border-radius: 8px;
      object-fit: cover;
    }
  }

  &__content {
    flex: 1;
    min-width: 0;

    small {
      color: #888;
    }
  }

  &__line {
    display: flex;
    justify-content: space-between;
    gap: 16px;

    strong {
      white-space: nowrap;
    }
  }
}
</style>
