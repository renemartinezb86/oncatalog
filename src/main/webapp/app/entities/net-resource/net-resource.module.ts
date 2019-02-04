import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OnCatalogSharedModule } from 'app/shared';
import {
    NetResourceComponent,
    NetResourceDetailComponent,
    NetResourceUpdateComponent,
    NetResourceDeletePopupComponent,
    NetResourceDeleteDialogComponent,
    netResourceRoute,
    netResourcePopupRoute
} from './';

const ENTITY_STATES = [...netResourceRoute, ...netResourcePopupRoute];

@NgModule({
    imports: [OnCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NetResourceComponent,
        NetResourceDetailComponent,
        NetResourceUpdateComponent,
        NetResourceDeleteDialogComponent,
        NetResourceDeletePopupComponent
    ],
    entryComponents: [NetResourceComponent, NetResourceUpdateComponent, NetResourceDeleteDialogComponent, NetResourceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogNetResourceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
