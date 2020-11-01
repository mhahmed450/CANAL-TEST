package org.test.canal.annotations;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Value("${server.env.host}")
public @interface ServerHost {
  
   
}
