import { type IJob } from '@/shared/model/job.model';

export interface IPersonnage {
  id?: number;
  name?: string | null;
  age?: number | null;
  birthday?: Date | null;
  jobs?: IJob[] | null;
}

export class Personnage implements IPersonnage {
  constructor(
    public id?: number,
    public name?: string | null,
    public age?: number | null,
    public birthday?: Date | null,
    public jobs?: IJob[] | null,
  ) {}
}
