import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OnCatalogSharedModule } from 'app/shared';
import {
    BSCSComponent,
    BSCSDetailComponent,
    BSCSUpdateComponent,
    BSCSDeletePopupComponent,
    BSCSDeleteDialogComponent,
    bSCSRoute,
    bSCSPopupRoute
} from './';

const ENTITY_STATES = [...bSCSRoute, ...bSCSPopupRoute];

@NgModule({
    imports: [OnCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BSCSComponent, BSCSDetailComponent, BSCSUpdateComponent, BSCSDeleteDialogComponent, BSCSDeletePopupComponent],
    entryComponents: [BSCSComponent, BSCSUpdateComponent, BSCSDeleteDialogComponent, BSCSDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogBSCSModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
