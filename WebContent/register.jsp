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
				<a class="navbar-brand" href="home.htm">Convertor</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="index.htm">Back</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Sign
							In<span class="caret"></span>
					</a>
						<div class="dropdown-menu" role="menu" style="padding: 10px">
							<form method="post">
								<div class="form-group">
									<label class="col-xs-12 contron-label paddingLeft0">Email
										Address</label> <input type="text" class="form-control">
								</div>
								<div class="form-group">
									<label class="col-xs-12 contron-label paddingLeft0">Password</label>
									<input type="text" class="form-control">
								</div>
								<button type="submit" class="btn btn-primary btn-block">Login</button>
							</form>
						</div></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<div class="container">
		<div class="col-xs-12">
			<h2>Sign up for free</h2>
			<hr />
		</div>
		<div class="col-xs-12 col-sm-9">
			<form method="post" id="register" action="register">
				<s:if test="hasActionErrors()">
				   <div class="alert alert-danger">
				      <s:actionerror/>
				   </div>
				</s:if>
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-xs-12 contron-label label_reg">First Name</label> -->
<!-- 					<div class="inlineBlock"> -->
<!-- 						<input type="text" class="form-control" name="reFirstName" id="reFirstName"> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-xs-12 contron-label label_reg">Last Name</label> -->
<!-- 					<div class="inlineBlock"> -->
<!-- 						<input type="text" class="form-control" name="reLastName" id="reLastName"> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="form-group">
					<label class="col-xs-12 contron-label label_reg">Email
						Address *</label>
					<div class="inlineBlock">
						<input type="text" class="form-control" name="reEmail" id="reEmail">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-12 contron-label label_reg">Password *</label>
					<div class="inlineBlock">
						<input type="password" class="form-control" name="rePassword" id="rePassword">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-12 contron-label label_reg">Re-type
						password *</label>
					<div class="inlineBlock">
						<input type="password" class="form-control" id="verify">
						<label id="error" style="display:none"></label>
					</div>
				</div>
				<script>
					$("#verify").on("keyup", function(){
						if ($("#verify").val() !== $("#rePassword").val()){
							$("#verify").css("border-color","red");
							$("#error").css("display","");
							$("#error").html("Password does not match!");
							$("#submit").prop("disabled","disabled");
						}
						else{
							$("#verify").css("border-color","");
							$("#error").html("");
							$("#error").css("display","none");
							$("#submit").prop("disabled","");
						}
					})
				</script>
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-xs-12 contron-label label_reg">City</label> -->
<!-- 					<div class="inlineBlock"> -->
<!-- 						<input type="text" class="form-control" name="reCity" id="reCity"> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label class="col-xs-12 contron-label label_reg">Country</label> -->
<!-- 					<div class="inlineBlock"> -->
<!-- 						<input type="text" class="form-control" name="reCountry" id="reCountry"> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<button type="submit" class="btn btn-success" id="submit">Submit</button>
				<a type="button" class="btn btn-default" href="index.jsp">Exit</a>
			</form>
		</div>
	</div>
</body>
</html>