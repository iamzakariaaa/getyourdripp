import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Order } from '../../../models/order';
import { OrderService } from '../../../services/order.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent {
  orders: any[] = [];
  selectedOrder: any = null;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.orderService.getOrders().subscribe({
      next: (data) => {
        console.log('Orders received:', data);
        this.orders = data;
      },
      error: (err) => {
        console.error('Error fetching orders:', err);
      }
    });
  }

  openModal(order: any): void {
    this.selectedOrder = order;
  }

  closeModal(): void {
    this.selectedOrder = null;
  }
}