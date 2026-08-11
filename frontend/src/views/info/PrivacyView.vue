<script setup lang="ts">
import { nextTick, onBeforeUnmount, onMounted, ref } from "vue";

type PrivacySection = {
  id: string;
  number: string;
  title: string;
  paragraphs: string[];
};

const activeSection = ref("collection");
let observer: IntersectionObserver | null = null;

const sections: PrivacySection[] = [
  {
    id: "collection",
    number: "01",
    title: "資料蒐集",
    paragraphs: [
      "當你註冊帳號、建立訂單或使用 MealGo 服務時，我們可能蒐集姓名、電子郵件、電話及外送地址等資料。",
      "為完成訂單，我們也會保存訂單內容、付款方式、配送方式及訂單狀態等紀錄。",
      "使用平台時，系統可能自動記錄瀏覽器類型、裝置資訊、IP 位址及服務使用紀錄。",
    ],
  },
  {
    id: "usage",
    number: "02",
    title: "資料使用方式",
    paragraphs: [
      "我們使用個人資料處理訂單、安排外送或自取，以及提供會員相關服務。",
      "相關資料也可能用於客服處理、身分驗證、交易安全及防止詐騙。",
      "經去識別化處理後的資料，可能用於分析及改善 MealGo 的服務品質。",
    ],
  },
  {
    id: "cookie",
    number: "03",
    title: "Cookie 與追蹤技術",
    paragraphs: [
      "MealGo 可能使用 Cookie 保存登入狀態、購物車內容及使用偏好。",
      "Cookie 也可能用於分析網站流量及改善使用者體驗。",
      "你可以透過瀏覽器設定管理或停用 Cookie，但部分功能可能因此無法正常使用。",
    ],
  },
  {
    id: "sharing",
    number: "04",
    title: "資料分享",
    paragraphs: [
      "為完成訂單，我們可能向合作商家及外送服務提供必要的訂單與配送資訊。",
      "依法令要求、政府機關調查或保障平台安全時，我們可能依法提供相關資料。",
      "MealGo 不會出售或任意公開你的個人資料。",
    ],
  },
  {
    id: "security",
    number: "05",
    title: "資料保存與安全",
    paragraphs: [
      "我們會於提供服務及符合法令所需的期間內保存個人資料。",
      "MealGo 採取合理的技術及管理措施，避免資料遭到未授權存取、竄改或洩漏。",
      "當資料不再需要保存時，我們將依規定刪除或進行去識別化處理。",
    ],
  },
  {
    id: "rights",
    number: "06",
    title: "使用者權利",
    paragraphs: [
      "你可以查看或修改會員帳戶中的個人資料。",
      "你可以依法申請查詢、更正或刪除個人資料。",
      "刪除帳戶後，部分交易紀錄仍可能因法令或爭議處理需要而保留。",
    ],
  },
  {
    id: "children",
    number: "07",
    title: "兒童隱私",
    paragraphs: [
      "未成年人使用 MealGo 服務前，應取得法定代理人的同意。",
      "若發現未經同意蒐集未成年人的個人資料，我們將依法進行處理。",
    ],
  },
  {
    id: "updates",
    number: "08",
    title: "政策更新",
    paragraphs: [
      "MealGo 可能因服務調整或法令變更修改本隱私權政策。",
      "更新後的政策將公布於本頁，並標示最後更新日期。",
      "繼續使用本平台，即表示你已閱讀更新後的隱私權政策。",
    ],
  },
];

function scrollToSection(id: string) {
  const element = document.getElementById(id);

  if (!element) return;

  activeSection.value = id;

  element.scrollIntoView({
    behavior: "smooth",
    block: "start",
  });
}

function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
}

onMounted(async () => {
  await nextTick();

  observer = new IntersectionObserver(
    (entries) => {
      const entry = entries.find((item) => item.isIntersecting);

      if (entry) {
        activeSection.value = entry.target.id;
      }
    },
    {
      rootMargin: "-20% 0px -65% 0px",
      threshold: 0,
    },
  );

  sections.forEach((section) => {
    const element = document.getElementById(section.id);

    if (element) {
      observer?.observe(element);
    }
  });
});

onBeforeUnmount(() => {
  observer?.disconnect();
});
</script>

<template>
  <div class="privacy-page">
    <header class="privacy-page__header">
      <h1 class="privacy-page__title">隱私權政策</h1>

      <p class="privacy-page__subtitle">我們重視你的個人資料與隱私安全</p>

      <p class="privacy-page__updated">最後更新日期：2026 年 7 月 23 日</p>
    </header>

    <div class="privacy-page__container">
      <aside class="privacy-menu">
        <h2 class="privacy-menu__title">政策目錄</h2>

        <nav class="privacy-menu__nav" aria-label="隱私權政策目錄">
          <button
            v-for="section in sections"
            :key="section.id"
            type="button"
            class="privacy-menu__item"
            :class="{
              'privacy-menu__item--active': activeSection === section.id,
            }"
            @click="scrollToSection(section.id)"
          >
            <span class="privacy-menu__number">
              {{ section.number }}
            </span>

            <span>{{ section.title }}</span>
          </button>
        </nav>
      </aside>

      <main class="privacy-content">
        <section
          v-for="(section, index) in sections"
          :id="section.id"
          :key="section.id"
          class="privacy-section"
        >
          <h2 class="privacy-section__title">
            {{ index + 1 }}、{{ section.title }}
          </h2>

          <ol class="privacy-section__list">
            <li
              v-for="paragraph in section.paragraphs"
              :key="paragraph"
              class="privacy-section__item"
            >
              {{ paragraph }}
            </li>
          </ol>
        </section>

        <div class="privacy-content__bottom">
          <div class="privacy-notice">
            <i class="bi bi-shield-check"></i>

            <span> MealGo 將依相關法令妥善保護你的個人資料。 </span>
          </div>

          <button
            type="button"
            class="privacy-content__top-button"
            @click="scrollToTop"
          >
            回到頂端
            <i class="bi bi-arrow-up"></i>
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<style scoped lang="scss">
.privacy-page {
  min-height: calc(100vh - 80px);
  padding: 44px 0 56px;
  color: #202939;
  background: #fafafa;
}

