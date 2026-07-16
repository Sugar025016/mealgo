<script setup lang="ts">
import { computed, ref } from "vue";
import CartProductCard from "@/components/cart/ProductCartCard.vue";

type CartItem = {
  id: number;
  name: string;
  price: number;
  qty: number;
  image: string;
  remark?: string;
};

const cartItems = ref<CartItem[]>([
  {
    id: 1,
    name: "經典豬肉起司堡",
    price: 85,
    qty: 1,
    image: "https://picsum.photos/120/120?random=1",
  },
  {
    id: 2,
    name: "奶茶",
    price: 30,
    qty: 2,
    image: "https://picsum.photos/120/120?random=2",
  },
  {
    id: 3,
    name: "原味蛋餅",
    price: 35,
    qty: 1,
    image: "https://picsum.photos/120/120?random=3",
  },
  {
    id: 4,
    name: "荷包蛋",
    price: 15,
    qty: 1,
    image: "https://picsum.photos/120/120?random=4",
  },
  {
    id: 5,
    name: "脆薯",
    price: 45,
    qty: 1,
    image: "https://picsum.photos/120/120?random=5",
  },
  {
    id: 6,
    name: "黃金雞塊",
    price: 55,
    qty: 1,
    image: "https://picsum.photos/120/120?random=6",
  },
]);

const orderNote = ref("");

const deliveryFee = 40;

const subtotal = computed(() => {
  return cartItems.value.reduce((total, item) => {
    return total + item.price * item.qty;
  }, 0);
});

const totalPrice = computed(() => {
  if (cartItems.value.length === 0) return 0;
  return subtotal.value + deliveryFee;
});

function increaseQty(item: CartItem) {
  item.qty++;
}

function decreaseQty(item: CartItem) {
  if (item.qty > 1) {
    item.qty--;
    return;
  }

  removeItem(item.id);
}

function removeItem(id: number) {
  cartItems.value = cartItems.value.filter((item) => item.id !== id);
}

function checkout() {
  console.log("前往結帳", {
    items: cartItems.value,
    orderNote: orderNote.value,
    subtotal: subtotal.value,
    deliveryFee,
    totalPrice: totalPrice.value,
  });
}
</script>

<template>
  <aside class="shop-cart-panel">
    <div v-if="cartItems.length === 0" class="shop-cart-panel__empty">
      <div class="shop-cart-panel__empty-icon">
        <i class="bi bi-cart"></i>
      </div>

      <h3>購物車是空的</h3>
      <p>選擇喜歡的餐點加入購物車</p>
    </div>

    <div v-else class="shop-cart-panel__body">
      <div class="shop-cart-panel__scroll">
        <div class="shop-cart-panel__items">
          <CartProductCard
            v-for="item in cartItems"
            :key="item.id"
            :item="item"
            @increase="increaseQty"
            @decrease="decreaseQty"
          />
        </div>

        <div class="shop-cart-panel__note">
          <label for="orderNote">訂單備註</label>

          <textarea
            id="orderNote"
            v-model="orderNote"
            rows="1"
            placeholder="例如：飲料少冰、餐點不要洋蔥"
          ></textarea>
        </div>

        <div class="shop-cart-panel__summary">
          <div class="shop-cart-panel__summary-row">
            <span>小計</span>
            <strong>NT$ {{ subtotal }}</strong>
          </div>

          <div class="shop-cart-panel__summary-row">
            <span>外送費</span>
            <strong>NT$ {{ deliveryFee }}</strong>
          </div>

          <div class="shop-cart-panel__summary-total">
            <span>總計</span>
            <strong>NT$ {{ totalPrice }}</strong>
          </div>
        </div>
      </div>

      <button type="button" class="shop-cart-panel__checkout" @click="checkout">
        前往結帳
        <span>NT$ {{ totalPrice }}</span>
      </button>
    </div>
  </aside>
</template>

<style scoped lang="scss">
.shop-cart-panel {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 100px);
  padding: 14px 10px 12px 18px;
  border: 1px solid #e5e7eb;
  border-radius: 18px;
  background: #fff;
  box-shadow: 0 10px 28px rgba(15, 23, 42, 0.08);
}

.shop-cart-panel__empty {
  padding: 44px 0 28px;
  text-align: center;

  h3 {
    margin: 12px 0 6px;
    color: #111827;
    font-size: 18px;
    font-weight: 800;
  }

  p {
    margin: 0;
    color: #9ca3af;
    font-size: 14px;
  }
}

.shop-cart-panel__empty-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 64px;
  height: 64px;
  border-radius: 999px;
  color: #ff5a00;
  background: #fff7ed;

  i {
    font-size: 30px;
  }
}

.shop-cart-panel__body {
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.shop-cart-panel__scroll {
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-height: calc(100vh - 180px);
  overflow-y: auto;
  padding-right: 8px;
  padding-bottom: 10px;
  scrollbar-width: thin;
  scrollbar-color: #fed7aa transparent;

  &::-webkit-scrollbar {
    width: 4px;
  }

  &::-webkit-scrollbar-track {
    background: transparent;
  }

  &::-webkit-scrollbar-thumb {
    border-radius: 999px;
    background: #ffa0a0;
  }

  &::-webkit-scrollbar-thumb:hover {
    background: #f88f8f;
  }
}

.shop-cart-panel__items {
  display: flex;
  flex-direction: column;
  
//   min-height: calc(100vh - 400px);
  gap: 8px;
}

.shop-cart-panel__note {
  padding-top: 8px;
  border-top: 1px solid #f1f5f9;

  label {
    display: block;
    margin-bottom: 6px;
    color: #111827;
    font-size: 14px;
    font-weight: 800;
  }

  textarea {
    width: 100%;
    min-height: 52px;
    resize: none;
    padding: 8px 10px;
    border: 1px solid #e5e7eb;
    border-radius: 10px;
    outline: none;
    color: #111827;
    font-size: 14px;
    line-height: 1.4;

    &::placeholder {
      color: #9ca3af;
    }

    &:focus {
      border-color: #ff5a00;
      box-shadow: 0 0 0 3px rgba(255, 90, 0, 0.12);
    }
  }
}

.shop-cart-panel__summary {
  padding-top: 8px;
  border-top: 1px solid #f1f5f9;
}

.shop-cart-panel__summary-row,
.shop-cart-panel__summary-total {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.shop-cart-panel__summary-row {
  margin-bottom: 5px;
  color: #6b7280;
  font-size: 14px;

  strong {
    color: #374151;
  }
}

.shop-cart-panel__summary-total {
  padding-top: 8px;
  border-top: 1px dashed #e5e7eb;
  color: #111827;
  font-size: 16px;
  font-weight: 800;

  strong {
    color: #ff5a00;
    font-size: 20px;
  }
}

.shop-cart-panel__checkout {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 44px;
  margin-top: 10px;
  padding: 0 16px;
  border: 0;
  border-radius: 12px;
  color: #fff;
  background: #ff5a00;
  font-size: 15px;
  font-weight: 800;
  cursor: pointer;
  box-shadow: 0 8px 18px rgba(255, 90, 0, 0.28);

  &:hover {
    background: #f97316;
  }

  &:active {
    background: #ea580c;
  }

  span {
    font-size: 14px;
  }
}
</style>