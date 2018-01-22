package in.fisicodietclinic.fisico.models;

/**
 * Created by manyamadan on 23/11/17.
 */

public class Diet {
    private String title, details, year;

    public Diet() {
    }

    public Diet(String title, String details, String year) {
        this.title = title;
        this.details = details;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
