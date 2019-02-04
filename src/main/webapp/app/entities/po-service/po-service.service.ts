import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPoService } from 'app/shared/model/po-service.model';

type EntityResponseType = HttpResponse<IPoService>;
type EntityArrayResponseType = HttpResponse<IPoService[]>;

@Injectable({ providedIn: 'root' })
export class PoServiceService {
    public resourceUrl = SERVER_API_URL + 'api/po-services';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/po-services';

    constructor(protected http: HttpClient) {}

    create(poService: IPoService): Observable<EntityResponseType> {
        return this.http.post<IPoService>(this.resourceUrl, poService, { observe: 'response' });
    }

    update(poService: IPoService): Observable<EntityResponseType> {
        return this.http.put<IPoService>(this.resourceUrl, poService, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IPoService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPoService[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPoService[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
