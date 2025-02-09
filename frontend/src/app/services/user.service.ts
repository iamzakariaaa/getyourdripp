import { Observable } from 'rxjs';
import { User } from '../models/user';
import { StorageService } from './storage.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/v1/users';

  constructor(private storageService: StorageService, private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    const token = this.storageService.getToken();
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.get<User[]>(`${this.baseUrl}`, { headers });
  }

  getUserByEmail(email: string): Observable<User> {
    const token = this.storageService.getToken();
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.get<User>(`${this.baseUrl}/email/${email}`, { headers });
  }
}
