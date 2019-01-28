import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OnCatalogSharedModule } from 'app/shared';
import {
    OptionalServiceComponent,
    OptionalServiceDetailComponent,
    OptionalServiceUpdateComponent,
    OptionalServiceDeletePopupComponent,
    OptionalServiceDeleteDialogComponent,
    optionalServiceRoute,
    optionalServicePopupRoute
} from './';

const ENTITY_STATES = [...optionalServiceRoute, ...optionalServicePopupRoute];

@NgModule({
    imports: [OnCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OptionalServiceComponent,
        OptionalServiceDetailComponent,
        OptionalServiceUpdateComponent,
        OptionalServiceDeleteDialogComponent,
        OptionalServiceDeletePopupComponent
    ],
    entryComponents: [
        OptionalServiceComponent,
        OptionalServiceUpdateComponent,
        OptionalServiceDeleteDialogComponent,
        OptionalServiceDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogOptionalServiceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
