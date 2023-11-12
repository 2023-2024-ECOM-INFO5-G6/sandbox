import dayjs from 'dayjs';
import { IPatient } from 'app/shared/model/patient.model';

export interface IRepas {
  id?: number;
  idR?: number;
  dateR?: string | null;
  heureR?: string | null;
  epa?: number | null;
  patient?: IPatient;
}

export const defaultValue: Readonly<IRepas> = {};
