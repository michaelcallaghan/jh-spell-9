import { IRule } from 'app/shared/model/rule.model';
import { IPgclo } from 'app/shared/model/pgclo.model';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

export interface IRuleExample {
  id?: number;
  info?: string;
  rule?: IRule;
  pgclo?: IPgclo;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class RuleExample implements IRuleExample {
  constructor(
    public id?: number,
    public info?: string,
    public rule?: IRule,
    public pgclo?: IPgclo,
    public firstIntroducedAt?: INationalEducationalLevel
  ) {}
}
