package com.siztao.framework.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 *
 * @author lipengjun
 * @date 2017年11月18日 下午13:13:23
 */
@Controller
public class SysPageController {

   @RequestMapping("/sys/main")
   public String main(){
       return "sys/main";
   }
    @RequestMapping("/sys/menu")
    public String menu(){
        return "sys/menu";
    }
    @RequestMapping("/sys/role")
    public String role(){
        return "sys/role";
    }
    @RequestMapping("/sys/user")
    public String user(){
        return "sys/user";
    }
    @RequestMapping("/sys/dept")
    public String dept(){
        return "sys/dept";
    }
    @RequestMapping("/sys/domain")
    public String domain(){
        return "sys/domain";
    }
    @RequestMapping("/sys/oss")
    public String oss(){
        return "sys/oss";
    }
    @RequestMapping("/sys/config")
    public String config(){
        return "sys/config";
    }
    @RequestMapping("/sys/log")
    public String log(){
        return "sys/log";
    }
    @RequestMapping("/sys/dict")
    public String dict(){
        return "sys/dict";
    }
}
