<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="https://sdk.amazonaws.com/js/aws-sdk-2.1.26.min.js"></script>
<title>Converter</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

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
				<a class="navbar-brand" href="#">Converter</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<s:if test="%{#session.email =='' || #session.email == null}">
						<li><a href="register.jsp">Sign Up</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Sign
								In<span class="caret"></span>
						</a>
							<div class="dropdown-menu" role="menu" style="padding: 10px">
								<form method="post" action="login">
									<div class="form-group">
										<label class="col-xs-12 contron-label paddingLeft0">Email
											Address</label> <input type="text" class="form-control" name="email">
									</div>
									<div class="form-group">
										<label class="col-xs-12 contron-label paddingLeft0">Password</label>
										<input type="password" class="form-control" name="password">
									</div>
									<button type="submit" class="btn btn-primary btn-block">Login</button>
								</form>
							</div></li>
					</s:if>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Welcome
						<s:if test="%{#session.email !=null}" >
							<s:property value="#session.email" />
						</s:if>
						<s:else>Guest</s:else>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu" role="menu">
						<s:if test="%{#session.email !=null}" >
							<li><a href="manage_account.jsp">Manage Account</a></li>
							<s:url action="logout" var="url"></s:url>
							<li><a href="${url}">Log Out</a></li>
						</s:if>
						<s:else>
							<li><a href="index.jsp">Back</a></li>
						</s:else>
					</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">
		<s:if test="hasActionMessages()">
			<div class="alert alert-warning">
				<s:actionmessage/>
			</div>
		</s:if>
		<header>
			<h1>Follow the instruction and convert your file for free</h1>
		</header>
		<s:hidden key="email" id="email" />
		<div class="row">
			<div class="col-xs-12 col-sm-4">
				<h2>Step One</h2>
				<div class="form-group">
					<label class="control-label">Select input file format</label> 
					<select class="form-control" id="from">
						<option>mov</option>
					</select>
				</div>
				<div class="form-group">
					<label class="control-label">Select output file
						format</label> 
					<select class="form-control" id="to">
						<option>mp4</option>
					</select>
				</div>
				<div class="form-group">
					<label class="control-label">Select file you want
						to convert</label>
					<input type="file" id="file-chooser" style="margin-bottom:5px"/>
					<script>
					$('#file-chooser').bind('change', function() {

						  //this.files[0].size gets the size of your file.
						  $("#fileName").val(this.files[0].name);
						  $("#fileSize").val((this.files[0].size)/1024/1024);
						});
					</script>
					<s:hidden key="fileName" id="fileName"></s:hidden> 
					<s:hidden key="fileSize" id="fileSize"></s:hidden> 
					<button id="upload-button" class="btn btn-success">Upload</button>
					<label id="results" style="display:inline-Block"></label>
					
					<script type="text/javascript">
						AWS.config.update({
						    accessKeyId: "AKIAJECRDECGP3WEEHHQ",
						    secretAccessKey: "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",
						    "region": "us-west-2"
						});
					  var bucket = new AWS.S3({params: {Bucket: 'input.yashi.bucket'}});
					
					  var fileChooser = document.getElementById('file-chooser');
					  var button = document.getElementById('upload-button');
					  var results = document.getElementById('results');
					  button.addEventListener('click', function() {
					    var file = fileChooser.files[0];
					    if (file) {
					      results.innerHTML = '';
					
					      var params = {Key: file.name, ContentType: file.type, Body: file};
					      bucket.upload(params, function (err, data) {
					        results.innerHTML = err ? 'ERROR!' : ('UPLOADED ' + file.name);
					        if(err == null){
						        $.ajax({
									method: "POST",
									data:{fileName: $("#fileName").val(), fileSize: $("#fileSize").val(), email: $("#email").val()},
									url:"/OnlineConvert/updateDB"
								})
					        }
					      });
					    } else {
					      results.innerHTML = 'Nothing to upload.';
					    }
					  }, false);
					</script>
				</div>
			</div>

			<div class="col-xs-12 col-sm-4">
				<h2>Step Two</h2>
				<div class="form-group">
					<label class="control-label">Enter Email if you are not a registered user</label> 
					<input type="text" class="form-control" id="guestEmail"> 
					<label>We will use this email address to send you the notice</label>
					<s:if test="%{#session.email ==null}" >
						<button type="button" class="btn btn-primary" id="verify">Verify your email</button>
						<script>
							$("#verify").on('click',function(){
								$.ajax({
									method: "POST",
									data:{guestEmail: $("#guestEmail").val()},
									url:"/OnlineConvert/verifyEmail"
								}).done(function(){
									alert( "Go to your email and activate!");
								})
							})
						</script>
					</s:if>
				</div>
			</div>

			<div class="col-xs-12 col-sm-4">
				<h2>Step Three</h2>
				<button type="button" class="btn btn-success" id="convert" style="margin-bottom:5px">Start conversion</button>
				<s:hidden key="flag" id="flag" />
				<div id="downloadBlk" style="display:none" class="alert alert-success"></div>
				<script>
					$("#convert").on('click', function(){
						var email;
						if($("#guestEmail").val() != ""){
							email = $("#guestEmail").val();
						}
						else{
							email = $("#email").val();
						}
						
						var newName = ($("#fileName").val()).split(".")[0] + "." + $("#to").val();
						$.ajax({
							method: "POST",
							data:{fileSize: $("#fileSize").val(), fileName: $("#fileName").val(), email: email, from:$("#from").val(), to:$("#to").val(), newFileName:newName},
							url:"/OnlineConvert/startConvert",
							success:function(html){
								$("#downloadBlk").html(html);
								$("#downloadBlk").css("display","");
								$("#file-chooser").val("");
								alert("File is Ready!");
								var s3 = new AWS.S3();
	
								// This URL will expire in one minute (60 seconds)
								var params = {Bucket: 'output.yashi.bucket', Key: newName, Expires: 600};
								var url = s3.getSignedUrl('getObject', params, function (err, url) {
									$("#downloadBlk").find("a").prop("href",url);
								});
							}
						})
					})
				</script>
			</div>
		</div>
		<hr>
		<div class="row">
			<div class="col-xs-12 col-sm-6">
				<div class="col-xs-4">
					<img src="img/file.png" alt="Upload" width="123px" />
				</div>
				<div class="col-xs-8">
					<h3>Multiple formats supported</h3>
					<label>We support the conversion between multiple audio,
						video, document, image, spreadsheet and presentation formats.</label>
				</div>
			</div>
			<div class="col-xs-12 col-sm-6">
				<div class="col-xs-4">
					<img src="img/upload.png" alt="Upload" />
				</div>
				<div class="col-xs-8">
					<h3>File conversion in the cloud</h3>
					<label>There is no need to install any software on your
						computer! Upload your files and we will do the job for you.</label>
				</div>
			</div>
		</div>
		<hr>
		<footer>
			<p>Copy right By CS218 team 4 SJSU</p>
		</footer>
	</div>
</body>
</html>