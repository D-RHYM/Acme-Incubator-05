  
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-url code="entrepreneur.investmentRound.form.label.ticker" path="ticker"/>
	<acme:form-url code="entrepreneur.investmentRound.form.label.moment" path="moment"/>
	<acme:form-url code="entrepreneur.investmentRound.form.label.round" path="round"/>
	<acme:form-url code="entrepreneur.investmentRound.form.label.title" path="title"/>
	<acme:form-url code="entrepreneur.investmentRound.form.label.description" path="description"/>
	<acme:form-url code="entrepreneur.investmentRound.form.label.amount" path="amount"/>
	<acme:form-url code="entrepreneur.investmentRound.form.label.link" path="link"/>
	
	<a href=/acme-incubator/entrepreneur/activity/list?id=${id}>
		<acme:message code="entrepreneur.investmentRound.activity.list"/>
	</a>
	
		<br>
		<br>
	<acme:form-submit test="${command == 'show'}" code="authenticated.investmentRound.form.button.list-accounting-record"
		action="/authenticated/accounting-record/list?id=${id}" method="get" />	
		<acme:form-submit test="${command == 'show'}"
		code="authenticated.investmentRound.form.button.create-forum" action="/authenticated/discussion-forum/create?investId=${id}" method="get" />
		<acme:form-return code="entrepreneur.investmentRound.form.button.return"/>
</acme:form>