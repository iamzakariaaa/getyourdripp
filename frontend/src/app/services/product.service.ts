import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { StorageService } from './storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/v1/products';
  
  constructor(private storageService: StorageService, private http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<Product[]>(this.baseUrl, { headers });
  }

  getProductById(id: number): Observable<Product> {
    const headers = this.createAuthHeaders();
    return this.http.get<Product>(`${this.baseUrl}/${id}`, { headers });
  }

  getProductImage(productId: number): Observable<Blob> {
    const headers = this.createAuthHeaders();
    return this.http.get(`${this.baseUrl}/${productId}/image`, { headers, responseType: 'blob' });
  }
  
  addProduct(productData: any, file: File): Observable<Product> {
    const headers = this.createAuthHeaders();
    const formData = new FormData();
    formData.append('file', file);
    formData.append('name', productData.name);
    formData.append('description', productData.description);
    formData.append('price', productData.price);
    formData.append('category', productData.category);
    formData.append('units', productData.units);
    
    return this.http.post<Product>(this.baseUrl, formData, { headers });
  }

  updateProduct(id: number, productData: any): Observable<Product> {
    const headers = this.createAuthHeaders();
    return this.http.put<Product>(`${this.baseUrl}/${id}`, productData, { headers });
  }
  
  deleteProduct(id: number): Observable<void> {
    const headers = this.createAuthHeaders();
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }

  private createAuthHeaders(): HttpHeaders {
    const token = this.storageService.getToken();
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }
}

