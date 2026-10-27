package com.ecommerce.user_service.Exception;

public class ResourceAlreadyExistsException extends RuntimeException {
   public ResourceAlreadyExistsException(String msg){
       super(msg);
   }
}
