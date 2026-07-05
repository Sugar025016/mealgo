import { createRouter, createWebHistory } from "vue-router";

import DefaultLayout from "@/layouts/DefaultLayout.vue";

import HomeView from "@/views/HomeView.vue";
// import ShopList from "@/pages/ShopList.vue";
// import ShopDetail from "@/pages/ShopDetail.vue";
// import Cart from "@/pages/Cart.vue";
// import Checkout from "@/pages/Checkout.vue";
// import Login from "@/pages/Login.vue";
// import Register from "@/pages/Register.vue";

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
        // {
        //   path: "shops",
        //   name: "shops",
        //   component: ShopList,
        // },
        // {
        //   path: "shops/:id",
        //   name: "shop-detail",
        //   component: ShopDetail,
        //   props: true,
        // },
        // {
        //   path: "cart",
        //   name: "cart",
        //   component: Cart,
        // },
        // {
        //   path: "checkout",
        //   name: "checkout",
        //   component: Checkout,
        // },
      ],
    },

    // 不使用 Header/Footer 的頁面
    // {
    //   path: "/login",
    //   name: "login",
    //   component: Login,
    // },
    // {
    //   path: "/register",
    //   name: "register",
    //   component: Register,
    // },
  ],

  scrollBehavior() {
    return {
      top: 0,
    };
  },
});

export default router;