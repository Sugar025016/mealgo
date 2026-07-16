<script setup lang="ts">
import { computed, ref } from "vue";

import ActiveOrderCard from "@/components/order/ActiveOrderCard.vue";
import CompletedOrderRow from "@/components/order/CompletedOrderRow.vue";

type OrderStatus =
  | "PENDING"
  | "ACCEPTED"
  | "COOKING"
  | "ON_THE_WAY"
  | "COMPLETED"
  | "CANCELLED";

type Order = {
  id: number;
  orderNumber: string;
  shopName: string;
  shopImage: string;
  orderTime: string;
  products: string[];
  totalPrice: number;
  status: OrderStatus;
  estimatedTime?: string;
};

type OrderTab = "ACTIVE" | "COMPLETED";

const visibleCompletedCount = ref(3);

const orders = ref<Order[]>([
  {
    id: 1,
    orderNumber: "MG202606180001",
    shopName: "健康少油餐盒",
    shopImage: "https://picsum.photos/500/320?random=21",
    orderTime: "2026/06/18 18:30",
    products: ["舒肥雞胸餐盒 ×1", "溏心蛋 ×1", "綠豆湯 ×1"],
    totalPrice: 160,
    status: "COOKING",
    estimatedTime: "19:00",
  },
  {
    id: 2,
    orderNumber: "MG202606180002",
    shopName: "川味麻辣麵",
    shopImage: "https://picsum.photos/500/320?random=22",
    orderTime: "2026/06/18 18:45",
    products: ["麻辣牛肉麵 ×1", "溏心蛋 ×1", "冬瓜茶 ×1"],
    totalPrice: 185,
    status: "ON_THE_WAY",
    estimatedTime: "19:15",
  },
  {
    id: 3,
    orderNumber: "MG202606170045",
    shopName: "好味雞肉飯",
    shopImage: "https://picsum.photos/180/120?random=23",
    orderTime: "2026/06/17 12:30",
    products: ["雞肉飯 ×1", "燙青菜 ×1"],
    totalPrice: 125,
    status: "COMPLETED",
  },
  {
    id: 4,
    orderNumber: "MG202606160032",
    shopName: "日式咖哩專賣",
    shopImage: "https://picsum.photos/180/120?random=24",
    orderTime: "2026/06/16 18:20",
    products: ["炸豬排咖哩飯 ×1"],
    totalPrice: 130,
    status: "COMPLETED",
  },
  {
    id: 5,
    orderNumber: "MG202606150018",
    shopName: "牛肉麵食館",
    shopImage: "https://picsum.photos/180/120?random=25",
    orderTime: "2026/06/15 19:05",
    products: ["紅燒牛肉麵 ×1"],
    totalPrice: 140,
    status: "COMPLETED",
  },
  {
    id: 6,
    orderNumber: "MG202606140012",
    shopName: "晨光早餐",
    shopImage: "https://picsum.photos/180/120?random=26",
    orderTime: "2026/06/14 08:10",
    products: ["培根蛋吐司 ×1", "冰紅茶 ×1"],
    totalPrice: 95,
    status: "CANCELLED",
  },
]);

const activeOrders = computed(() => {
  const activeStatuses: OrderStatus[] = [
    "PENDING",
    "ACCEPTED",
    "COOKING",
    "ON_THE_WAY",
  ];

  return orders.value.filter((order) => activeStatuses.includes(order.status));
});

const activeTab = ref<OrderTab>(
  activeOrders.value.length === 0 ? "COMPLETED" : "ACTIVE",
);

const completedOrders = computed(() => {
  const completedStatuses: OrderStatus[] = ["COMPLETED", "CANCELLED"];

  return orders.value.filter((order) =>
    completedStatuses.includes(order.status),
  );
});

const visibleCompletedOrders = computed(() =>
  completedOrders.value.slice(0, visibleCompletedCount.value),
);

const hasMoreCompletedOrders = computed(
  () => visibleCompletedCount.value < completedOrders.value.length,
);

function viewOrder(orderId: number) {
  console.log("查看訂單：", orderId);
}

function contactSupport(orderId: number) {
  console.log("聯絡客服：", orderId);
}

