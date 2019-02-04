/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { ChargingSystemUpdateComponent } from 'app/entities/charging-system/charging-system-update.component';
import { ChargingSystemService } from 'app/entities/charging-system/charging-system.service';
import { ChargingSystem } from 'app/shared/model/charging-system.model';

describe('Component Tests', () => {
    describe('ChargingSystem Management Update Component', () => {
        let comp: ChargingSystemUpdateComponent;
        let fixture: ComponentFixture<ChargingSystemUpdateComponent>;
        let service: ChargingSystemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [ChargingSystemUpdateComponent]
            })
                .overrideTemplate(ChargingSystemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChargingSystemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChargingSystemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ChargingSystem('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chargingSystem = entity;
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
                    const entity = new ChargingSystem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chargingSystem = entity;
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
