<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/3Aselect/select.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/3Aselect/select.css"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")},
					enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
				},
				messages: {
					name: {remote: "角色名已存在"},
					enname: {remote: "角色标示已存在"}
				},
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#officeIds").val(ids2);
					
					 var ids3 = [];
					$("#selected div").each(function(){
						ids3.push($(this).attr("data-value"));
					  });
					$("#columnIds").val(ids3);
					
					/*var ids4 = [];
					$("#selectedOffice div").each(function(){
						ids4.push($(this).attr("data-value"));
					  });
					$("#officeColumnIds").val(ids4); */
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(false);
			
			// 用户-机构
			var zNodes2=[
					<c:forEach items="${officeList}" var="office">{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},
		            </c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
			// 不选择父节点
			//tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			tree2.setting.check.chkboxType = { "Y" : "s", "N" : "s" };
			// 默认选择节点
			var ids2 = "${role.officeIds}".split(",");
			for(var i=0; i<ids2.length; i++) {
				var node = tree2.getNodeByParam("id", ids2[i]);
				try{tree2.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree2.expandAll(false);
			// 刷新（显示/隐藏）机构
			refreshOfficeTree();
			/* $("#dataScope").change(function(){
				refreshOfficeTree();
			}); */
			
			
			
			
			
		});
		function refreshOfficeTree(){
			/* if($("#dataScope").val()==9){
				$("#officeTree").show();
			}else{
				$("#officeTree").hide();
			} */
			$("#menuTree").hide();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/">角色列表</a></li>
		<li class="active"><a href="${ctx}/sys/role/form?id=${role.id}">角色<shiro:hasPermission name="sys:role:edit">${not empty role.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:role:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">部门名称:</label>
			<div class="controls">
                <sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}"
					title="机构" url="/sys/office/treeData" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色名称:</label>
			<div class="controls">
				<input id="oldName" name="oldName" type="hidden" value="${role.name}">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">角色标识:</label>
			<div class="controls">
				<input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">
				<form:input path="enname" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">角色类型:</label>
			<div class="controls">
				<form:select path="roleType" class="input-medium">
					<form:option value="assignment">任务分配</form:option>
					<form:option value="security-role">管理角色</form:option>
					<form:option value="user">普通角色</form:option>
				</form:select>
				<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
					工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span>
			</div>
		</div> --%>
		<div class="control-group" style="display: none;">
			<label class="control-label">是否系统数据:</label>
			<%-- <div class="controls">
				<form:select path="sysData">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span>
			</div> --%>
			<form:input path="sysData" value="1" htmlEscape="false" maxlength="1" class="required"/>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">是否可用</label>
			<%-- <div class="controls">
				<form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span>
			</div> --%>
			<form:input path="useable" value="1" htmlEscape="false" maxlength="1" class="required"/>
		</div>
		<div class="control-group" style="display: none;">
			<label class="control-label">数据范围:</label>
			<%-- <div class="controls">
				<form:select path="dataScope" class="input-medium">
					<form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">特殊情况下，设置为“按明细设置”，可进行跨机构授权</span>
			</div> --%>
			<form:input path="dataScope" value="1" htmlEscape="false" maxlength="1" class="required"/>
		</div>
		
		<%-- <div class="control-group">
			<label class="control-label">部门授权:</label>
		</div>
		<div class="control-group">
			<div id="two_way_list_selector_b" class="two_way_list_selector margin_top_10">
				<div class="select_l">
					<div class="option">
						<div class="l">可选</div>
						<!-- <div class="r">
							<a>数量</a>
						</div> -->
					</div>
					<form:hidden path="officeColumnIds"/>
					<div class="select_a" id="selectItemOffice">
						<c:forEach items="${officeList}" var="items" varStatus="num">
							<c:if test="${items.id != '' and items.id != 'null' and items.id != null}">
								<div data-value="${items.id }" data-index="0" class="item">${items.name }</div>
							</c:if>
						</c:forEach>
						<!-- <div data-value="2" data-index="1" class="item">价格</div>
						<div data-value="6" data-index="5" class="item">产地</div> -->
					</div>
				</div>
				<div class="select_btn">
					<div>
						<input type="button" value="--&gt;" class="button btn_a"> 
						<input type="button" value="--&gt;&gt;" class="button btn_add_all"> 
						<input type="button" value="&lt;&lt;--" class="button btn_remove_all"> 
						<input type="button" value="&lt;--" class="button btn_b">
					</div>
				</div>
				<div class="select_r">
					<div class="option">
						<div class="r" style="border-bottom: 1px solid #ddd;">已选</div>
					</div>
					<div class="select_b" id="selectedOffice">
						<c:forEach items="${officeSelectList}" var="items" varStatus="num">
							<c:if test="${items.id != '' and items.id != 'null' and items.id != null}">
								<div data-value="${items.id }" data-index="0" class="item">${items.name }</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div> --%>
		
		
		
		
		<div class="control-group">
			<label class="control-label">门禁授权:</label>
		</div>
		<div class="control-group">
			<div id="two_way_list_selector_a" class="two_way_list_selector margin_top_10">
				<div class="select_l">
					<div class="option">
						<div class="l">可选</div>
					</div>
					<form:hidden path="columnIds"/>
					<div class="select_a" id="selectItem">
						<c:forEach items="${doorList}" var="items" varStatus="num">
							<c:if test="${items.fDoorname != '' and items.fDoorname != 'null' and items.fDoorname != null}">
								<div data-value="${items.fControllerid }" data-index="0" class="item">${items.fDoorname }</div>
							</c:if>
						</c:forEach>
					
					</div>
				</div>
				<div class="select_btn">
					<div>
						<input type="button" value="--&gt;" class="button btn_a"> 
						<input type="button" value="--&gt;&gt;" class="button btn_add_all"> 
						<input type="button" value="&lt;&lt;--" class="button btn_remove_all"> 
						<input type="button" value="&lt;--" class="button btn_b">
					</div>
				</div>
				<div class="select_r">
					<div class="option">
						<div class="r" style="border-bottom: 1px solid #ddd;">已选</div>
					</div>
					<div class="select_b" id="selected">
						<c:forEach items="${doorSelectList}" var="items" varStatus="num">
							<c:if test="${items.fDoorname != '' and items.fDoorname != 'null' and items.fDoorname != null}">
								<div data-value="${items.fControllerid }" data-index="0" class="item">${items.fDoorname }</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		
		
		
		<div class="control-group">
			<label class="control-label">部门授权:</label>
			<div class="controls">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="menuIds"/>
				<div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
				<form:hidden path="officeIds"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
				<shiro:hasPermission name="sys:role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>