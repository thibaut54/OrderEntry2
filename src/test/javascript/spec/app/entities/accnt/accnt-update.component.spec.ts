import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrderEntry2TestModule } from '../../../test.module';
import { AccntUpdateComponent } from 'app/entities/accnt/accnt-update.component';
import { AccntService } from 'app/entities/accnt/accnt.service';
import { Accnt } from 'app/shared/model/accnt.model';

describe('Component Tests', () => {
  describe('Accnt Management Update Component', () => {
    let comp: AccntUpdateComponent;
    let fixture: ComponentFixture<AccntUpdateComponent>;
    let service: AccntService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrderEntry2TestModule],
        declarations: [AccntUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AccntUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccntUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccntService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Accnt(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Accnt();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
