export interface INationalEducationalLevel {
  id?: number;
  name?: string;
  description?: string;
  level?: number;
}

export class NationalEducationalLevel implements INationalEducationalLevel {
  constructor(public id?: number, public name?: string, public description?: string, public level?: number) {}
}
