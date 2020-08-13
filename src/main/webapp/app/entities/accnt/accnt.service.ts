import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAccnt } from 'app/shared/model/accnt.model';

type EntityResponseType = HttpResponse<IAccnt>;
type EntityArrayResponseType = HttpResponse<IAccnt[]>;

@Injectable({ providedIn: 'root' })
export class AccntService {
  public resourceUrl = SERVER_API_URL + 'api/accnts';

  constructor(protected http: HttpClient) {}

  create(accnt: IAccnt): Observable<EntityResponseType> {
    return this.http.post<IAccnt>(this.resourceUrl, accnt, { observe: 'response' });
  }

  update(accnt: IAccnt): Observable<EntityResponseType> {
    return this.http.put<IAccnt>(this.resourceUrl, accnt, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccnt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccnt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
