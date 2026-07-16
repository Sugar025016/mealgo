<script setup lang="ts">
export type ReviewStatus = "PENDING" | "REVIEWED";

export type ReviewItem = {
  id: number;
  shopName: string;
  shopImage: string;
  orderDate: string;
  orderNumber: string;
  products: string[];
  status: ReviewStatus;
  rating?: number;
  content?: string;
  shopReply?: string;
};

const props = defineProps<{
  review: ReviewItem;
}>();

const emit = defineEmits<{
  review: [review: ReviewItem];
  edit: [review: ReviewItem];
  reorder: [review: ReviewItem];
}>();

function handlePrimaryAction() {
  if (props.review.status === "PENDING") {
    emit("review", props.review);
    return;
  }

  emit("edit", props.review);
}
</script>

<template>
  <article class="review-card">
    <img
      :src="review.shopImage"
      :alt="review.shopName"
      class="review-card__image"
    />

    <div class="review-card__body">
      <div class="review-card__header">
        <div class="review-card__shop">
          <h2 class="review-card__title">
            {{ review.shopName }}
          </h2>

          <div class="review-card__order-info">
            <span>訂單日期：{{ review.orderDate }}</span>
            <span>訂單編號：{{ review.orderNumber }}</span>
          </div>
        </div>

        <div class="review-card__actions">
          <button
            type="button"
            class="review-card__button review-card__button--outline"
            @click="handlePrimaryAction"
          >
            {{
              review.status === "PENDING"
                ? "立即評價"
                : "編輯評價"
            }}
          </button>

          <button
            type="button"
            class="review-card__button review-card__button--primary"
            @click="emit('reorder', review)"
          >
            再次訂購
          </button>
        </div>
      </div>

      <template v-if="review.status === 'REVIEWED'">
        <div
          class="review-card__rating"
          :aria-label="`${review.rating ?? 0} 顆星`"
        >
          <i
            v-for="star in 5"
            :key="star"
            class="bi"
            :class="
              star <= (review.rating ?? 0)
                ? 'bi-star-fill'
                : 'bi-star'
            "
          />
        </div>
      </template>

      <div class="review-card__products">
        <span
          v-for="product in review.products"
          :key="product"
          class="review-card__product"
        >
          {{ product }}
        </span>
      </div>

      <template v-if="review.status === 'REVIEWED'">
        <p class="review-card__content">
          {{ review.content }}
        </p>

        <div
          v-if="review.shopReply"
          class="review-card__reply"
        >
          <div class="review-card__reply-icon">
            <i class="bi bi-shop" />
          </div>

          <div class="review-card__reply-content">
            <strong>店家回覆</strong>
            <p>{{ review.shopReply }}</p>
          </div>
        </div>
      </template>

      <div
        v-else
        class="review-card__pending"
      >
        <i class="bi bi-star" />

        <div>
          <strong>你尚未留下評價</strong>
          <p>你的回饋能幫助其他顧客做出更好的選擇。</p>
        </div>
      </div>
    </div>
  </article>
</template>

<style scoped lang="scss">
.review-card {
  display: grid;
  grid-template-columns: 160px minmax(0, 1fr);
  gap: 20px;

  padding: 20px;
  border: 1px solid #e8e8e8;
  border-radius: 14px;
  background: #fff;
  box-shadow: 0 4px 16px rgb(0 0 0 / 4%);
}

.review-card__image {
  width: 160px;
  height: 160px;
  border-radius: 12px;
  object-fit: cover;
}

.review-card__body {
  min-width: 0;
}

.review-card__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 20px;
}

.review-card__shop {
  min-width: 0;
}

.review-card__title {
  margin: 0 0 8px;
  color: #252525;
  font-size: 22px;
  font-weight: 700;
  line-height: 1.35;
}

.review-card__order-info {
  display: flex;
  flex-wrap: wrap;
  gap: 4px 18px;

  color: #777;
  font-size: 14px;
}

.review-card__actions {
  display: flex;
  flex: 0 0 auto;
  gap: 10px;
}

