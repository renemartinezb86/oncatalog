/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OnCatalogTestModule } from '../../../test.module';
import { PoServiceDeleteDialogComponent } from 'app/entities/po-service/po-service-delete-dialog.component';
import { PoServiceService } from 'app/entities/po-service/po-service.service';

describe('Component Tests', () => {
    describe('PoService Management Delete Component', () => {
        let comp: PoServiceDeleteDialogComponent;
        let fixture: ComponentFixture<PoServiceDeleteDialogComponent>;
        let service: PoServiceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [PoServiceDeleteDialogComponent]
            })
                .overrideTemplate(PoServiceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PoServiceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoServiceService);
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
