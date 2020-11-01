package org.test.canal.annotations;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Value("${server.env.port}")
public @interface ServerPort {
}
