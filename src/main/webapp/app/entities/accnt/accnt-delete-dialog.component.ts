import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAccnt } from 'app/shared/model/accnt.model';
import { AccntService } from './accnt.service';

@Component({
  templateUrl: './accnt-delete-dialog.component.html',
})
export class AccntDeleteDialogComponent {
  accnt?: IAccnt;

  constructor(protected accntService: AccntService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accntService.delete(id).subscribe(() => {
      this.eventManager.broadcast('accntListModification');
      this.activeModal.close();
    });
  }
}
