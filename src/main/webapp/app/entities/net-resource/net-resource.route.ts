import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NetResource } from 'app/shared/model/net-resource.model';
import { NetResourceService } from './net-resource.service';
import { NetResourceComponent } from './net-resource.component';
import { NetResourceDetailComponent } from './net-resource-detail.component';
import { NetResourceUpdateComponent } from './net-resource-update.component';
import { NetResourceDeletePopupComponent } from './net-resource-delete-dialog.component';
import { INetResource } from 'app/shared/model/net-resource.model';

@Injectable({ providedIn: 'root' })
export class NetResourceResolve implements Resolve<INetResource> {
    constructor(private service: NetResourceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INetResource> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NetResource>) => response.ok),
                map((netResource: HttpResponse<NetResource>) => netResource.body)
            );
        }
        return of(new NetResource());
    }
}

export const netResourceRoute: Routes = [
    {
        path: '',
        component: NetResourceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'onCatalogApp.netResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: NetResourceDetailComponent,
        resolve: {
            netResource: NetResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.netResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: NetResourceUpdateComponent,
        resolve: {
            netResource: NetResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.netResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: NetResourceUpdateComponent,
        resolve: {
            netResource: NetResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.netResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const netResourcePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: NetResourceDeletePopupComponent,
        resolve: {
            netResource: NetResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.netResource.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
