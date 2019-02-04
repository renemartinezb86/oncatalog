/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { PoServiceDetailComponent } from 'app/entities/po-service/po-service-detail.component';
import { PoService } from 'app/shared/model/po-service.model';

describe('Component Tests', () => {
    describe('PoService Management Detail Component', () => {
        let comp: PoServiceDetailComponent;
        let fixture: ComponentFixture<PoServiceDetailComponent>;
        const route = ({ data: of({ poService: new PoService('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [PoServiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PoServiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PoServiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.poService).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
