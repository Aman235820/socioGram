package com.aman.socialMedia.Exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

        String resourceName;
        String fieldName;
        long fieldValue;

        public ResourceNotFoundException (String resourceName , String fieldName , long fieldValue ){
            super(String.format("%s with %s : %s - Not found !! " , resourceName , fieldName , fieldValue));
            this.resourceName = resourceName;
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;

        }

}
