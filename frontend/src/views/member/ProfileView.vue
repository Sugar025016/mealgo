<script setup lang="ts">
import { computed, ref } from "vue";

type ProviderId = "google" | "apple";

type Provider = {
  id: ProviderId;
  name: string;
  description: string;
  email: string | null;
  connected: boolean;
};

const profile = ref({
  email: "ruby028016@gmail.com",
  name: "jessie",
  phone: "0928776769",
  phoneVerified: true,
  hasPassword: true,
  passwordUpdatedAt: "2024/07/01",
  lastLoginAt: "2024/07/17 14:30",
  lastLoginLocation: "台北",
});

const providers = ref<Provider[]>([
  {
    id: "google",
    name: "Google",
    description: "使用 Google 帳號登入",
    email: "ruby028016@gmail.com",
    connected: true,
  },
  {
    id: "apple",
    name: "Apple",
    description: "使用 Apple 帳號登入",
    email: "ruby028016@gmail.com",
    connected: true,
  },
]);

const connectedProviderCount = computed(() => {
  return providers.value.filter((provider) => provider.connected).length;
});

function editEmail() {
  console.log("更換信箱");
}

function editName() {
  console.log("修改姓名");
}

function editPassword() {
  console.log("修改密碼");
}

function editPhone() {
  console.log("修改手機號碼");
}

function connectProvider(provider: Provider) {
  provider.connected = true;
  provider.email = profile.value.email;
}

function disconnectProvider(provider: Provider) {
  const hasAnotherLoginMethod =
    profile.value.hasPassword || connectedProviderCount.value > 1;

  if (!hasAnotherLoginMethod) {
    window.alert("請先設定密碼或連結其他登入方式。");
    return;
  }

  const confirmed = window.confirm(
    `確定要解除 ${provider.name} 帳號連結嗎？`,
  );

  if (!confirmed) return;

  provider.connected = false;
  provider.email = null;
}

function logoutAllDevices() {
  const confirmed = window.confirm("確定要登出其他所有裝置嗎？");

  if (!confirmed) return;

  console.log("登出所有裝置");
}

function deleteAccount() {
  const confirmed = window.confirm(
    "刪除帳戶後將無法復原，確定要繼續嗎？",
  );

  if (!confirmed) return;

  console.log("刪除帳戶");
}
</script>

