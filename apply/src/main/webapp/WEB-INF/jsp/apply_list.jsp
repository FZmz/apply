<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<style>
    .top-select-area {
        display: flex;
        align-items: center;
    }

    .select-item {
        display: flex;
        margin: 10px;
        align-items: center;
    }

    .operate-area {
        display: flex;
    }
</style>
<table class="easyui-datagrid" id="applyList" title="申请列表" data-options="singleSelect:false,collapsible:true,
		pagination:true,rownumbers:true,url:'http://www.iscs.com:30003/apply/toGetApplyListByRes?applyType=0&applyModule=All&applyStatus=1', method:'get', fitColumns:true,
		toolbar:toolbar_custom">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'applyId',width:100,align:'center'">审核编号</th>
        <th data-options="field:'applyModule',width:100,align:'center'">审核模块</th>
        <th data-options="field:'applyType',width:200,align:'center'">审核类型</th>
        <th data-options="field:'applyStatus',width:200,align:'center'">审核状态</th>
        <th data-options="field:'applyDate',width:300,align:'center'">申请日期</th>
        <%-- <th data-options="field:'status',width:60,align:'center',formatter:TAOTAO.formatCustomStatus">客户状态</th>--%>
        <th data-options="field:'note',width:130,align:'center', formatter:formatCustomNote">操作</th>
    </tr>
    </thead>
</table>
<div id="toolbar_custom" style=" padding: 3px 11px; background: #fafafa;">
    <div class="top-select-area">
        <div class="select-item">
            <div class="label">申请类型：</div>
            <div class="selector">
                <select id="se_applyType">
                    <option value="0" selected="selected">All</option>
                    <option value="1">add</option>
                    <option value="2">delete</option>
                    <option value="3">update</option>
                </select>
            </div>
        </div>

        <div class="select-item">
            <div class="label">申请模块：</div>
            <div class="selector">
                <select id="se_applyModule">
                    <option value="All" selected="selected">All</option>
                    <option value="schedule">schedule</option>
                    <option value="device">device</option>
                    <option value="technology">technology</option>
                    <option value="material">material</option>
                    <option value="quality">quality</option>
                    <option value="employee">employee</option>
                    <option value="system">system</option>
                </select>
            </div>
        </div>
        <div class="select-item">
            <div class="label">申请状态：</div>
            <div class="selector">
                <select id="se_applyStatus">
                    <option value="1" selected="selected">待审核</option>
                    <option value="2">审核通过</option>
                    <option value="3">审核未通过</option>
                </select>
            </div>
        </div>
        <div class="operate-area">
            <div class="top-btn">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-ok" onclick="queryEnsure()">确认</a>
            </div>
            <div class="top-btn">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="queryReset()">重置</a>
            </div>
        </div>
    </div>
    <%--	<c:forEach items="${sessionScope.sysPermissionList}" var="per" >--%>
    <%--		<c:if test="${per=='custom:add' }" >--%>
    <%--		    <div style="float: left;">  --%>
    <%--		        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="custom_add()">新增</a>  --%>
    <%--		    </div>  --%>
    <%--		</c:if>--%>
    <%--		<c:if test="${per=='custom:edit' }" >--%>
    <%--		    <div style="float: left;">  --%>
    <%--		        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="custom_edit()">编辑</a>  --%>
    <%--		    </div>  --%>
    <%--		</c:if>--%>
    <%--		<c:if test="${per=='custom:delete' }" >--%>
    <%--		    <div style="float: left;">  --%>
    <%--		        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-cancel"--%>
    <%--		        	 onclick="custom_delete()">删除</a>  --%>
    <%--		    </div>  --%>
    <%--		</c:if>--%>
    <%--	</c:forEach>--%>

    <%--	<div class="datagrid-btn-separator"></div>  --%>

    <%--	<div style="float: left;">  --%>
    <%--		<a href="#" class="easyui-linkbutton" plain="true" icon="icon-reload" onclick="custom_reload()">刷新</a>  --%>
    <%--	</div>  --%>

    <%--    <div id="search_custom" style="float: right;">--%>
    <%--        <input id="search_text_custom" class="easyui-searchbox"  --%>
    <%--            data-options="searcher:doSearch_custom,prompt:'请输入...',menu:'#menu_custom'"  --%>
    <%--            style="width:250px;vertical-align: middle;">--%>
    <%--        </input>--%>
    <%--        <div id="menu_custom" style="width:120px"> --%>
    <%--			<div data-options="name:'customId'">客户编号</div> --%>
    <%--			<div data-options="name:'customName'">客户名称</div>--%>
    <%--		</div>     --%>
    <%--    </div>  --%>
