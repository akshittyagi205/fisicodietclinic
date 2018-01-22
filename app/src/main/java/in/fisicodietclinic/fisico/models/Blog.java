package in.fisicodietclinic.fisico.models;


public class Blog {
    private String title, date, link,picture;

    public Blog() {
    }

    public Blog(String title, String date, String link) {
        this.title = title;
        this.date = date;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}