<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="customer.list.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.books.CustomersActionBean" var="actionBean"/>

        <s:errors/>
        <p><f:message key="customer.list.allcustomers"/></p>

        <table class="basic">
            <tr>
                <th>id</th>
                <th><f:message key="customer.firstname"/></th>
                <th><f:message key="customer.lastname"/></th>
                <th><f:message key="customer.address"/></th>
                <th><f:message key="customer.phone"/></th>
                <th></th>
                <th></th>
            </tr>
            <!-- zavola getBoos alebo ine get netody -->
            <c:forEach items="${actionBean.customers}" var="customer">
                <tr>
                    <td>${customer.id}</td>
                    <td><c:out value="${customer.firstName}"/></td>
                    <td><c:out value="${customer.lastName}"/></td>
                    <td><c:out value="${customer.address}"/></td>
                    <td><c:out value="${customer.phone}"/></td>
                    <td>
                     <s:link beanclass="cz.muni.fi.pa165.books.CustomersActionBean" event="edit"><s:param name="customer.id" value="${customer.id}"/>edit</s:link>
                    </td>
                    <td>
                        <s:form beanclass="cz.muni.fi.pa165.books.CustomersActionBean">
                            <s:hidden name="customer.id" value="${customer.id}"/>
                            <s:submit name="delete"><f:message key="customer.list.delete"/></s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <s:form beanclass="cz.muni.fi.pa165.books.CustomersActionBean">
            <fieldset><legend><f:message key="customer.list.newcustomer"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="add">Vytvořit noveho zakaznika</s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>