<template>
  <main class="profile-page">
    <section class="profile-card">
      <header class="profile-header">
        <h1 class="profile-header__title">個人資料</h1>

        <p class="profile-header__description">
          管理您的個人資料與帳號設定
        </p>
      </header>

      <!-- 個人資料 -->
      <div class="profile-information">
        <!-- Email -->
        <div class="profile-row">
          <div class="profile-row__icon">
            <i class="bi bi-envelope"></i>
          </div>

          <div class="profile-row__label">
            Email（帳號）
          </div>

          <div class="profile-row__value">
            {{ profile.email }}
          </div>

          <div class="profile-row__action">
            <button
              type="button"
              class="outline-button"
              @click="editEmail"
            >
              更換信箱
            </button>
          </div>
        </div>

        <!-- 姓名 -->
        <div class="profile-row">
          <div class="profile-row__icon">
            <i class="bi bi-person"></i>
          </div>

          <div class="profile-row__label">
            姓名
          </div>

          <div class="profile-row__value">
            {{ profile.name }}
          </div>

          <div class="profile-row__action">
            <button
              type="button"
              class="outline-button"
              @click="editName"
            >
              修改
            </button>
          </div>
        </div>

        <!-- 密碼 -->
        <div class="profile-row">
          <div class="profile-row__icon">
            <i class="bi bi-lock"></i>
          </div>

          <div class="profile-row__label">
            密碼
          </div>

          <div class="profile-row__value">
            <template v-if="profile.hasPassword">
              已設定
              <span class="profile-row__secondary">
                （上次修改：{{ profile.passwordUpdatedAt }}）
              </span>
            </template>

            <template v-else>
              尚未設定密碼
            </template>
          </div>

          <div class="profile-row__action">
            <button
              type="button"
              class="outline-button"
              @click="editPassword"
            >
              {{ profile.hasPassword ? "修改" : "設定" }}
            </button>
          </div>
        </div>

        <!-- 手機號碼 -->
        <div class="profile-row">
          <div class="profile-row__icon">
            <i class="bi bi-telephone"></i>
          </div>

          <div class="profile-row__label">
            手機號碼
          </div>

          <div class="profile-row__value profile-row__phone">
            <span>{{ profile.phone }}</span>

            <span
              v-if="profile.phoneVerified"
              class="verified-badge"
            >
              <i class="bi bi-check-lg"></i>
              已驗證
            </span>

            <span
              v-else
              class="unverified-badge"
            >
              未驗證
            </span>
          </div>

          <div class="profile-row__action">
            <button
              type="button"
              class="outline-button"
              @click="editPhone"
            >
              修改
            </button>
          </div>
        </div>
      </div>

      <!-- 第三方登入 -->
      <section class="provider-section">
        <header class="section-header">
          <h2 class="section-header__title">
            第三方登入設定
          </h2>

          <p class="section-header__description">
            管理您連結的第三方登入帳號
          </p>
        </header>

        <div class="provider-list">
          <article
            v-for="provider in providers"
            :key="provider.id"
            class="provider-row"
          >
            <div
              class="provider-row__logo"
              :class="`provider-row__logo--${provider.id}`"
            >
              <i
                v-if="provider.id === 'google'"
                class="bi bi-google"
              ></i>

              <i
                v-else
                class="bi bi-apple"
              ></i>
            </div>

            <div class="provider-row__information">
              <h3 class="provider-row__name">
                {{ provider.name }}
              </h3>

              <p class="provider-row__description">
                {{ provider.description }}
              </p>
            </div>

            <div class="provider-row__status">
              <template v-if="provider.connected">
                <div class="provider-row__connected">
                  <span>已連結</span>
                  <i class="bi bi-check-circle-fill"></i>
                </div>

                <div class="provider-row__email">
                  {{ provider.email }}
                </div>
              </template>

              <span
                v-else
                class="provider-row__not-connected"
              >
                未連結
              </span>
            </div>

            <div class="provider-row__action">
              <button
                v-if="provider.connected"
                type="button"
                class="provider-button provider-button--disconnect"
                @click="disconnectProvider(provider)"
              >
                解除連結
              </button>

              <button
                v-else
                type="button"
                class="provider-button provider-button--connect"
                @click="connectProvider(provider)"
              >
                連結
              </button>
            </div>
          </article>
        </div>
      </section>

      <!-- 帳戶安全 -->
      <section class="security-section">
        <header class="section-header">
          <h2 class="section-header__title">
            帳戶安全
          </h2>
        </header>

        <div class="security-grid">
          <button
            type="button"
            class="security-card"
          >
            <span class="security-card__icon">
              <i class="bi bi-shield-check"></i>
            </span>

            <span class="security-card__content">
              <strong>最近登入時間</strong>

              <small>
                {{ profile.lastLoginAt }}（{{ profile.lastLoginLocation }}）
              </small>
            </span>

            <i class="bi bi-chevron-right security-card__arrow"></i>
          </button>

          <button
            type="button"
            class="security-card"
            @click="logoutAllDevices"
          >
            <span class="security-card__icon">
              <i class="bi bi-phone"></i>
            </span>

            <span class="security-card__content">
              <strong>登出所有裝置</strong>
              <small>登出其他所有登入中的裝置</small>
            </span>

            <i class="bi bi-chevron-right security-card__arrow"></i>
          </button>

          <button
            type="button"
            class="security-card security-card--danger"
            @click="deleteAccount"
          >
            <span class="security-card__icon">
              <i class="bi bi-trash3"></i>
            </span>

            <span class="security-card__content">
              <strong>刪除帳戶</strong>
              <small>永久刪除您的帳戶與資料</small>
            </span>

            <i class="bi bi-chevron-right security-card__arrow"></i>
          </button>
        </div>
      </section>
    </section>
  </main>
</template>

<style scoped lang="scss">
$primary: #ff5f07;
$primary-hover: #e65500;
$primary-soft: #fff5ef;

$text-main: #202124;
$text-secondary: #858585;

$border: #e5e5e5;

$success: #40b85a;
$success-soft: #effaf1;
$success-border: #aee0b7;

$danger: #ff5364;
$danger-soft: #fff5f6;

.profile-page {
  width: 100%;
}

.profile-card {
  width: 100%;
  padding: 18px 32px 22px;
  border: 1px solid #f0ebe7;
  border-radius: 18px;
  background: #ffffff;
  box-shadow: 0 8px 28px rgb(0 0 0 / 4%);
}

/* 標題 */

.profile-header {
  padding-bottom: 12px;
  border-bottom: 1px solid $border;
}

.profile-header__title {
  margin: 0 0 10px;
  color: $text-main;
  font-size: 28px;
  font-weight: 700;
  line-height: 1.3;
}

.profile-header__description {
  margin: 0;
  color: $text-secondary;
  font-size: 16px;
}

/* 個人資料 */

.profile-row {
  display: grid;
  grid-template-columns: 56px 210px minmax(0, 1fr) 116px;
  align-items: center;
  column-gap: 16px;
  min-height: 74px;
  border-bottom: 1px solid $border;
}

