<%--
  Created by IntelliJ IDEA.
  User: Ville
  Date: 8.1.2013
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title><g:message code="bookStore.index.title"/></title>
    </head>

    <body>
        <h1><g:message code="bookStore.index.title"/></h1>

        <h3><g:message code="bookStore.index.bookList"/></h3>

        <table>
            <thead>
                <th><g:message code="book.title"/></th>
                <th><g:message code="book.author"/></th>
                <th><g:message code="book.yearReleased"/></th>
            </thead>
            <g:each in="${books}" var="book">
                <tr>
                    <td>${book.title}</td><td>${book.author}</td><td>${book.yearReleased}</td>
                </tr>
            </g:each>
        </table>

        <h3><g:message code="bookStore.index.topList"/></h3>

        <table>
            <thead>
                <th><g:message code="book.title"/></th>
                <th><g:message code="book.author"/></th>
                <th><g:message code="book.yearReleased"/></th>
            </thead>
            <g:each in="${top3}" var="book">
                <tr>
                    <td>${book.title}</td><td>${book.author}</td><td>${book.yearReleased}</td>
                </tr>
            </g:each>
        </table>

    </body>
</html>