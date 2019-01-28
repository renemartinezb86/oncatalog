/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OnCatalogTestModule } from '../../../test.module';
import { OptionalServiceDeleteDialogComponent } from 'app/entities/optional-service/optional-service-delete-dialog.component';
import { OptionalServiceService } from 'app/entities/optional-service/optional-service.service';

describe('Component Tests', () => {
    describe('OptionalService Management Delete Component', () => {
        let comp: OptionalServiceDeleteDialogComponent;
        let fixture: ComponentFixture<OptionalServiceDeleteDialogComponent>;
        let service: OptionalServiceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [OptionalServiceDeleteDialogComponent]
            })
                .overrideTemplate(OptionalServiceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OptionalServiceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OptionalServiceService);
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
