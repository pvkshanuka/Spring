export class AppointmentSearchDTO {

  // category: number;
  // hospital: number;
  // doctor: number;
  // date: string;

  constructor(private client: number, private doctor: number, private date: Date, private status: string) {
    // this.cat = cat;
    // this.hospital = hos;
    // this.doctor = doc;
    // this.date = d;
  }

}