.profile-row__icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 42px;
  height: 42px;
  border-radius: 50%;
  background: $primary-soft;
  color: #c9581b;
  font-size: 19px;
}

.profile-row__label {
  color: $text-main;
  font-size: 17px;
  font-weight: 700;
  white-space: nowrap;
}

.profile-row__value {
  min-width: 0;
  color: $text-main;
  font-size: 17px;
  line-height: 1.5;
  overflow-wrap: anywhere;
}

.profile-row__secondary {
  color: $text-secondary;
  font-size: 15px;
}

.profile-row__phone {
  display: flex;
  align-items: center;
  gap: 18px;
}

.profile-row__phone > span:first-child {
  white-space: nowrap;
}

.profile-row__action {
  display: flex;
  justify-content: flex-end;
}

.outline-button {
  min-width: 96px;
  min-height: 46px;
  padding: 9px 16px;
  border: 1px solid $primary;
  border-radius: 9px;
  background: #ffffff;
  color: $primary;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease,
    border-color 0.2s ease;

  &:hover {
    border-color: $primary-hover;
    background: $primary-hover;
    color: #ffffff;
  }

  &:focus-visible {
    outline: 3px solid rgb(255 95 7 / 18%);
    outline-offset: 2px;
  }
}

.verified-badge,
.unverified-badge {
  display: inline-flex;
  flex-shrink: 0;
  align-items: center;
  gap: 6px;
  padding: 6px 14px;
  border-radius: 999px;
  font-size: 14px;
  font-weight: 700;
  white-space: nowrap;
}

.verified-badge {
  border: 1px solid $success-border;
  background: $success-soft;
  color: $success;
}

.unverified-badge {
  border: 1px solid #ffc4ca;
  background: $danger-soft;
  color: $danger;
}

/* 共用區塊標題 */

.provider-section,
.security-section {
  padding-top: 26px;
}

.section-header {
  margin-bottom: 12px;
}

.section-header__title {
  margin: 0 0 7px;
  color: $text-main;
  font-size: 24px;
  font-weight: 700;
}

.section-header__description {
  margin: 0;
  color: $text-secondary;
  font-size: 15px;
}

/* 第三方登入 */

.provider-list {
  overflow: hidden;
  border: 1px solid #dedede;
  border-radius: 10px;
  background: #ffffff;
}

.provider-row {
  display: grid;
//   grid-template-columns: 54px minmax(200px, 1fr) minmax(250px, 1.4fr) 116px; 
  grid-template-columns: 54px 300px minmax(240px, 1fr) 116px;
  align-items: center;
  column-gap: 16px;
  min-height: 74px;
  padding: 12px 20px;

  & + & {
    border-top: 1px solid $border;
  }
}

.provider-row__logo {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  font-size: 36px;
}

.provider-row__logo--google {
  color: #4285f4;
}

.provider-row__logo--apple {
  color: #000000;
}

.provider-row__information,
.provider-row__status {
  min-width: 0;
}

.provider-row__name {
  margin: 0 0 5px;
  color: $text-main;
  font-size: 18px;
  font-weight: 700;
}

.provider-row__description {
  margin: 0;
  color: $text-secondary;
  font-size: 14px;
}

.provider-row__connected {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-bottom: 5px;
  color: $success;
  font-size: 14px;
  font-weight: 700;
}

.provider-row__email {
  color: $text-main;
  font-size: 15px;
  overflow-wrap: anywhere;
}

.provider-row__not-connected {
  color: $text-secondary;
  font-size: 15px;
}

.provider-row__action {
  display: flex;
  justify-content: flex-end;
}

.provider-button {
  min-width: 102px;
  min-height: 44px;
  padding: 8px 14px;
  border-radius: 8px;
  background: #ffffff;
  font-size: 15px;
  font-weight: 700;
  cursor: pointer;
  transition:
    color 0.2s ease,
    background-color 0.2s ease;
}

.provider-button--disconnect {
  border: 1px solid $danger;
  color: $danger;

  &:hover {
    background: $danger;
    color: #ffffff;
  }
}

.provider-button--connect {
  border: 1px solid $primary;
  color: $primary;

  &:hover {
    background: $primary;
    color: #ffffff;
  }
}

/* 帳戶安全 */

.security-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 14px;
}

.security-card {
  display: grid;
  grid-template-columns: 42px minmax(0, 1fr) 18px;
  align-items: center;
  gap: 12px;
  min-width: 0;
  min-height: 82px;
  padding: 15px 18px;
  border: 1px solid #dedede;
  border-radius: 10px;
  background: #ffffff;
  color: $text-main;
  text-align: left;
  cursor: pointer;
  transition:
    border-color 0.2s ease,
    box-shadow 0.2s ease,
    transform 0.2s ease;

  &:hover {
    border-color: #cfcfcf;
    box-shadow: 0 6px 18px rgb(0 0 0 / 6%);
    transform: translateY(-1px);
  }
}

