<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="${command != 'create'}">
	<acme:form-hidden path="threadId" />
	<acme:form-textbox code="authenticated.participant.form.label.username" path="authenticated.userAccount.username" />

	<acme:form-submit test="${command == 'create'}" code="authenticated.participant.form.button.create" action="create" />
	<acme:form-submit test="${command != 'create' && requestScope['messageThread.creator.id'] == principal.getActiveRoleId()}" code="authenticated.participant.form.button.delete" action="delete" />
	<acme:form-return code="authenticated.participant.form.button.return" />

	<jstl:forEach items="${requestScope}" var="url">
		<jstl:out value="${url.key}" /></br>
		<jstl:out value="${url.value}" /></br></br>
	</jstl:forEach>
</acme:form>