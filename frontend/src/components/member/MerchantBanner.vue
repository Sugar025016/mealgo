<script setup lang="ts">
import MemberSidebar from "@/components/member/MemberSidebar.vue";
import OpenShopBanner from "@/components/member/OpenShopBanner.vue";
</script>

<template>
  <div class="member-left-panel">
    <MemberSidebar />
    <!-- <OpenShopBanner /> -->
  </div>
</template>

<style scoped lang="scss">
.member-left-panel {
  display: flex;
  flex: 0 0 240px;
  flex-direction: column;
  gap: 20px;
  width: 240px;
}

@media (max-width: 991.98px) {
  .member-left-panel {
    flex: none;
    width: 100%;
  }
}
</style>
