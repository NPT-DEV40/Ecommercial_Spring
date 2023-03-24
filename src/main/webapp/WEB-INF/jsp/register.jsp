<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Guru Registration Form</title>
</head>
<body>
    <h1>Register Form</h1>
    <form:form action="${pageContext.request.contextPath}/account/register" method="post" modelAttribute="user">
        <table style="with: 50%">
            <tr>
                <td>First Name</td>
                <td><label>
                    <input type="text" name="first_name" />
                </label></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><label>
                    <input type="text" name="last_name" />
                </label></td>
            </tr>
            <tr>
                <td>UserName</td>
                <td><label>
                    <input type="text" name="email" />
                </label></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><label>
                    <input type="password" name="password" />
                </label></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
    </form:form>
</body>
</html>