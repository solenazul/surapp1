import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { Modelo3DDetailComponent } from 'app/entities/modelo-3-d/modelo-3-d-detail.component';
import { Modelo3D } from 'app/shared/model/modelo-3-d.model';

describe('Component Tests', () => {
  describe('Modelo3D Management Detail Component', () => {
    let comp: Modelo3DDetailComponent;
    let fixture: ComponentFixture<Modelo3DDetailComponent>;
    const route = ({ data: of({ modelo3D: new Modelo3D(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [Modelo3DDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Modelo3DDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Modelo3DDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load modelo3D on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modelo3D).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
