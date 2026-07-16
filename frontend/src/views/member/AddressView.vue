<script setup lang="ts">
import { ref } from "vue";

import AddressCard from "@/components/member/address/AddressCard.vue";
import AddressFormModal from "@/components/member/address/AddressFormModal.vue";

import type {
  AddressFormData,
  AddressItem,
} from "@/types/address";

const addresses = ref<AddressItem[]>([
  {
    id: 1,
    label: "住家",
    type: "HOME",
    city: "台北市",
    area: "中正區",
    street: "忠孝東路一段 123 號",
    detail: "5 樓之 3",
    contactName: "jessie",
    phone: "0928-776-769",
    isDefault: true,
  },
  {
    id: 2,
    label: "公司",
    type: "COMPANY",
    city: "台北市",
    area: "大安區",
    street: "復興南路二段 456 號",
    detail: "12 樓",
    contactName: "jessie",
    phone: "0928-776-769",
    isDefault: false,
  },
  {
    id: 3,
    label: "學校",
    type: "SCHOOL",
    city: "台北市",
    area: "文山區",
    street: "指南路二段 64 號",
    detail: "世新大學舍我樓",
    contactName: "jessie",
    phone: "0928-776-769",
    isDefault: false,
  },
  {
    id: 4,
    label: "父母家",
    type: "OTHER",
    city: "新北市",
    area: "永和區",
    street: "中正路 789 巷 10 弄 5 號",
    detail: "3 樓",
    contactName: "jessie",
    phone: "0928-776-769",
    isDefault: false,
  },
]);

const isFormOpen = ref(false);

const editingAddress =
  ref<AddressItem | null>(null);

const deleteTarget =
  ref<AddressItem | null>(null);

function openCreateForm() {
  editingAddress.value = null;
  isFormOpen.value = true;
}

function openEditForm(address: AddressItem) {
  editingAddress.value = address;
  isFormOpen.value = true;
}

function closeForm() {
  isFormOpen.value = false;
  editingAddress.value = null;
}

function clearDefaultAddress() {
  addresses.value.forEach((address) => {
    address.isDefault = false;
  });
}

function saveAddress(formData: AddressFormData) {
  if (formData.isDefault) {
    clearDefaultAddress();
  }

  if (editingAddress.value) {
    const target = addresses.value.find(
      (address) =>
        address.id === editingAddress.value?.id,
    );

    if (target) {
      Object.assign(target, formData);
    }
  } else {
    const nextId =
      addresses.value.length > 0
        ? Math.max(
            ...addresses.value.map(
              (address) => address.id,
            ),
          ) + 1
        : 1;

    const newAddress: AddressItem = {
      id: nextId,
      ...formData,
    };

    if (addresses.value.length === 0) {
      newAddress.isDefault = true;
    }

    addresses.value.push(newAddress);
  }

  closeForm();
}

function setDefault(addressId: number) {
  addresses.value.forEach((address) => {
    address.isDefault =
      address.id === addressId;
  });
}

function requestDelete(address: AddressItem) {
  deleteTarget.value = address;
}

function cancelDelete() {
  deleteTarget.value = null;
}

function confirmDelete() {
  if (!deleteTarget.value) return;

  const deletedId = deleteTarget.value.id;
  const wasDefault =
    deleteTarget.value.isDefault;

  addresses.value = addresses.value.filter(
    (address) => address.id !== deletedId,
  );

  if (
    wasDefault &&
    addresses.value.length > 0 &&
    !addresses.value.some(
      (address) => address.isDefault,
    )
  ) {
    addresses.value[0].isDefault = true;
  }

  deleteTarget.value = null;
}
</script>

<template>
  <section class="address-view">
    <header class="address-view__header">
      <div>
        <h1 class="address-view__title">
          外送地址管理
        </h1>

        <p class="address-view__description">
          管理您的外送地址，結帳時可快速選擇
        </p>
      </div>

      <button
        type="button"
        class="address-view__add-button"
        @click="openCreateForm"
      >
        <i class="bi bi-plus-lg"></i>
        <span>新增地址</span>
      </button>
    </header>

    <div
      v-if="addresses.length"
      class="address-list"
    >
      <AddressCard
        v-for="address in addresses"
        :key="address.id"
        :address="address"
        @edit="openEditForm"
        @delete="requestDelete"
        @set-default="setDefault"
      />
    </div>

    <div
      v-else
      class="address-empty"
    >
      <div class="address-empty__icon">
        <i class="bi bi-geo-alt"></i>
      </div>

      <h2>尚未新增外送地址</h2>

      <p>
        新增常用地址，結帳時可以更快速完成訂單。
      </p>

      <button
        type="button"
        @click="openCreateForm"
      >
        新增第一個地址
      </button>
    </div>

    <p
      v-if="addresses.length"
      class="address-view__notice"
    >
      <i class="bi bi-info-circle"></i>
      結帳時將會優先使用預設地址
    </p>

    <AddressFormModal
      v-if="isFormOpen"
      :address="editingAddress"
      @close="closeForm"
      @submit="saveAddress"
    />

    <Teleport to="body">
      <div
        v-if="deleteTarget"
        class="delete-modal"
        @click.self="cancelDelete"
      >
        <section
          class="delete-modal__dialog"
          role="alertdialog"
          aria-modal="true"
          aria-label="刪除地址"
        >
          <div class="delete-modal__icon">
            <i class="bi bi-trash3"></i>
          </div>

          <h2>刪除地址</h2>

          <p>
            確定要刪除「{{ deleteTarget.label }}」嗎？
            此操作無法復原。
          </p>

          <div class="delete-modal__actions">
            <button
              type="button"
              @click="cancelDelete"
            >
              取消
            </button>

            <button
              type="button"
              class="delete-modal__confirm"
              @click="confirmDelete"
            >
              確定刪除
            </button>
          </div>
        </section>
      </div>
    </Teleport>
  </section>
