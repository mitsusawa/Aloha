<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  errorPage="./error.jsp" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<title>新規登録</title>

	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<style type="text/css">
		.bs-component + .bs-component {
			margin-top: 1rem;
		}

		@media (min-width: 768px) {
			.bs-docs-section {
				margin-top: 8em;
			}

			.bs-component {
				position: relative;
			}

			.bs-docs-section .page-header h1 {
				padding: .5rem 0;
				margin-bottom: 2rem;
				border-bottom: 1px solid #eee;
			}

			.bs-component .modal {
				position: relative;
				top: auto;
				right: auto;
				bottom: auto;
				left: auto;
				z-index: 1;
				display: block;
			}

			.bs-component .modal-dialog {
				width: 90%;
			}

			.bs-component .popover {
				position: relative;
				display: inline-block;
				width: 220px;
				margin: 20px;
			}

			.nav-tabs {
				margin-bottom: 15px;
			}

			.progress {
				margin-bottom: 10px;
			}
		}
	</style>
</head>
<body>
<div class="container">
	<div class="row">
		<p>
		<h1 id="title">新規登録</h1></p>
	</div>
	<div class="row">
		<div class="col-md-3 col-lg-3"></div>
		<div class="col-md-6 col-lg-6">
			<div class="well well-sm">
				<f:form modelAttribute="signupForm" method="post" action="signup">
					<fieldset id="signup_field">
						<div class="form-group">
							<label>ユーザID</label>
							<input type="text" class="form-control" id="signupUserName" name="signupUserName"
							       placeholder="ログインIDを入力してください" autofocus="" required=""/>
						</div>
						<div class="form-group">
							<label>パスワード</label>
							<input type="password" class="form-control" id="signupPassword" name="signupPassword"
							       placeholder="パスワードを入力してください" required=""/>
						</div>
						<div class="form-group">
							<input id="signup_button" type="submit" value="ログイン"/>
						</div>
					</fieldset>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</f:form>
			</div>
		</div>
		<div class="col-md-3 col-lg-3"></div>
	</div>
</div>
</body>
</html>
