import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {
  private baseUrl = '/api/blacklist';

  constructor(private http: HttpClient) { }

  addTokenToBlacklist(sessionId: string, token: string, expireAt: Date): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + token);
    const body = {
      sessionId: sessionId,
      expireAt: expireAt.toISOString()
    };
    return this.http.post(this.baseUrl + '/addTokenToBlacklist', body, { headers });
  }
}