</div>
<script>
    let applyType = $('#se_applyType').val();
    let applyModule = $('#se_applyModule').val();
    let applyStatus = $('#se_applyStatus').val();
    var customNoteEditor;
    let applyEntity = {
        "applyId":0,
        "applyModule":"",
        "applyRoute":"",
        "applyType":1,
        "applyData":"",
        "applyStatus":1
    }
    //根据index拿到该行值
    function onCustomClickRow(index) {
        var rows = $('#customList').datagrid('getRows');
        return rows[index];

    }
    //格式化客户介绍
    function formatCustomNote(value, row, index) {
        console.log(row.applyId);
        if (row.applyStatus != 1){
            return `
                 <a href="#" style="pointer-events: none;opacity: 0.5" class="easyui-linkbutton" plain="true" icon="icon-ok">通过</a>
                <a href="#" style="pointer-events: none;opacity: 0.5" class="easyui-linkbutton" plain="true" icon="icon-no">拒绝</a>
            `
        }else{
            return "<a href=# class=easyui-linkbutton plain=true icon=icon-ok onclick=javascript:statusEnsure(" +row.applyId+ ",2)>"+"通过"+"</a>    " +
               "<a href=# class=easyui-linkbutton plain=true icon=icon-no onclick=javascript:statusEnsure("+row.applyId+",3)>"+"拒绝"+"</a>"
        }
    }
    function statusEnsure(id,status){
        $.messager.confirm('确认','确定审核id为：'+id+' 的申请吗？',function(r){
            applyEntity.applyId = id;
            applyEntity.applyStatus = status;
            if (r){
                $.post("http://www.iscs.com:30003/apply/toUpdateApplyStatus",applyEntity, function(data){
                    if(data.status == 200){
                        $.messager.alert('提示','审核成功!',undefined,function(){
                            $("#applyList").datagrid("reload");
                        });
                    }
                });
            }
        });
    }
    function queryReset() {
        $("#applyList").datagrid("reload");
    }

    // 根据自定义条件查询
    function queryEnsure() {
        // 获取三个select框的值
        applyType = $('#se_applyType').val();
        applyModule = $('#se_applyModule').val();
        applyStatus = $('#se_applyStatus').val();
        // 确认时搜索
        $("#applyList").datagrid({
            title: '申请列表',
            singleSelect: false,
            collapsible: true,
            pagination: true,
            rownumbers: true,
            method: 'get',
            nowrap: true,
            toolbar: "toolbar_custom",
            url: 'http://www.iscs.com:30003/apply/toGetApplyListByRes?applyType=' + applyType + '&applyModule=' + applyModule + '&applyStatus=' + applyStatus,
            loadMsg: '数据加载中......',
            fitColumns: true,//允许表格自动缩放,以适应父容器
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'applyId', width: 100, title: '审核编号', align: 'center'},
                {field: 'applyModule', width: 100, align: 'center', title: '审核模块'},
                {field: 'applyType', width: 200, align: 'center', title: '审核类型'},
                {field: 'applyStatus', width: 200, title: '审核状态', align: 'center'},
                {field: 'applyDate', width: 300, title: '申请日期', align: 'center'},
                {field: 'note', width: 130, title: '操作', align: 'center',formatter:formatCustomNote},
            ]]
        });
    }
</script>