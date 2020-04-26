import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { TablerosDetailComponent } from 'app/entities/tableros/tableros-detail.component';
import { Tableros } from 'app/shared/model/tableros.model';

describe('Component Tests', () => {
  describe('Tableros Management Detail Component', () => {
    let comp: TablerosDetailComponent;
    let fixture: ComponentFixture<TablerosDetailComponent>;
    const route = ({ data: of({ tableros: new Tableros(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [TablerosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TablerosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TablerosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tableros on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tableros).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
