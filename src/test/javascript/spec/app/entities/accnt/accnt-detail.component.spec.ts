import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrderEntry2TestModule } from '../../../test.module';
import { AccntDetailComponent } from 'app/entities/accnt/accnt-detail.component';
import { Accnt } from 'app/shared/model/accnt.model';

describe('Component Tests', () => {
  describe('Accnt Management Detail Component', () => {
    let comp: AccntDetailComponent;
    let fixture: ComponentFixture<AccntDetailComponent>;
    const route = ({ data: of({ accnt: new Accnt(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderEntry2TestModule],
        declarations: [AccntDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AccntDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccntDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accnt on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accnt).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
