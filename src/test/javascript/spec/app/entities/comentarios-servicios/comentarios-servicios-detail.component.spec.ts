import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { ComentariosServiciosDetailComponent } from 'app/entities/comentarios-servicios/comentarios-servicios-detail.component';
import { ComentariosServicios } from 'app/shared/model/comentarios-servicios.model';

describe('Component Tests', () => {
  describe('ComentariosServicios Management Detail Component', () => {
    let comp: ComentariosServiciosDetailComponent;
    let fixture: ComponentFixture<ComentariosServiciosDetailComponent>;
    const route = ({ data: of({ comentariosServicios: new ComentariosServicios(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [ComentariosServiciosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComentariosServiciosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComentariosServiciosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load comentariosServicios on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comentariosServicios).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
