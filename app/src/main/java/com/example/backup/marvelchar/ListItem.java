package com.example.backup.marvelchar;

/**
 * Created by Backup on 5/24/2017.
 */
public class ListItem {
    private String name;
    private String descp;
    private String url;
    private String modified;
    private String id;
    private String resourceURI;



    public ListItem(String name, String descp,String url,String modified,String id,String resourceURI) {
        this.name = name;
        this.descp = descp;
        this.url=url;
        this.modified=modified;
        this.id=id;
        this.resourceURI=resourceURI;



    }

    public String getName() {
        return name;
    }


    public String getDescp() {
        return descp;
    }
public  String getUrl(){ return  url;}

    public String getModified(){return modified;}
    public String getId(){ return id;}
    public String getResourceURI(){return resourceURI;}





}
