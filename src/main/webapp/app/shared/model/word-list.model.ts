export interface IWordList {
  id?: number;
  name?: string;
  description?: string;
}

export class WordList implements IWordList {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
