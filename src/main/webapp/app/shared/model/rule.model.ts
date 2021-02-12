import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

export interface IRule {
  id?: number;
  name?: string;
  text?: string;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class Rule implements IRule {
  constructor(public id?: number, public name?: string, public text?: string, public firstIntroducedAt?: INationalEducationalLevel) {}
}
