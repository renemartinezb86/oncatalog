/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { ChargingSystemDetailComponent } from 'app/entities/charging-system/charging-system-detail.component';
import { ChargingSystem } from 'app/shared/model/charging-system.model';

describe('Component Tests', () => {
    describe('ChargingSystem Management Detail Component', () => {
        let comp: ChargingSystemDetailComponent;
        let fixture: ComponentFixture<ChargingSystemDetailComponent>;
        const route = ({ data: of({ chargingSystem: new ChargingSystem('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [ChargingSystemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChargingSystemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChargingSystemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chargingSystem).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
