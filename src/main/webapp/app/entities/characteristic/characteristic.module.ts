import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OnCatalogSharedModule } from 'app/shared';
import {
    CharacteristicComponent,
    CharacteristicDetailComponent,
    CharacteristicUpdateComponent,
    CharacteristicDeletePopupComponent,
    CharacteristicDeleteDialogComponent,
    characteristicRoute,
    characteristicPopupRoute
} from './';

const ENTITY_STATES = [...characteristicRoute, ...characteristicPopupRoute];

@NgModule({
    imports: [OnCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CharacteristicComponent,
        CharacteristicDetailComponent,
        CharacteristicUpdateComponent,
        CharacteristicDeleteDialogComponent,
        CharacteristicDeletePopupComponent
    ],
    entryComponents: [
        CharacteristicComponent,
        CharacteristicUpdateComponent,
        CharacteristicDeleteDialogComponent,
        CharacteristicDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogCharacteristicModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
