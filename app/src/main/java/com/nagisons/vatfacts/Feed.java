package com.nagisons.vatfacts;

public class Feed {
    private String text;
    private String title,links,desc,image_list,pub_date,item;
    private int id;

    public String getText() {
        return text;
    }
    public String getTitle() {
        return title;
    }
    public String getLinks() {
        return links;
    }
    public String getDesc() {
        return desc;
    }
    public String getImage_list() {
        return image_list;
    }
    public String getPub_date() {
        return pub_date;
    }
    public String getItem() {
        return item;
    }
    public int getId() {
        return id;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLinks(String links) {
        this.links = links;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setImage_list(String image_list) {
        this.image_list = image_list;
        //parse description for any image or video links

    }
    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public void setId(int id) {
        this.id = id;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Title/n"+title+ "Links/n"+links+"Image_url/n"+image_list;
    }
}