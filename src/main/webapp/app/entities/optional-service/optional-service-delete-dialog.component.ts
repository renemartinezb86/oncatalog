import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptionalService } from 'app/shared/model/optional-service.model';
import { OptionalServiceService } from './optional-service.service';

@Component({
    selector: 'jhi-optional-service-delete-dialog',
    templateUrl: './optional-service-delete-dialog.component.html'
})
export class OptionalServiceDeleteDialogComponent {
    optionalService: IOptionalService;

    constructor(
        protected optionalServiceService: OptionalServiceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.optionalServiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'optionalServiceListModification',
                content: 'Deleted an optionalService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-optional-service-delete-popup',
    template: ''
})
export class OptionalServiceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ optionalService }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OptionalServiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.optionalService = optionalService;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/optional-service', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/optional-service', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
