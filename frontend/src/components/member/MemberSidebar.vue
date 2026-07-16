<script setup lang="ts">
import OpenShopBanner from "@/components/member/OpenShopBanner.vue";
type MenuItem = {
  label: string;
  icon: string;
  to: string;
};

const menuItems: MenuItem[] = [
  {
    label: "個人資料",
    icon: "bi-person",
    to: "/member/profile",
  },
  {
    label: "歷史訂單",
    icon: "bi-receipt",
    to: "/member/orders",
  },
  {
    label: "我的最愛",
    icon: "bi-heart",
    to: "/member/favorites",
  },
  {
    label: "我的地址",
    icon: "bi-geo-alt",
    to: "/member/addresses",
  },
  {
    label: "我的評價",
    icon: "bi-chat-square-text",
    to: "/member/reviews",
  },
//   {
//     label: "帳戶設定",
//     icon: "bi-gear",
//     to: "/member/settings",
//   },
];
</script>

<template>
  <aside class="member-sidebar">
    <div class="member-sidebar__title">
      <i class="bi bi-person-circle"></i>
      <span>會員中心</span>
    </div>

    <nav class="member-sidebar__nav" aria-label="會員中心選單">
      <RouterLink
        v-for="item in menuItems"
        :key="item.to"
        :to="item.to"
        class="member-sidebar__link"
      >
        <i class="bi" :class="item.icon"></i>
        <span>{{ item.label }}</span>
        <i class="bi bi-chevron-right member-sidebar__arrow"></i>
      </RouterLink>
    </nav>

    <button type="button" class="member-sidebar__logout">
      <i class="bi bi-box-arrow-right"></i>
      <span>登出</span>
    </button>
    <div class="member-sidebar__open-shop-banner"><OpenShopBanner /></div>
  </aside>
</template>

<style scoped lang="scss">
.member-sidebar {
  width: 240px;
  overflow: hidden;
  flex-shrink: 0;

  border: 1px solid #eeeeee;
  border-radius: 14px;
  background: #ffffff;
  box-shadow: 0 4px 18px rgb(0 0 0 / 4%);
}

.member-sidebar__title {
  display: flex;
  align-items: center;
  gap: 10px;

  min-height: 64px;
  padding: 0 20px;

  border-bottom: 1px solid #eeeeee;

  color: #333333;
  font-size: 19px;
  font-weight: 700;
}

.member-sidebar__title i {
  color: #ff5f07;
  font-size: 24px;
}

.member-sidebar__nav {
  padding: 10px;
}

.member-sidebar__link {
  display: flex;
  align-items: center;
  gap: 12px;

  min-height: 50px;
  padding: 0 14px;

  border-radius: 9px;

//   color: #555555;
  color: #343434;
  font-size: 16px;
  font-weight: 500;
  text-decoration: none;

  transition:
    background-color 0.2s ease,
    color 0.2s ease;
}

.member-sidebar__link:hover {
  background: #fff2ea;
  color: #ff5f07;
}

.member-sidebar__link.router-link-active {
  background: #fff2ea;
  color: #ff5f07;
  font-weight: 700;
}

.member-sidebar__link > i:first-child {
  width: 22px;
  font-size: 19px;
  text-align: center;
}

.member-sidebar__arrow {
  margin-left: auto;
  color: #aaaaaa;
  font-size: 13px;
}

.member-sidebar__link.router-link-active .member-sidebar__arrow,
.member-sidebar__link:hover .member-sidebar__arrow {
  color: #ff5f07;
}

.member-sidebar__logout {
  display: flex;
  align-items: center;
  gap: 12px;

  width: calc(100% - 20px);
  min-height: 48px;
  margin: 0 10px 10px;
  padding: 0 14px;

  border: 0;
  border-top: 1px solid #eeeeee;
  border-bottom: 1px solid #eeeeee;
  border-radius: 9px;
  background: transparent;
  color: #777777;

  font-size: 16px;
  font-weight: 500;
  text-align: left;

  cursor: pointer;

  transition:
    background-color 0.2s ease,
    color 0.2s ease;
}

.member-sidebar__logout:hover {
  background: #fff1f1;
  color: #dc3545;
}

.member-sidebar__logout i {
  width: 22px;
  font-size: 19px;
  text-align: center;
}

.member-sidebar__open-shop-banner{
    margin:18px 12px 12px;
}

@media (max-width: 991.98px) {
  .member-sidebar {
    width: 100%;
  }

  .member-sidebar__nav {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 575.98px) {
  .member-sidebar__nav {
    grid-template-columns: 1fr;
  }
}
</style>
