import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPoService } from 'app/shared/model/po-service.model';
import { PoServiceService } from './po-service.service';
import { IBasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from 'app/entities/basic-po';

@Component({
    selector: 'jhi-po-service-update',
    templateUrl: './po-service-update.component.html'
})
export class PoServiceUpdateComponent implements OnInit {
    poService: IPoService;
    isSaving: boolean;

    basicpos: IBasicPO[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected poServiceService: PoServiceService,
        protected basicPOService: BasicPOService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ poService }) => {
            this.poService = poService;
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
        if (this.poService.id !== undefined) {
            this.subscribeToSaveResponse(this.poServiceService.update(this.poService));
        } else {
            this.subscribeToSaveResponse(this.poServiceService.create(this.poService));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoService>>) {
        result.subscribe((res: HttpResponse<IPoService>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
