export class CategoryDTO{

  categoryid: string;
  status: string;

  constructor(private catid){
    this.categoryid = catid;
  }

}
