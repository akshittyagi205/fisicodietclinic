package in.fisicodietclinic.fisico.models;

/**
 * Created by hp on 1/20/2018.
 */

public class ChatModel {
    private String message,sender;
    public ChatModel(String message,String sender){
        this.message=message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
