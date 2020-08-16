import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.OrderEntry2OrderModule),
      },
      {
        path: 'instrument',
        loadChildren: () => import('./instrument/instrument.module').then(m => m.OrderEntry2InstrumentModule),
      },
      {
        path: 'accnt',
        loadChildren: () => import('./accnt/accnt.module').then(m => m.OrderEntry2AccntModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class OrderEntry2EntityModule {}
