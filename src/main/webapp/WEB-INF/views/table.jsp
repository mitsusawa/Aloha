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
				<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAYAAABV7bNHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABN5JREFUeNrsnN1R4zAQgEUm75cOzlQQd4BTwZm3eyNUAKkgUAFQgc3bvREqwKkAUwGmA18FnBbWcxpjWyt5JYexd0aTScCW8mm1P/IqQkwySR85+o6Dfv8jIvkSyrZQPi5ky49+i3yUgBDKmWxxDUxdStky2R4lrNQLIBzcFjsvZMcbz2Cg78jictCqjRzvzrkGyYE+4OyBgBqvZMelQzCgJTeyrRlud26rTSaAAvnyqnzkDJLsCyYi0SwlUzmWYy1ML5qRSX7ePFM+AiP5hDPNpjWyAZgHZjjCVhNnhv9/X3sfon3ggPMBnGlJsYkpoKzhs0s0pH3grBFO6PC7Fs4B4TJr6mjbA86NA3vzZWJtjfTM4pqmQCxCI25qb8DWXDrUGIByKuGsbG8yt7jmRXH3qlxAzGHgwl0sKYByjhE1i3e1AdTWsYkdWigwo44vWzRMQtzlRCSYjJP4nGmJCRNtqNkyky+USe2DWCwwHJs3L0ZJC1xLV9oQHDQgFwNskL8df1seAqBiYEBdSzIcHJBNPuNRQs7Ux9ZIC58q3jBBYKi7BPLDUtGoRYMhh72iqyEALQ5Biwh/B00TFEjGgLhV2LDfEFvBYO9gd/LKhQaFnoBE2NcSg0luBxAAdF3EPRcHIggE2okQXuKpyuvm3IAWjEsmRiCx8G+/SsoTkEGWGPN+s62QEuvZECODdS8bZN3HmH3vOpJgds0RBpv4Nhp0wggKvFGKrdp2jdAwh4wOoaziH5gMk2D3YIw0AsvrRhOhBQjrhwIt0qQj11Vg6TuSjgaCtquBe++4bM+1L2Rkg4YKEi3k51DJqs4m7D1CyHzsKpgCCsTIZALEDEi3nVFOGtQt+dgBhdMSa3fxFDijXmLaGIi7PvC7AYrECMUEkC46zccOSOfByrED0i2xwvPYo4MBRKz9eRuzBlEAjXqJUdR51Eb65zfTIDZ7OOdaYi6CRLR9NnvTb74BRb60B1Oa6tBKMLQqzgkDpmyz5gxgYBJsD63UZUl5rMxlg5wmqaAxskHF6xNjbAPa9yrve+kDEEXNXyzhXMmXZ0dB38fTW6jF7vOwgQtQYQEnEUznPAjaZH3ohgJoyQ0INWft0dZan0yaEVXVCBAcTmkbDLrurfAv1Wki/0tMfdaNSyfpMO6J4Rgz8VmJAYf3jqD1gYSHZ1jjoICqPbqlg9pDMcjgFe9kS+uFBgzF6nB8a089x8pRvFAoX/5C878XhPtBTHXaUYHxi2HMiRxvRomTOOuDbgj2KiTAXmnKUziMe1XAxWKDtB4KTwzGDEHnrmtWsR/dJGSCdkBmTdnn4gAUthje3MIjxh3ejzrrUCR1T9SkLQegPUFdvxjZFk3QzWzQFK8oB34pIccOy+sKohYt+gJKLdb4rkdSCzCewSNiSzAdoeSEqtejalHcC1DDeXmKtA3ujnh9FUxuDYwyaOy18v7WoK/eXuzaAE7eVv6GsG+FG9mo3g+XeO++SIDwC1M72xBgc+8+pi1lvZS+Uq44iNLZra54Emd2xQgpxZrrrr7axnSuKwk2ymswbnhu8SZGP3aC3iMhxk/GcBr6gxTlTPw/n3FHqZc2TvxaXK71L8HgL71shdnGfCF6/i4QVawyY9SkKmPf4WB77f8i+OpwS9NJwcqbPvoAM8kkPPJPgAEAyEmTr+M2lQ0AAAAASUVORK5CYII="
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
							Menu
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
							<th scope="col" class="table-active"><span style="color: red; text-align: center;">日</span></th>
							<th scope="col" class="table-primary"><span style="text-align: center;">月</span></th>
							<th scope="col" class="table-success"><span style="text-align: center;">火</span></th>
							<th scope="col" class="table-primary"><span style="text-align: center;">水</span></th>
							<th scope="col" class="table-success"><span style="text-align: center;">木</span></th>
							<th scope="col" class="table-primary"><span style="text-align: center;">金</span></th>
							<th scope="col" class="table-active"><span style="color: blue; text-align: center;">土</span></th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${getTable}" var="list" begin="0" step="1" varStatus="i">
							<tr>
								<div class="form-group">
									<th scope="col" class="table-bordered"><span style="text-align: center;">${i.index + 1}</span></th>
								</div>
								<c:forEach items="${list.array}" var="lessonArray" begin="0" step="1" varStatus="j">
									<td  class="<c:choose><c:when test="${(j.index % 2 == 0 && i.index % 2 == 0) || (j.index % 2 == 1 && i.index % 2 == 1)}">table-light</c:when><c:otherwise>table-secondary</c:otherwise></c:choose>">
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
