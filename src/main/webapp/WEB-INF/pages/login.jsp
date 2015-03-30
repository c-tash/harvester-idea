<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Вход на Харвестер</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
<!--     <link href="../css/bootstrap.css" rel="stylesheet"> -->
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
  </head>

  <body>

	<div class="container">
		<div class="form-signin">
			<form action="loginsubmit" method="post" id="signinForm">
			  <h2 class="form-signin-heading">Введитие логин и пароль для входа на Харвестер</h2>
			  <input type="text" class="input-block-level" placeholder="Логин" name="username">
			  <input type="password" class="input-block-level" placeholder="Пароль" name="password">
			  <%--<label class="checkbox">--%>
			    <%--<input type="checkbox" value="remember-me"> Remember me--%>
			  <%--</label>--%>
			  <div class="container">
			  	<button class="btn btn-large btn-primary" type="submit">Войти</button>
			  </div>
			</form>
			<form class="form-inline" action="register">
				<div class="form-group">
					<label for="btnSignUp">В первый раз здесь?</label>
					<button class="btn btn-small btn-info" type="submit" id="btnSignUp">Регистрация</button>
				</div>
	      	</form>
		</div>
	</div>
    
     <!-- /container -->
	
	
	<!-- Include Bootstrap Asserts JavaScript Files. -->
	
    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-transition.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-alert.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-modal.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-dropdown.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-scrollspy.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-tab.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-tooltip.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-popover.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-button.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-collapse.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-carousel.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-typeahead.js"></script>

  </body>
</html>
