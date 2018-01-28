/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tdkorg.systemmanagement.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.dao.SysUserDSDao;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity.SysUserDSEntity;

/**
 * 人员数据抽取Service
 * @author xubaifu
 * @version 2017-03-19
 */
@Service
@Transactional(readOnly = true)
public class SysUserDSService extends CrudService<SysUserDSDao, SysUserDSEntity> {
	@Resource
	private SysUserDSDao sysUserDSDao;
	/**
	 * 查新人事系统中的人员信息
	 * @param params
	 * @return
	 */
	
	public List<SysUserDSEntity> getUserFromEHR(SysUserDSEntity entity){
		List<SysUserDSEntity> list = new ArrayList<SysUserDSEntity>();
		list = sysUserDSDao.getAllUser(entity);
		return list;
	}
	/**
	 * 查询3A系统下的人员信息
	 * @return
	 */
	public List<SysUserDSEntity> getUserFrom3A(SysUserDSEntity entity){
		List<SysUserDSEntity> list = new ArrayList<SysUserDSEntity>();
		list = sysUserDSDao.getUserById(entity);
		return list;
	}
	/**
	 * 新增3A系统中的人员信息
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void addUser(SysUserDSEntity entity){
		sysUserDSDao.addUser(entity);
	}
	/**
	 * 修改3A系统中的人员信息
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void updateUser(SysUserDSEntity entity){
		sysUserDSDao.updateUser(entity);
	}
	/**
	 * 根据更新日期查询人员信息
	 * @param entity
	 * @return
	 */
	public List<SysUserDSEntity> getAllUserByUpdate(SysUserDSEntity entity){
		List<SysUserDSEntity> list = new ArrayList<SysUserDSEntity>();
		list = sysUserDSDao.getAllUserByUpdate(entity);
		return list;
	}
	/**
	 * 删除人员信息
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void deleteUser(SysUserDSEntity entity){
		sysUserDSDao.deleteUser(entity);
	}
	
	@Transactional(readOnly = false)
	public void startOrEndUser(List<SysUserDSEntity> listEHR){
		SysUserDSEntity entity = new SysUserDSEntity();
		List<SysUserDSEntity> list3A = new ArrayList<SysUserDSEntity>();
		int length = listEHR.size();
		//遍历查询结果，跟3A数据库中的人员信息对比，若有对应的结构，则修改为最新的，若没有则新增
		if(length>0){
			for(int i = 0; i < length; i++){
				System.out.println(i);
				if(listEHR.get(i).getE0122() == null){
					break;
				}
				entity.setName(listEHR.get(i).getA0101());
				entity.setOfficeId(listEHR.get(i).getE0122());
				entity.setLoginName(listEHR.get(i).getE0127());
				entity.setNo(listEHR.get(i).getE0127());
				entity.setCreateTime(listEHR.get(i).getCreateTime());
				entity.setKQID(listEHR.get(i).getC01UG());
				entity.setPassword(SystemService.entryptPassword("admin"));
				entity.setKqDetailId(listEHR.get(i).getA0100());
				list3A = getUserFrom3A(entity);
				if("0".equals(list3A.get(0).getNum())){
					addUser(entity);
				}else{
					updateUser(entity);
				}
			}
		}
		//根据当前日期查询人员信息，凡是更新日期在今日之前的人员删除
		List<SysUserDSEntity> listForDel = new ArrayList<SysUserDSEntity>();
		listForDel = getAllUserByUpdate(entity);
		if(listForDel.size()>0){
			for(int j = 0; j < listForDel.size(); j++){
				if(!"-1".equals(listForDel.get(j).getCompanyId())){
					entity.setNo(listForDel.get(j).getNo());
					deleteUser(entity);
				}else{
					System.out.println(0);
				}
				
			}
		}
	}
	
	
	
}