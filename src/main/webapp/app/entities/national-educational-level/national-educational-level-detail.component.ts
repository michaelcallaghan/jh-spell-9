import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

@Component({
  selector: 'jhi-national-educational-level-detail',
  templateUrl: './national-educational-level-detail.component.html',
})
export class NationalEducationalLevelDetailComponent implements OnInit {
  nationalEducationalLevel: INationalEducationalLevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nationalEducationalLevel }) => (this.nationalEducationalLevel = nationalEducationalLevel));
  }

  previousState(): void {
    window.history.back();
  }
}
