package com.return0.dev.backend.exception;

public class ProductException extends BaseException {

    public ProductException(String code){
        super("product." + code);
    }

    public static ProductException notFound(){
        return new ProductException("not.found");
    }
}
