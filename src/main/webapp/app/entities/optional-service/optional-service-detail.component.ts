import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionalService } from 'app/shared/model/optional-service.model';

@Component({
    selector: 'jhi-optional-service-detail',
    templateUrl: './optional-service-detail.component.html'
})
export class OptionalServiceDetailComponent implements OnInit {
    optionalService: IOptionalService;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ optionalService }) => {
            this.optionalService = optionalService;
        });
    }

    previousState() {
        window.history.back();
    }
}
