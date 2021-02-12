import { IPgc } from 'app/shared/model/pgc.model';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { Location } from 'app/shared/model/enumerations/location.model';
import { SyllabicContext } from 'app/shared/model/enumerations/syllabic-context.model';
import { Frequency } from 'app/shared/model/enumerations/frequency.model';

export interface IPgcl {
  id?: number;
  text?: string;
  location?: Location;
  syllabicContext?: SyllabicContext;
  frequency?: Frequency;
  pgc?: IPgc;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class Pgcl implements IPgcl {
  constructor(
    public id?: number,
    public text?: string,
    public location?: Location,
    public syllabicContext?: SyllabicContext,
    public frequency?: Frequency,
    public pgc?: IPgc,
    public firstIntroducedAt?: INationalEducationalLevel
  ) {}
}
