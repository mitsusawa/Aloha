<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<title>Aloha</title>

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

<header>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container">
			<a class="navbar-brand" href="./">
				<img src="data:image/png;base64,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"
				     width="30" height="30" class="d-inline-block align-top mr-1" alt="Aloha">
				Aloha
			</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"
			        aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbar">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<a class="nav-link" href="./">Top <span class="sr-only">(current)</span></a>
					</li>
					<li class="nav-item dropdown active">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
						   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							Account
						</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="./logout">Logout</a>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</header>

<div class="container">
	<div class="page-header">
		<h1 id="tables">${tableName}</h1>
	</div>
	<section class="bs-docs-section">
		<div class="row">
			<div class="col-lg-12">
				<div class="bs-component">
					<table class="table table-hover" style="text-align: center;">
						<thead>
						<tr>
							<th scope="col" class="table-bordered"><span style="text-align: center;"></span></th>
							<th scope="col" class="table-active"><span style="color: red; text-align: center;">日</span>
							</th>
							<th scope="col" class="table-primary"><span style="text-align: center;">月</span></th>
							<th scope="col" class="table-success"><span style="text-align: center;">火</span></th>
							<th scope="col" class="table-primary"><span style="text-align: center;">水</span></th>
							<th scope="col" class="table-success"><span style="text-align: center;">木</span></th>
							<th scope="col" class="table-primary"><span style="text-align: center;">金</span></th>
							<th scope="col" class="table-active"><span style="color: blue; text-align: center;">土</span>
							</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${getTable}" var="list" begin="0" step="1" varStatus="i">
							<tr>
								<th scope="col" class="table-bordered"
								    style="text-align: center; vertical-align: middle;">${i.index + 1}</th>
								<c:forEach items="${list.array}" var="lessonArray" begin="0" step="1" varStatus="j">
									<td class="<c:choose><c:when test="${(j.index % 2 == 0 && i.index % 2 == 0) || (j.index % 2 == 1 && i.index % 2 == 1)}">table-light</c:when><c:otherwise>table-secondary</c:otherwise></c:choose>">
										<form:form method="get" action="./edit">
											<fieldset id="editPage_field">
												<div class="form-group">
													<input type="hidden" class="form-control" id="tableNum"
													       name="tableNum" value="${tableNum}"/>
												</div>
												<div class="form-group">
													<input type="hidden" class="form-control" id="wrapNum"
													       name="wrapNum" value="${i.index}"/>
												</div>
												<div class="form-group">
													<input type="hidden" class="form-control" id="editLessonNum"
													       name="editLessonNum" value="${j.index}"/>
												</div>
												<div class="form-group">
													<input id="editPage_link" type="submit" value="${lessonArray.name}"
													       style="border:none;background-color:transparent;color:black;text-decoration:underline;"/>
												</div>
											</fieldset>
										</form:form>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</section>
</div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="./js/bootstrap.min.js"></script>

<script type="text/javascript">
    $('.bs-component [data-toggle="popover"]').popover();
    $('.bs-component [data-toggle="tooltip"]').tooltip();
</script>

</body>
</html>
