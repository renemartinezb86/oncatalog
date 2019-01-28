import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBasicPO } from 'app/shared/model/basic-po.model';

@Component({
    selector: 'jhi-basic-po-detail',
    templateUrl: './basic-po-detail.component.html'
})
export class BasicPODetailComponent implements OnInit {
    basicPO: IBasicPO;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ basicPO }) => {
            this.basicPO = basicPO;
        });
    }

    previousState() {
        window.history.back();
    }
}