</template>

<style scoped lang="scss">
.address-view {
  padding: 28px;

  border: 1px solid #eeeeee;
  border-radius: 16px;
  background: #ffffff;
  box-shadow: 0 4px 18px rgb(0 0 0 / 4%);
}

.address-view__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 20px;

  margin-bottom: 24px;
}

.address-view__title {
  margin: 0;
  color: #292929;

  font-size: 28px;
  font-weight: 800;
}

.address-view__description {
  margin: 8px 0 0;
  color: #888888;

  font-size: 15px;
}

.address-view__add-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 8px;

  min-height: 44px;
  padding: 0 18px;

  border: 1px solid #ff5f07;
  border-radius: 9px;
  background: #ff5f07;
  color: #ffffff;

  font-size: 15px;
  font-weight: 700;
  cursor: pointer;

  transition:
    background-color 0.2s ease,
    border-color 0.2s ease,
    transform 0.2s ease;
}

.address-view__add-button:hover {
  border-color: #e65500;
  background: #e65500;
  transform: translateY(-1px);
}

.address-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.address-view__notice {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 7px;

  margin: 20px 0 0;
  color: #888888;

  font-size: 14px;
}

.address-empty {
  padding: 70px 20px;

  border: 1px dashed #dddddd;
  border-radius: 12px;

  text-align: center;
}

.address-empty__icon {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 70px;
  height: 70px;
  margin: 0 auto 18px;

  border-radius: 50%;
  background: #fff2ea;
  color: #ff5f07;

  font-size: 32px;
}

.address-empty h2 {
  margin: 0;
  color: #333333;

  font-size: 20px;
}

.address-empty p {
  margin: 9px 0 20px;
  color: #888888;
}

.address-empty button {
  min-height: 42px;
  padding: 0 18px;

  border: 0;
  border-radius: 9px;
  background: #ff5f07;
  color: #ffffff;

  font-weight: 700;
  cursor: pointer;
}

.delete-modal {
  position: fixed;
  z-index: 2100;
  inset: 0;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 20px;
  background: rgb(0 0 0 / 45%);
}

.delete-modal__dialog {
  width: min(100%, 420px);
  padding: 30px;

  border-radius: 16px;
  background: #ffffff;
  box-shadow: 0 20px 60px rgb(0 0 0 / 20%);

  text-align: center;
}

.delete-modal__icon {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 62px;
  height: 62px;
  margin: 0 auto 18px;

  border-radius: 50%;
  background: #fff0f1;
  color: #dc3545;

  font-size: 27px;
}

.delete-modal__dialog h2 {
  margin: 0;
  color: #292929;

  font-size: 22px;
}

.delete-modal__dialog p {
  margin: 12px 0 24px;
  color: #777777;

  line-height: 1.6;
}

.delete-modal__actions {
  display: flex;
  gap: 12px;
}

.delete-modal__actions button {
  flex: 1;
  min-height: 44px;

  border: 1px solid #dddddd;
  border-radius: 9px;
  background: #ffffff;
  color: #555555;

  font-weight: 700;
  cursor: pointer;
}

.delete-modal__actions button:hover {
  border-color: #bbbbbb;
}

.delete-modal__actions
  .delete-modal__confirm {
  border-color: #dc3545;
  background: #dc3545;
  color: #ffffff;
}

.delete-modal__actions
  .delete-modal__confirm:hover {
  border-color: #b42331;
  background: #b42331;
}

@media (max-width: 767.98px) {
  .address-view {
    padding: 20px 16px;
  }

  .address-view__header {
    align-items: stretch;
    flex-direction: column;
  }

  .address-view__add-button {
    width: 100%;
  }
}

@media (max-width: 479.98px) {
  .address-view__title {
    font-size: 25px;
  }

  .delete-modal__actions {
    flex-direction: column-reverse;
  }
}
</style>