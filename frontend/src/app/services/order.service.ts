import { Injectable } from '@angular/core';
import axios from 'axios';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
import handleRequest from '../helpers/handleRequest';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  
  private baseUrl = 'http://localhost:8080/api/v1/orders';
  constructor(private storageService : StorageService) { }
  token = this.storageService.getToken();
  
  /* getAllOrders(): Observable<OrderDTO[]> {
    const headers = { Authorization: `Bearer ${this.token}` };
    return handleRequest(axios.get<OrderDTO[]>(`${this.baseUrl}`, {headers}));
  } */

 
}
