import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBasicPO } from 'app/shared/model/basic-po.model';

type EntityResponseType = HttpResponse<IBasicPO>;
type EntityArrayResponseType = HttpResponse<IBasicPO[]>;

@Injectable({ providedIn: 'root' })
export class BasicPOService {
    public resourceUrl = SERVER_API_URL + 'api/basic-pos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/basic-pos';

    constructor(protected http: HttpClient) {}

    create(basicPO: IBasicPO): Observable<EntityResponseType> {
        return this.http.post<IBasicPO>(this.resourceUrl, basicPO, { observe: 'response' });
    }

    update(basicPO: IBasicPO): Observable<EntityResponseType> {
        return this.http.put<IBasicPO>(this.resourceUrl, basicPO, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IBasicPO>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBasicPO[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBasicPO[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
