import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBSCS } from 'app/shared/model/bscs.model';

type EntityResponseType = HttpResponse<IBSCS>;
type EntityArrayResponseType = HttpResponse<IBSCS[]>;

@Injectable({ providedIn: 'root' })
export class BSCSService {
    public resourceUrl = SERVER_API_URL + 'api/bscs';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/bscs';

    constructor(protected http: HttpClient) {}

    create(bSCS: IBSCS): Observable<EntityResponseType> {
        return this.http.post<IBSCS>(this.resourceUrl, bSCS, { observe: 'response' });
    }

    update(bSCS: IBSCS): Observable<EntityResponseType> {
        return this.http.put<IBSCS>(this.resourceUrl, bSCS, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IBSCS>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBSCS[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBSCS[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
