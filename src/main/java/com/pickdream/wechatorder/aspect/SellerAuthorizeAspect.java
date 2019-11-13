package com.pickdream.wechatorder.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//@Aspect
//@Component
public class SellerAuthorizeAspect {

//    @Pointcut("execution(public * com.pickdream.wechatorder.controller.Seller*.*(..))"+
//    "&& !execution(public * com.pickdream.wechatorder.controller.SellerUserController.*(..))")
//    public void verify(){
//
//    }
//    @Before("verify()")
//    public void doVerify(){
//        ServletRequestAttributes attributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//    }
}
