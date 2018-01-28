/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tdkorg.systemmanagement.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity.SysUserDSEntity;

/**
 * 人员数据抽取DAO接口
 * @author xubaifu
 * @version 2017-03-19
 */
@MyBatisDao
public interface SysUserDSDao extends CrudDao<SysUserDSEntity> {
	/**
	 * 获取所有人员信息（通过人事系统人员信息表获取）
	 * @return
	 */
	List<SysUserDSEntity> getAllUser(SysUserDSEntity entity);
	/**
	 * 根据条件获取人员信息（通过3A系统SYS_OFFICE表获取）
	 * @return
	 */
	List<SysUserDSEntity> getUserById(SysUserDSEntity entity);
	
	/**
	 * 添加人员信息（插入到3A系统中）
	 */
	void addUser(SysUserDSEntity entity);
	/**
	 * 修改人员信息（插入到3A系统中）
	 */
	void updateUser(SysUserDSEntity entity);
	/**
	 * 根据更新日期查询人员信息
	 * @param entity
	 * @return
	 */
	List<SysUserDSEntity> getAllUserByUpdate(SysUserDSEntity entity);
	/**
	 * 删除人员信息
	 * @param entity
	 */
	void deleteUser(SysUserDSEntity entity);
	
}