import { defineStore } from "pinia";
import { ref, watch } from "vue";

export const useThemeStore = defineStore("theme", () => {
  const theme = ref(localStorage.getItem("theme") || "theme-orange");

  watch(
    theme,
    (value) => {
      document.documentElement.className = value;
      localStorage.setItem("theme", value);
    },
    { immediate: true },
  );

  //   function setTheme(value: string) {
  //     theme.value = value;
  //   }
  function setTheme(theme: "theme-orange" | "theme-dark") {
    document.documentElement.classList.remove("theme-orange", "theme-dark");
    document.documentElement.classList.add(theme);
    localStorage.setItem("theme", theme);
  }

  return {
    theme,
    setTheme,
  };
});
