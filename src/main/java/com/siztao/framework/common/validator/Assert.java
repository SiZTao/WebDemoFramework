package com.siztao.framework.common.validator;

import com.siztao.framework.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

public abstract class Assert {
    public static void isBlank(String str,String message){
        if(StringUtils.isBlank(str)){
            throw new RRException(message);
        }
    }
    public static void isNull(Object object ,String message){
        if(object == null){
            throw new RRException(message);
        }
    }
}
