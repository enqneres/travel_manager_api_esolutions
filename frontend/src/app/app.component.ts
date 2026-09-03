import { ChangeDetectionStrategy, Component, OnInit, inject } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { finalize } from 'rxjs';
import { Travel, TravelPayload, TravelStatus } from './travel.model';
import { TravelService } from './travel.service';

@Component({
  selector: 'atlas-root',
  standalone: true,
  imports: [ReactiveFormsModule, DatePipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly travelService = inject(TravelService);
  readonly form = this.formBuilder.group({
    destination: ['', [Validators.required, Validators.maxLength(120)]],
    country: ['', [Validators.required, Validators.maxLength(80)]],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    status: ['PLANNED' as TravelStatus, Validators.required],
    notes: ['', Validators.maxLength(500)]
  });
  travels: Travel[] = [];
  loading = true;
  saving = false;
  feedback = '';
  error = '';
  readonly statusLabels: Record<TravelStatus, string> = {
    PLANNED: 'Planejada', COMPLETED: 'Concluída', CANCELLED: 'Cancelada'
  };

  ngOnInit(): void {
    this.loadTravels();
  }

  loadTravels(): void {
    this.loading = true;
    this.travelService.list().pipe(finalize(() => this.loading = false)).subscribe({
      next: travels => { this.travels = travels; this.error = ''; },
      error: () => this.error = 'Não foi possível carregar suas viagens.'
    });
  }

  submit(): void {
    if (this.form.invalid || this.form.controls.endDate.value < this.form.controls.startDate.value) {
      this.feedback = 'Confira os campos e o período informado.';
      this.form.markAllAsTouched();
      return;
    }
    this.saving = true;
    this.feedback = '';
    const payload = this.form.getRawValue() as TravelPayload;
    this.travelService.create(payload).pipe(finalize(() => this.saving = false)).subscribe({
      next: () => { this.form.reset({ status: 'PLANNED' }); this.feedback = 'Viagem adicionada!'; this.loadTravels(); },
      error: () => this.feedback = 'Não foi possível salvar. Tente novamente.'
    });
  }
}
