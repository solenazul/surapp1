import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Surapp1TestModule } from '../../../test.module';
import { InvitacionTableroDetailComponent } from 'app/entities/invitacion-tablero/invitacion-tablero-detail.component';
import { InvitacionTablero } from 'app/shared/model/invitacion-tablero.model';

describe('Component Tests', () => {
  describe('InvitacionTablero Management Detail Component', () => {
    let comp: InvitacionTableroDetailComponent;
    let fixture: ComponentFixture<InvitacionTableroDetailComponent>;
    const route = ({ data: of({ invitacionTablero: new InvitacionTablero(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [InvitacionTableroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvitacionTableroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitacionTableroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invitacionTablero on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invitacionTablero).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
