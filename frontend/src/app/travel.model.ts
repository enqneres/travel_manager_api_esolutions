export type TravelStatus = 'PLANNED' | 'COMPLETED' | 'CANCELLED';

export interface Travel {
  id: number;
  destination: string;
  country: string;
  startDate: string;
  endDate: string;
  status: TravelStatus;
  notes?: string;
}

export interface TravelPayload {
  destination: string;
  country: string;
  startDate: string;
  endDate: string;
  status: TravelStatus;
  notes: string;
}
