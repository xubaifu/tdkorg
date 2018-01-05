/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * ehr系统Entity
 * @author baifu
 * @version 2017-03-07
 */
public class SystemManagementEntity extends DataEntity<SystemManagementEntity> {
	
	private static final long serialVersionUID = 1L;
	
	public String codeitemidEHR;
	public String codeitemdescEHR;
	public String parentIdEHR;
	public String gradeEHR;
	public String corCodeEHR;
	public String startDateEHR;
	
	
	public String id3A;
	public String name3A;
	public String parentId3A;
	public String grade3A;
	public String code3A;
	public String parentIds3A;
	public String type3A;
	public String createDate3A;
	public String updateDate3A;
	public String sort3A;
	public String areaId3A; 
	
	public String parentId;
	
	public String num;
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCodeitemidEHR() {
		return codeitemidEHR;
	}
	public void setCodeitemidEHR(String codeitemidEHR) {
		this.codeitemidEHR = codeitemidEHR;
	}
	public String getCodeitemdescEHR() {
		return codeitemdescEHR;
	}
	public void setCodeitemdescEHR(String codeitemdescEHR) {
		this.codeitemdescEHR = codeitemdescEHR;
	}
	public String getParentIdEHR() {
		return parentIdEHR;
	}
	public void setParentIdEHR(String parentIdEHR) {
		this.parentIdEHR = parentIdEHR;
	}
	public String getGradeEHR() {
		return gradeEHR;
	}
	public void setGradeEHR(String gradeEHR) {
		this.gradeEHR = gradeEHR;
	}
	public String getCorCodeEHR() {
		return corCodeEHR;
	}
	public void setCorCodeEHR(String corCodeEHR) {
		this.corCodeEHR = corCodeEHR;
	}
	public String getStartDateEHR() {
		return startDateEHR;
	}
	public void setStartDateEHR(String startDateEHR) {
		this.startDateEHR = startDateEHR;
	}
	public String getId3A() {
		return id3A;
	}
	public void setId3A(String id3a) {
		id3A = id3a;
	}
	public String getName3A() {
		return name3A;
	}
	public void setName3A(String name3a) {
		name3A = name3a;
	}
	public String getParentId3A() {
		return parentId3A;
	}
	public void setParentId3A(String parentId3A) {
		this.parentId3A = parentId3A;
	}
	public String getGrade3A() {
		return grade3A;
	}
	public void setGrade3A(String grade3a) {
		grade3A = grade3a;
	}
	public String getCode3A() {
		return code3A;
	}
	public void setCode3A(String code3a) {
		code3A = code3a;
	}
	public String getParentIds3A() {
		return parentIds3A;
	}
	public void setParentIds3A(String parentIds3A) {
		this.parentIds3A = parentIds3A;
	}
	public String getType3A() {
		return type3A;
	}
	public void setType3A(String type3a) {
		type3A = type3a;
	}
	public String getCreateDate3A() {
		return createDate3A;
	}
	public void setCreateDate3A(String createDate3A) {
		this.createDate3A = createDate3A;
	}
	public String getUpdateDate3A() {
		return updateDate3A;
	}
	public void setUpdateDate3A(String updateDate3A) {
		this.updateDate3A = updateDate3A;
	}
	public String getSort3A() {
		return sort3A;
	}
	public void setSort3A(String sort3a) {
		sort3A = sort3a;
	}
	public String getAreaId3A() {
		return areaId3A;
	}
	public void setAreaId3A(String areaId3A) {
		this.areaId3A = areaId3A;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	
	
}