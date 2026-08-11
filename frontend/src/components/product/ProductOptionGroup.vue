<script setup lang="ts">
export type ProductOptionType = "RADIO" | "CHECKBOX";

export type ProductOption = {
  id: number;
  name: string;
  extraPrice: number;
  disabled?: boolean;
};

export type ProductOptionGroup = {
  id: number;
  name: string;
  type: ProductOptionType;
  required: boolean;
  minSelect?: number;
  maxSelect?: number;
  options: ProductOption[];
};

const props = defineProps<{
  group: ProductOptionGroup;
  modelValue: number[];
}>();

const emit = defineEmits<{
  "update:modelValue": [value: number[]];
}>();

function isSelected(optionId: number) {
  return props.modelValue.includes(optionId);
}

function isMaxSelected() {
  if (!props.group.maxSelect) return false;

  return props.modelValue.length >= props.group.maxSelect;
}

function selectOption(option: ProductOption) {
  if (option.disabled) return;

  const selectedIds = [...props.modelValue];
  const index = selectedIds.indexOf(option.id);

  if (props.group.type === "RADIO") {
    /*
     * 必選單選：
     * 點擊選項後直接取代。
     *
     * 非必選單選：
     * 再點一次已選項目可以取消。
     */
    if (!props.group.required && index !== -1) {
      emit("update:modelValue", []);
      return;
    }

    emit("update:modelValue", [option.id]);
    return;
  }

  if (index !== -1) {
    selectedIds.splice(index, 1);
    emit("update:modelValue", selectedIds);
    return;
  }

  if (isMaxSelected()) return;

  selectedIds.push(option.id);
  emit("update:modelValue", selectedIds);
}

function isOptionBlocked(option: ProductOption) {
  if (option.disabled) return true;

  if (props.group.type !== "CHECKBOX") return false;
  if (isSelected(option.id)) return false;

  return isMaxSelected();
}
</script>

<template>
  <section class="product-option-group">
    <header class="product-option-group__header">
      <div class="product-option-group__heading">
        <h3 class="product-option-group__title">
          {{ group.name }}
        </h3>

        <span
          v-if="group.required"
          class="product-option-group__required"
        >
          必選
        </span>

        <span
          v-else
          class="product-option-group__optional"
        >
          選填
        </span>
      </div>

      <span
        v-if="group.type === 'CHECKBOX' && group.maxSelect"
        class="product-option-group__limit"
      >
        最多選 {{ group.maxSelect }} 項
      </span>
    </header>

    <div class="product-option-group__list">
      <label
        v-for="option in group.options"
        :key="option.id"
        class="product-option"
        :class="{
          'product-option--selected': isSelected(option.id),
          'product-option--disabled':
            isOptionBlocked(option),
        }"
      >
        <input
          :type="
            group.type === 'RADIO'
              ? 'radio'
              : 'checkbox'
          "
          :name="`product-option-group-${group.id}`"
          :checked="isSelected(option.id)"
          :disabled="isOptionBlocked(option)"
          @click.prevent="selectOption(option)"
        />

        <span
          class="product-option__control"
          :class="{
            'product-option__control--radio':
              group.type === 'RADIO',
            'product-option__control--checkbox':
              group.type === 'CHECKBOX',
          }"
        />

        <span class="product-option__name">
          {{ option.name }}
        </span>

        <span
          v-if="option.extraPrice > 0"
          class="product-option__price"
        >
          + NT$ {{ option.extraPrice }}
        </span>

        <span
          v-if="option.disabled"
          class="product-option__sold-out"
        >
          售完
        </span>
      </label>
    </div>
  </section>
</template>

<style scoped lang="scss">
.product-option-group {
  padding: 22px 0;
  border-bottom: 1px solid #ededed;
}

.product-option-group__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;

  margin-bottom: 12px;
}

.product-option-group__heading {
  display: flex;
  align-items: center;
  gap: 8px;
}

.product-option-group__title {
  margin: 0;

  color: #292929;
  font-size: 18px;
  font-weight: 700;
}

.product-option-group__required {
  color: #ff5f07;
  font-size: 14px;
}

.product-option-group__optional,
.product-option-group__limit {
  color: #888;
  font-size: 14px;
}

.product-option-group__list {
  display: grid;
  gap: 9px;
}

.product-option {
  display: flex;
  align-items: center;
  gap: 12px;

  min-height: 56px;
  padding: 10px 16px;

  border: 1px solid #dedede;
  border-radius: 10px;

  background: #fff;
  cursor: pointer;

  transition:
    border-color 0.2s ease,
    background-color 0.2s ease;
}

.product-option:hover {
  border-color: #ffb184;
  background: #fffaf7;
}

.product-option--selected {
  border-color: #ff5f07;
  background: #fff8f4;
}

.product-option--disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.product-option--disabled:hover {
  border-color: #dedede;
  background: #fff;
}

.product-option input {
  position: absolute;
  opacity: 0;
  pointer-events: none;
}

.product-option__control {
  position: relative;

  flex: 0 0 22px;
  width: 22px;
  height: 22px;

  border: 2px solid #b8bdc5;
  background: #fff;
}

.product-option__control--radio {
  border-radius: 50%;
}

.product-option__control--checkbox {
  border-radius: 5px;
}

.product-option--selected .product-option__control {
  border-color: #ff5f07;
}

.product-option--selected
  .product-option__control--radio::after {
  position: absolute;
  top: 50%;
  left: 50%;

  width: 11px;
  height: 11px;

  border-radius: 50%;
  background: #ff5f07;

  content: "";
  transform: translate(-50%, -50%);
}

.product-option--selected
  .product-option__control--checkbox {
  background: #ff5f07;
}

.product-option--selected
  .product-option__control--checkbox::after {
  position: absolute;
  top: 2px;
  left: 6px;

  width: 6px;
  height: 11px;

  border-right: 2px solid #fff;
  border-bottom: 2px solid #fff;

  content: "";
  transform: rotate(45deg);
}

.product-option__name {
  min-width: 0;

  color: #333;
  font-size: 16px;
  line-height: 1.5;
}

.product-option__price {
  margin-left: auto;

  color: #666;
  font-size: 15px;
  white-space: nowrap;
}

.product-option__sold-out {
  margin-left: auto;

  color: #999;
  font-size: 14px;
}

@media (max-width: 575.98px) {
  .product-option-group {
    padding: 18px 0;
  }

  .product-option-group__title {
    font-size: 17px;
  }

  .product-option {
    min-height: 52px;
    padding: 9px 13px;
  }

  .product-option__name,
  .product-option__price {
    font-size: 14px;
  }
}
</style>