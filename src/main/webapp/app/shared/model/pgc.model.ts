import { IPhoneme } from 'app/shared/model/phoneme.model';
import { IGrapheme } from 'app/shared/model/grapheme.model';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { Frequency } from 'app/shared/model/enumerations/frequency.model';

export interface IPgc {
  id?: number;
  text?: string;
  frequency?: Frequency;
  phoneme?: IPhoneme;
  grapheme?: IGrapheme;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class Pgc implements IPgc {
  constructor(
    public id?: number,
    public text?: string,
    public frequency?: Frequency,
    public phoneme?: IPhoneme,
    public grapheme?: IGrapheme,
    public firstIntroducedAt?: INationalEducationalLevel
  ) {}
}
