import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBSCS } from 'app/shared/model/bscs.model';
import { BSCSService } from './bscs.service';

@Component({
    selector: 'jhi-bscs-delete-dialog',
    templateUrl: './bscs-delete-dialog.component.html'
})
export class BSCSDeleteDialogComponent {
    bSCS: IBSCS;

    constructor(protected bSCSService: BSCSService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.bSCSService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bSCSListModification',
                content: 'Deleted an bSCS'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bscs-delete-popup',
    template: ''
})
export class BSCSDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bSCS }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BSCSDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bSCS = bSCS;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bscs', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bscs', { outlets: { popup: null } }]);
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
