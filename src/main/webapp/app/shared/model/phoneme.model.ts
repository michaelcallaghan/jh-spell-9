import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

export interface IPhoneme {
  id?: number;
  ipa?: string;
  aka?: string;
  informalUkGov?: string;
  wikiIpa?: string;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class Phoneme implements IPhoneme {
  constructor(
    public id?: number,
    public ipa?: string,
    public aka?: string,
    public informalUkGov?: string,
    public wikiIpa?: string,
    public firstIntroducedAt?: INationalEducationalLevel
  ) {}
}
