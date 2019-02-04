/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { BSCSDetailComponent } from 'app/entities/bscs/bscs-detail.component';
import { BSCS } from 'app/shared/model/bscs.model';

describe('Component Tests', () => {
    describe('BSCS Management Detail Component', () => {
        let comp: BSCSDetailComponent;
        let fixture: ComponentFixture<BSCSDetailComponent>;
        const route = ({ data: of({ bSCS: new BSCS('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [BSCSDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BSCSDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BSCSDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bSCS).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
