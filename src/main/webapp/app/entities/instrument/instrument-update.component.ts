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
    name: [],
    bloombergTicker: [],
    currencyQuotation: [],
    isin: [],
    market: [],
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
      name: instrument.name,
      bloombergTicker: instrument.bloombergTicker,
      currencyQuotation: instrument.currencyQuotation,
      isin: instrument.isin,
      market: instrument.market,
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
      name: this.editForm.get(['name'])!.value,
      bloombergTicker: this.editForm.get(['bloombergTicker'])!.value,
      currencyQuotation: this.editForm.get(['currencyQuotation'])!.value,
      isin: this.editForm.get(['isin'])!.value,
      market: this.editForm.get(['market'])!.value,
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
