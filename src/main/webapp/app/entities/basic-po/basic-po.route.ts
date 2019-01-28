import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from './basic-po.service';
import { BasicPOComponent } from './basic-po.component';
import { BasicPODetailComponent } from './basic-po-detail.component';
import { BasicPOUpdateComponent } from './basic-po-update.component';
import { BasicPODeletePopupComponent } from './basic-po-delete-dialog.component';
import { IBasicPO } from 'app/shared/model/basic-po.model';

@Injectable({ providedIn: 'root' })
export class BasicPOResolve implements Resolve<IBasicPO> {
    constructor(private service: BasicPOService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBasicPO> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BasicPO>) => response.ok),
                map((basicPO: HttpResponse<BasicPO>) => basicPO.body)
            );
        }
        return of(new BasicPO());
    }
}

export const basicPORoute: Routes = [
    {
        path: '',
        component: BasicPOComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.basicPO.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BasicPODetailComponent,
        resolve: {
            basicPO: BasicPOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.basicPO.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BasicPOUpdateComponent,
        resolve: {
            basicPO: BasicPOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.basicPO.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BasicPOUpdateComponent,
        resolve: {
            basicPO: BasicPOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.basicPO.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const basicPOPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BasicPODeletePopupComponent,
        resolve: {
            basicPO: BasicPOResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.basicPO.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
