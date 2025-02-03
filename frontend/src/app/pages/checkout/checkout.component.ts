import { CommonModule } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CartService } from '../../services/cart.service';
import { User } from '../../models/user';
import { StorageService } from '../../services/storage.service';
import { UserService } from '../../services/user.service';
import { Router, RouterLink } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Order } from '../../models/order';
import { OrderItem } from '../../models/order-item';
import { Purchase } from '../../models/purchase';
import { Address } from '../../models/address';
import { CheckoutService } from '../../services/checkout.service';
import Swal from 'sweetalert2';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
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
  imageMap: Map<number, string> = new Map<number, string>();

  constructor(
    private cartService: CartService,
    private formBuilder: FormBuilder,
    private storageService: StorageService,
    private userService: UserService,
    private productService: ProductService,
    private checkoutService: CheckoutService,
    private router: Router
  ) {
    this.checkoutForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', Validators.required],
      street: ['', Validators.required],
      zipcode: ['', Validators.required],
      city: ['', Validators.required],
      address: ['']
    });
  }

  ngOnInit(): void {
    this.orderItems = this.cartService.getCartItems();
    this.calculateSubTotal();
    this.totalPrice = this.subtotal + this.tax;
    this.fetchUserByEmail();
    this.orderItems.forEach(item => {
      this.fetchProductImage(item.id);
    });
  }

  ngOnDestroy(): void {
    this.checkoutSubscription?.unsubscribe();
    this.userSubscription?.unsubscribe();
  }

  onSubmit(): void {
    if (this.checkoutForm.valid && this.currentUser) {
      const formValue = this.checkoutForm.value;

      // Create the Address object
      const shippingAddress: Address = {
        street: formValue.street,
        city: formValue.city,
        lineAddress: formValue.address,
        zipCode: formValue.zipcode
      };

      // Calculate total quantity and total amount for the Order
      const totalQuantity = this.orderItems.reduce((total, item) => total + item.quantity, 0);
      const totalAmount = this.orderItems.reduce((total, item) => total + (item.amount * item.quantity), 0);

      // Create the Order object
      const order: Order = {
        totalQuantity: totalQuantity,
        totalAmount: totalAmount
      };

      // Map CartItems to OrderItems
      const orderItems: OrderItem[] = this.orderItems.map(cartItem => new OrderItem(cartItem));

      // Create the Purchase object
      const purchase: Purchase = {
        user: this.currentUser, // Use the current logged-in user
        shippingAddress: shippingAddress,
        order: order,
        orderItems: orderItems,
        phoneNumber:formValue.phoneNumber
      };

      // Send the Purchase object to the backend using CheckoutService
      this.checkoutSubscription = this.checkoutService.placeOrder(purchase).subscribe({
        next: (response) => {
          console.log('Purchase successful', response);
  
          // Show success alert using SweetAlert2
          Swal.fire({
            title: 'Purchase Successful!',
            text: 'Your order has been placed successfully.',
            icon: 'success',
            confirmButtonText: 'OK'
          }).then(() => {
            this.router.navigate(['/store']); 
            this.cartService.clearCart(); 
          });
        },
        error: (error) => {
          console.error('Purchase failed', error);
  
          // Show error alert using SweetAlert2
          Swal.fire({
            title: 'Purchase Failed',
            text: 'There was an error processing your order. Please try again.',
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      });
    } else {
      // Show validation error alert using SweetAlert2
      Swal.fire({
        title: 'Form Invalid',
        text: 'Please fill out all required fields correctly.',
        icon: 'warning',
        confirmButtonText: 'OK'
      });
    }
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

  getImageUrl(productId: number): string | undefined {
    return this.imageMap.get(productId);
  }

  fetchProductImage(productId: number): void {
    this.productService.getProductImage(productId).subscribe({
      next: (imageData: any) => {
        const imageUrl = URL.createObjectURL(new Blob([imageData], { type: 'image/png' }));
        this.imageMap.set(productId, imageUrl);
      },
      error: (error: any) => {
        console.error('Error fetching product image:', error);
      }
    });
  }
}