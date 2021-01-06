

export class Meeting{
    itemId :{space:string, id:string};
    type:string;
    name:string;
    active:boolean;
    createdTimeStamp:string;
    createdBy :{
      userId:{space: string, email:string}
    };
    location:{lat:number, lng:number};
    itemAttributes:Map<string,Object>;
  }