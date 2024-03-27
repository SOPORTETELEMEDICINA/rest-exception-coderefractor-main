package net.amentum.common.v2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class RestBaseController {

   /**
    * This method catch the {@link net.amentum.common.GenericException} to make a
    * custom response
    *
    * @param ex the exception to be formated
    * @return custom message with specific http status code
    */
   @ExceptionHandler(value = GenericException.class)
   public HttpEntity<Object> error(GenericException ex) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("version", "v2");
      return new ResponseEntity<>(ex.formatResponse(), headers, ex.getStatus());
   }

   @ExceptionHandler(value = MissingServletRequestParameterException.class)
   @ResponseBody
   public HttpEntity<Object> requiredNotPresentError(MissingServletRequestParameterException ex) {
      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("code", 400);
      responseMap.put("message", "Error al validar peticion");
      List<String> errorList = new ArrayList<>();
      errorList.add(ex.getParameterName() + ": " + "El parametro es requerido");
      if (!errorList.isEmpty()) {
         responseMap.put("errors", errorList);
      }
      return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   @ResponseBody
   public HttpEntity<Object> validBeanError(MethodArgumentNotValidException ex) {
      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("code", 400);
//      responseMap.put("message", "Algunos campos no cumplen con los requerimientos");
      responseMap.put("message", "Error de validación en campos");
      List<String> errorList = new ArrayList<>();
      BindingResult result = ex.getBindingResult();
      final List<FieldError> fieldErrors = result.getFieldErrors();
      for (FieldError error : fieldErrors) {
         errorList.add(error.getField() + ": " + error.getDefaultMessage());
      }
      if (!errorList.isEmpty()) {
         responseMap.put("errors", errorList);
      }
      HttpHeaders headers = new HttpHeaders();
      headers.add("version", "v2");
      return new ResponseEntity<>(responseMap, headers, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
   @ResponseBody
   public HttpEntity<Object> paramError(MethodArgumentTypeMismatchException ex) {
      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("code", 400);
//      responseMap.put("message", "Algunos campos tienen valores incorrectos");
      responseMap.put("message", "Campos con valores incorrectos");
      List<String> errorList = new ArrayList<>();
      errorList.add(ex.getName() + ": " + ex.getCause());
      if (!errorList.isEmpty()) {
         responseMap.put("errors", errorList);
      }
      HttpHeaders headers = new HttpHeaders();
      headers.add("version", "v2");
      return new ResponseEntity<>(responseMap, headers, HttpStatus.BAD_REQUEST);
   }

}
