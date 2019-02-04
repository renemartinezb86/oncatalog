/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { PoServiceUpdateComponent } from 'app/entities/po-service/po-service-update.component';
import { PoServiceService } from 'app/entities/po-service/po-service.service';
import { PoService } from 'app/shared/model/po-service.model';

describe('Component Tests', () => {
    describe('PoService Management Update Component', () => {
        let comp: PoServiceUpdateComponent;
        let fixture: ComponentFixture<PoServiceUpdateComponent>;
        let service: PoServiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [PoServiceUpdateComponent]
            })
                .overrideTemplate(PoServiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PoServiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoServiceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PoService('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poService = entity;
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
                    const entity = new PoService();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poService = entity;
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
