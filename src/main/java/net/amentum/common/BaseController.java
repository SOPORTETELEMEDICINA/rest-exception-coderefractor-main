package net.amentum.common;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Created by dev06 on 9/03/17.
 */
@RestController
@ControllerAdvice
public class BaseController {

   private final Logger logger = LoggerFactory.getLogger(BaseController.class);


   /**
    * This method catch the {@link GenericException} to make a custom response
    *
    * @param ex the exception to be formated
    * @return {@link ErrorMessage} custom response with code and message
    */
   @ExceptionHandler(value = GenericException.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ResponseBody
   public ErrorMessage error(GenericException ex) {
      ErrorMessage errorMessage = new ErrorMessage(ex.getExceptionCode(), ex.getMessage(), ex.getErrorList());
      return errorMessage;
   }

   /**
    * This method catch error when the request have no the correct format or a required parameter is not present
    *
    * @param ex the exception to be formated
    * @return {@link GenericException} custom response
    */
   @ExceptionHandler(value = Exception.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErrorMessage exception(Exception ex) {
      if (ex.getCause() instanceof JsonParseException || ex.getCause() instanceof JsonMappingException) {
         // return new ErrorMessage("CNTREQ101", "Formato incorrecto de request");
         return new ErrorMessage("CNTREQ101", ex.getMessage());
      } else {
         logger.error("Not managed error", ex);
         // return new ErrorMessage("CNTREQ102", "Formato incorrecto de request");
         return new ErrorMessage("CNTREQ102", ex.getMessage());
      }
   }


   /**
    * Method to handle unrecognized properties from Request
    **/
   @ExceptionHandler(value = HttpMessageNotReadableException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ErrorMessage propertyUnrecognized(HttpMessageNotReadableException ex) {
      ErrorMessage baseException = new ErrorMessage("CNTREQ103", "No se reconocen algunos atributos del Request");
      if (ex.getCause() instanceof UnrecognizedPropertyException) {
         UnrecognizedPropertyException unrecognized = (UnrecognizedPropertyException) ex.getCause();
         baseException.addError("No se encuentra atributo: " + unrecognized.getPropertyName());
      } else if (ex.getCause() instanceof InvalidFormatException) {
         InvalidFormatException invalid = (InvalidFormatException) ex.getCause();
         baseException.setMessage(invalid.getMessage());
      } else if (ex.getCause() instanceof JsonMappingException) {
         JsonMappingException exception = (JsonMappingException) ex.getCause();
         baseException.setMessage("No se reconoce atributo de request: " + exception.getMessage());
      }
      return baseException;
   }

   /**
    * This method is used to catch error when the request is not correct according to Object Request expected
    *
    * @param ex The exception to be managed
    * @return {@link GenericException} complete detail of Exception
    */
   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ErrorMessage validationErrors(MethodArgumentNotValidException ex) {
      ErrorMessage bse = new ErrorMessage("CNTREQ104", "Error al validar Peticion");
      for (FieldError fError : ex.getBindingResult().getFieldErrors()) {
         bse.addError(fError.getField() + ": " + fError.getDefaultMessage());
      }
      return bse;
   }


   /**
    * Method to handle errors of type HttpMediaTypeNotSupportedException
    * client is not sending request as application/json
    **/
   @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ErrorMessage applicationJson(HttpMediaTypeNotSupportedException ex) {
      ErrorMessage bse = new ErrorMessage("CNTREQ106", "Petición incorrecta");
      bse.addError("El servidor solo acepta peticiones de tipo application/json");
      return bse;
   }

   /**
    * Method to handle acces denied exceptions
    * client is not sending request as application/json
    **/
   @ExceptionHandler(value = AccessDeniedException.class)
   @ResponseStatus(HttpStatus.FORBIDDEN)
   @ResponseBody
   public ErrorMessage accessDenied(AccessDeniedException denied) {
      ErrorMessage bse = new ErrorMessage("CNTREQ403", "Acceso denegado");
      bse.addError("No cuenta con los permisos para acceder al recurso solicitado");
      return bse;
   }

// YOLO
   @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ErrorMessage validationErrors2(MethodArgumentTypeMismatchException ex) {
      ErrorMessage bse = new ErrorMessage("CNTREQ105", "Error al validar Peticion");
      bse.addError(ex.getName() + ": " + ex.getCause());
      return bse;
   }


}
