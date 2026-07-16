<script setup lang="ts">
const model = defineModel<string>({
  required: true,
});

function clearKeyword() {
  model.value = "";
}
</script>

<template>
  <div class="shop-menu-search">
    <i class="bi bi-search"></i>

    <input
      v-model="model"
      type="text"
      placeholder="搜尋店內餐點"
      class="shop-menu-search__input"
    />

    <button
      v-if="model"
      type="button"
      class="shop-menu-search__clear"
      @click="clearKeyword"
    >
      <i class="bi bi-x-lg"></i>
    </button>
  </div>
</template>

<style scoped lang="scss">
.shop-menu-search {
  display: flex;
  align-items: center;
  width: 260px;
  height: 44px;
  padding: 0 14px;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  background: #fff;

  > i {
    margin-right: 8px;
    color: #9ca3af;
    font-size: 16px;
  }
}

.shop-menu-search__input {
  width: 100%;
  border: 0;
  outline: 0;
  color: #111827;
  background: transparent;
  font-size: 15px;

  &::placeholder {
    color: #9ca3af;
  }
}

.shop-menu-search__clear {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  border: 0;
  color: #9ca3af;
  background: transparent;
  cursor: pointer;

  &:hover {
    color: #ff5a00;
  }

  i {
    font-size: 13px;
  }
}

@media (max-width: 768px) {
  .shop-menu-search {
    width: 100%;
  }
}
</style>