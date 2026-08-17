<!-- src/components/checkout/CheckoutForm.vue -->
<script setup lang="ts">
import { reactive } from "vue";

type FulfillmentType = "delivery" | "pickup";
type PaymentType = "cash" | "credit-card" | "line-pay";
type InvoiceType = "member" | "mobile" | "company";

const form = reactive({
  address: "台南市南區健康路二段 123 號",
  fulfillmentType: "delivery" as FulfillmentType,
  paymentType: "cash" as PaymentType,
  orderNote: "",
  invoiceType: "member" as InvoiceType,
  mobileBarcode: "",
  companyNumber: "",
  companyName: "",
});
</script>

<template>
  <div class="checkout-form">
    <!-- 外送地址 -->
    <section class="checkout-form__section">
      <div class="checkout-form__heading">
        <div class="checkout-form__icon">
          <i class="bi bi-geo-alt"></i>
        </div>

        <div class="checkout-form__heading-text">
          <h2 class="checkout-form__title">外送地址</h2>
          <p class="checkout-form__description">請確認餐點要送達的地址</p>
        </div>
      </div>

      <div class="checkout-form__content">
        <div class="checkout-form__address">
          <div class="checkout-form__address-icon">
            <i class="bi bi-house-door"></i>
          </div>

          <div class="checkout-form__address-text">
            <span class="checkout-form__address-label">目前地址</span>
            <strong>{{ form.address }}</strong>
          </div>

          <RouterLink to="/member/addresses" class="checkout-form__edit-button">
            修改
          </RouterLink>
        </div>
      </div>
    </section>

    <!-- 取餐方式 -->
    <section class="checkout-form__section">
      <div class="checkout-form__heading">
        <div class="checkout-form__icon">
          <i class="bi bi-bag-check"></i>
        </div>

        <div class="checkout-form__heading-text">
          <h2 class="checkout-form__title">取餐方式</h2>
          <p class="checkout-form__description">選擇外送或到店自取</p>
        </div>
      </div>

      <div class="checkout-form__content">
        <div class="checkout-form__options checkout-form__options--two">
          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.fulfillmentType === 'delivery',
            }"
          >
            <input
              v-model="form.fulfillmentType"
              type="radio"
              value="delivery"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-bicycle"></i>
            </span>

            <span class="checkout-option__text">
              <strong>外送</strong>
              <small>預計 25～35 分鐘送達</small>
            </span>
          </label>

          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.fulfillmentType === 'pickup',
            }"
          >
            <input
              v-model="form.fulfillmentType"
              type="radio"
              value="pickup"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-person-walking"></i>
            </span>

            <span class="checkout-option__text">
              <strong>自取</strong>
              <small>完成後請至店家取餐</small>
            </span>
          </label>
        </div>
      </div>
    </section>

    <!-- 付款方式 -->
    <section class="checkout-form__section">
      <div class="checkout-form__heading">
        <div class="checkout-form__icon">
          <i class="bi bi-credit-card"></i>
        </div>

        <div class="checkout-form__heading-text">
          <h2 class="checkout-form__title">付款方式</h2>
          <p class="checkout-form__description">選擇本次訂單的付款方式</p>
        </div>
      </div>

      <div class="checkout-form__content">
        <div class="checkout-form__options checkout-form__options--three">
          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.paymentType === 'cash',
            }"
          >
            <input
              v-model="form.paymentType"
              type="radio"
              value="cash"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-cash-stack"></i>
            </span>

            <span class="checkout-option__text">
              <strong>現金付款</strong>
              <small>餐點送達時付款</small>
            </span>
          </label>

          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.paymentType === 'credit-card',
            }"
          >
            <input
              v-model="form.paymentType"
              type="radio"
              value="credit-card"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-credit-card-2-front"></i>
            </span>

            <span class="checkout-option__text">
              <strong>信用卡</strong>
              <small>Visa、Mastercard、JCB</small>
            </span>
          </label>

          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.paymentType === 'line-pay',
            }"
          >
            <input
              v-model="form.paymentType"
              type="radio"
              value="line-pay"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-wallet2"></i>
            </span>

            <span class="checkout-option__text">
              <strong>LINE Pay</strong>
              <small>使用 LINE Pay 快速付款</small>
            </span>
          </label>
        </div>
      </div>
    </section>

    <!-- 訂單備註 -->
    <section class="checkout-form__section">
      <div class="checkout-form__heading">
        <div class="checkout-form__icon">
          <i class="bi bi-chat-left-text"></i>
        </div>

        <div class="checkout-form__heading-text">
          <h2 class="checkout-form__title">訂單備註</h2>
          <p class="checkout-form__description">有特殊需求可以告訴店家</p>
        </div>
      </div>

      <div class="checkout-form__content">
        <textarea
          v-model="form.orderNote"
          class="checkout-form__textarea"
          maxlength="100"
          placeholder="例如：餐點不要辣、請多附一套餐具"
        ></textarea>

        <div class="checkout-form__count">
          {{ form.orderNote.length }} / 100
        </div>
      </div>
    </section>

    <!-- 發票資訊 -->
    <section class="checkout-form__section">
      <div class="checkout-form__heading">
        <div class="checkout-form__icon">
          <i class="bi bi-receipt"></i>
        </div>

        <div class="checkout-form__heading-text">
          <h2 class="checkout-form__title">發票資訊</h2>
          <p class="checkout-form__description">選擇電子發票的開立方式</p>
        </div>
      </div>

      <div class="checkout-form__content">
        <div class="checkout-form__options checkout-form__options--three">
          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.invoiceType === 'member',
            }"
          >
            <input
              v-model="form.invoiceType"
              type="radio"
              value="member"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-person"></i>
            </span>

            <span class="checkout-option__text">
              <strong>會員載具</strong>
              <small>儲存在會員帳號</small>
            </span>
          </label>

          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.invoiceType === 'mobile',
            }"
          >
            <input
              v-model="form.invoiceType"
              type="radio"
              value="mobile"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-phone"></i>
            </span>

            <span class="checkout-option__text">
              <strong>手機條碼</strong>
              <small>財政部手機載具</small>
            </span>
          </label>

          <label
            class="checkout-option"
            :class="{
              'checkout-option--active': form.invoiceType === 'company',
            }"
          >
            <input
              v-model="form.invoiceType"
              type="radio"
              value="company"
              class="checkout-option__input"
            />

            <span class="checkout-option__radio"></span>

            <span class="checkout-option__icon">
              <i class="bi bi-building"></i>
            </span>

            <span class="checkout-option__text">
              <strong>公司發票</strong>
              <small>填寫統編與公司名稱</small>
            </span>
          </label>
        </div>

        <div v-if="form.invoiceType === 'mobile'" class="checkout-form__extra">
          <label class="checkout-form__field">
            <span>手機條碼</span>

            <input
              v-model="form.mobileBarcode"
              type="text"
              placeholder="/ABC1234"
            />
          </label>
        </div>

        <div
          v-if="form.invoiceType === 'company'"
          class="checkout-form__extra checkout-form__extra--grid"
        >
          <label class="checkout-form__field">
            <span>統一編號</span>

            <input
              v-model="form.companyNumber"
              type="text"
              maxlength="8"
              inputmode="numeric"
              placeholder="請輸入 8 位數統一編號"
            />
          </label>

          <label class="checkout-form__field">
            <span>公司名稱</span>

            <input
              v-model="form.companyName"
              type="text"
              placeholder="請輸入公司名稱"
            />
          </label>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped lang="scss">
