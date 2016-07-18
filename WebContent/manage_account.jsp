<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="https://sdk.amazonaws.com/js/aws-sdk-2.1.26.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Converter</title>
</head>
<body>
<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://cs218.cmghf6skbbba.us-west-2.rds.amazonaws.com:3306/cs218"
     user="root"  password="12345678"/>
 
<sql:query dataSource="${snapshot}" var="result">
SELECT * from File WHERE email= '<s:property value="#session.email" />' ;
</sql:query>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Convertor</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="home.jsp">Back</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Welcome<s:property value="#session.email" />
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="manage_account.jsp">Manage Account</a></li>
							<s:url action="logout" var="url"></s:url>
							<li><a href="${url}">Log Out</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<div class="container">
		<div role="tabpanel">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#overview"
					aria-controls="overview" role="tab" data-toggle="tab">Overview</a></li>
				<li role="presentation"><a href="#profile"
					aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
			</ul>

			<!-- Tab panes -->
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="overview">
					<div class="col-xs-12 row">
						<h1 class="page-header">Dashboard</h1>
						<%-- <div class="form-group">
							<input type="text" class="form-control inlineBlock pull-right"
								style="width: 300px" placeholder="file name" name="searchString">
							<s:submit cssClass="btn btn-primary inlineBlock" action="search" value="Search"/>
						</div>
						<div>
							<label>Result</label>
							<table class="table table-bordered table-striped table-responsive">
								<thead>
									<tr>
										<td>File Name</td>
										<td>File size</td>
									</tr>
								</thead>
								
								<tbody>
								<s:iterator var="row" value="list">
									<tr>
										<td>$row[0]</td>
										<td>$row[2]</td>
									</tr>
								</s:iterator>
								</tbody>
							</table>
						</div> --%>
						<div>
							<label>History</label> <label class="pull-right">You have
								5GB available space</label>
							<table
								class="table table-bordered table-striped table-responsive">
								<thead>
									<tr>
										<td>File Name</td>
										<td>File size</td>
										<td></td>
									</tr>
								</thead>
								<tbody>
								<c:forEach var="row" items="${result.rows}">
									<tr>
										<td><c:out value="${row.file_name}"/></td>
										<td><c:out value="${row.size}"/></td>
										<td><button class="btn btn-primary" onclick="Download('<c:out value="${row.file_name}"/>', '<c:out value="${row.type}"/>');return false">Download</button></td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							<script>
								function Download(fileName, type){
									AWS.config.update({
									    accessKeyId: "AKIAJECRDECGP3WEEHHQ",
									    secretAccessKey: "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",
									    "region": "us-west-2"
									});
									var s3 = new AWS.S3();
									var params;
									// This URL will expire in one minute (60 seconds)
									if(type === "input"){
										params = {Bucket: 'input.yashi.bucket', Key: fileName, Expires: 600};
									}
									if(type==="output"){
										params = {Bucket: 'output.yashi.bucket', Key: fileName, Expires: 600};
									}
									var url = s3.getSignedUrl('getObject', params, function (err, url) {
										window.open(url);
									})
								}
							</script>
						</div>
						<a href="home.jsp" class="btn btn-default pull-right">Back</a>
					</div>
				</div>
				<div role="tabpanel" class="tab-pane" id="profile">
					<div class="col-xs-12 row">
						<h1 class="page-header">Update Your Profile</h1>
						<form method="post" id="update" action="update">
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-xs-12 contron-label label_reg">First -->
<!-- 									Name</label> -->
<!-- 								<div class="inlineBlock"> -->
<!-- 									<input type="text" class="form-control" name="updateFirstName"> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-xs-12 contron-label label_reg">Last -->
<!-- 									Name</label> -->
<!-- 								<div class="inlineBlock"> -->
<!-- 									<input type="text" class="form-control" name="updatelastName"> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="form-group">
								<label class="col-xs-12 contron-label label_reg">Password</label>
								<div class="inlineBlock">
									<input type="password" class="form-control" name="updatePassword" id="updatePassword">
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-12 contron-label label_reg">Confirm
									password *</label>
								<div class="inlineBlock">
									<input type="password" class="form-control" id="verifychange">
									<label id="updateerror" style="display:none"></label>
								</div>
							</div>
							<script>
								$("a[href='#profile']").on('click',function(){
									if($("#updatePassword").val() == null || $("#updatePassword").val()=== ""){
										$("#updatechange").prop("disabled","disabled");
									}
								})
									$("#verifychange").on("keyup", function(){
										if ($("#verifychange").val() !== $("#updatePassword").val()){
											$("#verifychange").css("border-color","red");
											$("#updateerror").css("display","");
											$("#updateerror").html("Password does not match!");
											$("#updatechange").prop("disabled","disabled");
										}
										else{
											$("#verifychange").css("border-color","");
											$("#updateerror").html("");
											$("#updateerror").css("display","none");
											$("#updatechange").prop("disabled","");
										}
									})
							</script>
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-xs-12 contron-label label_reg">City</label> -->
<!-- 								<div class="inlineBlock"> -->
<!-- 									<input type="text" class="form-control" name="updateCity"> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="form-group"> -->
<!-- 								<label class="col-xs-12 contron-label label_reg">Country</label> -->
<!-- 								<div class="inlineBlock"> -->
<!-- 									<input type="text" class="form-control" name="updateCountry"> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<button type="submit" class="btn btn-primary" id="updatechange">Update</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>