export interface AuthResponse {
  token: string;
  user: { id: number; email: string };
}

export interface AuthPayload {
  email: string;
  password: string;
}
