import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccnt } from 'app/shared/model/accnt.model';
import { AccntService } from './accnt.service';
import { AccntDeleteDialogComponent } from './accnt-delete-dialog.component';

@Component({
  selector: 'jhi-accnt',
  templateUrl: './accnt.component.html',
})
export class AccntComponent implements OnInit, OnDestroy {
  accnts?: IAccnt[];
  eventSubscriber?: Subscription;

  constructor(protected accntService: AccntService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.accntService.query().subscribe((res: HttpResponse<IAccnt[]>) => (this.accnts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAccnts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAccnt): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAccnts(): void {
    this.eventSubscriber = this.eventManager.subscribe('accntListModification', () => this.loadAll());
  }

  delete(accnt: IAccnt): void {
    const modalRef = this.modalService.open(AccntDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accnt = accnt;
  }
}
