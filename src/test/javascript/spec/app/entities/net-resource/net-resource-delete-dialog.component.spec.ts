/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OnCatalogTestModule } from '../../../test.module';
import { NetResourceDeleteDialogComponent } from 'app/entities/net-resource/net-resource-delete-dialog.component';
import { NetResourceService } from 'app/entities/net-resource/net-resource.service';

describe('Component Tests', () => {
    describe('NetResource Management Delete Component', () => {
        let comp: NetResourceDeleteDialogComponent;
        let fixture: ComponentFixture<NetResourceDeleteDialogComponent>;
        let service: NetResourceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [NetResourceDeleteDialogComponent]
            })
                .overrideTemplate(NetResourceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NetResourceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NetResourceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
