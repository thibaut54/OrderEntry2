import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IInstrument } from 'app/shared/model/instrument.model';
import { InstrumentService } from 'app/entities/instrument/instrument.service';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  instruments: IInstrument[] = [];

  editForm = this.fb.group({
    id: [],
    orderInitiator: [],
    sideCombo: [],
    quantity: [],
    orderType: [],
    goodTill: [],
    tradingInstruction: [],
    instrumentId: [],
  });

  constructor(
    protected orderService: OrderService,
    protected instrumentService: InstrumentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);

      this.instrumentService.query().subscribe((res: HttpResponse<IInstrument[]>) => (this.instruments = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      orderInitiator: order.orderInitiator,
      sideCombo: order.sideCombo,
      quantity: order.quantity,
      orderType: order.orderType,
      goodTill: order.goodTill,
      tradingInstruction: order.tradingInstruction,
      instrumentId: order.instrumentId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      orderInitiator: this.editForm.get(['orderInitiator'])!.value,
      sideCombo: this.editForm.get(['sideCombo'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      orderType: this.editForm.get(['orderType'])!.value,
      goodTill: this.editForm.get(['goodTill'])!.value,
      tradingInstruction: this.editForm.get(['tradingInstruction'])!.value,
      instrumentId: this.editForm.get(['instrumentId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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

  trackById(index: number, item: IInstrument): any {
    return item.id;
  }
}
