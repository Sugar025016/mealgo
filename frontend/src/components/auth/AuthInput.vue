<script setup lang="ts">
import { ref } from "vue";

withDefaults(
  defineProps<{
    modelValue: string;
    id: string;
    label: string;
    type?: "text" | "email" | "tel" | "password";
    placeholder?: string;
    autocomplete?: string;
    icon?: string;
    error?: string;
  }>(),
  {
    type: "text",
    placeholder: "",
    autocomplete: "off",
    icon: "bi-person",
    error: "",
  },
);

const emit = defineEmits<{
  "update:modelValue": [value: string];
  input: [];
}>();

const showPassword = ref(false);

function updateValue(event: Event) {
  emit("update:modelValue", (event.target as HTMLInputElement).value);
  emit("input");
}
</script>

<template>
  <div class="auth-input">
    <label :for="id">{{ label }}</label>
    <div class="auth-input__control" :class="{ 'is-error': error }">
      <i class="bi" :class="icon" aria-hidden="true"></i>
      <input
        :id="id"
        :value="modelValue"
        :type="type === 'password' && showPassword ? 'text' : type"
        :placeholder="placeholder"
        :autocomplete="autocomplete"
        :inputmode="type === 'email' ? 'email' : type === 'tel' ? 'tel' : undefined"
        :aria-invalid="Boolean(error)"
        :aria-describedby="error ? `${id}-error` : undefined"
        @input="updateValue"
      />
      <button
        v-if="type === 'password'"
        type="button"
        class="auth-input__toggle"
        :aria-label="showPassword ? '隱藏密碼' : '顯示密碼'"
        @click="showPassword = !showPassword"
      >
        <i class="bi" :class="showPassword ? 'bi-eye-slash' : 'bi-eye'"></i>
      </button>
    </div>
    <small v-if="error" :id="`${id}-error`" class="auth-input__error">
      {{ error }}
    </small>
  </div>
</template>

<style scoped lang="scss">
.auth-input {
  box-sizing: border-box;
  min-width: 0;

  label {
    display: block;
    margin-bottom: 6px;
    color: #342b26;
    font-size: 14px;
    font-weight: 700;
  }
}

.auth-input__control {
  display: flex;
  min-height: 48px;
  align-items: center;
  gap: 11px;
  padding: 0 14px;
  border: 1px solid #d8d0ca;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.78);

  &:focus-within {
    border-color: #ff5f07;
    box-shadow: 0 0 0 4px rgba(255, 95, 7, 0.1);
  }

  &.is-error {
    border-color: #c92d35;
  }

  > i {
    flex: 0 0 auto;
    color: #746d68;
    font-size: 18px;
  }

  input {
    min-width: 0;
    flex: 1;
    border: 0;
    outline: 0;
    background: transparent;
    color: #211b18;
    font: inherit;

    &::placeholder {
      color: #aaa19b;
    }
  }
}

.auth-input__toggle {
  width: 34px;
  height: 34px;
  padding: 0;
  border: 0;
  background: transparent;
  color: #756c67;
  cursor: pointer;
}

.auth-input__error {
  display: block;
  margin-top: 5px;
  color: #c92d35;
  font-size: 12px;
}
</style>
