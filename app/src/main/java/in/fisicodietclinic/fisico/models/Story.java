package in.fisicodietclinic.fisico.models;

/**
 * Created by hp on 1/24/2018.
 */

public class Story {
    private String title,imgLink,videoLink;

    public Story(String title,String imgLink,String videoLink){
        this.title=title;
        this.imgLink=imgLink;
        this.videoLink=videoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
