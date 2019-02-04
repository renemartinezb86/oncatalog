/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { BSCSUpdateComponent } from 'app/entities/bscs/bscs-update.component';
import { BSCSService } from 'app/entities/bscs/bscs.service';
import { BSCS } from 'app/shared/model/bscs.model';

describe('Component Tests', () => {
    describe('BSCS Management Update Component', () => {
        let comp: BSCSUpdateComponent;
        let fixture: ComponentFixture<BSCSUpdateComponent>;
        let service: BSCSService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [BSCSUpdateComponent]
            })
                .overrideTemplate(BSCSUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BSCSUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BSCSService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BSCS('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bSCS = entity;
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
                    const entity = new BSCS();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bSCS = entity;
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
