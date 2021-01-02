import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpWrapperService {
private headers: HttpHeaders = new HttpHeaders(
  { 'Content-Type':'application/json'}
  );

  private fixedUrl: string;
  constructor(private httpClient: HttpClient) { }

  private transformUrl(url: string, params: any[]){

  }
  public getWithParams(url: string ,params: any[]): Observable<any> {

    return this.get(url,{ headers: this.headers, params: params});
  }

  public get(url: string, options?: any): Observable<any> {
    return this.httpClient.get(url, options ? options : this.headers);
  }

  public post(url: string, body: string, options?: any): Observable<any> {
    return this.httpClient.post(url, body, options ? options : this.headers);
  }

  public put(url: string, body: string, options?: any): Observable<any> {
    return this.httpClient.put(url, body, options ? options : this.headers);
  }

  public delete(url: string, options?: any): Observable<any> {
    return this.httpClient.delete(url, options ? options : this.headers);
  }

}
