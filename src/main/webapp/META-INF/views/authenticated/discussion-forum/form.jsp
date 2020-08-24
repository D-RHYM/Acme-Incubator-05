<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<jstl:if test="${command != 'create'}">
		<acme:form-textbox code="authenticated.discussion-forum.form.label.investmentRound.title" path="investmentRound.title" />
		<acme:form-textbox code="authenticated.discussion-forum.form.label.creator" path="creator.userAccount.username" />
	</jstl:if>


	<acme:form-submit test="${command == 'show'}" code="authenticated.discussion-forum.form.button.add-message"
		action="/authenticated/message/create?discussionForumId=${id}" method="get" />
	
	<acme:form-submit test="${command == 'show'}" code="authenticated.discussion-forum.form.button.list-messages" action="/authenticated/message/list?id=${id}"
		method="get" />
	<acme:form-return code="authenticated.discussion-forum.form.button.return" />
</acme:form>