<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="${requestScope['status'] == 'PUBLISHED' || command == 'show'}">
	<acme:form-hidden path="investmentRoundId" />
	<acme:form-textbox code="authenticated.accounting-record.form.label.title" path="title" />
	<acme:form-moment code="authenticated.accounting-record.form.label.moment" path="moment" />
	<acme:form-textbox code="authenticated.accounting-record.form.label.body" path="body" />

	<acme:form-submit test="${command == 'create'}" code="authenticated.accounting-record.form.button.create" action="create" />
	
	<acme:form-return code="authenticated.accounting-record.form.button.return" />
</acme:form>
