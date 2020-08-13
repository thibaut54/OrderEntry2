import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccnt } from 'app/shared/model/accnt.model';

@Component({
  selector: 'jhi-accnt-detail',
  templateUrl: './accnt-detail.component.html',
})
export class AccntDetailComponent implements OnInit {
  accnt: IAccnt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accnt }) => (this.accnt = accnt));
  }

  previousState(): void {
    window.history.back();
  }
}
