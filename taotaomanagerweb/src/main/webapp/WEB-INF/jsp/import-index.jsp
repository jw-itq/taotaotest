<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<div>
	<a class="easyui-linkbutton" onclick="importIndex()">一键导入商品数据到索引库</a>
</div>
<script type="text/javascript">
	function importIndex() {
		$.post("/index/import",function (data) {
			if(data.status == 200){
				$.messager.alert('提示','导入数据到索引库成功！！1');
			}else {
				$.messager.alert('提示','导入失败～～～');
			}
		})
	}
</script>
