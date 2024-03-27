package net.amentum.common.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorMessage implements Serializable, Comparable<ErrorMessage> {

    
    private static final long serialVersionUID = 4873326660797403275L;

    private String code;
    private String message;
    
    public ErrorMessage(){}

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

    @Override
    public int compareTo(ErrorMessage o) {
        return this.code.compareTo(o.code);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
