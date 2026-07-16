<script setup lang="ts">
function goToMerchantRegister() {
  window.location.href = "/merchant/register";
}
</script>

<template>
  <aside class="merchant-banner">
    <img
      class="merchant-banner__image"
      src="@/assets/images/open-shop3.png"
      alt="開店賺外快，成為 MealGo2 合作商家"
    />

    <button
      type="button"
      class="merchant-banner__button"
      @click="goToMerchantRegister"
    >
      <span>立即開店</span>
      <i class="bi bi-chevron-right"></i>
    </button>
  </aside>
</template>

<style scoped lang="scss">
.merchant-banner {
  position: relative;
  width: 100%;
  overflow: hidden;
  border-radius: 16px;
  background: #fff8f2;
  box-shadow: 0 4px 18px rgb(0 0 0 / 6%);
}

.merchant-banner__image {
  display: block;
  width: 100%;
  height: auto;
  
  //   object-fit: cover;
//   margin-top: -2px;
}

.merchant-banner__button {
  position: absolute;
  left: 10%;
  right: 10%;
  bottom: 6%;

  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;

  min-height: 36px;
  padding: 6px 16px;

  border: 0;
  border-radius: 10px;
  background: #ff5f07;
  color: #fff;

  font-size: 18px;
  font-weight: 700;

  cursor: pointer;
  transition:
    background-color 0.2s ease,
    transform 0.2s ease,
    box-shadow 0.2s ease;
}

.merchant-banner__button:hover {
  background: #e65500;
  box-shadow: 0 6px 16px rgb(255 95 7 / 25%);
  transform: translateY(-2px);
}

.merchant-banner__button:active {
  background: #cc4d00;
  transform: translateY(0);
}

.merchant-banner__button i {
  font-size: 18px;
}

// @media (max-width: 1199.98px) {
//   .merchant-banner__button {
//     min-height: 36px;
//     font-size: 16px;
//   }
// }
</style>