function loadMore() {
  visibleCompletedCount.value += 3;
}
</script>

<template>
  <section class="order-history">
    <header class="order-history__header">
      <h1 class="order-history__title">歷史訂單</h1>

      <p class="order-history__description">查看您的訂單紀錄與狀態</p>
    </header>

    <div class="order-history__tabs">
      <button
        type="button"
        class="order-history__tab"
        :class="{
          'order-history__tab--active': activeTab === 'ACTIVE',
        }"
        @click="activeTab = 'ACTIVE'"
      >
        進行中（{{ activeOrders.length }}）
      </button>

      <button
        type="button"
        class="order-history__tab"
        :class="{
          'order-history__tab--active': activeTab === 'COMPLETED',
        }"
        @click="activeTab = 'COMPLETED'"
      >
        已結束（{{ completedOrders.length }}）
      </button>
    </div>

    <div v-if="activeTab === 'ACTIVE'" class="order-history__active-list">
      <ActiveOrderCard
        v-for="order in activeOrders"
        :key="order.id"
        :order="order"
        @view="viewOrder"
        @support="contactSupport"
      />

      <div v-if="activeOrders.length === 0" class="order-history__empty">
        目前沒有進行中的訂單
      </div>
    </div>

    <div v-else class="order-history__completed">
      <h2 class="order-history__section-title">已結束訂單</h2>

      <div class="order-history__completed-list">
        <CompletedOrderRow
          v-for="order in visibleCompletedOrders"
          :key="order.id"
          :order="order"
          @view="viewOrder"
        />
      </div>

      <button
        v-if="hasMoreCompletedOrders"
        type="button"
        class="order-history__load-more"
        @click="loadMore"
      >
        載入更多
        <i class="bi bi-chevron-down"></i>
      </button>
    </div>
  </section>
</template>

<style scoped lang="scss">
.order-history {
  padding: 28px;
  border: 1px solid #eeeeee;
  border-radius: 16px;
  background: #ffffff;
  box-shadow: 0 4px 18px rgb(0 0 0 / 4%);
}

.order-history__header {
  margin-bottom: 16px;
}

.order-history__title {
  margin: 0;
  color: #292929;
  font-size: 28px;
  font-weight: 800;
}

.order-history__description {
  margin: 7px 0 0;
  color: #888888;
  font-size: 15px;
}

.order-history__tabs {
  display: flex;
  gap: 36px;
  margin-bottom: 16px;
  border-bottom: 1px solid #eeeeee;
}

.order-history__tab {
  position: relative;
  min-width: 170px;
  padding: 14px 16px;

  border: 0;
  background: transparent;
  color: #777777;

  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
}

.order-history__tab::after {
  position: absolute;
  right: 0;
  bottom: -1px;
  left: 0;

  height: 3px;
  border-radius: 999px;
  background: transparent;

  content: "";
}

.order-history__tab--active {
  color: #ff5f07;
}

.order-history__tab--active::after {
  background: #ff5f07;
}

.order-history__active-list {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.order-history__section-title {
  margin: 0 0 12px;
  color: #333333;
  font-size: 18px;
  font-weight: 800;
}

.order-history__completed-list {
  overflow: hidden;
  border: 1px solid #eeeeee;
  border-radius: 10px;
}

.order-history__load-more {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;

  min-width: 150px;
  min-height: 42px;
  margin: 16px auto 0;
  padding: 0 18px;

  border: 1px solid #dddddd;
  border-radius: 8px;
  background: #ffffff;
  color: #555555;

  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
}

.order-history__load-more:hover {
  border-color: #ff5f07;
  color: #ff5f07;
}

.order-history__empty {
  padding: 60px 20px;
  color: #999999;
  text-align: center;
}

@media (max-width: 767.98px) {
  .order-history {
    padding: 20px 16px;
  }

  .order-history__title {
    font-size: 25px;
  }

  .order-history__tabs {
    gap: 0;
  }

  .order-history__tab {
    min-width: 0;
    flex: 1;
    padding-right: 6px;
    padding-left: 6px;
    font-size: 15px;
  }

  .order-history__completed-list {
    overflow: visible;
    border: 0;
  }
}
</style>
