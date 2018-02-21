package com.siztao.framework.admin.shiro;

import com.siztao.framework.admin.dao.SysMenuDao;
import com.siztao.framework.admin.dao.SysUserDao;
import com.siztao.framework.admin.entity.SysMenuEntity;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.common.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class UserRealm extends AuthorizingRealm{
    @Autowired  private SysUserDao  sysUserDao;
    @Autowired  private SysMenuDao  sysMenuDao;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        String userId = user.getUserId();
        List<String> permsList = null;
        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN.equals(userId)){
            List<SysMenuEntity> menuList = sysMenuDao.queryList(new HashMap<String,Object>());
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu:menuList){
                permsList.add(menu.getPerms());
            }
        }else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<String>();

        for(String perms:permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String  username = (String) token.getPrincipal();
        String  password = new String((char[]) token.getCredentials());
        //查询用户信息
        SysUserEntity   user = sysUserDao.queryByUserName(username);
        //账户不存在
        if(user == null){
            throw new UnknownAccountException("账户或密码不正确");
        }
        if(!password.equals(user.getPassWord())){
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        // 把当前用户放入到session中
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute(Constant.CURRENT_USER,user);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password,getName());
        return info;
    }
}
