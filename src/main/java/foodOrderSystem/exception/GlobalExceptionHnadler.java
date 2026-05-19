
package foodOrderSystem.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import foodOrderSystem.dto.ResponseStructure;


@ControllerAdvice
public class GlobalExceptionHnadler extends ResponseEntityExceptionHandler{
          
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> 
	handleIdNotFoundException(IdNotFoundException exp){
		ResponseStructure<String> res=new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exp.getMessage());
		res.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoRecordFoundException.class)      
	public ResponseEntity<ResponseStructure<String>> 
	handleNoRecordFoundException(NoRecordFoundException nr){
		ResponseStructure<String> res=new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(nr.getMessage());
		res.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	 
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> 
	handleResourceNotFoundException(ResourceNotFoundException nr){
		ResponseStructure<String> res=new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(nr.getMessage());
		res.setData("Failure");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
}

