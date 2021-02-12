import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

export interface IGrapheme {
  id?: number;
  text?: string;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class Grapheme implements IGrapheme {
  constructor(public id?: number, public text?: string, public firstIntroducedAt?: INationalEducationalLevel) {}
}
