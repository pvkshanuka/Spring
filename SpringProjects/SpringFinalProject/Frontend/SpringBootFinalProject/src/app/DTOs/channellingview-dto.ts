import { HospitalDTO } from './hospital-dto';
import { CategoryDTO } from './category-dto';
import { DoctorDTO } from './doctor-dto';
export class ChannellingViewDTO{

  id: number;
  hospital: HospitalDTO;
  doctor: DoctorDTO;
  categories: CategoryDTO;
  day: string;
  statTime: Date;
  endTime: Date;
  room: string;
  price: number;
  status: string;

  constructor(private hos, private doc, private d, private cat, private st, private et, private rm, private p){
    this.hospital = hos;
    this.doctor = doc;
    this.categories = cat;
    this.day = d;
    this.statTime = st;
    this.endTime = et;
    this.room = rm;
    this.price = p;
  }

}
