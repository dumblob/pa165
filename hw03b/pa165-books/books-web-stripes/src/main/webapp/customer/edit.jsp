<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="customer.edit.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.books.CustomersActionBean" var="actionBean"/>

        <s:form beanclass="cz.muni.fi.pa165.books.CustomersActionBean">
            <s:hidden name="customer.id"/>
            <fieldset><legend><f:message key="customer.edit.edit"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="save"><f:message key="customer.edit.save"/></s:submit>
                <s:submit name="cancel"><f:message key="customer.edit.cancel"/></s:submit>

            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>