.checkout-form {
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.checkout-form__section {
  overflow: hidden;
  border: 1px solid #e5e7eb;
  border-radius: 14px;
  background: #ffffff;
  box-shadow: 0 3px 12px rgb(15 23 42 / 4%);
}

.checkout-form__heading {
  display: flex;
  align-items: center;
  gap: 11px;
  padding: 13px 16px;
  border-bottom: 1px solid #f1f3f5;
}

.checkout-form__heading-text {
  min-width: 0;
}

.checkout-form__icon {
  display: grid;
  flex: 0 0 auto;
  width: 36px;
  height: 36px;
  place-items: center;
  border-radius: 10px;
  color: #ff5a00;
  background: #fff2eb;
  font-size: 17px;
}

.checkout-form__title {
  margin: 0 0 2px;
  color: #1f2937;
  font-size: 16px;
  font-weight: 700;
  line-height: 1.3;
}

.checkout-form__description {
  margin: 0;
  color: #9ca3af;
  font-size: 12px;
  line-height: 1.4;
}

.checkout-form__content {
  padding: 14px 16px;
}

.checkout-form__address {
  display: flex;
  align-items: center;
  gap: 11px;
  min-height: 60px;
  padding: 9px 12px;
  border: 1px solid #e5e7eb;
  border-radius: 11px;
  background: #fafafa;
}

.checkout-form__address-icon {
  display: grid;
  flex: 0 0 auto;
  width: 34px;
  height: 34px;
  place-items: center;
  border-radius: 50%;
  color: #ff5a00;
  background: #ffffff;
  font-size: 15px;
  box-shadow: 0 2px 7px rgb(15 23 42 / 7%);
}

.checkout-form__address-text {
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 1px;
  min-width: 0;
  color: #374151;
  font-size: 13px;
}

.checkout-form__address-text strong {
  overflow: hidden;
  font-size: 13px;
  font-weight: 600;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.checkout-form__address-label {
  color: #9ca3af;
  font-size: 11px;
}

.checkout-form__edit-button {
  flex: 0 0 auto;
  padding: 6px 12px;
  border: 1px solid #ff5a00;
  border-radius: 8px;
  color: #ff5a00;
  background: #ffffff;
  font-size: 12px;
  font-weight: 600;
  transition:
    color 0.2s ease,
    background 0.2s ease,
    box-shadow 0.2s ease;
  text-decoration: none;
}

.checkout-form__edit-button:hover {
  color: #ffffff;
  background: #ff5a00;
  box-shadow: 0 4px 10px rgb(255 90 0 / 20%);
}

.checkout-form__edit-button:active {
  background: #e95000;
  box-shadow: none;
}

.checkout-form__options {
  display: grid;
  gap: 10px;
}

.checkout-form__options--two {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

.checkout-form__options--three {
  grid-template-columns: repeat(3, minmax(0, 1fr));
}

.checkout-option {
  position: relative;
  display: flex;
  align-items: center;
  gap: 9px;
  min-width: 0;
  min-height: 60px;
  padding: 9px 11px;
  border: 1px solid #e5e7eb;
  border-radius: 11px;
  background: #ffffff;
  cursor: pointer;
  transition:
    border-color 0.2s ease,
    background 0.2s ease,
    box-shadow 0.2s ease;
}

.checkout-option:hover {
  border-color: #ffb78f;
  box-shadow: 0 3px 10px rgb(15 23 42 / 5%);
}

.checkout-option--active {
  border-color: #ff5a00;
  background: #fffaf7;
  box-shadow: 0 0 0 2px rgb(255 90 0 / 7%);
}

.checkout-option__input {
  position: absolute;
  width: 1px;
  height: 1px;
  opacity: 0;
  pointer-events: none;
}

.checkout-option__radio {
  position: relative;
  flex: 0 0 auto;
  width: 17px;
  height: 17px;
  border: 2px solid #d1d5db;
  border-radius: 50%;
  background: #ffffff;
}

.checkout-option--active .checkout-option__radio {
  border-color: #ff5a00;
}

.checkout-option--active .checkout-option__radio::after {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 7px;
  height: 7px;
  border-radius: 50%;
  background: #ff5a00;
  content: "";
  transform: translate(-50%, -50%);
}

.checkout-option__icon {
  display: grid;
  flex: 0 0 auto;
  width: 34px;
  height: 34px;
  place-items: center;
  border-radius: 9px;
  color: #6b7280;
  background: #f3f4f6;
  font-size: 15px;
}

.checkout-option--active .checkout-option__icon {
  color: #ff5a00;
  background: #fff0e8;
}

.checkout-option__text {
  display: flex;
  flex-direction: column;
  gap: 1px;
  min-width: 0;
}

.checkout-option__text strong {
  overflow: hidden;
  color: #374151;
  font-size: 13px;
  font-weight: 700;
  line-height: 1.35;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.checkout-option__text small {
  overflow: hidden;
  color: #9ca3af;
  font-size: 10px;
  line-height: 1.4;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.checkout-form__textarea {
  display: block;
  width: 100%;
  min-height: 72px;
  padding: 9px 11px;
  resize: vertical;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  outline: none;
  color: #374151;
  background: #ffffff;
  font: inherit;
  font-size: 13px;
  line-height: 1.5;
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.checkout-form__textarea::placeholder {
  color: #b6bbc3;
}

.checkout-form__textarea:focus {
  border-color: #ff5a00;
  box-shadow: 0 0 0 3px rgb(255 90 0 / 9%);
}

.checkout-form__count {
  margin-top: 4px;
  color: #9ca3af;
  font-size: 11px;
  text-align: right;
}

.checkout-form__extra {
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid #f1f3f5;
}

.checkout-form__extra--grid {
  display: grid;
  grid-template-columns: 160px minmax(0, 1fr);
  gap: 10px;
}

.checkout-form__field {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.checkout-form__field span {
  color: #4b5563;
  font-size: 12px;
  font-weight: 600;
}

.checkout-form__field input {
  width: 100%;
  height: 38px;
  padding: 0 11px;
  border: 1px solid #e5e7eb;
  border-radius: 9px;
  outline: none;
  color: #374151;
  background: #ffffff;
  font-size: 13px;
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease;
}

.checkout-form__field input::placeholder {
  color: #b6bbc3;
}

.checkout-form__field input:focus {
  border-color: #ff5a00;
  box-shadow: 0 0 0 3px rgb(255 90 0 / 9%);
}

@media (max-width: 1200px) {
  .checkout-form__options--three {
    grid-template-columns: 1fr;
  }

  .checkout-option__text small {
    white-space: normal;
  }
}

@media (max-width: 768px) {
  .checkout-form__options--two,
  .checkout-form__options--three {
    grid-template-columns: 1fr;
  }

  .checkout-option__text small {
    white-space: normal;
  }
}

@media (max-width: 576px) {
  .checkout-form {
    gap: 12px;
  }

  .checkout-form__section {
    border-radius: 12px;
  }

  .checkout-form__heading {
    gap: 9px;
    padding: 12px;
  }

  .checkout-form__content {
    padding: 12px;
  }

  .checkout-form__icon {
    width: 34px;
    height: 34px;
  }

  .checkout-form__address {
    flex-wrap: wrap;
  }

  .checkout-form__address-text {
    width: calc(100% - 45px);
    flex-basis: calc(100% - 45px);
  }

  .checkout-form__edit-button {
    width: 100%;
  }

  .checkout-form__extra--grid {
    grid-template-columns: 1fr;
  }
}
</style>
