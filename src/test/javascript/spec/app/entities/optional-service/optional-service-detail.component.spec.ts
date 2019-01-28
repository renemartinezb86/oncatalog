/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { OptionalServiceDetailComponent } from 'app/entities/optional-service/optional-service-detail.component';
import { OptionalService } from 'app/shared/model/optional-service.model';

describe('Component Tests', () => {
    describe('OptionalService Management Detail Component', () => {
        let comp: OptionalServiceDetailComponent;
        let fixture: ComponentFixture<OptionalServiceDetailComponent>;
        const route = ({ data: of({ optionalService: new OptionalService('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [OptionalServiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OptionalServiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OptionalServiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.optionalService).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
