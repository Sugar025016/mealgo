<script setup lang="ts">
import {
  computed,
  nextTick,
  onBeforeUnmount,
  onMounted,
  ref,
  watch,
} from "vue";

import ProductOptionGroup, {
  type ProductOptionGroup as ProductOptionGroupType,
} from "@/components/product/ProductOptionGroup.vue";

export type ProductDetailModalProduct = {
  id: number;
  name: string;
  description: string;
  price: number;
  image: string;
  isFavorite?: boolean;
  ingredients?: string[];
  allergenNotice?: string;
  optionGroups?: ProductOptionGroupType[];
};

export type ProductDetailSubmitPayload = {
  productId: number;
  quantity: number;
  remark: string;

  selectedOptions: {
    groupId: number;
    groupName: string;

    options: {
      optionId: number;
      optionName: string;
      extraPrice: number;
    }[];
  }[];

  productPrice: number;
  optionPrice: number;
  unitPrice: number;
  totalPrice: number;
};

const props = defineProps<{
  modelValue: boolean;
  product: ProductDetailModalProduct | null;
}>();

const emit = defineEmits<{
  "update:modelValue": [value: boolean];
  addToCart: [payload: ProductDetailSubmitPayload];
  toggleFavorite: [productId: number];
}>();

const scrollRef = ref<HTMLDivElement | null>(null);

const quantity = ref(1);
const remark = ref("");

/*
 * key：選項群組 ID
 * value：該群組目前選取的選項 ID
 *
 * RADIO：陣列內最多一個 ID
 * CHECKBOX：陣列內可以有多個 ID
 */
const selectedOptionIds = ref<Record<number, number[]>>({});

const validationErrors = ref<Record<number, string>>({});

const normalizedOptionGroups = computed(() => {
  return props.product?.optionGroups ?? [];
});

const selectedOptions = computed(() => {
  return normalizedOptionGroups.value.map((group) => {
    const selectedIds = selectedOptionIds.value[group.id] ?? [];

    const options = group.options.filter((option) =>
      selectedIds.includes(option.id),
    );

    return {
      group,
      options,
    };
  });
});

const optionPrice = computed(() => {
  return selectedOptions.value.reduce((groupTotal, selectedGroup) => {
    const groupOptionPrice = selectedGroup.options.reduce(
      (optionTotal, option) => optionTotal + option.extraPrice,
      0,
    );

    return groupTotal + groupOptionPrice;
  }, 0);
});

const unitPrice = computed(() => {
  if (!props.product) return 0;

  return props.product.price + optionPrice.value;
});

const totalPrice = computed(() => {
  return unitPrice.value * quantity.value;
});

function initializeSelections() {
  const result: Record<number, number[]> = {};

  for (const group of normalizedOptionGroups.value) {
    /*
     * 必選的 RADIO 預設選擇第一個可使用項目。
     * 例如辣度第一個選項是「不辣」。
     */
    if (group.type === "RADIO" && group.required) {
      const firstAvailableOption = group.options.find(
        (option) => !option.disabled,
      );

      result[group.id] = firstAvailableOption ? [firstAvailableOption.id] : [];

      continue;
    }

    result[group.id] = [];
  }

  selectedOptionIds.value = result;
}

function resetForm() {
  quantity.value = 1;
  remark.value = "";
  validationErrors.value = {};

  initializeSelections();

  nextTick(() => {
    if (scrollRef.value) {
      scrollRef.value.scrollTop = 0;
    }
  });
}

function closeProductModal() {
  emit("update:modelValue", false);
}

function increaseQuantity() {
  quantity.value += 1;
}

function decreaseQuantity() {
  if (quantity.value <= 1) return;

  quantity.value -= 1;
}

function updateGroupSelection(groupId: number, selectedIds: number[]) {
  selectedOptionIds.value = {
    ...selectedOptionIds.value,
    [groupId]: selectedIds,
  };

  if (!validationErrors.value[groupId]) return;

  const nextErrors = {
    ...validationErrors.value,
  };

  delete nextErrors[groupId];

  validationErrors.value = nextErrors;
}

function scrollToOptionGroup(groupId: number) {
  nextTick(() => {
    const target = document.querySelector(
      `[data-option-group-id="${groupId}"]`,
    );

    target?.scrollIntoView({
      behavior: "smooth",
      block: "center",
    });
  });
}

