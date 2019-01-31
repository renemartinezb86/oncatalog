import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOptionalService } from 'app/shared/model/optional-service.model';
import { OptionalServiceService } from './optional-service.service';
import { IBasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from 'app/entities/basic-po';

@Component({
    selector: 'jhi-optional-service-update',
    templateUrl: './optional-service-update.component.html'
})
export class OptionalServiceUpdateComponent implements OnInit {
    optionalService: IOptionalService;
    isSaving: boolean;

    basicpos: IBasicPO[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected optionalServiceService: OptionalServiceService,
        protected basicPOService: BasicPOService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ optionalService }) => {
            this.optionalService = optionalService;
        });
        this.basicPOService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBasicPO[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBasicPO[]>) => response.body)
            )
            .subscribe((res: IBasicPO[]) => (this.basicpos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.optionalService.id !== undefined) {
            this.subscribeToSaveResponse(this.optionalServiceService.update(this.optionalService));
        } else {
            this.subscribeToSaveResponse(this.optionalServiceService.create(this.optionalService));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptionalService>>) {
        result.subscribe((res: HttpResponse<IOptionalService>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackBasicPOById(index: number, item: IBasicPO) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
