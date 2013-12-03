<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:layout-render name="/layout.jsp" titlekey="index.title">
    <s:layout-component name="body">

       <ul>
           <li><s:link beanclass="cz.muni.fi.pa165.books.BooksActionBean"><f:message key="index.books.link"/></s:link></li>
           <li><s:link beanclass="cz.muni.fi.pa165.books.CustomersActionBean"><f:message key="index.customer.link"/></s:link></li>
       </ul>

    </s:layout-component>
</s:layout-render>