function validateSelections() {
  const errors: Record<number, string> = {};

  for (const group of normalizedOptionGroups.value) {
    const selectedCount = selectedOptionIds.value[group.id]?.length ?? 0;

    if (group.required && selectedCount === 0) {
      errors[group.id] = `請選擇「${group.name}」`;
      continue;
    }

    if (group.minSelect !== undefined && selectedCount < group.minSelect) {
      errors[group.id] = `「${group.name}」至少選 ${group.minSelect} 項`;

      continue;
    }

    if (group.maxSelect !== undefined && selectedCount > group.maxSelect) {
      errors[group.id] = `「${group.name}」最多選 ${group.maxSelect} 項`;
    }
  }

  validationErrors.value = errors;

  const firstErrorGroupId = Number(Object.keys(errors)[0]);

  if (firstErrorGroupId) {
    scrollToOptionGroup(firstErrorGroupId);
  }

  return Object.keys(errors).length === 0;
}

function createSelectedOptionPayload() {
  return selectedOptions.value
    .filter((selectedGroup) => selectedGroup.options.length > 0)
    .map((selectedGroup) => ({
      groupId: selectedGroup.group.id,
      groupName: selectedGroup.group.name,

      options: selectedGroup.options.map((option) => ({
        optionId: option.id,
        optionName: option.name,
        extraPrice: option.extraPrice,
      })),
    }));
}

function addToCart() {
  if (!props.product) return;
  if (!validateSelections()) return;

  emit("addToCart", {
    productId: props.product.id,
    quantity: quantity.value,
    remark: remark.value.trim(),

    selectedOptions: createSelectedOptionPayload(),

    productPrice: props.product.price,
    optionPrice: optionPrice.value,
    unitPrice: unitPrice.value,
    totalPrice: totalPrice.value,
  });

  closeProductModal();
}

function toggleFavorite() {
  if (!props.product) return;

  emit("toggleFavorite", props.product.id);
}

function handleEscape(event: KeyboardEvent) {
  if (event.key === "Escape" && props.modelValue) {
    closeProductModal();
  }
}

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen) {
      resetForm();
      document.body.style.overflow = "hidden";
      return;
    }

    document.body.style.overflow = "";
  },
);

watch(
  () => props.product?.id,
  () => {
    if (props.modelValue) {
      resetForm();
    }
  },
);

onMounted(() => {
  window.addEventListener("keydown", handleEscape);
});

onBeforeUnmount(() => {
  document.body.style.overflow = "";

  window.removeEventListener("keydown", handleEscape);
});
</script>

