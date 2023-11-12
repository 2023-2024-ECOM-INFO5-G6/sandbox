import { IChambre } from 'app/shared/model/chambre.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface IEtablissement {
  id?: number;
  idE?: number;
  nomE?: string | null;
  adresseE?: string | null;
  chambres?: IChambre[];
  utilisateurs?: IUtilisateur[];
}

export const defaultValue: Readonly<IEtablissement> = {};
