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
 constructor(){}
}