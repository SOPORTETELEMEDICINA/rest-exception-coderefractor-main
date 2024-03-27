package net.amentum.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev06 on 9/03/17.
 */
public class ErrorMessage implements Serializable{

    private String code;
    private String message;
    private List<String> errorList = new ArrayList<String>();

    public ErrorMessage(){}


    public ErrorMessage(String code, String message, List<String> errorList) {
        this.code = code;
        this.message = message;
        this.errorList = errorList;
    }

    public ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public void addError(String error){
        this.errorList.add(error);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", errorList=" + errorList +
                '}';
    }
}
