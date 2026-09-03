export type TravelStatus = 'PLANNED' | 'COMPLETED' | 'CANCELLED';

export interface Travel {
  id: number;
  destination: string;
  country: string;
  startDate: string;
  endDate: string;
  status: TravelStatus;
  notes?: string;
  createdAt?: string;
}

export interface TravelPage {
  content: Travel[];
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface TravelPayload {
  destination: string;
  country: string;
  startDate: string;
  endDate: string;
  status: TravelStatus;
  notes: string;
}
