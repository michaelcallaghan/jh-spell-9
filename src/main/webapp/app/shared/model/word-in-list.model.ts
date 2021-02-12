import { IWord } from 'app/shared/model/word.model';
import { IWordList } from 'app/shared/model/word-list.model';

export interface IWordInList {
  id?: number;
  position?: number;
  word?: IWord;
  wordList?: IWordList;
}

export class WordInList implements IWordInList {
  constructor(public id?: number, public position?: number, public word?: IWord, public wordList?: IWordList) {}
}
