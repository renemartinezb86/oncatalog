import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OnCatalogSharedModule } from 'app/shared';
import {
    ChargingSystemComponent,
    ChargingSystemDetailComponent,
    ChargingSystemUpdateComponent,
    ChargingSystemDeletePopupComponent,
    ChargingSystemDeleteDialogComponent,
    chargingSystemRoute,
    chargingSystemPopupRoute
} from './';

const ENTITY_STATES = [...chargingSystemRoute, ...chargingSystemPopupRoute];

@NgModule({
    imports: [OnCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChargingSystemComponent,
        ChargingSystemDetailComponent,
        ChargingSystemUpdateComponent,
        ChargingSystemDeleteDialogComponent,
        ChargingSystemDeletePopupComponent
    ],
    entryComponents: [
        ChargingSystemComponent,
        ChargingSystemUpdateComponent,
        ChargingSystemDeleteDialogComponent,
        ChargingSystemDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogChargingSystemModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
