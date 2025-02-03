import { CartItem } from "./cart-item";

export class OrderItem {
  image: string;
  price: number;
  quantity: number;
  productId: number;

  constructor(cartItem: CartItem) {
      this.image = cartItem.image;
      this.quantity = cartItem.quantity;
      this.price = cartItem.price;
      this.productId = cartItem.id;
  }
  }
  