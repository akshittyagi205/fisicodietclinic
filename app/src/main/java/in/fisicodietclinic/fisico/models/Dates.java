package in.fisicodietclinic.fisico.models;


public class Dates {

    private String date;
    private String day;


    public Dates() {
    }

    public Dates(String date, String day) {
        this.date = date;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
