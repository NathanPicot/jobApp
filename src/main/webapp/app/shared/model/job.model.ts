import { type IEnterprise } from '@/shared/model/enterprise.model';
import { type IPersonnage } from '@/shared/model/personnage.model';

export interface IJob {
  id?: number;
  name1?: string | null;
  salary?: number | null;
  tasks?: IEnterprise[] | null;
  personnage?: IPersonnage | null;
}

export class Job implements IJob {
  constructor(
    public id?: number,
    public name1?: string | null,
    public salary?: number | null,
    public tasks?: IEnterprise[] | null,
    public personnage?: IPersonnage | null,
  ) {}
}
