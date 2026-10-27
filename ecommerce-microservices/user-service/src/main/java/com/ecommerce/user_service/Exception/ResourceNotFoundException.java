package com.ecommerce.user_service.Exception;

public class ResourceNotFoundException  extends  RuntimeException{

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
