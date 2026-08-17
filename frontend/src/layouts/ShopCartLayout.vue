<script setup lang="ts">
interface BreadcrumbItem {
  label: string;
  to?: string;
}

withDefaults(
  defineProps<{
    breadcrumbItems?: BreadcrumbItem[];
  }>(),
  {
    breadcrumbItems: () => [],
  },
);
</script>

<template>
  <section class="shop-cart-layout">
    <nav
      v-if="breadcrumbItems.length"
      class="shop-cart-layout__breadcrumb"
      aria-label="麵包屑"
    >
      <template
        v-for="(item, index) in breadcrumbItems"
        :key="`${item.label}-${index}`"
      >
        <RouterLink
          v-if="item.to"
          :to="item.to"
          class="shop-cart-layout__breadcrumb-link"
        >
          {{ item.label }}
        </RouterLink>

        <span
          v-else
          class="shop-cart-layout__breadcrumb-current"
          aria-current="page"
        >
          {{ item.label }}
        </span>

        <i
          v-if="index < breadcrumbItems.length - 1"
          class="bi bi-chevron-right shop-cart-layout__breadcrumb-icon"
          aria-hidden="true"
        ></i>
      </template>
    </nav>

    <div class="shop-cart-layout__content">
      <section class="shop-cart-layout__main">
        <div class="shop-cart-layout__body">
          <slot />
        </div>
      </section>

      <aside class="shop-cart-layout__cart">
        <slot name="cart" />
      </aside>
    </div>
  </section>
</template>

<style scoped lang="scss">
.shop-cart-layout {
  width: 100%;
}

.shop-cart-layout__breadcrumb {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 7px;
  margin-bottom: 14px;
  color: #777;
  font-size: 14px;
}

.shop-cart-layout__breadcrumb-link {
  color: #666;
  text-decoration: none;

  &:hover {
    color: #ff5f07;
  }
}

.shop-cart-layout__breadcrumb-current {
  color: #202938;
  font-weight: 600;
}

.shop-cart-layout__breadcrumb-icon {
  color: #aaa;
  font-size: 11px;
}

.shop-cart-layout__content {
  display: grid;
  grid-template-columns: minmax(0, 1fr) minmax(320px, 390px);
  align-items: start;
  gap: 22px;
}

.shop-cart-layout__main,
.shop-cart-layout__body {
  min-width: 0;
}

.shop-cart-layout__cart {
  position: sticky;
  top: 96px;
  min-width: 0;
}

@media (max-width: 992px) {
  .shop-cart-layout__content {
    grid-template-columns: minmax(0, 1fr);
  }

  .shop-cart-layout__cart {
    position: static;
  }
}

@media (max-width: 576px) {
  .shop-cart-layout__breadcrumb {
    padding: 0 4px;
    font-size: 13px;
  }
}
</style>