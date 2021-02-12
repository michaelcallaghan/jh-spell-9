import { IPgcl } from 'app/shared/model/pgcl.model';
import { IWord } from 'app/shared/model/word.model';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

export interface IPgclo {
  id?: number;
  text?: string;
  start?: number;
  end?: number;
  pgcl?: IPgcl;
  word?: IWord;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class Pgclo implements IPgclo {
  constructor(
    public id?: number,
    public text?: string,
    public start?: number,
    public end?: number,
    public pgcl?: IPgcl,
    public word?: IWord,
    public firstIntroducedAt?: INationalEducationalLevel
  ) {}
}