.privacy-page__header {
  margin-bottom: 24px;
  text-align: center;
}

.privacy-page__title {
  margin: 0;
  color: #172033;
  font-size: 36px;
  font-weight: 700;
}

.privacy-page__subtitle {
  margin: 8px 0 0;
  color: #4b5563;
  font-size: 16px;
}

.privacy-page__updated {
  margin: 10px 0 0;
  color: #9299a5;
  font-size: 14px;
}

.privacy-page__container {
  display: grid;
  grid-template-columns: 240px minmax(0, 1fr);
  align-items: start;
  gap: 20px;

  width: min(100% - 64px, 1240px);
  margin: 0 auto;
}

.privacy-menu {
  position: sticky;
  top: 96px;

  overflow: hidden;
  background: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 12px;
  box-shadow: 0 4px 14px rgb(0 0 0 / 5%);
}

.privacy-menu__title {
  margin: 0;
  padding: 20px 22px 12px;
  font-size: 18px;
  font-weight: 700;
}

.privacy-menu__nav {
  display: flex;
  flex-direction: column;
  padding-bottom: 10px;
}

.privacy-menu__item {
  display: grid;
  grid-template-columns: 34px minmax(0, 1fr);
  align-items: center;

  width: 100%;
  min-height: 46px;
  padding: 0 20px;

  color: #3f4652;
  font-size: 15px;
  text-align: left;

  cursor: pointer;
  background: transparent;
  border: 0;
  border-left: 3px solid transparent;

  transition:
    color 0.2s,
    background-color 0.2s,
    border-color 0.2s;
}

.privacy-menu__item:hover {
  color: #ff5f07;
  background: #fff8f4;
}

.privacy-menu__item--active {
  color: #ff5f07;
  font-weight: 600;
  background: #fff2ea;
  border-left-color: #ff5f07;
}

.privacy-menu__number {
  font-size: 13px;
}

.privacy-content {
  padding: 8px 24px 16px;
  background: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 12px;
  box-shadow: 0 4px 14px rgb(0 0 0 / 5%);
}

.privacy-section {
  padding: 22px 0;
  scroll-margin-top: 104px;
  border-bottom: 1px solid #eceff3;
}

.privacy-section:last-of-type {
  border-bottom: 0;
}

.privacy-section__title {
  margin: 0 0 12px;
  color: #172033;
  font-size: 19px;
  font-weight: 700;
}

.privacy-section__list {
  display: flex;
  flex-direction: column;
  gap: 7px;

  margin: 0;
  padding-left: 24px;
}

.privacy-section__item {
  padding-left: 4px;
  color: #4b5563;
  font-size: 15px;
  line-height: 1.7;
}

.privacy-content__bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding-top: 16px;
}

.privacy-notice {
  display: flex;
  flex: 1;
  align-items: center;
  gap: 10px;

  min-height: 44px;
  padding: 10px 14px;

  color: #c94c05;
  font-size: 14px;

  background: #fff7f2;
  border: 1px solid #ffd5be;
  border-radius: 8px;
}

.privacy-notice i {
  flex-shrink: 0;
  color: #ff5f07;
  font-size: 18px;
}

.privacy-content__top-button {
  display: inline-flex;
  flex-shrink: 0;
  align-items: center;
  gap: 8px;

  min-height: 42px;
  padding: 0 16px;

  color: #ff5f07;
  font-size: 14px;
  font-weight: 600;

  cursor: pointer;
  background: #ffffff;
  border: 1px solid #ff5f07;
  border-radius: 8px;

  transition:
    color 0.2s,
    background-color 0.2s;
}

.privacy-content__top-button:hover {
  color: #ffffff;
  background: #ff5f07;
}

@media (max-width: 991.98px) {
  .privacy-page {
    padding: 32px 0 44px;
  }

  .privacy-page__container {
    grid-template-columns: 1fr;
    width: min(100% - 32px, 820px);
  }

  .privacy-menu {
    position: static;
  }

  .privacy-menu__nav {
    flex-direction: row;
    gap: 8px;

    overflow-x: auto;
    padding: 0 16px 16px;
    scrollbar-width: none;
  }

  .privacy-menu__nav::-webkit-scrollbar {
    display: none;
  }

  .privacy-menu__item {
    display: inline-flex;
    flex: 0 0 auto;
    gap: 6px;

    width: auto;
    min-height: 38px;
    padding: 0 14px;

    white-space: nowrap;
    border: 1px solid #e2e5e9;
    border-radius: 999px;
  }

  .privacy-menu__item--active {
    border-color: #ff5f07;
  }
}

@media (max-width: 575.98px) {
  .privacy-page {
    padding: 24px 0 36px;
  }

  .privacy-page__title {
    font-size: 28px;
  }

  .privacy-page__subtitle {
    font-size: 14px;
  }

  .privacy-page__container {
    width: min(100% - 24px, 520px);
  }

  .privacy-content {
    padding: 4px 16px 16px;
  }

  .privacy-section {
    padding: 20px 0;
  }

  .privacy-section__title {
    font-size: 17px;
  }

  .privacy-section__item {
    font-size: 14px;
  }

  .privacy-content__bottom {
    align-items: stretch;
    flex-direction: column;
  }

  .privacy-content__top-button {
    justify-content: center;
  }
}
</style>
