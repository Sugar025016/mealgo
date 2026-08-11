import { createRouter, createWebHistory } from "vue-router";

import DefaultLayout from "@/layouts/DefaultLayout.vue";
import MemberLayout from "@/layouts/MemberLayout.vue";

import HomeView from "@/views/HomeView.vue";
import ShopsView from "@/views/ShopsView.vue";
import ShopDetailView from "@/views/ShopDetailView.vue";
import CheckoutView from "@/views/CheckoutView.vue";
// import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import OrderView from "@/views/OrderView.vue";
import ProfileView from "@/views/member/ProfileView.vue";
import FavoriteView from "@/views/member/FavoriteView.vue";
import OrderHistoryView from "@/views/member/OrderHistoryView.vue";
import AddressView from "@/views/member/AddressView.vue";
import ReviewView from "@/views/member/ReviewView.vue";
import OrderDetailView from "@/views/OrderDetailView.vue";

// import MemberLoginView from "@/views/AuthLayout/MemberLoginView.vue";

import MemberLoginView from "@/views/auth/MemberLoginView.vue";
import MemberRegisterView from "@/views/auth/MemberRegisterView.vue";
import MerchantLoginView from "@/views/auth/MerchantLoginView.vue";
import MerchantRegisterView from "@/views/auth/MerchantRegisterView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: "/",
      component: DefaultLayout,

      children: [
        {
          path: "",
          name: "home",
          component: HomeView,
        },
        {
          path: "shops",
          name: "shops",
          component: ShopsView,
        },
        {
          path: "shops/:id",
          name: "shop-detail",
          component: ShopDetailView,
          props: true,
        },
        {
          path: "checkout/:id",
          name: "checkout",
          component: CheckoutView,
          props: true,
        },
        {
          path: "OrderDetail/:orderId",
          name: "OrderDetail",
          component: OrderDetailView,
          props: true,
        },
        {
          path: "member",
          component: MemberLayout,

          children: [
            {
              path: "",
              redirect: {
                name: "member-profile",
              },
            },
            {
              path: "profile",
              name: "member-profile",
              component: ProfileView,
            },
            // {
            //   path: "profile",
            //   name: "member-profile",
            //   component: ProfileView,
            // },
            {
              path: "orders",
              name: "member-orders",
              component: OrderHistoryView,
            },
            {
              path: "favorites",
              name: "member-favorites",
              component: FavoriteView,
            },
            {
              path: "addresses",
              name: "member-addresses",
              component: AddressView,
            },
            {
              path: "reviews",
              name: "member-reviews",
              component: ReviewView,
            },
            // {
            //   path: "settings",
            //   name: "member-settings",
            //   component: AccountSettingsView,
            // },
          ],
        },
        {
          path: "/about",
          name: "about",
          component: () => import("@/views/info/AboutView.vue"),
        },
        {
          path: "/terms",
          name: "terms",
          component: () => import("@/views/info/TermsView.vue"),
        },
        {
          path: "/privacy",
          name: "privacy",
          component: () => import("@/views/info/PrivacyView.vue"),
        },
      ],
    },

    // 不使用 Header/Footer 的頁面
    {
      path: "/login",
      name: "login",
      component: MemberLoginView,
    },
    {
      path: "/register",
      name: "register",
      component: MemberRegisterView,
    },
    {
      path: "/merchant-login",
      name: "merchant-login",
      component: MerchantLoginView,
    },
    {
      path: "/merchant-register",
      name: "merchant-register",
      component: MerchantRegisterView,
    },
    {
      path: "/order",
      name: "order",
      component: OrderView,
    },
  ],

  scrollBehavior() {
    return {
      top: 0,
    };
  },
});

export default router;
