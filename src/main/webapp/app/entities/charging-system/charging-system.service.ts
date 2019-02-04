import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChargingSystem } from 'app/shared/model/charging-system.model';

type EntityResponseType = HttpResponse<IChargingSystem>;
type EntityArrayResponseType = HttpResponse<IChargingSystem[]>;

@Injectable({ providedIn: 'root' })
export class ChargingSystemService {
    public resourceUrl = SERVER_API_URL + 'api/charging-systems';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/charging-systems';

    constructor(protected http: HttpClient) {}

    create(chargingSystem: IChargingSystem): Observable<EntityResponseType> {
        return this.http.post<IChargingSystem>(this.resourceUrl, chargingSystem, { observe: 'response' });
    }

    update(chargingSystem: IChargingSystem): Observable<EntityResponseType> {
        return this.http.put<IChargingSystem>(this.resourceUrl, chargingSystem, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IChargingSystem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChargingSystem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChargingSystem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
