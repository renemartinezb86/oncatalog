/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OnCatalogTestModule } from '../../../test.module';
import { BasicPODeleteDialogComponent } from 'app/entities/basic-po/basic-po-delete-dialog.component';
import { BasicPOService } from 'app/entities/basic-po/basic-po.service';

describe('Component Tests', () => {
    describe('BasicPO Management Delete Component', () => {
        let comp: BasicPODeleteDialogComponent;
        let fixture: ComponentFixture<BasicPODeleteDialogComponent>;
        let service: BasicPOService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [BasicPODeleteDialogComponent]
            })
                .overrideTemplate(BasicPODeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BasicPODeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BasicPOService);
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
