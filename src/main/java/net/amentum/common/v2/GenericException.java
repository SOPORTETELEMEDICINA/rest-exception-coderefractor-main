package net.amentum.common.v2;

import org.springframework.http.HttpStatus;

import java.util.*;

public abstract class GenericException extends Exception {
    // legacy constants
    public static final String 	LAYER_REST = "REST";
    /** Code Exception for BMP layer */
    public static final String 	LAYER_BPM = "BPM";
    /** Code Exception for DAO layer */
    public static final String 	LAYER_DAO = "DAO";
    /** Code Exception for Service layer */
    public static final String 	LAYER_SERVICE = "SER";
    /** Code Exception for Action layer */
    public static final String 	LAYER_CONTROLLER = "CNT";
    /** Code Exception for Action layer */
    public static final String 	LAYER_CONVERTER = "CNV";
    /** Code Exception for Insert actions */
    public static final String 	ACTION_INSERT = "101";
    /** Code Exception for Delete actions */
    public static final String 	ACTION_DELETE = "102";
    /** Code Exception for Update actions */
    public static final String 	ACTION_UPDATE = "103";
    /** Code Exception for List Handle */
    public static final String 	ACTION_LISTS = "104";
    /** Code Exception for Select */
    public static final String 	ACTION_SELECT = "105";
    /** Code Exception for Select */
    public static final String 	ACTION_PARSE = "106";
    /** Code Exception for validation*/
    public static final String 	ACTION_VALIDATE = "107";

    public static final String BAD_REQUEST = "400";
    public static final String CONFLICT = "409";
    public static final String SERVER_ERROR = "500";

    public final HttpStatus status;
    public final Integer code;
    public final String message;
    public final Set<ErrorMessage> errorList = new TreeSet<>();

    public GenericException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.code = status.value();
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Set<ErrorMessage> getErrorList() {
        return errorList;
    }

    public void addError(ErrorMessage error){
        this.errorList.add(error);
    }

    public Map<String, Object> formatResponse() {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", this.code);
        responseMap.put("message", this. message);
        if(!this.errorList.isEmpty()) {
            responseMap.put("errors", errorList);
        }
        return responseMap;
    }
}
