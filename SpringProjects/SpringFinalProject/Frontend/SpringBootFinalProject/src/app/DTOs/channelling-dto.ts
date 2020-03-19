export class ChannellingDTO{

  id: number;
  hospital: number;
  doctor: number;
  day: string;
  statTime: string;
  endTime: string;
  room: string;
  price: number;
  status: string;

  constructor(private hos, private doc, private d, private st, private et, private rm, private p){
    this.hospital = hos;
    this.doctor = doc;
    this.day = d;
    this.statTime = st;
    this.endTime = et;
    this.room = rm;
    this.price = p;
  }

}
