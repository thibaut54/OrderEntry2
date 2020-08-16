import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrderEntry2TestModule } from '../../../test.module';
import { AccntComponent } from 'app/entities/accnt/accnt.component';
import { AccntService } from 'app/entities/accnt/accnt.service';
import { Accnt } from 'app/shared/model/accnt.model';

describe('Component Tests', () => {
  describe('Accnt Management Component', () => {
    let comp: AccntComponent;
    let fixture: ComponentFixture<AccntComponent>;
    let service: AccntService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderEntry2TestModule],
        declarations: [AccntComponent],
      })
        .overrideTemplate(AccntComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccntComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccntService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Accnt(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.accnts && comp.accnts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
