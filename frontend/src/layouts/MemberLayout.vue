<script setup lang="ts">
import MemberSidebar from "@/components/member/MemberSidebar.vue";
</script>

<template>
  <div class="member-layout">
    <div class="member-layout__container">
      <aside class="member-layout__left">
        <MemberSidebar />
      </aside>

      <main class="member-layout__content">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped lang="scss">
.member-layout {
  min-height: calc(100vh - 80px);
  padding: 16px 0 20px;
  background: #fafafa;
}

.member-layout__container {
  display: flex;
  align-items: flex-start;
  gap: 16px;

  width: min(100% - 64px, 1440px);
  margin: 0 auto;
}

.member-layout__left {
  position: sticky;
  top: 89px;

  display: flex;
  flex: 0 0 240px;
  flex-direction: column;
  gap: 16px;
  width: 240px;

  max-height: calc(100vh - 112px);
  overflow-y: auto;
}

.member-layout__content {
  min-width: 0;
  flex: 1;
}

// @media (max-width: 991.98px) {
//   .member-layout__container {
//     flex-direction: column;
//     width: min(100% - 32px, 760px);
//   }

//   .member-layout__left {
//     position: static;

//     flex: none;
//     width: 100%;
//     max-height: none;
//     overflow: visible;
//   }

//   .member-layout__content {
//     width: 100%;
//   }
// }

@media (max-width: 991.98px) {
  .member-layout {
    padding: 16px 0 40px;
  }

  .member-layout__container {
    width: calc(100% - 24px);
  }

  .member-layout__left {
    display: none;
  }

  .member-layout__content {
    width: 100%;
  }
}
</style>