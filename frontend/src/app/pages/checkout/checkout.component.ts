import { CommonModule } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { forkJoin, Subscription } from 'rxjs';
import { User } from '../../models/user';
import { StorageService } from '../../services/storage.service';
import { UserService } from '../../services/user.service';
import { RouterLink } from '@angular/router';
import { Order } from '../../models/order';
import { Item } from '../../models/item';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit, OnDestroy {
  dropdownOpen: boolean = false;
  orderItems: any[] = [];
  tax: number = 21;
  subtotal: number = 0;
  totalPrice: number = 0;
  checkoutForm: FormGroup;
  checkoutSubscription?: Subscription;
  userSubscription?: Subscription;
  currentUser?: User;

  constructor(
    private cartService: CartService,
    private formBuilder: FormBuilder,
    private orderService: OrderService,
    private storageService: StorageService,
    private userService: UserService
  ) {
    this.checkoutForm = this.formBuilder.group({
      address: ['', Validators.required],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]]
    });
  }

  ngOnInit(): void {
    this.orderItems = this.cartService.getCartItems();
    this.calculateSubTotal();
    this.totalPrice = this.subtotal + this.tax;
    this.fetchUserByEmail();
  }

  ngOnDestroy(): void {
    this.checkoutSubscription?.unsubscribe();
    this.userSubscription?.unsubscribe();
  }

  fetchUserByEmail(): void {
    const userEmail = this.storageService.getUser()?.sub;
    if (userEmail) {
      this.userSubscription = this.userService.getUserByEmail(userEmail).subscribe({
        next: user => {
          this.currentUser = user;
          console.log('fetching user:', this.currentUser);
        },
        error: error => {
          console.error('Error fetching user:', error);
        }
      });
    }
  }

  calculateSubTotal(): void {
    this.subtotal = this.orderItems.reduce((total, item) => total + (item.amount * item.quantity), 0);
  }

  toggleDropdown(): void {
    this.dropdownOpen = !this.dropdownOpen;
  }

  submitOrder(): void {
    if (this.checkoutForm.valid && this.orderItems.length > 0) {
      const address = this.checkoutForm.get('address')?.value;
      const phoneNumber = this.checkoutForm.get('phoneNumber')?.value;

      const order: Order = {
        id: 0,
        address: address,
        phoneNumber: phoneNumber,
        createdAt: new Date(),
        totalAmount: this.totalPrice,
        status: 'PENDING',
        customer: this.currentUser ,
        items: []
      };

      this.orderService.addOrder(order).subscribe({
        next: response => {
          console.log('Order placed successfully:', response);
          this.addOrderItems(response);
        },
        error: error => {
          console.error('Error placing order:', error);
        }
      });
    } else {
      console.log("Please fill out the form");
    }
  }

  addOrderItems(order: Order): void {
    const addItemObservables = this.orderItems.map(item => {
      const orderItem: Item = {
        id: 0, 
        amount: item.amount,
        quantity: item.quantity,
        product: item.product,
        order: order 
      };
      return this.orderService.assignItemToOrder(order.id, orderItem);
    });

    this.checkoutSubscription = forkJoin(addItemObservables).subscribe({
      next: responses => {
        console.log('All order items added successfully:', responses);
        this.cartService.clearCart();
      },
      error: error => {
        console.error('Error adding one or more order items:', error);
      }
    });
  }
}
