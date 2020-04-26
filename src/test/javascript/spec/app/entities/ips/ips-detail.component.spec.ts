import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { IPSDetailComponent } from 'app/entities/ips/ips-detail.component';
import { IPS } from 'app/shared/model/ips.model';

describe('Component Tests', () => {
  describe('IPS Management Detail Component', () => {
    let comp: IPSDetailComponent;
    let fixture: ComponentFixture<IPSDetailComponent>;
    const route = ({ data: of({ iPS: new IPS(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [IPSDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IPSDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IPSDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load iPS on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.iPS).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
