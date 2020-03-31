export class CategoryDTO{

  categoryid: string;
  name: string;
  status: string;

  constructor(private catid){
    this.categoryid = catid;
  }

}
