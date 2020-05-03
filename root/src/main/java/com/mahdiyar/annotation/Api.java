package com.mahdiyar.annotation;

import io.swagger.annotations.Authorization;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@io.swagger.annotations.Api()
public @interface Api {
    @AliasFor(annotation = io.swagger.annotations.Api.class)
    String value() default "";

    @AliasFor(annotation = io.swagger.annotations.Api.class)
    String[] tags() default "v1";

    @AliasFor(annotation = io.swagger.annotations.Api.class)
    String produces() default MediaType.APPLICATION_JSON_VALUE;

    @AliasFor(annotation = io.swagger.annotations.Api.class)
    String consumes() default MediaType.APPLICATION_JSON_VALUE;

    @AliasFor(annotation = io.swagger.annotations.Api.class)
    Authorization[] authorizations() default @Authorization(value = "");

    @AliasFor(annotation = io.swagger.annotations.Api.class)
    boolean hidden() default false;
}
