package com.example.Usermicroservice.exception;

import java.util.HashMap;
import java.util.Map;
 
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
 
 
@RestControllerAdvice
public class ExceptionHandler {
 
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String>handleInvalidArgument(MethodArgumentNotValidException ex){
		Map<String,String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->{
			errorMap.put(error.getField(),error.getDefaultMessage());
		});
		return errorMap;
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
	public String handle(DataIntegrityViolationException ex){
		String st=ex.getRootCause().toString();
		System.out.println("this"+st);
		if(st.contains("Duplicate") && st.contains("@")) {
			System.out.println(ex.getMessage());
			String s="Email already exist enter new email";
			return s;
		}
		else {
			System.out.println(ex.getMessage());
			String str="Username already taken";
			return str;
		}
	}
}