/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { BasicPOUpdateComponent } from 'app/entities/basic-po/basic-po-update.component';
import { BasicPOService } from 'app/entities/basic-po/basic-po.service';
import { BasicPO } from 'app/shared/model/basic-po.model';

describe('Component Tests', () => {
    describe('BasicPO Management Update Component', () => {
        let comp: BasicPOUpdateComponent;
        let fixture: ComponentFixture<BasicPOUpdateComponent>;
        let service: BasicPOService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [BasicPOUpdateComponent]
            })
                .overrideTemplate(BasicPOUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BasicPOUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BasicPOService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BasicPO('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.basicPO = entity;
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
                    const entity = new BasicPO();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.basicPO = entity;
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
