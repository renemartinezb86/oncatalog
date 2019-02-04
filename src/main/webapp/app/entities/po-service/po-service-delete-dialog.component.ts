import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoService } from 'app/shared/model/po-service.model';
import { PoServiceService } from './po-service.service';

@Component({
    selector: 'jhi-po-service-delete-dialog',
    templateUrl: './po-service-delete-dialog.component.html'
})
export class PoServiceDeleteDialogComponent {
    poService: IPoService;

    constructor(
        protected poServiceService: PoServiceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.poServiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'poServiceListModification',
                content: 'Deleted an poService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-po-service-delete-popup',
    template: ''
})
export class PoServiceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ poService }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PoServiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.poService = poService;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/po-service', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/po-service', { outlets: { popup: null } }]);
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
