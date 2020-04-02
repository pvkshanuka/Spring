export class AppointmnentDTO{

  client: number;
  channelling: number;
  date: Date;

  constructor(private cl, private ch, private da){
    this.client = cl;
    this.channelling = ch;
    this.date = da;
  }

}
