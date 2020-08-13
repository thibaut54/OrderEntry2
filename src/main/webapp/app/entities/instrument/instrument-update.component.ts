import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInstrument, Instrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from './instrument.service';

@Component({
  selector: 'jhi-instrument-update',
  templateUrl: './instrument-update.component.html',
})
export class InstrumentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    instrumentId: [],
    field1: [],
    field2: [],
    field3: [],
    field4: [],
    field5: [],
  });

  constructor(protected instrumentService: InstrumentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ instrument }) => {
      this.updateForm(instrument);
    });
  }

  updateForm(instrument: IInstrument): void {
    this.editForm.patchValue({
      id: instrument.id,
      instrumentId: instrument.instrumentId,
      field1: instrument.field1,
      field2: instrument.field2,
      field3: instrument.field3,
      field4: instrument.field4,
      field5: instrument.field5,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const instrument = this.createFromForm();
    if (instrument.id !== undefined) {
      this.subscribeToSaveResponse(this.instrumentService.update(instrument));
    } else {
      this.subscribeToSaveResponse(this.instrumentService.create(instrument));
    }
  }

  private createFromForm(): IInstrument {
    return {
      ...new Instrument(),
      id: this.editForm.get(['id'])!.value,
      instrumentId: this.editForm.get(['instrumentId'])!.value,
      field1: this.editForm.get(['field1'])!.value,
      field2: this.editForm.get(['field2'])!.value,
      field3: this.editForm.get(['field3'])!.value,
      field4: this.editForm.get(['field4'])!.value,
      field5: this.editForm.get(['field5'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstrument>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
