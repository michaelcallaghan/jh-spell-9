import { IWord } from 'app/shared/model/word.model';

export interface IHomophonePair {
  id?: number;
  text?: string;
  word?: IWord;
  homophone?: IWord;
}

export class HomophonePair implements IHomophonePair {
  constructor(public id?: number, public text?: string, public word?: IWord, public homophone?: IWord) {}
}
