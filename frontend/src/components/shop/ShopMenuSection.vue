<script setup lang="ts">
import { computed, ref } from "vue";
import ShopCategoryTabs from "@/components/shop/ShopCategoryTabs.vue";
import ShopMenuSearch from "@/components/shop/ShopMenuSearch.vue";
import ShopProductCard from "@/components/shop/ShopProductCard.vue";

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
    image: "https://picsum.photos/300/200?random=1",
    category: "推薦餐點",
  },
  {
    id: 2,
    name: "原味蛋餅",
    description: "手工蛋餅皮、蔥花蛋",
    price: 35,
    image: "https://picsum.photos/300/200?random=2",
    category: "推薦餐點",
  },
  {
    id: 3,
    name: "奶油厚片吐司",
    description: "香濃奶油厚片，烤得酥香",
    price: 35,
    image: "https://picsum.photos/300/200?random=3",
    category: "早餐",
  },
  {
    id: 4,
    name: "荷包蛋",
    description: "半熟荷包蛋",
    price: 15,
    image: "https://picsum.photos/300/200?random=4",
    category: "早餐",
  },
  {
    id: 14,
    name: "荷包蛋",
    description: "半熟荷包蛋",
    price: 15,
    image: "https://picsum.photos/300/200?random=4",
    category: "早餐",
  },
  {
    id: 5,
    name: "豬肉漢堡",
    description: "豬肉排、生菜、起司",
    price: 75,
    image: "https://picsum.photos/300/200?random=5",
    category: "漢堡",
  },
  {
    id: 6,
    name: "香雞堡",
    description: "香酥雞排、生菜、美乃滋",
    price: 80,
    image: "https://picsum.photos/300/200?random=6",
    category: "漢堡",
  },
  {
    id: 7,
    name: "奶茶",
    description: "香濃奶茶，甜度固定",
    price: 30,
    image: "https://picsum.photos/300/200?random=7",
    category: "飲品",
  },
  {
    id: 8,
    name: "紅茶",
    description: "古早味紅茶",
    price: 25,
    image: "https://picsum.photos/300/200?random=8",
    category: "飲品",
  },
  {
    id: 9,
    name: "黃金雞塊",
    description: "酥脆雞塊，搭配番茄醬",
    price: 55,
    image: "https://picsum.photos/300/200?random=9",
    category: "點心",
  },
]);

const selectedCategory = ref("推薦餐點");
const keyword = ref("");

const sectionRefs = ref<Record<string, HTMLElement | null>>({});

function setSectionRef(category: string, el: Element | null) {
  sectionRefs.value[category] = el as HTMLElement | null;
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

function handleAddToCart(product: Product) {
  console.log("加入購物車", product);
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
            <ShopProductCard
              v-for="product in group.products"
              :key="product.id"
              :product="product"
              @add-to-cart="handleAddToCart"
            />
          </div>
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
