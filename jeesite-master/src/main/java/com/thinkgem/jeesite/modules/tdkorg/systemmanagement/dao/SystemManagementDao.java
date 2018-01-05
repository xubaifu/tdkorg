package com.thinkgem.jeesite.modules.tdkorg.systemmanagement.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity.SystemManagementEntity;
/**
 * 系统管理DAO接口
 * @author baifu
 * @version 2017-03-07
 */
@MyBatisDao
public interface SystemManagementDao extends CrudDao<SystemManagementEntity> {
	/**
	 * 获取所有组织架构（通过人事系统组织架构表获取）
	 * @return
	 */
	List<SystemManagementEntity> getAllOrganization(SystemManagementEntity entity);
	/**
	 * 根据条件获取组织架构（通过3A系统SYS_OFFICE表获取）
	 * @return
	 */
	List<SystemManagementEntity> getOrganizationById(SystemManagementEntity entity);
	
	/**
	 * 添加组织架构（插入到3A系统中）
	 */
	void addOrganization(SystemManagementEntity entity);
	/**
	 * 修改组织架构（插入到3A系统中）
	 */
	void updateOrganization(SystemManagementEntity entity);
	
	//获得未更新数据
	List<SystemManagementEntity> getListByUpdate(SystemManagementEntity entity);
}
