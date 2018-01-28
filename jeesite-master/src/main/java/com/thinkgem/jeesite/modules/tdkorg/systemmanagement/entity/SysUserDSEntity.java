/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tdkorg.systemmanagement.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 人员数据抽取Entity
 * @author xubaifu
 * @version 2017-03-19
 */
public class SysUserDSEntity extends DataEntity<SysUserDSEntity> {
	
	private static final long serialVersionUID = 1L;
	private String companyId;		// company_id
	private String officeId;		// office_id
	private String loginName;		// login_name
	private String password;		// password
	private String no;		// no
	private String name;		// name
	private String email;		// email
	private String phone;		// phone
	private String mobile;		// mobile
	private String userType;		// user_type
	private String photo;		// photo
	private String loginIp;		// login_ip
	private Date loginDate;		// login_date
	private String loginFlag;		// login_flag
	
	private String A0101;
	private String A0100;
	private String kqDetailId;
	private String E0122;
	private String E0127;
	private String passWorldTdk;
	private String createTime;
	public String getDoorid() {
		return doorid;
	}

	public void setDoorid(String doorid) {
		this.doorid = doorid;
	}

	private String sysDate;
	private String C01UG;
	private String KQID;
	private String doorid;
	public String num;
	
	public SysUserDSEntity() {
		super();
	}

	public SysUserDSEntity(String id){
		super(id);
	}

	@Length(min=1, max=128, message="company_id长度必须介于 1 和 128 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	@Length(min=1, max=200, message="login_name长度必须介于 1 和 200 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=1, max=200, message="password长度必须介于 1 和 200 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=200, message="no长度必须介于 0 和 200 之间")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@Length(min=1, max=200, message="name长度必须介于 1 和 200 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=400, message="email长度必须介于 0 和 400 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=400, message="phone长度必须介于 0 和 400 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=400, message="mobile长度必须介于 0 和 400 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=2, message="user_type长度必须介于 0 和 2 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Length(min=0, max=2000, message="photo长度必须介于 0 和 2000 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Length(min=0, max=200, message="login_ip长度必须介于 0 和 200 之间")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	@Length(min=0, max=128, message="login_flag长度必须介于 0 和 128 之间")
	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getA0101() {
		return A0101;
	}

	public void setA0101(String a0101) {
		A0101 = a0101;
	}

	public String getE0122() {
		return E0122;
	}

	public void setE0122(String e0122) {
		E0122 = e0122;
	}

	public String getE0127() {
		return E0127;
	}

	public void setE0127(String e0127) {
		E0127 = e0127;
	}

	public String getPassWorldTdk() {
		return passWorldTdk;
	}

	public void setPassWorldTdk(String passWorldTdk) {
		this.passWorldTdk = passWorldTdk;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSysDate() {
		return sysDate;
	}

	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getC01UG() {
		return C01UG;
	}

	public void setC01UG(String c01ug) {
		C01UG = c01ug;
	}

	public String getKQID() {
		return KQID;
	}

	public void setKQID(String kQID) {
		KQID = kQID;
	}

	public String getA0100() {
		return A0100;
	}

	public void setA0100(String a0100) {
		A0100 = a0100;
	}

	public String getKqDetailId() {
		return kqDetailId;
	}

	public void setKqDetailId(String kqDetailId) {
		this.kqDetailId = kqDetailId;
	}
	
	
}