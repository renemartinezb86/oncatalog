import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INetResource } from 'app/shared/model/net-resource.model';

type EntityResponseType = HttpResponse<INetResource>;
type EntityArrayResponseType = HttpResponse<INetResource[]>;

@Injectable({ providedIn: 'root' })
export class NetResourceService {
    public resourceUrl = SERVER_API_URL + 'api/net-resources';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/net-resources';

    constructor(protected http: HttpClient) {}

    create(netResource: INetResource): Observable<EntityResponseType> {
        return this.http.post<INetResource>(this.resourceUrl, netResource, { observe: 'response' });
    }

    update(netResource: INetResource): Observable<EntityResponseType> {
        return this.http.put<INetResource>(this.resourceUrl, netResource, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<INetResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INetResource[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INetResource[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