.security-card__icon {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #3f454c;
  font-size: 25px;
}

.security-card__content {
  display: flex;
  min-width: 0;
  flex-direction: column;
  gap: 4px;

  strong {
    font-size: 15px;
    font-weight: 700;
  }

  small {
    color: $text-secondary;
    font-size: 13px;
    line-height: 1.4;
  }
}

.security-card__arrow {
  color: #6f7479;
  font-size: 14px;
}

.security-card--danger {
  .security-card__icon,
  .security-card__content strong,
  .security-card__arrow {
    color: $danger;
  }
}

/* 小型桌面與平板 */

@media (max-width: 1199.98px) {
  .profile-card {
    padding-inline: 30px;
  }

  .profile-row {
    grid-template-columns: 50px 160px minmax(0, 1fr) 100px;
    column-gap: 12px;
  }

  .provider-row {
    grid-template-columns: 48px minmax(170px, 1fr) minmax(210px, 1.2fr) 106px;
    column-gap: 12px;
  }
}

@media (max-width: 991.98px) {
  .profile-card {
    padding: 26px 24px 32px;
  }

  .profile-row {
    grid-template-columns: 48px 130px minmax(0, 1fr) 88px;
  }

  .profile-row__phone {
    gap: 10px;
  }

  .outline-button {
    min-width: 82px;
  }

  .provider-row {
    grid-template-columns: 46px minmax(145px, 1fr) minmax(180px, 1.2fr) 94px;
  }

  .provider-button {
    min-width: 90px;
  }

  .security-grid {
    grid-template-columns: 1fr;
  }
}

/* 手機 */

@media (max-width: 767.98px) {
  .profile-card {
    padding: 22px 16px 28px;
    border-radius: 14px;
  }

  .profile-header__title {
    font-size: 24px;
  }

  .profile-header__description {
    font-size: 14px;
  }

  .profile-row {
    grid-template-columns: 42px minmax(0, 1fr) 72px;
    gap: 5px 10px;
    min-height: auto;
    padding: 16px 0;
  }

  .profile-row__icon {
    grid-row: 1 / 3;
    align-self: flex-start;
    width: 36px;
    height: 36px;
    font-size: 17px;
  }

  .profile-row__label {
    grid-column: 2;
    font-size: 15px;
  }

  .profile-row__value {
    grid-column: 2;
    font-size: 15px;
  }

  .profile-row__secondary {
    display: block;
    margin-top: 2px;
    font-size: 13px;
  }

  .profile-row__action {
    grid-column: 3;
    grid-row: 1 / 3;
  }

  .profile-row__phone {
    align-items: flex-start;
    flex-direction: column;
    gap: 7px;
  }

  .outline-button {
    min-width: 68px;
    min-height: 38px;
    padding: 6px 8px;
    font-size: 13px;
  }

  .verified-badge,
  .unverified-badge {
    padding: 4px 9px;
    font-size: 12px;
  }

  .section-header__title {
    font-size: 21px;
  }

  .provider-row {
    grid-template-columns: 40px minmax(0, 1fr) 82px;
    gap: 8px 10px;
    min-height: auto;
    padding: 15px 12px;
  }

  .provider-row__logo {
    grid-row: 1 / 3;
    align-self: flex-start;
    font-size: 29px;
  }

  .provider-row__information {
    grid-column: 2;
  }

  .provider-row__status {
    grid-column: 2;
  }

  .provider-row__action {
    grid-column: 3;
    grid-row: 1 / 3;
  }

  .provider-row__name {
    font-size: 16px;
  }

  .provider-row__description,
  .provider-row__connected,
  .provider-row__email,
  .provider-row__not-connected {
    font-size: 12px;
  }

  .provider-button {
    min-width: 78px;
    min-height: 38px;
    padding: 6px;
    font-size: 12px;
  }
}

@media (max-width: 420px) {
  .profile-row {
    grid-template-columns: 38px minmax(0, 1fr) 62px;
    column-gap: 7px;
  }

  .outline-button {
    min-width: 58px;
  }

  .provider-row {
    grid-template-columns: 36px minmax(0, 1fr);
  }

  .provider-row__logo {
    grid-row: 1;
  }

  .provider-row__information,
  .provider-row__status,
  .provider-row__action {
    grid-column: 2;
  }

  .provider-row__action {
    grid-row: auto;
    justify-content: flex-start;
  }
}
</style>