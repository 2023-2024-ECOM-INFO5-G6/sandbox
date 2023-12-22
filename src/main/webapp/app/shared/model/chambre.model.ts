import { IEtablissement } from 'app/shared/model/etablissement.model';
import { IPatient } from 'app/shared/model/patient.model';

export interface IChambre {
  id?: number;
  numC?: string;
  etablissement?: IEtablissement;
  patient?: IPatient | null;
}

export const defaultValue: Readonly<IChambre> = {};
