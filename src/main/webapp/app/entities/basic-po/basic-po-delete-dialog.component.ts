import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from './basic-po.service';

@Component({
    selector: 'jhi-basic-po-delete-dialog',
    templateUrl: './basic-po-delete-dialog.component.html'
})
export class BasicPODeleteDialogComponent {
    basicPO: IBasicPO;

    constructor(protected basicPOService: BasicPOService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.basicPOService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'basicPOListModification',
                content: 'Deleted an basicPO'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-basic-po-delete-popup',
    template: ''
})
export class BasicPODeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ basicPO }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BasicPODeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.basicPO = basicPO;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/basic-po', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/basic-po', { outlets: { popup: null } }]);
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
