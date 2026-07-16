<script setup lang="ts">
type BreadcrumbItem = {
  label: string;
  to?: string;
};

defineProps<{
  items: BreadcrumbItem[];
}>();
</script>

<template>
  <nav class="mg-breadcrumb" aria-label="breadcrumb">
    <template
      v-for="(item, index) in items"
      :key="item.label"
    >
      <RouterLink
        v-if="item.to && index !== items.length - 1"
        :to="item.to"
        class="mg-breadcrumb__link"
      >
        {{ item.label }}
      </RouterLink>

      <span
        v-else
        class="mg-breadcrumb__current"
      >
        {{ item.label }}
      </span>

      <span
        v-if="index !== items.length - 1"
        class="mg-breadcrumb__separator"
      >
        /
      </span>
    </template>
  </nav>
</template>

<style scoped lang="scss">
.mg-breadcrumb {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  color: #6b7280;
  font-size: 15px;
}

.mg-breadcrumb__link {
  color: #4b5563;
  text-decoration: none;

  &:hover {
    color: #ff5a00;
  }
}

.mg-breadcrumb__current {
  color: #ff5a00;
  font-weight: 700;
}

.mg-breadcrumb__separator {
  color: #9ca3af;
}
</style>