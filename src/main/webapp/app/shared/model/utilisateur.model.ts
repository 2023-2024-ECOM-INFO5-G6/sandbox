import dayjs from 'dayjs';
import { IUserRole } from 'app/shared/model/user-role.model';
import { IPatient } from 'app/shared/model/patient.model';
import { IEtablissement } from 'app/shared/model/etablissement.model';

export interface IUtilisateur {
  id?: number;
  idU?: number;
  emailU?: string;
  passwordU?: string;
  nomU?: string | null;
  prenomU?: string | null;
  dateNaissanceU?: string | null;
  userRoles?: IUserRole[];
  patients?: IPatient[];
  etablissements?: IEtablissement[];
}

export const defaultValue: Readonly<IUtilisateur> = {};
