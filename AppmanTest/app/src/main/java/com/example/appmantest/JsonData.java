package com.example.appmantest;

public class JsonData {


    String id, fName,lName,th,en,dataID;

    public void setTitle(String id,String fName, String lName){
        this.fName = fName;
        this.lName = lName;
        this.id = id;
    }

    public String getTitle(){
        return "id:"+this.id+" Name:"+this.fName+" "+this.lName;
    }

    public String getId(){
        return  this.dataID;
    }
    public  void setDescription(String dataID,String th,String en){
        this.th = th;
        this.en = en;
        this.dataID = dataID;
    }
    public String getTh(){
        return th;
    }
    public String getEn(){
        return en;
    }
}
