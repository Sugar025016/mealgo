export type AddressType =
  | "HOME"
  | "COMPANY"
  | "SCHOOL"
  | "OTHER";

export type AddressItem = {
  id: number;
  label: string;
  type: AddressType;
  city: string;
  area: string;
  street: string;
  detail: string;
  contactName: string;
  phone: string;
  isDefault: boolean;
};

export type AddressFormData = Omit<AddressItem, "id">;