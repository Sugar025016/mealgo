<script setup lang="ts">
type AddressType = "HOME" | "COMPANY" | "SCHOOL" | "OTHER";

type AddressItem = {
  id: number;
  label: string;
  type: AddressType;
  city: string;
  area: string;
  street: string;
  detail: string;
  contactName: string;
  phone: string;
  isDefault: boolean;
};

defineProps<{
  address: AddressItem;
}>();

const emit = defineEmits<{
  edit: [address: AddressItem];
  delete: [address: AddressItem];
  setDefault: [addressId: number];
}>();

function getAddressIcon(type: AddressType) {
  const iconMap: Record<AddressType, string> = {
    HOME: "bi-house-door",
    COMPANY: "bi-building",
    SCHOOL: "bi-mortarboard",
    OTHER: "bi-geo-alt",
  };

  return iconMap[type];
}

function getFullAddress(address: AddressItem) {
  return [
    address.city,
    address.area,
    address.street,
    address.detail,
  ]
    .filter(Boolean)
    .join(" ");
}
</script>

<template>
  <article
    class="address-card"
    :class="{
      'address-card--default': address.isDefault,
    }"
  >
    <div
      class="address-card__icon"
      :class="{
        'address-card__icon--default': address.isDefault,
      }"
    >
      <i class="bi" :class="getAddressIcon(address.type)"></i>
    </div>

    <div class="address-card__content">
      <div class="address-card__heading">
        <h2 class="address-card__title">
          {{ address.label }}
        </h2>

        <span
          v-if="address.isDefault"
          class="address-card__default-badge"
        >
          預設地址
        </span>

        <button
          v-else
          type="button"
          class="address-card__set-default"
          @click="emit('setDefault', address.id)"
        >
          設為預設
        </button>
      </div>

      <p class="address-card__address">
        {{ getFullAddress(address) }}
      </p>

      <!-- <div class="address-card__contact">
        <span>聯絡人：{{ address.contactName }}</span>
        <span class="address-card__divider"></span>
        <span>{{ address.phone }}</span>
      </div> -->
    </div>

    <div class="address-card__actions">
      <button
        type="button"
        class="address-card__action"
        @click="emit('edit', address)"
      >
        <i class="bi bi-pencil"></i>
        <span>編輯</span>
      </button>

      <button
        type="button"
        class="address-card__action address-card__action--delete"
        @click="emit('delete', address)"
      >
        <i class="bi bi-trash3"></i>
        <span>刪除</span>
      </button>
    </div>
  </article>
</template>

<style scoped lang="scss">
.address-card {
  display: grid;
  grid-template-columns: 52px minmax(0, 1fr) auto;
  align-items: center;
  gap: 16px;

  min-height: 108px;
  padding: 16px;

  border: 1px solid #e5e5e5;
  border-radius: 10px;
  background: #ffffff;

  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.address-card:hover {
  border-color: #ffb380;
  box-shadow: 0 4px 14px rgb(0 0 0 / 5%);
}

.address-card--default {
  border-color: #ffd2b8;
}

.address-card__icon {
  display: flex;
  align-items: center;
  justify-content: center;

  width: 48px;
  height: 48px;
  flex-shrink: 0;

  border-radius: 10px;
  background: #f5f5f5;
  color: #666666;

  font-size: 22px;
}

.address-card__icon--default {
  background: #fff2ea;
  color: #ff5f07;
}

.address-card__content {
  min-width: 0;
}

.address-card__heading {
  display: flex;
  align-items: center;
  gap: 10px;
}

.address-card__title {
  margin: 0;
  color: #303030;
  font-size: 18px;
  font-weight: 800;
}

.address-card__default-badge {
  padding: 4px 9px;
  border-radius: 999px;
  background: #fff2ea;
  color: #ff5f07;

  font-size: 12px;
  font-weight: 700;
}

.address-card__set-default {
  padding: 4px 8px;
  border: 0;
  background: transparent;
  color: #999999;

  font-size: 12px;
  cursor: pointer;
}

.address-card__set-default:hover {
  color: #ff5f07;
}

.address-card__address {
  margin: 7px 0 0;
  color: #555555;
  font-size: 15px;
  line-height: 1.5;
}

.address-card__contact {
  display: flex;
  align-items: center;
  gap: 10px;

  margin-top: 5px;
  color: #888888;
  font-size: 14px;
}

.address-card__divider {
  width: 1px;
  height: 14px;
  background: #dddddd;
}

.address-card__actions {
  display: flex;
  align-items: center;
  gap: 18px;
}

.address-card__action {
  display: inline-flex;
  align-items: center;
  gap: 6px;

  padding: 7px 4px;

  border: 0;
  background: transparent;
  color: #555555;

  font-size: 14px;
  cursor: pointer;

  transition: color 0.2s ease;
}

.address-card__action:hover {
  color: #ff5f07;
}

.address-card__action--delete {
  color: #dc3545;
}

.address-card__action--delete:hover {
  color: #b42331;
}

@media (max-width: 767.98px) {
  .address-card {
    grid-template-columns: 46px minmax(0, 1fr);
    gap: 12px;
  }

  .address-card__icon {
    width: 44px;
    height: 44px;
  }

  .address-card__actions {
    grid-column: 1 / -1;
    justify-content: flex-end;

    padding-top: 10px;
    border-top: 1px solid #eeeeee;
  }
}

@media (max-width: 479.98px) {
  .address-card__heading {
    align-items: flex-start;
    flex-direction: column;
    gap: 6px;
  }

  .address-card__contact {
    align-items: flex-start;
    flex-direction: column;
    gap: 4px;
  }

  .address-card__divider {
    display: none;
  }
}
</style>