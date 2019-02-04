import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OnCatalogSharedModule } from 'app/shared';
import {
    PoServiceComponent,
    PoServiceDetailComponent,
    PoServiceUpdateComponent,
    PoServiceDeletePopupComponent,
    PoServiceDeleteDialogComponent,
    poServiceRoute,
    poServicePopupRoute
} from './';

const ENTITY_STATES = [...poServiceRoute, ...poServicePopupRoute];

@NgModule({
    imports: [OnCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PoServiceComponent,
        PoServiceDetailComponent,
        PoServiceUpdateComponent,
        PoServiceDeleteDialogComponent,
        PoServiceDeletePopupComponent
    ],
    entryComponents: [PoServiceComponent, PoServiceUpdateComponent, PoServiceDeleteDialogComponent, PoServiceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogPoServiceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