<template>
  <Teleport to="body">
    <Transition name="product-modal">
      <div
        v-if="modelValue && product"
        class="product-modal-backdrop"
        role="presentation"
        @click.self="closeProductModal"
      >
        <article
          class="product-modal"
          role="dialog"
          aria-modal="true"
          :aria-labelledby="`product-modal-title-${product.id}`"
        >
          <!-- 固定在右上角，不會跟著內容捲動 -->
          <button
            type="button"
            class="product-modal__close"
            aria-label="關閉商品視窗"
            @click="closeProductModal"
          >
            <i class="bi bi-x-lg" />
          </button>

          <!-- 圖片與內容共用同一個捲軸 -->
          <div ref="scrollRef" class="product-modal__scroll">
            <div class="product-modal__image-wrap">
              <img
                :src="product.image"
                :alt="product.name"
                class="product-modal__image"
              />
            </div>

            <!--
              圖片往上捲完後，
              商品名稱固定在捲軸最上方。
            -->
            <header class="product-modal__sticky-header">
              <h2
                :id="`product-modal-title-${product.id}`"
                class="product-modal__name"
              >
                {{ product.name }}
              </h2>

              <!-- 需要收藏功能時可打開 -->
              <!--
              <button
                type="button"
                class="product-modal__favorite"
                :class="{
                  'product-modal__favorite--active':
                    product.isFavorite,
                }"
                :aria-label="
                  product.isFavorite
                    ? '取消收藏'
                    : '加入收藏'
                "
                @click="toggleFavorite"
              >
                <i
                  class="bi"
                  :class="
                    product.isFavorite
                      ? 'bi-heart-fill'
                      : 'bi-heart'
                  "
                />
              </button>
              -->
            </header>

            <div class="product-modal__content">
              <section class="product-modal__summary">
                <p class="product-modal__description">
                  {{ product.description }}
                </p>

                <p class="product-modal__price">NT$ {{ product.price }}</p>
              </section>

              <!-- 單選與多選選項 -->
              <div
                v-for="group in normalizedOptionGroups"
                :key="group.id"
                :data-option-group-id="group.id"
                class="product-modal__option-group"
              >
                <ProductOptionGroup
                  :group="group"
                  :model-value="selectedOptionIds[group.id] ?? []"
                  @update:model-value="updateGroupSelection(group.id, $event)"
                />

                <p
                  v-if="validationErrors[group.id]"
                  class="product-modal__error"
                >
                  <i class="bi bi-exclamation-circle" />

                  {{ validationErrors[group.id] }}
                </p>
              </div>

              <!-- 數量 -->
              <section class="product-modal__section">
                <h3 class="product-modal__section-title">數量</h3>

                <div class="product-quantity">
                  <button
                    type="button"
                    class="product-quantity__button"
                    :disabled="quantity <= 1"
                    aria-label="減少數量"
                    @click="decreaseQuantity"
                  >
                    <i class="bi bi-dash-lg" />
                  </button>

                  <span class="product-quantity__value">
                    {{ quantity }}
                  </span>

                  <button
                    type="button"
                    class="product-quantity__button"
                    aria-label="增加數量"
                    @click="increaseQuantity"
                  >
                    <i class="bi bi-plus-lg" />
                  </button>
                </div>
              </section>

              <!-- 備註 -->
              <section class="product-modal__section">
                <div class="product-modal__section-heading">
                  <h3 class="product-modal__section-title">備註</h3>

                  <span class="product-modal__optional"> 選填 </span>
                </div>

                <div class="product-remark">
                  <textarea
                    v-model="remark"
                    class="product-remark__input"
                    maxlength="100"
                    placeholder="例如：不要蔥、醬料分開……"
                  />

                  <span class="product-remark__count">
                    {{ remark.length }}/100
                  </span>
                </div>
              </section>

              <!-- 商品資訊 -->
              <section
                v-if="product.ingredients?.length || product.allergenNotice"
                class="product-modal__section"
              >
                <h3
                  class="product-modal__section-title product-modal__section-title--icon"
                >
                  <i class="bi bi-clipboard2" />
                  商品資訊
                </h3>

                <div class="product-information">
                  <div
                    v-if="product.ingredients?.length"
                    class="product-information__row"
                  >
                    <i class="bi bi-leaf" />

                    <span>
                      {{ product.ingredients.join("、") }}
                    </span>
                  </div>

                  <div
                    v-if="product.allergenNotice"
                    class="product-information__row product-information__row--warning"
                  >
                    <i class="bi bi-exclamation-triangle" />

                    <span>
                      {{ product.allergenNotice }}
                    </span>
                  </div>
                </div>
              </section>
            </div>
          </div>

          <!-- 唯一不放入捲軸的區域 -->
          <footer class="product-modal__footer">
            <button
              type="button"
              class="product-modal__cart-button"
              @click="addToCart"
            >
              <span class="product-modal__cart-label">
                <i class="bi bi-cart3" />
                加入購物車
              </span>

              <strong> NT$ {{ totalPrice }} </strong>
            </button>
          </footer>
        </article>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped lang="scss">
.product-modal-backdrop {
  position: fixed;
  z-index: 2000;
  inset: 0;

  display: grid;
  place-items: center;

  padding: 24px;

  background: rgb(0 0 0 / 1%);
  backdrop-filter: blur(2px);
}

.product-modal {
  position: relative;

  display: flex;
  overflow: hidden;
  flex-direction: column;

  width: min(100%, 720px);
  height: min(92dvh, 920px);

  border-radius: 22px;
  background: #fff;

  box-shadow: 0 24px 70px rgb(0 0 0 / 30%);
}

/* 固定右上角關閉按鈕 */
.product-modal__close {
  position: absolute;
  z-index: 100;
  top: 14px;
  right: 18px;

  display: grid;
  width: 46px;
  height: 46px;
  place-items: center;

  border: 1px solid rgb(255 255 255 / 65%);
  border-radius: 50%;

  background: rgb(255 255 255 / 88%);
  color: #272727;

  font-size: 20px;
  cursor: pointer;

  box-shadow: 0 5px 18px rgb(0 0 0 / 18%);
  backdrop-filter: blur(8px);

  transition:
    background-color 0.2s ease,
    color 0.2s ease,
    transform 0.2s ease;
}

.product-modal__close:hover {
  background: #fff;
  color: #ff5f07;
  transform: scale(1.05);
}

