/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { NetResourceUpdateComponent } from 'app/entities/net-resource/net-resource-update.component';
import { NetResourceService } from 'app/entities/net-resource/net-resource.service';
import { NetResource } from 'app/shared/model/net-resource.model';

describe('Component Tests', () => {
    describe('NetResource Management Update Component', () => {
        let comp: NetResourceUpdateComponent;
        let fixture: ComponentFixture<NetResourceUpdateComponent>;
        let service: NetResourceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [NetResourceUpdateComponent]
            })
                .overrideTemplate(NetResourceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NetResourceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NetResourceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new NetResource('123');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.netResource = entity;
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
                    const entity = new NetResource();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.netResource = entity;
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
