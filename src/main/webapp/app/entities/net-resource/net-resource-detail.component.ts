import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INetResource } from 'app/shared/model/net-resource.model';

@Component({
    selector: 'jhi-net-resource-detail',
    templateUrl: './net-resource-detail.component.html'
})
export class NetResourceDetailComponent implements OnInit {
    netResource: INetResource;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ netResource }) => {
            this.netResource = netResource;
        });
    }

    previousState() {
        window.history.back();
    }
}
