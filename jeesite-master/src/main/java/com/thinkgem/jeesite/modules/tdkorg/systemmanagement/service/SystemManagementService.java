/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tdkorg.systemmanagement.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.db.CustomerContextHolder;
import com.thinkgem.jeesite.common.db.DynamicDataSource;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.dao.SystemManagementDao;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity.SystemManagementEntity;

/**
 * 系统管理Service
 * @author baifu
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class SystemManagementService extends CrudService<SystemManagementDao, SystemManagementEntity> {
	
	@Resource
	private SystemManagementDao systemManagementDao;
	/**
	 * 查新人事系统中的组织机构
	 * @param params
	 * @return
	 */
	public List<SystemManagementEntity> getOrganizationFromEHR(SystemManagementEntity entity){
		List<SystemManagementEntity> list = new ArrayList<SystemManagementEntity>();
		list = systemManagementDao.getAllOrganization(entity);
		return list;
	}
	/**
	 * 查询3A系统下的组织机构
	 * @return
	 */
	public List<SystemManagementEntity> getOrganizationFrom3A(SystemManagementEntity entity){
		List<SystemManagementEntity> list = new ArrayList<SystemManagementEntity>();
		list = systemManagementDao.getOrganizationById(entity);
		return list;
	}
	/**
	 * 新增3A系统中的组织结构
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void addOrganization(SystemManagementEntity entity){
		systemManagementDao.addOrganization(entity);
	}
	/**
	 * 修改3A系统中的组织结构
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void updateOrganization(SystemManagementEntity entity){
		systemManagementDao.updateOrganization(entity);
	}
	
	
	/**
	 * 新增3A系统中的组织结构
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void addOrganizationAll(List<SystemManagementEntity> listEHR){
		SystemManagementEntity entity = new SystemManagementEntity();
		List<SystemManagementEntity> list3A = new ArrayList<SystemManagementEntity>();
		int length = listEHR.size();
		
		//遍历查询结果，跟3A数据库中的组织结构对比，若有对应的结构，则修改为最新的，若没有则新增
		String parent_ids = "";
		int grade;
		String[] pids = new String[10];
		if(length>0){
			for(int i = 0; i < length; i++){
				//id = listEHR.get(i).getCodeitemidEHR();
				entity.setId3A(listEHR.get(i).getCodeitemidEHR());
				
				entity.setCode3A(listEHR.get(i).getCorCodeEHR());
				entity.setCreateDate3A(listEHR.get(i).getStartDateEHR());
				entity.setGrade3A(listEHR.get(i).getGradeEHR());
				entity.setId(listEHR.get(i).getCodeitemidEHR());
				entity.setName3A(listEHR.get(i).getCodeitemdescEHR());
				entity.setParentId3A(listEHR.get(i).getParentIdEHR());
				entity.setType3A(listEHR.get(i).getGradeEHR());
				entity.setUpdateDate3A("");
				//获取组织结构的父级结构
				grade = Integer.parseInt(listEHR.get(i).getGradeEHR());
				if(grade == 1){
					parent_ids="-1,";
					pids[grade-1]=parent_ids;
				}else if(grade>1){
					parent_ids = pids[grade-2]+listEHR.get(i).getParentIdEHR()+",";
					pids[grade-1]=parent_ids;
				}
				entity.setParentIds3A(pids[grade-1]);
				
				list3A = getOrganizationFrom3A(entity);
				
				System.out.println(list3A.size());
				if("0".equals(list3A.get(0).getNum())){
					addOrganization(entity);
				}else{
					updateOrganization(entity);
				}
			}
		}
       //根据当前日期查询信息，凡是更新日期在今日之前的删除
		List<SystemManagementEntity> listForDel = new ArrayList<SystemManagementEntity>();
		listForDel = getListByUpdate(entity);
		if(listForDel.size()>0){
			for(int j = 0; j < listForDel.size(); j++){
				System.out.println("--------"+listForDel.get(j).getId());
				System.out.println("++++++++"+listForDel.get(j).getParentId());
				if("1001".equals(listForDel.get(j).getId()) || "-1".equals(listForDel.get(j).getParentId()) || "-1".equals(listForDel.get(j).getId())){
					System.out.println(0);
				}else{
					delete(listForDel.get(j));
				}
			}
		}
	}
	/**
	 * 修改3A系统中的组织结构
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void updateOrganizationAll(List<SystemManagementEntity> list){
		//systemManagementDao.updateOrganization(entity);
	}
	
	

	public void getAllOfficeByUpdate(SystemManagementEntity entity){
		systemManagementDao.updateOrganization(entity);
	}
	

	public List<SystemManagementEntity> getListByUpdate(SystemManagementEntity entity){
		List<SystemManagementEntity> list = new ArrayList<SystemManagementEntity>();
		list = systemManagementDao.getListByUpdate(entity);
		return list;
	}
	
	
	/*public SystemManagement get(String id) {
		return super.get(id);
	}
	
	public List<SystemManagement> findList(SystemManagement systemManagement) {
		return super.findList(systemManagement);
	}
	
	public Page<SystemManagement> findPage(Page<SystemManagement> page, SystemManagement systemManagement) {
		return super.findPage(page, systemManagement);
	}
	
	@Transactional(readOnly = false)
	public void save(SystemManagement systemManagement) {
		super.save(systemManagement);
	}
	
	@Transactional(readOnly = false)
	public void delete(SystemManagement systemManagement) {
		super.delete(systemManagement);
	}*/
	
	
}