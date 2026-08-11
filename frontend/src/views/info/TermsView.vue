<script setup lang="ts">
import { nextTick, onBeforeUnmount, onMounted, ref } from "vue";

type TermSection = {
  id: string;
  number: string;
  title: string;
  paragraphs: string[];
};

const activeSection = ref("service");
let observer: IntersectionObserver | null = null;

const sections: TermSection[] = [
  {
    id: "service",
    number: "01",
    title: "服務說明",
    paragraphs: [
      "MealGo（以下簡稱「本平台」）提供使用者瀏覽餐飲商家、選購餐點及建立訂單等服務。",
      "本平台作為使用者與合作商家之間的線上點餐服務平台，餐點內容、價格及供應狀況以商家提供的資訊為準。",
      "本平台得因維護、更新或其他營運需求，暫停或調整部分服務內容。",
    ],
  },
  {
    id: "account",
    number: "02",
    title: "帳戶與使用規範",
    paragraphs: [
      "使用者應提供正確且完整的註冊資料，並妥善保管帳號及登入資訊。",
      "使用者不得將帳戶提供他人使用，也不得冒用他人身分註冊。",
      "若發現帳戶遭到未授權使用，應立即修改密碼並聯絡平台客服。",
    ],
  },
  {
    id: "order",
    number: "03",
    title: "訂單與付款",
    paragraphs: [
      "使用者送出訂單前，應確認餐點內容、數量、配送地址及付款方式。",
      "訂單是否成立，仍須視合作商家是否接受訂單而定。",
      "若商品價格或訂單資訊發生明顯錯誤，本平台得取消訂單並通知使用者。",
    ],
  },
  {
    id: "refund",
    number: "04",
    title: "取消與退款",
    paragraphs: [
      "訂單尚未被商家接受前，使用者可以申請取消訂單。",
      "商家接受訂單並開始製作後，可能無法取消或取得全額退款。",
      "若因商品缺貨、商家拒絕接單或其他不可歸責於使用者的原因取消訂單，平台將協助辦理退款。",
    ],
  },
  {
    id: "responsibility",
    number: "05",
    title: "使用者責任",
    paragraphs: [
      "使用者應遵守中華民國相關法令，不得利用本平台從事違法行為。",
      "使用者不得提供虛假資料、惡意下單、干擾平台運作或侵害他人權益。",
      "因使用者提供錯誤地址或聯絡資訊所造成的損失，應由使用者自行負責。",
    ],
  },
  {
    id: "copyright",
    number: "06",
    title: "智慧財產權",
    paragraphs: [
      "本平台的商標、介面設計、文字、圖片及程式內容，均受智慧財產權相關法令保護。",
      "未經授權，不得擅自重製、修改、散布或作為商業用途。",
    ],
  },
  {
    id: "disclaimer",
    number: "07",
    title: "免責聲明",
    paragraphs: [
      "因天災、交通、網路中斷或其他不可抗力因素造成服務延遲，本平台不負損害賠償責任。",
      "合作商家提供的餐點內容、過敏原及食品品質，由商家依相關法令負責。",
    ],
  },
  {
    id: "changes",
    number: "08",
    title: "條款修改",
    paragraphs: [
      "本平台得依服務需要修改本使用條款，修改後的內容將公布於本頁面。",
      "使用者於條款修改後繼續使用本平台，即視為同意修改後的內容。",
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
      const visibleEntry = entries.find((entry) => entry.isIntersecting);

      if (visibleEntry) {
        activeSection.value = visibleEntry.target.id;
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
  <div class="terms-page">
    <section class="terms-page__header">
      <h1 class="terms-page__title">使用條款</h1>

      <p class="terms-page__subtitle">
        歡迎使用 MealGo，使用本平台前請詳閱以下條款
      </p>

      <p class="terms-page__updated">最後更新日期：2026 年 7 月 23 日</p>
    </section>

    <div class="terms-page__container">
      <aside class="terms-menu">
        <h2 class="terms-menu__title">條款目錄</h2>

        <nav class="terms-menu__nav" aria-label="使用條款目錄">
          <button
            v-for="section in sections"
            :key="section.id"
            type="button"
            class="terms-menu__item"
            :class="{
              'terms-menu__item--active': activeSection === section.id,
            }"
            @click="scrollToSection(section.id)"
          >
            <span class="terms-menu__number">
              {{ section.number }}
            </span>

            <span>{{ section.title }}</span>
          </button>
        </nav>
      </aside>

      <main class="terms-content">
        <section
          v-for="(section, index) in sections"
          :id="section.id"
          :key="section.id"
          class="terms-section"
        >
          <h2 class="terms-section__title">
            {{ index + 1 }}、{{ section.title }}
          </h2>

          <ol class="terms-section__list">
            <li
              v-for="paragraph in section.paragraphs"
              :key="paragraph"
              class="terms-section__item"
            >
              {{ paragraph }}
            </li>
          </ol>
        </section>

        <div class="terms-content__bottom">
          <div class="terms-notice">
            <i class="bi bi-info-circle terms-notice__icon"></i>

            <span> 使用 MealGo 服務，即表示你已閱讀並同意本使用條款。 </span>
          </div>

          <button
            type="button"
            class="terms-content__top-button"
            @click="scrollToTop"
          >
            回頂端
            <i class="bi bi-arrow-up"></i>
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<style scoped lang="scss">
.terms-page {
  min-height: calc(100vh - 80px);
  padding: 44px 0 56px;
  background: #fafafa;
  color: #202939;
}

.terms-page__header {
  margin-bottom: 24px;
  text-align: center;
}

.terms-page__title {
  margin: 0;
  color: #172033;
  font-size: 36px;
  font-weight: 700;
  line-height: 1.3;
}

.terms-page__subtitle {
  margin: 8px 0 0;
  color: #4b5563;
  font-size: 16px;
}

.terms-page__updated {
  margin: 10px 0 0;
  color: #9299a5;
  font-size: 14px;
}

.terms-page__container {
  display: grid;
  grid-template-columns: 240px minmax(0, 1fr);
  align-items: start;
  gap: 20px;

  width: min(100% - 64px, 1240px);
  margin: 0 auto;
}

.terms-menu {
  position: sticky;
  top: 96px;

  overflow: hidden;
  background: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 12px;
  box-shadow: 0 4px 14px rgb(0 0 0 / 5%);
}

.terms-menu__title {
  margin: 0;
  padding: 20px 22px 12px;
  color: #202939;
  font-size: 18px;
  font-weight: 700;
}

.terms-menu__nav {
  display: flex;
  flex-direction: column;
  padding-bottom: 10px;
}

.terms-menu__item {
  display: grid;
  grid-template-columns: 34px minmax(0, 1fr);
  align-items: center;

  width: 100%;
  min-height: 46px;
  padding: 0 20px;

  color: #3f4652;
  font-size: 15px;
  text-align: left;

  background: transparent;
  border: 0;
  border-left: 3px solid transparent;

  cursor: pointer;
  transition:
    color 0.2s,
    background-color 0.2s,
    border-color 0.2s;
}

.terms-menu__item:hover {
  color: #ff5f07;
  background: #fff8f4;
}

.terms-menu__item--active {
  color: #ff5f07;
  font-weight: 600;
  background: #fff2ea;
  border-left-color: #ff5f07;
}

.terms-menu__number {
  font-size: 13px;
}

.terms-content {
  padding: 8px 24px 16px;
  background: #ffffff;
  border: 1px solid #e8e8e8;
  border-radius: 12px;
  box-shadow: 0 4px 14px rgb(0 0 0 / 5%);
}

.terms-section {
  padding: 22px 0;
  scroll-margin-top: 104px;
  border-bottom: 1px solid #eceff3;
}

.terms-section:last-of-type {
  border-bottom: 0;
}

.terms-section__title {
  margin: 0 0 12px;
  color: #172033;
  font-size: 19px;
  font-weight: 700;
}

.terms-section__list {
  display: flex;
  flex-direction: column;
  gap: 7px;

  margin: 0;
  padding-left: 24px;
}

.terms-section__item {
  padding-left: 4px;
  color: #4b5563;
  font-size: 15px;
  line-height: 1.7;
}

.terms-content__bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding-top: 16px;
}

.terms-notice {
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

.terms-notice__icon {
  flex-shrink: 0;
  color: #ff5f07;
  font-size: 17px;
}

.terms-content__top-button {
  display: inline-flex;
  flex-shrink: 0;
  align-items: center;
  gap: 8px;

  min-height: 42px;
  padding: 0 16px;

  color: #ff5f07;
  font-size: 14px;
  font-weight: 600;

  background: #ffffff;
  border: 1px solid #ff5f07;
  border-radius: 8px;

  cursor: pointer;
  transition:
    color 0.2s,
    background-color 0.2s;
}

.terms-content__top-button:hover {
  color: #ffffff;
  background: #ff5f07;
}

@media (max-width: 991.98px) {
  .terms-page {
    padding: 32px 0 44px;
  }

  .terms-page__container {
    grid-template-columns: 1fr;
    width: min(100% - 32px, 820px);
  }

  .terms-menu {
    position: static;
  }

  .terms-menu__title {
    padding-bottom: 8px;
  }

  .terms-menu__nav {
    display: flex;
    flex-direction: row;
    gap: 8px;

    overflow-x: auto;
    padding: 0 16px 16px;
    scrollbar-width: none;
  }

  .terms-menu__nav::-webkit-scrollbar {
    display: none;
  }

  .terms-menu__item {
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

  .terms-menu__item--active {
    border-color: #ff5f07;
  }
}

@media (max-width: 575.98px) {
  .terms-page {
    padding: 24px 0 36px;
  }

  .terms-page__title {
    font-size: 28px;
  }

  .terms-page__subtitle {
    padding: 0 20px;
    font-size: 14px;
  }

  .terms-page__container {
    width: min(100% - 24px, 520px);
  }

  .terms-menu__title {
    font-size: 16px;
  }

  .terms-content {
    padding: 4px 16px 16px;
  }

  .terms-section {
    padding: 20px 0;
  }

  .terms-section__title {
    font-size: 17px;
  }

  .terms-section__item {
    font-size: 14px;
  }

  .terms-content__bottom {
    align-items: stretch;
    flex-direction: column;
  }

  .terms-content__top-button {
    justify-content: center;
  }
}
</style>
