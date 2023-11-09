import { type IEnterprise } from '@/shared/model/enterprise.model';

export interface IApplication {
  id?: number;
  name3?: string | null;
  enterprise?: IEnterprise | null;
}

export class Application implements IApplication {
  constructor(
    public id?: number,
    public name3?: string | null,
    public enterprise?: IEnterprise | null,
  ) {}
}
