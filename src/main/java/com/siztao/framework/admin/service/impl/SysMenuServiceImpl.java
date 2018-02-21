package com.siztao.framework.admin.service.impl;

import com.siztao.framework.admin.dao.SysMenuDao;
import com.siztao.framework.admin.entity.SysMenuEntity;
import com.siztao.framework.admin.entity.SysUserEntity;
import com.siztao.framework.admin.service.SysMenuService;
import com.siztao.framework.admin.service.SysUserService;
import com.siztao.framework.admin.shiro.ShiroUtils;
import com.siztao.framework.common.utils.Constant;
import com.siztao.framework.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService{
    @Autowired  private SysMenuDao sysMenuDao;
    @Autowired  private SysUserService  sysUserService;
    @Override
    public List<SysMenuEntity> queryListParentId(String parentId, List<String> menuIdList, String domainId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("parentId",parentId);
        if(StringUtils.isNotEmpty(domainId)){
            map.put("domainId",domainId);
        }
        List<SysMenuEntity> menuList = sysMenuDao.queryListParentId(map);
        if (menuIdList == null){
            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for(SysMenuEntity menu: menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(String userId, String domainId) {
        //系统管理员 ,拥有最高权限
        if(Constant.SUPER_ADMIN.equals(userId)){
            return getAllMenuList(null,domainId);
        }
        List<String> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList,domainId);
    }

    @Override
    public SysMenuEntity queryObject(String menuId) {
        return sysMenuDao.queryObject(menuId);
    }

    @Override
    public List<SysMenuEntity> queryList(Map<String, Object> map) {
        return  sysMenuDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysMenuDao.queryTotal(map);
    }

    @Override
    public void save(SysMenuEntity menu) {
        String  parentId = menu.getParentId();
        String maxId = sysMenuDao.queryMaxIdByParentId(parentId);
        menu.setMenuId(StringUtils.addOne(parentId,maxId));
        menu.setCreateUser(((SysUserEntity) ShiroUtils.getUserEntity()).getUserId());
        menu.setCreateTime(new Date());
        sysMenuDao.save(menu);
    }

    @Override
    public void update(SysMenuEntity menu) {
        menu.setUpdateUser(((SysUserEntity) ShiroUtils.getUserEntity()).getUserId());
        menu.setUpdateTime(new Date());
        sysMenuDao.update(menu);
    }

    @Override
    public void deleteBatch(String[] menuIds) {
        sysMenuDao.deleteBatch(menuIds);
    }

    @Override
    public List<SysMenuEntity> queryUserList(String userId) {
        return sysMenuDao.queryUserList(userId);
    }

    /**
     * 获取所有菜单
     * @param menuIdList
     * @param domainId
     * @return
     */
    private List<SysMenuEntity> getAllMenuList(List<String> menuIdList,String domainId){
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId("0",menuIdList,domainId);
        getMenuTreeList(menuList,menuIdList,domainId);
        return menuList;

    }
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList,List<String> menuIdList,String domainId){
        List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
        for(SysMenuEntity entity:menuList){
            if(entity.getType() == Constant.MenuType.CATALOG.getValue()){//目录
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(),menuIdList,domainId),menuIdList,domainId));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }
}