/* 圖片與內容共用同一個捲軸 */
.product-modal__scroll {
  position: relative;

  min-height: 0;
  overflow-x: hidden;
  overflow-y: auto;

  overscroll-behavior: contain;
  scrollbar-color: #aaa transparent;
  scrollbar-gutter: stable;
  scrollbar-width: thin;
}

.product-modal__scroll::-webkit-scrollbar {
  width: 8px;
}

.product-modal__scroll::-webkit-scrollbar-track {
  background: transparent;
}

.product-modal__scroll::-webkit-scrollbar-thumb {
  border: 2px solid transparent;
  border-radius: 999px;

  background: #aaa;
  background-clip: padding-box;
}

.product-modal__image-wrap {
  position: relative;

  width: 100%;
  height: 330px;

  background: #f2f2f2;
}

.product-modal__image {
  display: block;

  width: 100%;
  height: 100%;

  object-fit: cover;
}

/*
 * 商品名稱原本位於圖片下面。
 * 捲到內容頂部時固定。
 */
.product-modal__sticky-header {
  position: sticky;
  z-index: 20;
  top: 0;

  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;

  min-height: 70px;
  padding: 13px 28px;

  border-bottom: 1px solid #ededed;

  background: rgb(255 255 255 / 96%);
  box-shadow: 0 5px 16px rgb(0 0 0 / 6%);

  backdrop-filter: blur(10px);
}

