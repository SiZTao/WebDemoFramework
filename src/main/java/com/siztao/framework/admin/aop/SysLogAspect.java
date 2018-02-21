package com.siztao.framework.admin.aop;

import com.alibaba.fastjson.JSON;

import com.siztao.framework.admin.entity.SysLogEntity;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysLogService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.annotation.SysLog;
import com.siztao.framework.common.utils.HttpContextUtils;
import com.siztao.framework.common.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.siztao.framework.common.annotation.SysLog)")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setTitle(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        sysLog.setParams(params);

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        //用户名
        SysUserEntity sysUserEntity = (SysUserEntity) ShiroUtils.getUserEntity();
        String username = "";
        if ("login".equals(methodName)) {
            username = params;
        }
        if (null != sysUserEntity) {
            username = sysUserEntity.getUserName();
        }
        sysLog.setUserName(username);

        sysLog.setCreateDate(new Date());
        //保存系统日志
        sysLogService.save(sysLog);
    }

}
