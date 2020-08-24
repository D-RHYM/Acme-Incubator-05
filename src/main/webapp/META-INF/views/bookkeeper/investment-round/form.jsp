  
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-url code="bookkeeper.investmentRound.form.label.ticker" path="ticker"/>
	<acme:form-url code="bookkeeper.investmentRound.form.label.moment" path="moment"/>
	<acme:form-url code="bookkeeper.investmentRound.form.label.round" path="round"/>
	<acme:form-url code="bookkeeper.investmentRound.form.label.title" path="title"/>
	<acme:form-url code="bookkeeper.investmentRound.form.label.description" path="description"/>
	<acme:form-url code="bookkeeper.investmentRound.form.label.amount" path="amount"/>
	<acme:form-url code="bookkeeper.investmentRound.form.label.link" path="link"/>
	
	<a href=/acme-incubator/bookkeeper/activity/list?id=${id}>
		<acme:message code="bookkeeper.investmentRound.activity.list"/>
	</a>
	
		<br>
		<br>
	<acme:form-submit test="${command == 'show'}" code="bookkeeper.investmentRound.form.button.list-accounting-record"
		action="/authenticated/accounting-record/list?id=${id}" method="get" />	
		<acme:form-return code="bookkeeper.investmentRound.form.button.return"/>
</acme:form>