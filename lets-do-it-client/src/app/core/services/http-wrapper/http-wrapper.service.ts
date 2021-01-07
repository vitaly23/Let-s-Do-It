import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const defaultHttpOptions: HttpHeaders = new HttpHeaders(
  { 'Content-Type':'application/json; charset=UTF-8'}
  );

@Injectable({
  providedIn: 'root'
})
export class HttpWrapperService {

  private fixedUrl: string;
  constructor(private httpClient: HttpClient) { }

  private transformUrl(url: string, params: string[]){
    if(!params){
      return url;
    }

    let fixedUrl = url;
    for (let index = 0; index < params.length; index++) {
      const param = params[index];
      fixedUrl = fixedUrl.replace(`#{${index}}#`, param);
    }

    return fixedUrl;
  }
  public getWithParams(url: string ,params: string[], options?: any): Observable<Object> {
    const fixedUrl = this.transformUrl(url, params);
    return this.httpClient.get(fixedUrl, 
      options ? Object.assign({}, defaultHttpOptions, options) : defaultHttpOptions);
  }

  public getWithQueryParams(url: string, params: any , options?: any): Observable<Object> {

    const httpParams: HttpParams = new HttpParams({
      fromObject: params
    })
    return this.httpClient.get(url, 
      options != null ? Object.assign({}, {params: httpParams}, options) : {params: httpParams});
 }

  public post(url: string, body: any, options?: any): Observable<Object> {
    return this.httpClient.post(url, body, 
      options ? Object.assign({}, defaultHttpOptions, options) : defaultHttpOptions);
  }

  public putWithParams(url: string, params: string[], body: any, options?: any): Observable<Object> {
    const fixedUrl = this.transformUrl(url, params);
    return this.httpClient.put(fixedUrl, body, 
      options ? Object.assign({}, defaultHttpOptions, options) : defaultHttpOptions);
  }

  public deleteWithParams(url: string, params: string[]): Observable<Object> {
    const fixedUrl = this.transformUrl(url, params);
    return this.httpClient.delete(fixedUrl);
  }

}
