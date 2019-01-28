/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { OptionalServiceUpdateComponent } from 'app/entities/optional-service/optional-service-update.component';
import { OptionalServiceService } from 'app/entities/optional-service/optional-service.service';
import { OptionalService } from 'app/shared/model/optional-service.model';

describe('Component Tests', () => {
    describe('OptionalService Management Update Component', () => {
        let comp: OptionalServiceUpdateComponent;
        let fixture: ComponentFixture<OptionalServiceUpdateComponent>;
        let service: OptionalServiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [OptionalServiceUpdateComponent]
            })
                .overrideTemplate(OptionalServiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OptionalServiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionalServiceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OptionalService('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.optionalService = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OptionalService();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.optionalService = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
