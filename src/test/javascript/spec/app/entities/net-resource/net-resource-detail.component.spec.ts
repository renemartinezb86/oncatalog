/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OnCatalogTestModule } from '../../../test.module';
import { NetResourceDetailComponent } from 'app/entities/net-resource/net-resource-detail.component';
import { NetResource } from 'app/shared/model/net-resource.model';

describe('Component Tests', () => {
    describe('NetResource Management Detail Component', () => {
        let comp: NetResourceDetailComponent;
        let fixture: ComponentFixture<NetResourceDetailComponent>;
        const route = ({ data: of({ netResource: new NetResource('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OnCatalogTestModule],
                declarations: [NetResourceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NetResourceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NetResourceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.netResource).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
