import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoService } from 'app/shared/model/po-service.model';

@Component({
    selector: 'jhi-po-service-detail',
    templateUrl: './po-service-detail.component.html'
})
export class PoServiceDetailComponent implements OnInit {
    poService: IPoService;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ poService }) => {
            this.poService = poService;
        });
    }

    previousState() {
        window.history.back();
    }
}
