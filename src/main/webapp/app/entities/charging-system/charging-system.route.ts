import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from './charging-system.service';
import { ChargingSystemComponent } from './charging-system.component';
import { ChargingSystemDetailComponent } from './charging-system-detail.component';
import { ChargingSystemUpdateComponent } from './charging-system-update.component';
import { ChargingSystemDeletePopupComponent } from './charging-system-delete-dialog.component';
import { IChargingSystem } from 'app/shared/model/charging-system.model';

@Injectable({ providedIn: 'root' })
export class ChargingSystemResolve implements Resolve<IChargingSystem> {
    constructor(private service: ChargingSystemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChargingSystem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ChargingSystem>) => response.ok),
                map((chargingSystem: HttpResponse<ChargingSystem>) => chargingSystem.body)
            );
        }
        return of(new ChargingSystem());
    }
}

export const chargingSystemRoute: Routes = [
    {
        path: '',
        component: ChargingSystemComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'onCatalogApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ChargingSystemDetailComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ChargingSystemUpdateComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ChargingSystemUpdateComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chargingSystemPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ChargingSystemDeletePopupComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
