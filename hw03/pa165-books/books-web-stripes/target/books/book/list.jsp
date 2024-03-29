<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="book.list.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.books.BooksActionBean" var="actionBean"/>

        <p><f:message key="book.list.allbooks"/></p>

        <table class="basic">
            <tr>
                <th>id</th>
                <th><f:message key="book.author"/></th>
                <th><f:message key="book.title"/></th>
                <th><f:message key="book.year"/></th>
                <th><f:message key="book.paperback"/></th>
                <th><f:message key="book.color"/></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${actionBean.books}" var="book">
                <tr>
                    <td>${book.id}</td>
                    <td><c:out value="${book.author}"/></td>
                    <td><c:out value="${book.title}"/></td>
                    <td><c:out value="${book.year}"/></td>
                    <td><c:out value="${book.paperback}"/></td>
                    <td><f:message key="Book.Color.${book.color}"/></td>
                    <td>
                     <s:link beanclass="cz.muni.fi.pa165.books.BooksActionBean" event="edit"><s:param name="book.id" value="${book.id}"/>edit</s:link>
                    </td>
                    <td>
                        <s:form beanclass="cz.muni.fi.pa165.books.BooksActionBean">
                            <s:hidden name="book.id" value="${book.id}"/>
                            <s:submit name="delete"><f:message key="book.list.delete"/></s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <s:form beanclass="cz.muni.fi.pa165.books.BooksActionBean">
            <fieldset><legend><f:message key="book.list.newbook"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="add">Vytvořit novou knihu</s:submit>
            </fieldset>
        </s:form>
    </s:layout-component>
</s:layout-render>