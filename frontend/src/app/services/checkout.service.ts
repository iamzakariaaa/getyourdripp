import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Purchase } from '../models/purchase';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  private baseUrl = 'http://localhost:8080/api/v1/checkout/purchase'; 
  constructor(private httpClient: HttpClient, private storageService: StorageService) { }

  placeOrder(purchase: Purchase): Observable<any> {
    const token = this.storageService.getToken();
    const headers = { Authorization: `Bearer ${token}` }; 
    return this.httpClient.post<Purchase>(`${this.baseUrl}`, purchase, {headers});    
  }
}
