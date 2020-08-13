import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAccnt, Accnt } from 'app/shared/model/accnt.model';
import { AccntService } from './accnt.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

@Component({
  selector: 'jhi-accnt-update',
  templateUrl: './accnt-update.component.html',
})
export class AccntUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    accountId: [],
    field1: [],
    field2: [],
    field3: [],
    field4: [],
    field5: [],
    orderId: [],
  });

  constructor(
    protected accntService: AccntService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accnt }) => {
      this.updateForm(accnt);

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(accnt: IAccnt): void {
    this.editForm.patchValue({
      id: accnt.id,
      accountId: accnt.accountId,
      field1: accnt.field1,
      field2: accnt.field2,
      field3: accnt.field3,
      field4: accnt.field4,
      field5: accnt.field5,
      orderId: accnt.orderId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accnt = this.createFromForm();
    if (accnt.id !== undefined) {
      this.subscribeToSaveResponse(this.accntService.update(accnt));
    } else {
      this.subscribeToSaveResponse(this.accntService.create(accnt));
    }
  }

  private createFromForm(): IAccnt {
    return {
      ...new Accnt(),
      id: this.editForm.get(['id'])!.value,
      accountId: this.editForm.get(['accountId'])!.value,
      field1: this.editForm.get(['field1'])!.value,
      field2: this.editForm.get(['field2'])!.value,
      field3: this.editForm.get(['field3'])!.value,
      field4: this.editForm.get(['field4'])!.value,
      field5: this.editForm.get(['field5'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccnt>>): void {
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

  trackById(index: number, item: IOrder): any {
    return item.id;
  }
}
