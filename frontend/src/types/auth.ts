export type AuthFeature = {
  icon: string;
  title: string;
  description: string;
};

export type LoginPayload = {
  email: string;
  password: string;
  rememberMe: boolean;
};

export type RegisterPayload = {
  name: string;
  email: string;
  password: string;
};

export type MerchantRegisterPayload = {
  shopName: string;
  ownerName: string;
  phone: string;
  email: string;
  password: string;
};
