import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBSCS } from 'app/shared/model/bscs.model';

@Component({
    selector: 'jhi-bscs-detail',
    templateUrl: './bscs-detail.component.html'
})
export class BSCSDetailComponent implements OnInit {
    bSCS: IBSCS;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bSCS }) => {
            this.bSCS = bSCS;
        });
    }

    previousState() {
        window.history.back();
    }
}
