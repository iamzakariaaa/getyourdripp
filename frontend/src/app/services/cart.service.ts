import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { StorageService } from './storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class CartService {
  
  private cartUpdated = new Subject<void>();
  private baseUrl = 'http://localhost:8080/api/v1/products';
  constructor(private storageService : StorageService, private http: HttpClient) { }
 
  addToCart(product: any): void {
    let cartItems: any[] = JSON.parse(localStorage.getItem('cartItems') || '[]');
    const existingItem = cartItems.find(item => item.id === product.id);

    if (existingItem) {
      existingItem.quantity++;
      existingItem.amount = existingItem.quantity * product.price; 
    } else {
      cartItems.push({
        id: product.id,
        product: { ...product },
        quantity: 1,
        amount: product.price 
      });
    }

    localStorage.setItem('cartItems', JSON.stringify(cartItems));
    this.cartUpdated.next();
}


  getCartItems(): any[] {
    return JSON.parse(localStorage.getItem('cartItems') || '[]');
  }

  clearCart(): void {
    localStorage.removeItem('cartItems');
    this.cartUpdated.next();
  }

  updateCartItem(item: any): void {
    let cartItems: any[] = JSON.parse(localStorage.getItem('cartItems') || '[]');
    const index = cartItems.findIndex(cartItem => cartItem.id === item.id);

    if (index !== -1) {
      cartItems[index].quantity = item.quantity;
      localStorage.setItem('cartItems', JSON.stringify(cartItems));
    }
    this.cartUpdated.next();
  }

  getCartUpdated():Observable<void> {
    return this.cartUpdated.asObservable();
  }

  deleteCartItem(item: any): void {
    let cartItems: any[] = JSON.parse(localStorage.getItem('cartItems') || '[]');
    const index = cartItems.findIndex(cartItem => cartItem.id === item.id);

    if (index !== -1) {
      cartItems.splice(index, 1);
      localStorage.setItem('cartItems', JSON.stringify(cartItems));
    }
    this.cartUpdated.next();
  }
  
  getProductImage(productId: number): Observable<Blob> {
    const token = this.storageService.getToken();
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.get(`${this.baseUrl}/${productId}/image`, { headers, responseType: 'blob' });
  }
}
