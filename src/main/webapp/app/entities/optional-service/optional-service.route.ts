import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OptionalService } from 'app/shared/model/optional-service.model';
import { OptionalServiceService } from './optional-service.service';
import { OptionalServiceComponent } from './optional-service.component';
import { OptionalServiceDetailComponent } from './optional-service-detail.component';
import { OptionalServiceUpdateComponent } from './optional-service-update.component';
import { OptionalServiceDeletePopupComponent } from './optional-service-delete-dialog.component';
import { IOptionalService } from 'app/shared/model/optional-service.model';

@Injectable({ providedIn: 'root' })
export class OptionalServiceResolve implements Resolve<IOptionalService> {
    constructor(private service: OptionalServiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOptionalService> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<OptionalService>) => response.ok),
                map((optionalService: HttpResponse<OptionalService>) => optionalService.body)
            );
        }
        return of(new OptionalService());
    }
}

export const optionalServiceRoute: Routes = [
    {
        path: '',
        component: OptionalServiceComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'onCatalogApp.optionalService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: OptionalServiceDetailComponent,
        resolve: {
            optionalService: OptionalServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.optionalService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: OptionalServiceUpdateComponent,
        resolve: {
            optionalService: OptionalServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.optionalService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: OptionalServiceUpdateComponent,
        resolve: {
            optionalService: OptionalServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.optionalService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const optionalServicePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: OptionalServiceDeletePopupComponent,
        resolve: {
            optionalService: OptionalServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.optionalService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
