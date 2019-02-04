import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from './charging-system.service';

@Component({
    selector: 'jhi-charging-system-delete-dialog',
    templateUrl: './charging-system-delete-dialog.component.html'
})
export class ChargingSystemDeleteDialogComponent {
    chargingSystem: IChargingSystem;

    constructor(
        protected chargingSystemService: ChargingSystemService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.chargingSystemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'chargingSystemListModification',
                content: 'Deleted an chargingSystem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-charging-system-delete-popup',
    template: ''
})
export class ChargingSystemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chargingSystem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChargingSystemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.chargingSystem = chargingSystem;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/charging-system', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/charging-system', { outlets: { popup: null } }]);
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
