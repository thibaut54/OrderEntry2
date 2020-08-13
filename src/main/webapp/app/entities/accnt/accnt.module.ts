import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrderEntry2SharedModule } from 'app/shared/shared.module';
import { AccntComponent } from './accnt.component';
import { AccntDetailComponent } from './accnt-detail.component';
import { AccntUpdateComponent } from './accnt-update.component';
import { AccntDeleteDialogComponent } from './accnt-delete-dialog.component';
import { accntRoute } from './accnt.route';

@NgModule({
  imports: [OrderEntry2SharedModule, RouterModule.forChild(accntRoute)],
  declarations: [AccntComponent, AccntDetailComponent, AccntUpdateComponent, AccntDeleteDialogComponent],
  entryComponents: [AccntDeleteDialogComponent],
})
export class OrderEntry2AccntModule {}
