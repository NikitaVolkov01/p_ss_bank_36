package com.bank.publicinfo.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("(execution(* com.bank.publicinfo.service.*Service.save(..)))")
    public void allSaveMethods(){}

    @Pointcut("(execution(* com.bank.publicinfo.service.*Service.update(..)))")
    public void allUpdateMethods(){}

    @Pointcut("(execution(* com.bank.publicinfo.service.*Service.delete(..)))")
    public void allDeleteMethods(){}
}
