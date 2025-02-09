import { Injectable } from '@angular/core';
import axios from 'axios';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
import handleRequest from '../helpers/handleRequest';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  
  private baseUrl = 'http://localhost:8080/api/v1/orders';
  constructor(private storageService : StorageService, private http: HttpClient) { }
  
  getOrders(): Observable<any[]> {
      const headers = this.createAuthHeaders();
      return this.http.get<any[]>(`${this.baseUrl}`, { headers });
    }
   private createAuthHeaders(): HttpHeaders {
      const token = this.storageService.getToken();
      return new HttpHeaders({ Authorization: `Bearer ${token}` });
    }
 
}
