<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 20px;
        }

        h2 {
            color: #333;
        }

        p {
            color: #555;
            margin-bottom: 10px;
        }

        form {
            margin-top: 20px;
        }

        button {
            background-color: #007bff;
            color: #fff;
            padding: 10px;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h2>User Profile</h2>

    <c:if test="${not empty username}">
        <p>Username: ${username}</p>
    </c:if>

    <c:if test="${not empty email}">
        <p>Email: ${email}</p>
    </c:if>

    <c:if test="${not empty password}">
        <p>Password: ${password}</p>
    </c:if>

    <c:if test="${not empty address}">
        <p>Address: ${address}</p>
    </c:if>

    <!-- Add a button to navigate to the updateProfile page -->
    <form action="/updateProfile" method="get">
        <button type="submit">Update Profile</button>
    </form>
</body>
</html>