.review-card__button {
  min-width: 104px;
  height: 42px;
  padding: 0 18px;

  border: 1px solid transparent;
  border-radius: 9px;

  font-size: 15px;
  font-weight: 700;

  cursor: pointer;
  transition:
    border-color 0.2s ease,
    background-color 0.2s ease,
    color 0.2s ease,
    transform 0.2s ease;
}

.review-card__button:hover {
  transform: translateY(-1px);
}

.review-card__button--outline {
  border-color: #ff5f07;
  background: #fff;
  color: #ff5f07;
}

.review-card__button--outline:hover {
  background: #fff2ea;
}

.review-card__button--primary {
  background: #ff5f07;
  color: #fff;
}

.review-card__button--primary:hover {
  background: #e65500;
}

.review-card__rating {
  display: flex;
  gap: 8px;
  margin-top: 14px;

  color: #ff5f07;
  font-size: 22px;
}

.review-card__rating .bi-star {
  color: #b8b8b8;
}

.review-card__products {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 14px;
}

.review-card__product {
  padding: 5px 12px;
  border: 1px solid #e4e4e4;
  border-radius: 999px;

  background: #f8f8f8;
  color: #555;

  font-size: 14px;
  line-height: 1.4;
}

.review-card__content {
  margin: 14px 0 0;
  color: #444;
  font-size: 15px;
  line-height: 1.75;
}

.review-card__reply {
  display: flex;
  gap: 12px;

  margin-top: 16px;
  padding: 14px 16px;

  border: 1px solid #ffd6bd;
  border-radius: 10px;
  background: #fff8f4;
}

.review-card__reply-icon {
  display: grid;
  flex: 0 0 38px;
  width: 38px;
  height: 38px;
  place-items: center;

  border: 1px solid #ffd6bd;
  border-radius: 50%;
  background: #fff;
  color: #ff5f07;
  font-size: 18px;
}

.review-card__reply-content {
  min-width: 0;
}

.review-card__reply-content strong {
  display: block;
  margin-bottom: 4px;
  color: #ff5f07;
  font-size: 15px;
}

.review-card__reply-content p {
  margin: 0;
  color: #555;
  font-size: 14px;
  line-height: 1.65;
}

.review-card__pending {
  display: flex;
  align-items: center;
  gap: 12px;

  margin-top: 16px;
  padding: 14px 16px;

  border: 1px solid #ededed;
  border-radius: 10px;
  background: #fafafa;
}

.review-card__pending > i {
  color: #a4a4a4;
  font-size: 24px;
}

.review-card__pending strong {
  display: block;
  margin-bottom: 3px;
  color: #444;
  font-size: 15px;
}

.review-card__pending p {
  margin: 0;
  color: #888;
  font-size: 13px;
  line-height: 1.5;
}

@media (max-width: 991.98px) {
  .review-card {
    grid-template-columns: 130px minmax(0, 1fr);
    gap: 16px;
    padding: 16px;
  }

  .review-card__image {
    width: 130px;
    height: 130px;
  }

  .review-card__header {
    display: block;
  }

  .review-card__actions {
    margin-top: 14px;
  }
}

@media (max-width: 767.98px) {
  .review-card {
    grid-template-columns: 96px minmax(0, 1fr);
    gap: 12px;
    padding: 14px;
  }

  .review-card__image {
    width: 96px;
    height: 96px;
  }

  .review-card__title {
    font-size: 18px;
  }

  .review-card__order-info {
    display: grid;
    gap: 2px;
    font-size: 12px;
  }

  .review-card__actions {
    display: grid;
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .review-card__button {
    min-width: 0;
    height: 38px;
    padding: 0 10px;
    font-size: 13px;
  }

  .review-card__rating {
    gap: 5px;
    margin-top: 12px;
    font-size: 18px;
  }

  .review-card__products {
    margin-top: 10px;
  }

  .review-card__product {
    padding: 4px 9px;
    font-size: 12px;
  }

  .review-card__content {
    font-size: 14px;
  }

  .review-card__reply,
  .review-card__pending {
    grid-column: 1 / -1;
  }
}

@media (max-width: 575.98px) {
  .review-card {
    display: block;
  }

  .review-card__image {
    width: 100%;
    height: 180px;
    margin-bottom: 14px;
  }

  .review-card__actions {
    width: 100%;
  }
}
</style>