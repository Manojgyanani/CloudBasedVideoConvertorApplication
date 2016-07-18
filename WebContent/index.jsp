<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Converter</title>
</head>
<body>
	<div class="container">
		<header class="header">
			<h1>
				On-line Converter&nbsp;<small>Convert anything on the cloud</small>
			</h1>
		</header>
		<hr>
		<img class="pull-left" src="img/logo.png" width="300" height="190"
			alt="CloudConverter">
		<h1 class="title_logo">
			Convert your file on <b>Cloud</b>
		</h1>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				<h2>Register</h2>
				<ul>
					<li><b>Upon register, you can convert files without limit</b></li>
					<li><b>Upon register, you can receive a storage space up
							to 5GB</b></li>
					<li><b>Upon register, you can search for previous files
							you stored within your account</b></li>
				</ul>
				<a href="register.jsp" class="btn btn-primary ">Register</a>
				<h2>Guest</h2>
				<ul>
					<li><b>You can convert any file with the limit of 25MB</b></li>
					<li><b>We will send the file back to you via email</b></li>
				</ul>
				<a href="home.jsp" class="btn btn-primary ">Start your trial</a>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<s:form method="post" id="login" action="login">
					<h2>Login</h2>
					<s:if test="hasActionErrors()">
					   <div class="alert alert-danger">
					      <s:actionerror/>
					   </div>
					</s:if>
					<div class="form-group">
						<label class="col-xs-12 contron-label paddingLeft0">Email
							Address</label> <input name="email" id="email" type="text"
							class="form-control">
					</div>
					<div class="form-group">
						<label class="col-xs-12 contron-label paddingLeft0">Password</label>
						<input name="password" id="password" type="password"
							class="form-control">
					</div>
					<s:submit value="Login" cssClass="btn btn-primary" />
				</s:form>
			</div>
		</div>
		<hr>
		<footer class="footer">
			<p>Copy right By CS218 team 4 SJSU</p>
		</footer>
	</div>
</body>
</html>