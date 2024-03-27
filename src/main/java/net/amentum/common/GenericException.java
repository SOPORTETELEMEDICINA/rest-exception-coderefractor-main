package net.amentum.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev06 on 9/03/17.
 */
public abstract class GenericException  extends  Exception{

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

    private final Logger logger = LoggerFactory.getLogger(GenericException.class);

    public GenericException(){}

    public GenericException(Exception ex){
        super(ex);
    }

    public GenericException(String message){
        super(message);
        setErrorCode(message);
    }

    public GenericException(Exception ex,String message){
        super(ex);
    }

    /** Specific error code*/
    private String errorCode = "";
    /** Specific error list messages*/
    private List<String> errorList = new ArrayList<String>();
    /**
     * Gets specific error code
     * @return Error code
     */
    public String getErrorCode() {
        return errorCode;
    }
    /**
     * Sets specific error code
     * @param nErrorCode
     */
    private void setErrorCode(String nErrorCode) {
        this.errorCode = nErrorCode;
    }

    /** This is used by the controller advice */
    public void addError(String error){
        this.errorList.add(error);
    }
    /** Abstract method to Gets the Exception Codes */
    public abstract String getExceptionCode();

    /** Get List of error messages */
    public List<String> getErrorList() {
        return errorList;
    }

    @Override
    public String toString() {
        return "GenericException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorList=" + errorList +", ExceptionCode: "+getExceptionCode()+
                '}';
    }
}
