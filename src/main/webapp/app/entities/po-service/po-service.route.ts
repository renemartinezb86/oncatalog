import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PoService } from 'app/shared/model/po-service.model';
import { PoServiceService } from './po-service.service';
import { PoServiceComponent } from './po-service.component';
import { PoServiceDetailComponent } from './po-service-detail.component';
import { PoServiceUpdateComponent } from './po-service-update.component';
import { PoServiceDeletePopupComponent } from './po-service-delete-dialog.component';
import { IPoService } from 'app/shared/model/po-service.model';

@Injectable({ providedIn: 'root' })
export class PoServiceResolve implements Resolve<IPoService> {
    constructor(private service: PoServiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPoService> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PoService>) => response.ok),
                map((poService: HttpResponse<PoService>) => poService.body)
            );
        }
        return of(new PoService());
    }
}

export const poServiceRoute: Routes = [
    {
        path: '',
        component: PoServiceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'onCatalogApp.poService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PoServiceDetailComponent,
        resolve: {
            poService: PoServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.poService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PoServiceUpdateComponent,
        resolve: {
            poService: PoServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.poService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PoServiceUpdateComponent,
        resolve: {
            poService: PoServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.poService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poServicePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PoServiceDeletePopupComponent,
        resolve: {
            poService: PoServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.poService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
