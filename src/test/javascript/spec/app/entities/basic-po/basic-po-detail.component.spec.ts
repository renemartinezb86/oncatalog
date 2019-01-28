/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { BasicPODetailComponent } from 'app/entities/basic-po/basic-po-detail.component';
import { BasicPO } from 'app/shared/model/basic-po.model';

describe('Component Tests', () => {
    describe('BasicPO Management Detail Component', () => {
        let comp: BasicPODetailComponent;
        let fixture: ComponentFixture<BasicPODetailComponent>;
        const route = ({ data: of({ basicPO: new BasicPO('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [BasicPODetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BasicPODetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BasicPODetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.basicPO).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
