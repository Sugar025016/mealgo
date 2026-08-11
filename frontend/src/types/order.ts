export type FulfillmentType = "DELIVERY" | "PICKUP";

export type OrderStatus =
  | "PENDING"
  | "ACCEPTED"
  | "COOKING"
  | "READY_FOR_PICKUP"
  | "ON_THE_WAY"
  | "COMPLETED"
  | "CANCELLED"
  | "STORE_NOT_ACCEPT"
  | "STORE_REFUSED";

export type OrderItem = {
  id: number;
  name: string;
  image: string;
  price: number;
  qty: number;
  remark?: string;
};

export type OrderShop = {
  id: number;
  name: string;
  logo: string;
  address: string;
};

export type OrderReceiver = {
  name: string;
  phone: string;
  address: string;
};

export type OrderPayment = {
  method: string;
  detail?: string;
};

export type OrderAmounts = {
  subtotal: number;
  deliveryFee: number;
  discount: number;
  total: number;
};

export type OrderCourier = {
  id: number;
  name: string;
  phone: string;
  plateNumber: string;
  lat?: number;
  lng?: number;
};

export type Order = {
  id: number;
  orderNumber: string;
  status: OrderStatus;
  fulfillmentType: FulfillmentType;

  createdAt: string;
  estimatedArrival?: string;

  shop: OrderShop;
  receiver: OrderReceiver;
  payment: OrderPayment;
  amounts: OrderAmounts;
  items: OrderItem[];

  courier?: OrderCourier;

  orderNote?: string;
};