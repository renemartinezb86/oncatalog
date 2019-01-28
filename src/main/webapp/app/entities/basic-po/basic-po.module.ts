import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OnCatalogSharedModule } from 'app/shared';
import {
    BasicPOComponent,
    BasicPODetailComponent,
    BasicPOUpdateComponent,
    BasicPODeletePopupComponent,
    BasicPODeleteDialogComponent,
    basicPORoute,
    basicPOPopupRoute
} from './';

const ENTITY_STATES = [...basicPORoute, ...basicPOPopupRoute];

@NgModule({
    imports: [OnCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BasicPOComponent,
        BasicPODetailComponent,
        BasicPOUpdateComponent,
        BasicPODeleteDialogComponent,
        BasicPODeletePopupComponent
    ],
    entryComponents: [BasicPOComponent, BasicPOUpdateComponent, BasicPODeleteDialogComponent, BasicPODeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogBasicPOModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