.product-modal__name {
  overflow: hidden;

  margin: 0;

  color: #242424;
  font-size: 27px;
  font-weight: 700;
  line-height: 1.35;

  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-modal__favorite {
  display: grid;
  flex: 0 0 42px;

  width: 42px;
  height: 42px;
  place-items: center;

  border: 0;
  background: transparent;
  color: #8d96a5;

  font-size: 28px;
  cursor: pointer;
}

.product-modal__favorite:hover,
.product-modal__favorite--active {
  color: #ff5f07;
}

.product-modal__content {
  padding: 0 28px 30px;
}

.product-modal__summary {
  padding: 16px 0 22px;
  border-bottom: 1px solid #ededed;
}

.product-modal__description {
  margin: 0;

  color: #747d8a;
  font-size: 17px;
  line-height: 1.6;
}

.product-modal__price {
  margin: 12px 0 0;

  color: #ff5f07;
  font-size: 28px;
  font-weight: 700;
}

.product-modal__option-group {
  position: relative;
}

.product-modal__error {
  display: flex;
  align-items: center;
  gap: 6px;

  margin: -12px 0 18px;

  color: #dc3545;
  font-size: 14px;
}

.product-modal__section {
  padding: 22px 0;
  border-bottom: 1px solid #ededed;
}

.product-modal__section:last-child {
  border-bottom: 0;
}

.product-modal__section-heading {
  display: flex;
  align-items: center;
  gap: 8px;

  margin-bottom: 13px;
}

.product-modal__section-title {
  margin: 0 0 13px;

  color: #292929;
  font-size: 18px;
  font-weight: 700;
}

.product-modal__section-heading .product-modal__section-title {
  margin: 0;
}

.product-modal__section-title--icon {
  display: flex;
  align-items: center;
  gap: 9px;
}

.product-modal__section-title--icon i {
  color: #677181;
}

.product-modal__optional {
  color: #8d8d8d;
  font-size: 14px;
}

/*
 * 數量控制
 * 外框為淡灰色，左右按鈕使用白底橘框。
 * 不使用整塊淡橘背景，畫面會更乾淨。
 */
.product-quantity {
  display: inline-flex;
  align-items: center;
  gap: 12px;

  height: 52px;
}

.product-quantity__button {
  display: grid;
  flex: 0 0 44px;

  width: 44px;
  height: 44px;
  place-items: center;

  border: 1.5px solid #ff9a5c;
  border-radius: 50%;

  background: #fff;
  color: #ff5f07;

  font-size: 18px;
  font-weight: 800;

  cursor: pointer;

  transition:
    border-color 0.2s ease,
    background-color 0.2s ease,
    color 0.2s ease,
    transform 0.15s ease;
}

.product-quantity__button:hover:not(:disabled) {
  border-color: #ff5f07;
  background: #ff5f07;
  color: #fff;

  transform: translateY(-1px);
}

.product-quantity__button:active:not(:disabled) {
  background: #e65500;
  transform: translateY(0);
}

.product-quantity__button:focus-visible {
  outline: 3px solid rgb(255 95 7 / 18%);
  outline-offset: 2px;
}

.product-quantity__button:disabled {
  border-color: #e3e3e3;
  background: #fafafa;
  color: #c8c8c8;

  cursor: not-allowed;
}

.product-quantity__value {
  display: grid;

  min-width: 34px;
  height: 44px;
  place-items: center;

  color: #111827;
  font-size: 18px;
  font-weight: 700;
  font-variant-numeric: tabular-nums;
}

.product-remark {
  position: relative;
}

.product-remark__input {
  display: block;
  resize: vertical;

  width: 100%;
  min-height: 125px;
  padding: 15px 16px 34px;

  border: 1px solid #dcdcdc;
  border-radius: 10px;
  outline: none;

  color: #333;

  font-family: inherit;
  font-size: 16px;
  line-height: 1.6;

  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.product-remark__input:focus {
  border-color: #ff5f07;
  box-shadow: 0 0 0 3px rgb(255 95 7 / 10%);
}

.product-remark__input::placeholder {
  color: #afb3ba;
}

.product-remark__count {
  position: absolute;
  right: 14px;
  bottom: 10px;

  color: #999fa8;
  font-size: 14px;
}

.product-information {
  display: grid;
  gap: 10px;
}

.product-information__row {
  display: flex;
  align-items: flex-start;
  gap: 10px;

  color: #6e7682;
  font-size: 15px;
  line-height: 1.6;
}

.product-information__row i {
  margin-top: 3px;
}

.product-information__row--warning i {
  color: #ff5f07;
}

/* 固定底部，不進入捲軸 */
.product-modal__footer {
  position: relative;
  z-index: 30;

  flex: 0 0 auto;

  padding: 14px 28px;

  border-top: 1px solid #e8e8e8;

  background: #fff;
  box-shadow: 0 -8px 24px rgb(0 0 0 / 8%);
}

.product-modal__cart-button {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;

  width: 100%;
  height: 56px;
  padding: 0 24px;

  border: 0;
  border-radius: 11px;

  background: #ff5f07;
  color: #fff;

  font-size: 18px;
  font-weight: 700;

  cursor: pointer;

  transition:
    background-color 0.2s ease,
    transform 0.2s ease;
}

.product-modal__cart-label {
  display: inline-flex;
  align-items: center;
  gap: 11px;
}

.product-modal__cart-button strong {
  font-size: inherit;
  font-variant-numeric: tabular-nums;
  white-space: nowrap;
}

.product-modal__cart-button:hover {
  background: #e65500;
  transform: translateY(-1px);
}

.product-modal__cart-button:active {
  background: #cc4d00;
  transform: translateY(0);
}

.product-modal__cart-button:focus-visible {
  outline: 3px solid rgb(255 95 7 / 22%);
  outline-offset: 3px;
}

/* 彈出動畫 */
.product-modal-enter-active,
.product-modal-leave-active {
  transition: opacity 0.2s ease;
}

.product-modal-enter-active .product-modal,
.product-modal-leave-active .product-modal {
  transition:
    opacity 0.2s ease,
    transform 0.2s ease;
}

.product-modal-enter-from,
.product-modal-leave-to {
  opacity: 0;
}

.product-modal-enter-from .product-modal,
.product-modal-leave-to .product-modal {
  opacity: 0;
  transform: translateY(18px) scale(0.98);
}

@media (max-width: 767.98px) {
  .product-modal-backdrop {
    align-items: end;
    padding: 0;
  }

  .product-modal {
    width: 100%;
    height: 94dvh;

    border-radius: 20px 20px 0 0;
  }

  .product-modal__image-wrap {
    height: 285px;
  }

  .product-modal__sticky-header {
    min-height: 66px;
    padding: 12px 18px;
  }

  .product-modal__name {
    padding-right: 52px;
    font-size: 23px;
  }

  .product-modal__content {
    padding: 0 18px 24px;
  }

  .product-modal__footer {
    padding: 12px 16px calc(12px + env(safe-area-inset-bottom));
  }

  .product-modal__cart-button {
    height: 52px;
    padding: 0 16px;

    font-size: 16px;
  }
}

@media (max-width: 480px) {
  .product-modal__image-wrap {
    height: 240px;
  }

  .product-modal__close {
    top: 12px;
    right: 12px;

    width: 42px;
    height: 42px;
  }

  .product-quantity {
    gap: 10px;
  }

  .product-quantity__button {
    flex-basis: 42px;

    width: 42px;
    height: 42px;
  }

  .product-quantity__value {
    min-width: 30px;
    height: 42px;
  }
}
</style>
