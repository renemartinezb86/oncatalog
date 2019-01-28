import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOptionalService } from 'app/shared/model/optional-service.model';

type EntityResponseType = HttpResponse<IOptionalService>;
type EntityArrayResponseType = HttpResponse<IOptionalService[]>;

@Injectable({ providedIn: 'root' })
export class OptionalServiceService {
    public resourceUrl = SERVER_API_URL + 'api/optional-services';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/optional-services';

    constructor(protected http: HttpClient) {}

    create(optionalService: IOptionalService): Observable<EntityResponseType> {
        return this.http.post<IOptionalService>(this.resourceUrl, optionalService, { observe: 'response' });
    }

    update(optionalService: IOptionalService): Observable<EntityResponseType> {
        return this.http.put<IOptionalService>(this.resourceUrl, optionalService, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IOptionalService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOptionalService[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOptionalService[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
