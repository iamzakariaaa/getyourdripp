import { Product } from './product';

export class CartItem {
    id: number;
    name: string;
    image: string;
    price: number;
    quantity: number;

    constructor(product: Product) {
        this.id = product.id;
        this.name = product.name;
        this.image = product.image;
        this.price = product.price;
        this.quantity = 1;
    }
}