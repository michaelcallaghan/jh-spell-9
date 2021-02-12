import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

export interface IWord {
  id?: number;
  text?: string;
  ipaText?: string;
  soundFile?: string;
  usageSoundFile?: string;
  altIpaText?: string;
  altSoundFile?: string;
  altUsageSoundFile?: string;
  firstIntroducedAt?: INationalEducationalLevel;
}

export class Word implements IWord {
  constructor(
    public id?: number,
    public text?: string,
    public ipaText?: string,
    public soundFile?: string,
    public usageSoundFile?: string,
    public altIpaText?: string,
    public altSoundFile?: string,
    public altUsageSoundFile?: string,
    public firstIntroducedAt?: INationalEducationalLevel
  ) {}
}
