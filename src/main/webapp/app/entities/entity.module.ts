import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'catalog',
                loadChildren: './catalog/catalog.module#OnCatalogCatalogModule'
            },
            {
                path: 'basic-po',
                loadChildren: './basic-po/basic-po.module#OnCatalogBasicPOModule'
            },
            {
                path: 'characteristic',
                loadChildren: './characteristic/characteristic.module#OnCatalogCharacteristicModule'
            },
            {
                path: 'optional-service',
                loadChildren: './optional-service/optional-service.module#OnCatalogOptionalServiceModule'
            },
            {
                path: 'catalog',
                loadChildren: './catalog/catalog.module#OnCatalogCatalogModule'
            },
            {
                path: 'basic-po',
                loadChildren: './basic-po/basic-po.module#OnCatalogBasicPOModule'
            },
            {
                path: 'characteristic',
                loadChildren: './characteristic/characteristic.module#OnCatalogCharacteristicModule'
            },
            {
                path: 'optional-service',
                loadChildren: './optional-service/optional-service.module#OnCatalogOptionalServiceModule'
            },
            {
                path: 'catalog',
                loadChildren: './catalog/catalog.module#OnCatalogCatalogModule'
            },
            {
                path: 'basic-po',
                loadChildren: './basic-po/basic-po.module#OnCatalogBasicPOModule'
            },
            {
                path: 'characteristic',
                loadChildren: './characteristic/characteristic.module#OnCatalogCharacteristicModule'
            },
            {
                path: 'optional-service',
                loadChildren: './optional-service/optional-service.module#OnCatalogOptionalServiceModule'
            },
            {
                path: 'catalog',
                loadChildren: './catalog/catalog.module#OnCatalogCatalogModule'
            },
            {
                path: 'basic-po',
                loadChildren: './basic-po/basic-po.module#OnCatalogBasicPOModule'
            },
            {
                path: 'characteristic',
                loadChildren: './characteristic/characteristic.module#OnCatalogCharacteristicModule'
            },
            {
                path: 'optional-service',
                loadChildren: './optional-service/optional-service.module#OnCatalogOptionalServiceModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OnCatalogEntityModule {}
