<script setup lang="ts">
withDefaults(defineProps<{ actionText?: string }>(), { actionText: "登入" });

const emit = defineEmits<{
  google: [];
  apple: [];
}>();
</script>

<template>
  <div class="social-login">
    <div class="social-login__divider"><span>或</span></div>
    <button type="button" @click="emit('google')">
      <span class="social-login__google">G</span>
      使用 Google {{ actionText }}
    </button>
    <button type="button" @click="emit('apple')">
      <i class="bi bi-apple" aria-hidden="true"></i>
      使用 Apple {{ actionText }}
    </button>
  </div>
</template>

<style scoped lang="scss">
.social-login {
  display: grid;
  gap: 10px;

  > button {
    display: flex;
    min-height: 46px;
    align-items: center;
    justify-content: center;
    gap: 10px;
    border: 1px solid #d8d0ca;
    border-radius: 10px;
    background: rgba(255, 255, 255, 0.7);
    color: #322a25;
    font: inherit;
    font-size: 14px;
    font-weight: 700;
    cursor: pointer;

    &:hover {
      border-color: #ff5f07;
      background: #fff8f3;
    }
  }
}

.social-login__divider {
  display: flex;
  align-items: center;
  gap: 14px;
  margin: 18px 0 6px;
  color: #8b817b;
  font-size: 13px;

  &::before,
  &::after {
    height: 1px;
    flex: 1;
    background: #ded7d1;
    content: "";
  }
}

.social-login__google {
  color: #4285f4;
  font-size: 18px;
  font-weight: 900;
}
</style>
