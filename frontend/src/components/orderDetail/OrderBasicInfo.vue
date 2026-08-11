<script setup lang="ts">
defineProps<{
  orderNumber: string;
  createdAt: string;
  canCancel?: boolean;
}>();

const emit = defineEmits<{
  contact: [];
  cancel: [];
}>();
</script>

<template>
  <div class="order-basic">
    <section class="order-info-card">
      <h2>訂單資訊</h2>
      <dl>
        <div>
          <dt>訂單編號</dt>
          <dd>{{ orderNumber }}</dd>
        </div>
        <div>
          <dt>訂單時間</dt>
          <dd>{{ createdAt }}</dd>
        </div>
      </dl>
    </section>

    <button type="button" class="order-basic__button" @click="emit('contact')">
      <i class="bi bi-headset"></i>
      聯絡客服
    </button>

    <button
      v-if="canCancel"
      type="button"
      class="order-basic__button"
      @click="emit('cancel')"
    >
      <i class="bi bi-x-circle"></i>
      取消訂單
    </button>
  </div>
</template>

<style scoped lang="scss">
.order-basic {
  display: grid;
  gap: 12px;
}

.order-info-card {
  padding: 20px;
  border: 1px solid #eee;
  border-radius: 14px;
  background: #fff;
  box-shadow: 0 4px 16px rgb(0 0 0 / 5%);

  h2 {
    margin: 0 0 14px;
    font-size: 18px;
  }

  dl {
    margin: 0;
  }

  dl > div {
    display: flex;
    justify-content: space-between;
    gap: 16px;

    + div {
      margin-top: 12px;
    }
  }

  dt {
    color: #777;
  }

  dd {
    margin: 0;
    text-align: right;
  }
}

.order-basic__button {
  width: 100%;
  padding: 11px 16px;
  border: 1px solid #ff5f07;
  border-radius: 10px;
  background: #fff;
  color: #ff5f07;
  font-weight: 600;
  transition: 0.2s;

  &:hover {
    background: #fff2ea;
  }

  i {
    margin-right: 8px;
  }
}
</style>
