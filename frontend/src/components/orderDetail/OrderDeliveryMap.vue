<script setup lang="ts">
import { computed } from "vue";

import type {
  FulfillmentType,
  Order,
} from "@/types/order";

type Courier = NonNullable<Order["courier"]> & {
  avatar?: string;
  etaMinutes?: number;
};

type Shop = Order["shop"] & {
  phone?: string;
};

const props = withDefaults(
  defineProps<{
    fulfillmentType?: FulfillmentType;
    destination: string;
    courier?: Courier;
    shop?: Shop;
  }>(),
  {
    fulfillmentType: "DELIVERY",
    courier: undefined,
    shop: undefined,
  },
);

const emit = defineEmits<{
  message: [];
}>();

const isDelivery = computed(() => {
  return props.fulfillmentType === "DELIVERY";
});

const mapDestination = computed(() => {
  if (isDelivery.value) {
    return props.destination;
  }

  return props.shop?.address || props.destination;
});

const mapUrl = computed(() => {
  return (
    "https://www.google.com/maps" +
    `?q=${encodeURIComponent(mapDestination.value)}` +
    "&output=embed"
  );
});

const navigationUrl = computed(() => {
  return (
    "https://www.google.com/maps/dir/" +
    "?api=1" +
    `&destination=${encodeURIComponent(mapDestination.value)}`
  );
});

const courierAvatar = computed(() => {
  return (
    props.courier?.avatar ||
    "https://picsum.photos/100/100?random=81"
  );
});

const etaMinutes = computed(() => {
  return props.courier?.etaMinutes ?? 6;
});
</script>

<template>
  <section class="order-map">
    <!-- <h2>
      {{
        isDelivery
          ? "即時配送進度"
          : "自取店家位置"
      }}
    </h2> -->

    <iframe
      class="order-map__frame"
      :src="mapUrl"
      :title="
        isDelivery
          ? '訂單配送地圖'
          : '自取店家位置'
      "
      loading="lazy"
      allowfullscreen
      referrerpolicy="no-referrer-when-downgrade"
    ></iframe>

    <!-- 外送員資訊 -->
    <div
      v-if="isDelivery && courier"
      class="order-map__courier"
    >
      <div class="order-map__courier-profile">
        <img
          :src="courierAvatar"
          :alt="`外送員 ${courier.name}`"
        />

        <div class="order-map__courier-info">
          <strong>外送員 {{ courier.name }}</strong>

          <span>
            <i class="bi bi-scooter"></i>
            {{ courier.plateNumber }}
          </span>
        </div>
      </div>

      <p class="order-map__courier-status">
        正在前往店家，約
        <strong>{{ etaMinutes }}</strong>
        分鐘抵達
      </p>

      <div class="order-map__courier-actions">
        <a
          :href="`tel:${courier.phone}`"
          aria-label="聯絡外送員"
        >
          <i class="bi bi-telephone-fill"></i>
        </a>

        <button
          type="button"
          aria-label="傳送訊息給外送員"
          @click="emit('message')"
        >
          <i class="bi bi-chat-dots-fill"></i>
        </button>
      </div>
    </div>

    <!-- 自取店家資訊 -->
    <div
      v-else-if="!isDelivery && shop"
      class="order-map__shop"
    >
      <div class="order-map__shop-profile">
        <img
          :src="shop.logo"
          :alt="shop.name"
        />

        <div>
          <strong>{{ shop.name }}</strong>
          <span>{{ shop.address }}</span>
        </div>
      </div>

      <div class="order-map__shop-actions">
        <a
          v-if="shop.phone"
          :href="`tel:${shop.phone}`"
          aria-label="聯絡店家"
        >
          <i class="bi bi-telephone-fill"></i>
        </a>

        <a
          :href="navigationUrl"
          target="_blank"
          rel="noopener noreferrer"
          aria-label="開始導航"
        >
          <i class="bi bi-navigation-fill"></i>
        </a>
      </div>
    </div>
  </section>
</template>

<style scoped lang="scss">
.order-map {
  overflow: hidden;
  border: 1px solid #eee;
  border-radius: 14px;
  background: #fff;
  box-shadow: 0 4px 16px rgb(0 0 0 / 5%);

  h2 {
    margin: 0;
    padding: 16px 20px;
    color: #222;
    font-size: 18px;
    font-weight: 700;
  }
}

.order-map__frame {
  display: block;
  width: 100%;
  height: 230px;
  border: 0;
}

.order-map__courier {
  display: grid;
  grid-template-columns:
    minmax(190px, auto)
    minmax(180px, 1fr)
    auto;
  align-items: center;
  gap: 20px;

  min-height: 86px;
  padding: 12px 20px;
  border-top: 1px solid #eee;
}

.order-map__courier-profile {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 0;

  img {
    flex: 0 0 auto;
    width: 52px;
    height: 52px;
    border-radius: 50%;
    object-fit: cover;
  }
}

.order-map__courier-info {
  display: grid;
  gap: 4px;
  min-width: 0;

  strong {
    overflow: hidden;
    color: #222;
    font-size: 16px;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  span {
    display: flex;
    align-items: center;
    gap: 7px;
    color: #777;
    font-size: 14px;
  }

  i {
    color: #737ba0;
  }
}

.order-map__courier-status {
  margin: 0;
  color: #333;
  font-size: 14px;
  text-align: center;

  strong {
    color: #ff5f07;
    font-size: 17px;
  }
}

.order-map__courier-actions,
.order-map__shop-actions {
  display: flex;
  align-items: center;
  gap: 12px;

  a,
  button {
    display: grid;
    flex: 0 0 auto;
    width: 46px;
    height: 46px;
    place-items: center;

    padding: 0;
    border: 1px solid #ff5f07;
    border-radius: 50%;
    background: #fff;
    color: #ff5f07;

    font-size: 19px;
    text-decoration: none;
    cursor: pointer;
  }

  a:hover,
  button:hover {
    background: #fff2ea;
  }
}

.order-map__shop {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;

  min-height: 86px;
  padding: 12px 20px;
  border-top: 1px solid #eee;
}

.order-map__shop-profile {
  display: flex;
  align-items: center;
  gap: 12px;
  min-width: 0;

  img {
    flex: 0 0 auto;
    width: 52px;
    height: 52px;
    border-radius: 10px;
    object-fit: cover;
  }

  div {
    display: grid;
    gap: 4px;
    min-width: 0;
  }

  strong {
    color: #222;
    font-size: 16px;
  }

  span {
    overflow: hidden;
    color: #777;
    font-size: 13px;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

@media (max-width: 767.98px) {
  .order-map__courier {
    grid-template-columns: minmax(0, 1fr) auto;
  }

  .order-map__courier-status {
    display: none;
  }
}

@media (max-width: 575.98px) {
  .order-map h2 {
    padding: 14px 16px;
    font-size: 16px;
  }

  .order-map__frame {
    height: 220px;
  }

  .order-map__courier,
  .order-map__shop {
    padding: 12px 16px;
  }

  .order-map__courier-profile img,
  .order-map__shop-profile img {
    width: 46px;
    height: 46px;
  }

  .order-map__courier-actions,
  .order-map__shop-actions {
    gap: 8px;

    a,
    button {
      width: 42px;
      height: 42px;
    }
  }
}
</style>