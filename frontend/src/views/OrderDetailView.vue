<script setup lang="ts">
import { computed, ref } from "vue";
import Swal from "sweetalert2";

import OrderStatusCard from "@/components/orderDetail/OrderStatusCard.vue";
import OrderItemsCard from "@/components/orderDetail/OrderItemsCard.vue";
import OrderDeliveryMap from "@/components/orderDetail/OrderDeliveryMap.vue";
import OrderDeliveryInfo from "@/components/orderDetail/OrderDeliveryInfo.vue";
import OrderPaymentInfo from "@/components/orderDetail/OrderPaymentInfo.vue";
import OrderAmountSummary from "@/components/orderDetail/OrderAmountSummary.vue";

import type { Order } from "@/types/order";

const props = defineProps<{
  order?: string;
}>();

const orderData = ref<Order>({
  id: 18,
  orderNumber: props.order || "MG202607220018",
  status: "ON_THE_WAY",
  fulfillmentType: "DELIVERY",
  createdAt: "2026/07/22 19:12",
  estimatedArrival: "19:45–19:55",

  shop: {
    name: "晨光早餐（健康店）",
    logo: "https://picsum.photos/100/100?random=31",
    address: "台南市南區健康路二段 66 號",
  },

  receiver: {
    name: "黃小姐",
    phone: "09**-***-168",
    address: "台南市南區健康路二段 123 號 7 樓",
  },

  payment: {
    method: "信用卡",
    detail: "•••• 1688",
  },

  amounts: {
    subtotal: 150,
    deliveryFee: 40,
    discount: 20,
    total: 170,
  },

  items: [
    {
      id: 1,
      name: "經典豬肉起司堡",
      image: "https://picsum.photos/160/120?random=41",
      price: 85,
      qty: 1,
    },
    {
      id: 2,
      name: "薯餅",
      image: "https://picsum.photos/160/120?random=42",
      price: 35,
      qty: 1,
    },
    {
      id: 3,
      name: "大杯冰紅茶",
      image: "https://picsum.photos/160/120?random=43",
      price: 30,
      qty: 1,
      remark: "飲料去冰",
    },
  ],

  courier: {
    name: "王先生",
    phone: "0912345678",
    plateNumber: "ABC-1234",
  },
});

const isDelivery = computed(() => {
  return orderData.value.fulfillmentType === "DELIVERY";
});

// 外送配送中才顯示即時地圖
const showDeliveryMap = computed(() => {
  return isDelivery.value && orderData.value.status === "ON_THE_WAY";
});

const canCancel = computed(() => {
  return ["PENDING", "ACCEPTED"].includes(orderData.value.status);
});

function goBack() {
  window.history.back();
}

function contactSupport() {
  Swal.fire({
    icon: "info",
    title: "聯絡客服",
    text: `訂單編號：${orderData.value.orderNumber}`,
    confirmButtonText: "確定",
    confirmButtonColor: "#ff5f07",
  });
}

async function cancelOrder() {
  const result = await Swal.fire({
    icon: "warning",
    title: "確定取消訂單？",
    text: "取消後將無法復原",
    showCancelButton: true,
    confirmButtonText: "確定取消",
    cancelButtonText: "返回",
    confirmButtonColor: "#ff5f07",
  });

  if (!result.isConfirmed) return;

  // TODO：呼叫取消訂單 API
  orderData.value.status = "CANCELLED";

  await Swal.fire({
    icon: "success",
    title: "訂單已取消",
    confirmButtonColor: "#ff5f07",
  });
}
</script>

