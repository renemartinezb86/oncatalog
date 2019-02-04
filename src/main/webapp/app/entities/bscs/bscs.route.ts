import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BSCS } from 'app/shared/model/bscs.model';
import { BSCSService } from './bscs.service';
import { BSCSComponent } from './bscs.component';
import { BSCSDetailComponent } from './bscs-detail.component';
import { BSCSUpdateComponent } from './bscs-update.component';
import { BSCSDeletePopupComponent } from './bscs-delete-dialog.component';
import { IBSCS } from 'app/shared/model/bscs.model';

@Injectable({ providedIn: 'root' })
export class BSCSResolve implements Resolve<IBSCS> {
    constructor(private service: BSCSService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBSCS> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BSCS>) => response.ok),
                map((bSCS: HttpResponse<BSCS>) => bSCS.body)
            );
        }
        return of(new BSCS());
    }
}

export const bSCSRoute: Routes = [
    {
        path: '',
        component: BSCSComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'onCatalogApp.bSCS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BSCSDetailComponent,
        resolve: {
            bSCS: BSCSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.bSCS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BSCSUpdateComponent,
        resolve: {
            bSCS: BSCSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.bSCS.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BSCSUpdateComponent,
        resolve: {
            bSCS: BSCSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.bSCS.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bSCSPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BSCSDeletePopupComponent,
        resolve: {
            bSCS: BSCSResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'onCatalogApp.bSCS.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
