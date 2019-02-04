import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChargingSystem } from 'app/shared/model/charging-system.model';

@Component({
    selector: 'jhi-charging-system-detail',
    templateUrl: './charging-system-detail.component.html'
})
export class ChargingSystemDetailComponent implements OnInit {
    chargingSystem: IChargingSystem;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chargingSystem }) => {
            this.chargingSystem = chargingSystem;
        });
    }

    previousState() {
        window.history.back();
    }
}