<template>
  <main class="order-detail">
    <div class="order-detail__container">
      <header class="order-detail__heading">
        <button
          type="button"
          class="order-detail__back"
          aria-label="返回歷史訂單"
          @click="goBack"
        >
          <i class="bi bi-arrow-left"></i>
        </button>

        <h1>訂單詳情</h1>

        <div
          class="order-detail__number"
          :aria-label="`訂單編號 ${orderData.orderNumber}`"
        >
          <i class="bi bi-receipt"></i>
          <span>{{ orderData.orderNumber }}</span>
        </div>
      </header>

      <div class="order-detail__layout">
        <div class="order-detail__main">
          <OrderStatusCard
            :status="orderData.status"
            :fulfillment-type="orderData.fulfillmentType"
            :estimated-arrival="orderData.estimatedArrival"
          />

          <OrderDeliveryMap
            v-if="showDeliveryMap"
            :destination="orderData.receiver.address"
            :shop="orderData.shop"
            :courier="orderData.courier"
            :fulfillment-type="orderData.fulfillmentType"
          />

          <OrderItemsCard
            :shop-name="orderData.shop.name"
            :shop-logo="orderData.shop.logo"
            :items="orderData.items"
          />
        </div>

        <aside class="order-detail__aside">
          <OrderDeliveryInfo
            :fulfillment-type="orderData.fulfillmentType"
            :shop="orderData.shop"
            :receiver="orderData.receiver"
            :created-at="orderData.createdAt"
          />

          <OrderPaymentInfo :payment="orderData.payment" />

          <OrderAmountSummary
            :amounts="orderData.amounts"
            :fulfillment-type="orderData.fulfillmentType"
          />

          <div class="order-detail__actions">
            <button
              type="button"
              class="order-detail__action"
              @click="contactSupport"
            >
              <i class="bi bi-headset"></i>
              聯絡客服
            </button>

            <button
              v-if="canCancel"
              type="button"
              class="order-detail__action"
              @click="cancelOrder"
            >
              <i class="bi bi-x-circle"></i>
              取消訂單
            </button>
          </div>
        </aside>
      </div>
    </div>
  </main>
</template>

<style scoped lang="scss">
.order-detail {
  min-height: calc(100vh - 80px);
  padding: 0px 0 48px;
  background: #fafafa;
}

.order-detail__container {
  width: min(100% - 64px, 1320px);
  margin: 0 auto;
}

.order-detail__heading {
  display: flex;
  align-items: center;
  gap: 16px;
  min-height: 52px;
  //   margin-bottom: 20px;

  h1 {
    margin: 0 0 0 1%;
    color: #222;
    font-size: 30px;
    font-weight: 700;
    line-height: 1.2;
  }
}

.order-detail__back {
  display: none;
  place-items: center;
  padding: 4px;
  border: 0;
  background: transparent;
  color: #333;
  font-size: 24px;
}

.order-detail__number {
  display: inline-flex;
  align-items: baseline;
  gap: 9px;

  color: #343434;
  font-size: 15px;
  font-weight: 600;
  line-height: 1;
  white-space: nowrap;

  i {
    position: relative;
    top: 4px;

    color: #ff5f07;
    font-size: 21px;
    line-height: 1;

    &::before {
      display: block;
      line-height: 1;
      vertical-align: 0;
    }
  }

  span {
    line-height: 1;
  }
}

.order-detail__layout {
  display: grid;
  grid-template-columns:
    minmax(0, 2fr)
    minmax(300px, 0.82fr);
  align-items: start;
  gap: 24px;
}

.order-detail__main,
.order-detail__aside {
  display: grid;
  gap: 18px;
  min-width: 0;
}

.order-detail__actions {
  display: grid;
  gap: 12px;
}

.order-detail__action {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 9px;

  width: 100%;
  min-height: 48px;
  padding: 10px 16px;

  border: 1px solid #ff5f07;
  border-radius: 10px;
  background: #fff;
  color: #ff5f07;

  font-size: 15px;
  font-weight: 600;

  transition:
    background-color 0.2s ease,
    color 0.2s ease;

  i {
    font-size: 18px;
  }

  &:hover {
    background: #fff2ea;
  }
}

@media (max-width: 992px) {
  .order-detail__main,
  .order-detail__aside {
    gap: 12px;
  }
  .order-detail__layout {
    gap: 14px;
  }
  .order-detail__container {
    width: min(100% - 32px, 820px);
  }

  .order-detail__layout {
    grid-template-columns: 1fr;
  }

  .order-detail__back {
    display: inline-grid;
  }

  .order-detail__aside {
    grid-template-columns: 1fr;
  }

  .order-detail__actions {
    grid-column: auto;
    grid-template-columns: 1fr;
  }
}

@media (max-width: 575.98px) {
  .order-detail {
    padding: 16px 0 32px;
  }

  .order-detail__container {
    width: min(100% - 24px, 520px);
  }

  .order-detail__heading {
    flex-wrap: wrap;
    gap: 8px 10px;
    margin-bottom: 16px;

    h1 {
      font-size: 24px;
      //   min-width: 160px;
    }
  }

  .order-detail__number {
    // flex-basis: 40%;
    margin-left: 12px;
    font-size: 13px;

    i {
      font-size: 18px;
    }
  }

  .order-detail__layout,
  .order-detail__main,
  .order-detail__aside {
    gap: 14px;
  }

  .order-detail__actions {
    grid-column: auto;
    grid-template-columns: 1fr;
  }
}
</style>
