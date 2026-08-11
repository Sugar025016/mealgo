<script setup lang="ts">
import { computed, ref, ComponentPublicInstance } from "vue";
import ShopCategoryTabs from "@/components/shop/ShopCategoryTabs.vue";
import ShopMenuSearch from "@/components/shop/ShopMenuSearch.vue";
import ProductCard from "@/components/product/ProductCard.vue";

import ProductDetailModal, {
  type ProductDetailModalProduct,
  type ProductDetailSubmitPayload,
} from "@/components/product/ProductDetailModal.vue";

type Product = {
  id: number;
  name: string;
  description: string;
  price: number;
  image: string;
  category: string;
};

const categories = ["推薦餐點", "早餐", "漢堡", "吐司", "飲品", "點心"];

const products = ref<Product[]>([
  {
    id: 1,
    name: "經典豬肉起司堡",
    description: "豬肉排、生菜、起司、番茄",
    price: 85,
    image: "https://picsum.photos/900/600?random=1",
    category: "推薦餐點",
    isFavorite: false,
    ingredients: ["漢堡麵包", "豬肉排", "生菜", "起司", "番茄"],
    allergenNotice: "本產品含有麩質、蛋及乳製品。",
    optionGroups: [
      {
        id: 1,
        name: "辣度選擇",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 1,
            name: "不辣",
            extraPrice: 0,
          },
          {
            id: 2,
            name: "小辣 🌶️",
            extraPrice: 0,
          },
          {
            id: 3,
            name: "中辣 🌶️🌶️",
            extraPrice: 0,
          },
          {
            id: 4,
            name: "大辣 🌶️🌶️🌶️",
            extraPrice: 0,
          },
        ],
      },
      {
        id: 2,
        name: "加料選擇",
        type: "CHECKBOX",
        required: false,
        maxSelect: 3,
        options: [
          {
            id: 5,
            name: "加蛋",
            extraPrice: 10,
          },
          {
            id: 6,
            name: "加起司",
            extraPrice: 15,
          },
          {
            id: 7,
            name: "加培根",
            extraPrice: 20,
          },
        ],
      },
    ],
  },
  {
    id: 2,
    name: "原味蛋餅",
    description: "手工蛋餅皮、蔥花蛋",
    price: 35,
    image: "https://picsum.photos/900/600?random=2",
    category: "推薦餐點",
    isFavorite: false,
    ingredients: ["蛋餅皮", "雞蛋", "蔥花", "醬油膏"],
    allergenNotice: "本產品含有蛋製品及麩質。",
    optionGroups: [
      {
        id: 3,
        name: "辣度選擇",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 8,
            name: "不辣",
            extraPrice: 0,
          },
          {
            id: 9,
            name: "小辣 🌶️",
            extraPrice: 0,
          },
          {
            id: 10,
            name: "中辣 🌶️🌶️",
            extraPrice: 0,
          },
          {
            id: 11,
            name: "大辣 🌶️🌶️🌶️",
            extraPrice: 0,
          },
        ],
      },
      {
        id: 4,
        name: "加料選擇",
        type: "CHECKBOX",
        required: false,
        maxSelect: 3,
        options: [
          {
            id: 12,
            name: "加蛋",
            extraPrice: 10,
          },
          {
            id: 13,
            name: "加起司",
            extraPrice: 15,
          },
          {
            id: 14,
            name: "加培根",
            extraPrice: 20,
          },
          {
            id: 15,
            name: "加火腿",
            extraPrice: 15,
            disabled: true,
          },
        ],
      },
      {
        id: 5,
        name: "醬料選擇",
        type: "CHECKBOX",
        required: false,
        maxSelect: 2,
        options: [
          {
            id: 16,
            name: "醬油膏",
            extraPrice: 0,
          },
          {
            id: 17,
            name: "甜辣醬",
            extraPrice: 0,
          },
          {
            id: 18,
            name: "胡椒粉",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
  {
    id: 3,
    name: "奶油厚片吐司",
    description: "香濃奶油厚片，烤得酥香",
    price: 35,
    image: "https://picsum.photos/900/600?random=3",
    category: "早餐",
    isFavorite: false,
    ingredients: ["厚片吐司", "奶油"],
    allergenNotice: "本產品含有麩質及乳製品。",
    optionGroups: [
      {
        id: 6,
        name: "吐司口感",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 19,
            name: "正常",
            extraPrice: 0,
          },
          {
            id: 20,
            name: "烤酥一點",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
  {
    id: 4,
    name: "荷包蛋",
    description: "半熟荷包蛋",
    price: 15,
    image: "https://picsum.photos/900/600?random=4",
    category: "早餐",
    isFavorite: false,
    ingredients: ["雞蛋"],
    allergenNotice: "本產品含有蛋製品。",
    optionGroups: [
      {
        id: 7,
        name: "熟度選擇",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 21,
            name: "半熟",
            extraPrice: 0,
          },
          {
            id: 22,
            name: "全熟",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
  {
    id: 14,
    name: "雙蛋荷包蛋",
    description: "兩顆現煎荷包蛋",
    price: 30,
    image: "https://picsum.photos/900/600?random=14",
    category: "早餐",
    isFavorite: false,
    ingredients: ["雞蛋"],
    allergenNotice: "本產品含有蛋製品。",
    optionGroups: [
      {
        id: 8,
        name: "熟度選擇",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 23,
            name: "半熟",
            extraPrice: 0,
          },
          {
            id: 24,
            name: "全熟",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
  {
    id: 5,
    name: "豬肉漢堡",
    description: "豬肉排、生菜、起司",
    price: 75,
    image: "https://picsum.photos/900/600?random=5",
    category: "漢堡",
    isFavorite: false,
    ingredients: ["漢堡麵包", "豬肉排", "生菜", "起司"],
    allergenNotice: "本產品含有麩質、蛋及乳製品。",
    optionGroups: [
      {
        id: 9,
        name: "辣度選擇",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 25,
            name: "不辣",
            extraPrice: 0,
          },
          {
            id: 26,
            name: "小辣 🌶️",
            extraPrice: 0,
          },
          {
            id: 27,
            name: "中辣 🌶️🌶️",
            extraPrice: 0,
          },
          {
            id: 28,
            name: "大辣 🌶️🌶️🌶️",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
  {
    id: 6,
    name: "香雞堡",
    description: "香酥雞排、生菜、美乃滋",
    price: 80,
    image: "https://picsum.photos/900/600?random=6",
    category: "漢堡",
    isFavorite: false,
    ingredients: ["漢堡麵包", "雞排", "生菜", "美乃滋"],
    allergenNotice: "本產品含有麩質及蛋製品。",
    optionGroups: [],
  },
  {
    id: 7,
    name: "奶茶",
    description: "香濃奶茶，甜度固定",
    price: 30,
    image: "https://picsum.photos/900/600?random=7",
    category: "飲品",
    isFavorite: false,
    ingredients: ["紅茶", "奶精", "糖"],
    allergenNotice: "本產品含有乳製品。",
    optionGroups: [
      {
        id: 10,
        name: "冰量選擇",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 29,
            name: "正常冰",
            extraPrice: 0,
          },
          {
            id: 30,
            name: "少冰",
            extraPrice: 0,
          },
          {
            id: 31,
            name: "去冰",
            extraPrice: 0,
          },
          {
            id: 32,
            name: "熱飲",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
  {
    id: 8,
    name: "紅茶",
    description: "古早味紅茶",
    price: 25,
    image: "https://picsum.photos/900/600?random=8",
    category: "飲品",
    isFavorite: false,
    ingredients: ["紅茶", "糖"],
    optionGroups: [
      {
        id: 11,
        name: "冰量選擇",
        type: "RADIO",
        required: true,
        options: [
          {
            id: 33,
            name: "正常冰",
            extraPrice: 0,
          },
          {
            id: 34,
            name: "少冰",
            extraPrice: 0,
          },
          {
            id: 35,
            name: "去冰",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
  {
    id: 9,
    name: "黃金雞塊",
    description: "酥脆雞塊，搭配番茄醬",
    price: 55,
    image: "https://picsum.photos/900/600?random=9",
    category: "點心",
    isFavorite: false,
    ingredients: ["雞肉", "麵衣"],
    allergenNotice: "本產品含有麩質。",
    optionGroups: [
      {
        id: 12,
        name: "醬料選擇",
        type: "CHECKBOX",
        required: false,
        maxSelect: 2,
        options: [
          {
            id: 36,
            name: "番茄醬",
            extraPrice: 0,
          },
          {
            id: 37,
            name: "甜辣醬",
            extraPrice: 0,
          },
          {
            id: 38,
            name: "胡椒粉",
            extraPrice: 0,
          },
        ],
      },
    ],
  },
]);

const selectedCategory = ref("推薦餐點");
const keyword = ref("");
/* 商品詳細視窗 */
const isProductDetailOpen = ref(false);

const sectionRefs = ref<Record<string, HTMLElement | null>>({});

// function setSectionRef(category: string, el: Element | null) {
//   sectionRefs.value[category] = el as HTMLElement | null;
// }
function setSectionRef(
  name: string,
  el: Element | ComponentPublicInstance | null,
) {
  if (el instanceof HTMLElement) {
    sectionRefs.value[name] = el;
  } else {
    delete sectionRefs.value[name];
  }
}

function scrollToCategory(category: string) {
  selectedCategory.value = category;

  const target = sectionRefs.value[category];

  if (!target) return;

  target.scrollIntoView({
    behavior: "smooth",
    block: "start",
  });
}

const displayCategories = computed(() => {
  return categories
    .map((category) => {
      const items = products.value.filter((product) => {
        const matchCategory = product.category === category;

        const matchKeyword =
          keyword.value.trim() === "" ||
          product.name.includes(keyword.value.trim()) ||
          product.description.includes(keyword.value.trim());

        return matchCategory && matchKeyword;
      });

      return {
        name: category,
        products: items,
      };
    })
    .filter((group) => group.products.length > 0);
});

// function handleAddToCart(product: Product) {
//   console.log("加入購物車", product);
// }

function handleAddToCart(payload: ProductDetailSubmitPayload) {
  console.log("加入購物車資料：", payload);

  /*
  await cartApi.addItem({
    productId: payload.productId,
    qty: payload.quantity,
    remark: payload.remark,
    selectedOptions: payload.selectedOptions,
  });
  */
}
const selectedProduct =
  ref<ProductDetailModalProduct | null>(null);

function openProductDetail(product: Product) {
  selectedProduct.value = product;
  isProductDetailOpen.value = true;
}
</script>

<template>
  <section class="shop-menu-section">
    <div class="shop-menu-section__toolbar">
      <ShopCategoryTabs
        :model-value="selectedCategory"
        :categories="categories"
        @select-category="scrollToCategory"
      />

      <ShopMenuSearch v-model="keyword" />
    </div>

    <div class="shop-menu-section__content">
      <template v-if="displayCategories.length > 0">
        <section
          v-for="group in displayCategories"
          :key="group.name"
          :ref="(el) => setSectionRef(group.name, el)"
          class="shop-menu-section__group"
        >
          <h2 class="shop-menu-section__title">
            {{ group.name }}
          </h2>

          <div class="shop-menu-section__grid">
            <ProductCard
              v-for="product in group.products"
              :key="product.id"
              :product="product"
              @add-to-cart="openProductDetail"
            />
          </div>
          <ProductDetailModal
            v-model="isProductDetailOpen"
            :product="selectedProduct"
            @add-to-cart="handleAddToCart"
          />
        </section>
      </template>

      <div v-else class="shop-menu-section__empty">找不到符合的餐點</div>
    </div>
  </section>
</template>

<style scoped lang="scss">
.shop-menu-section {
  margin-top: 16px;
}

.shop-menu-section__toolbar {
  position: sticky;
  top: 72px;
  z-index: 10;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding: 8px 0;
  border-bottom: 1px solid #e5e7eb;
  background: #fff;
}

.shop-menu-section__content {
  padding-top: 18px;
}

.shop-menu-section__group {
  scroll-margin-top: 150px;
  margin-bottom: 34px;
}

.shop-menu-section__title {
  margin: 0 0 14px;
  color: #111827;
  font-size: 20px;
  font-weight: 800;
}

.shop-menu-section__grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px 18px;
}

.shop-menu-section__empty {
  padding: 40px 0;
  color: #9ca3af;
  text-align: center;
  font-size: 15px;
}
@media (max-width: 1200px) {
  .shop-menu-section__toolbar {
    align-items: stretch;
    flex-direction: column;
  }

  .shop-menu-section__grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 992px) {
  .shop-menu-section__grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
    gap: 14px 18px;
  }
}

@media (max-width: 768px) {
  .shop-menu-section__toolbar {
    align-items: stretch;
    flex-direction: column;
  }

  .shop-menu-section__grid {
    grid-template-columns: 1fr;
  }
}
</style>
