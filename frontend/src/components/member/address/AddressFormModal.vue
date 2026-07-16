<script setup lang="ts">
import { reactive, watch } from "vue";

import type { AddressFormData, AddressItem } from "@/types/address";

const props = defineProps<{
  address?: AddressItem | null;
}>();

const emit = defineEmits<{
  close: [];
  submit: [formData: AddressFormData];
}>();

function createEmptyForm(): AddressFormData {
  return {
    label: "",
    type: "HOME",
    city: "",
    area: "",
    street: "",
    detail: "",
    contactName: "",
    phone: "",
    isDefault: false,
  };
}

const form = reactive<AddressFormData>(createEmptyForm());

watch(
  () => props.address,
  (address) => {
    if (!address) {
      Object.assign(form, createEmptyForm());
      return;
    }

    Object.assign(form, {
      label: address.label,
      type: address.type,
      city: address.city,
      area: address.area,
      street: address.street,
      detail: address.detail,
      contactName: address.contactName,
      phone: address.phone,
      isDefault: address.isDefault,
    });
  },
  {
    immediate: true,
  },
);

function validateForm() {
  return (
    form.label.trim() &&
    form.city.trim() &&
    form.area.trim() &&
    form.street.trim() &&
    form.contactName.trim() &&
    form.phone.trim()
  );
}

function handleSubmit() {
  if (!validateForm()) {
    window.alert("請填寫完整的地址與聯絡資料");
    return;
  }

  emit("submit", {
    label: form.label.trim(),
    type: form.type,
    city: form.city.trim(),
    area: form.area.trim(),
    street: form.street.trim(),
    detail: form.detail.trim(),
    contactName: form.contactName.trim(),
    phone: form.phone.trim(),
    isDefault: form.isDefault,
  });
}
</script>

<template>
  <Teleport to="body">
    <div class="address-modal" @click.self="emit('close')">
      <section
        class="address-modal__dialog"
        role="dialog"
        aria-modal="true"
        :aria-label="address ? '編輯地址' : '新增地址'"
      >
        <header class="address-modal__header">
          <h2>
            {{ address ? "編輯地址" : "新增地址" }}
          </h2>

          <button
            type="button"
            class="address-modal__close"
            aria-label="關閉視窗"
            @click="emit('close')"
          >
            <i class="bi bi-x-lg"></i>
          </button>
        </header>

        <form class="address-form" @submit.prevent="handleSubmit">
          <div class="address-form__grid">
            <div class="address-form__field">
              <label for="address-label"> 地址名稱 </label>

              <input
                id="address-label"
                v-model.trim="form.label"
                type="text"
                placeholder="例如：住家、公司"
              />
            </div>

            <div class="address-form__field">
              <label for="address-type"> 地址類型 </label>

              <select id="address-type" v-model="form.type">
                <option value="HOME">住家</option>
                <option value="COMPANY">公司</option>
                <option value="SCHOOL">學校</option>
                <option value="OTHER">其他</option>
              </select>
            </div>

            <div class="address-form__field">
              <label for="address-city"> 縣市 </label>

              <input
                id="address-city"
                v-model.trim="form.city"
                type="text"
                placeholder="例如：台南市"
              />
            </div>

            <div class="address-form__field">
              <label for="address-area"> 區域 </label>

              <input
                id="address-area"
                v-model.trim="form.area"
                type="text"
                placeholder="例如：南區"
              />
            </div>

            <div class="address-form__field address-form__field--full">
              <label for="address-street"> 路名與門牌 </label>

              <input
                id="address-street"
                v-model.trim="form.street"
                type="text"
                placeholder="例如：中華南路一段 100 號"
              />
            </div>

            <div class="address-form__field address-form__field--full">
              <label for="address-detail"> 樓層／地址說明 </label>

              <input
                id="address-detail"
                v-model.trim="form.detail"
                type="text"
                placeholder="例如：5 樓之 3、請按門鈴"
              />
            </div>
          </div>

          <label class="address-form__checkbox">
            <input v-model="form.isDefault" type="checkbox" />

            <span>設為預設地址</span>
          </label>

          <footer class="address-form__actions">
            <button
              type="button"
              class="address-form__cancel"
              @click="emit('close')"
            >
              取消
            </button>

            <button type="submit" class="address-form__save">
              {{ address ? "儲存變更" : "新增地址" }}
            </button>
          </footer>
        </form>
      </section>
    </div>
  </Teleport>
</template>

<style scoped lang="scss">
.address-modal {
  position: fixed;
  z-index: 2000;
  inset: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 20px;
  background: rgb(0 0 0 / 45%);
}

.address-modal__dialog {
  width: min(100%, 680px);
  max-height: calc(100vh - 40px);
  overflow-y: auto;

  border-radius: 16px;
  background: #ffffff;
  box-shadow: 0 20px 60px rgb(0 0 0 / 20%);
}

.address-modal__header {
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 20px 24px;
  border-bottom: 1px solid #eeeeee;
}

.address-modal__header h2 {
  margin: 0;
  color: #292929;
  font-size: 22px;
  font-weight: 800;
}

.address-modal__close {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 38px;
  height: 38px;

  border: 0;
  border-radius: 50%;
  background: transparent;
  color: #666666;

  cursor: pointer;
}

.address-modal__close:hover {
  background: #f5f5f5;
}

.address-form {
  padding: 24px;
}

.address-form__grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 20px;
}

.address-form__field--full {
  grid-column: 1 / -1;
}

.address-form__field label {
  display: block;
  margin-bottom: 8px;

  color: #444444;
  font-size: 14px;
  font-weight: 700;
}

.address-form__field input,
.address-form__field select {
  width: 100%;
  height: 46px;
  padding: 0 14px;

  border: 1px solid #dddddd;
  border-radius: 9px;
  outline: none;
  background: #ffffff;
  color: #333333;

  font-size: 15px;

  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.address-form__field input:focus,
.address-form__field select:focus {
  border-color: #ff5f07;
  box-shadow: 0 0 0 3px rgb(255 95 7 / 12%);
}

.address-form__checkbox {
  display: flex;
  align-items: center;
  gap: 9px;

  margin-top: 22px;

  color: #555555;
  font-size: 14px;
  cursor: pointer;
}

.address-form__checkbox input {
  width: 17px;
  height: 17px;
  accent-color: #ff5f07;
}

.address-form__actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;

  margin-top: 24px;
  padding-top: 20px;

  border-top: 1px solid #eeeeee;
}

.address-form__cancel,
.address-form__save {
  min-width: 110px;
  min-height: 44px;
  padding: 0 18px;

  border-radius: 9px;

  font-size: 15px;
  font-weight: 700;
  cursor: pointer;
}

.address-form__cancel {
  border: 1px solid #dddddd;
  background: #ffffff;
  color: #555555;
}

.address-form__cancel:hover {
  border-color: #bbbbbb;
}

.address-form__save {
  border: 1px solid #ff5f07;
  background: #ff5f07;
  color: #ffffff;
}

.address-form__save:hover {
  border-color: #e65500;
  background: #e65500;
}

@media (max-width: 767.98px) {
  .address-form__grid {
    grid-template-columns: 1fr;
  }

  .address-form__field--full {
    grid-column: auto;
  }
}

@media (max-width: 479.98px) {
  .address-modal {
    padding: 12px;
  }

  .address-modal__header {
    padding: 18px;
  }

  .address-form {
    padding: 18px;
  }

  .address-form__actions {
    flex-direction: column-reverse;
  }

  .address-form__cancel,
  .address-form__save {
    width: 100%;
  }
}
</style>
