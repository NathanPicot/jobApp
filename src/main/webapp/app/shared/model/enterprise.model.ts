import { type IApplication } from '@/shared/model/application.model';
import { type IJob } from '@/shared/model/job.model';

export interface IEnterprise {
  id?: number;
  name2?: string | null;
  nbEmployee?: number | null;
  international?: boolean | null;
  apps?: IApplication[] | null;
  jobs?: IJob[] | null;
}

export class Enterprise implements IEnterprise {
  constructor(
    public id?: number,
    public name2?: string | null,
    public nbEmployee?: number | null,
    public international?: boolean | null,
    public apps?: IApplication[] | null,
    public jobs?: IJob[] | null,
  ) {
    this.international = this.international ?? false;
  }
}
