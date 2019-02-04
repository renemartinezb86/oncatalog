import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INetResource } from 'app/shared/model/net-resource.model';
import { NetResourceService } from './net-resource.service';

@Component({
    selector: 'jhi-net-resource-delete-dialog',
    templateUrl: './net-resource-delete-dialog.component.html'
})
export class NetResourceDeleteDialogComponent {
    netResource: INetResource;

    constructor(
        protected netResourceService: NetResourceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.netResourceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'netResourceListModification',
                content: 'Deleted an netResource'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-net-resource-delete-popup',
    template: ''
})
export class NetResourceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ netResource }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NetResourceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.netResource = netResource;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/net-resource', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/net-resource', { outlets: { popup: null } }]);
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
