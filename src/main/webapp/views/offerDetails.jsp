<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Offer Details</title>
</head>
<body>
<h2>Offer Details</h2>
<c:if test="${not empty offer}">
    <ul>
        <li>ID: ${offer.id}</li>
        <li>Candidate ID: ${offer.candidateId}</li>
        <li>Recruiter ID: ${offer.recruiterId}</li>
        <li>Company Name: ${offer.companyName}</li>
        <li>Status: ${offer.status}</li>
        <li>Estimated Salary: ${offer.estimatedSalary}</li>
        <li>Created At: ${offer.createdAt}</li>
        <li>Updated At: ${offer.updatedAt}</li>
    </ul>
</c:if>
<p><a href="${pageContext.request.contextPath}/offer">Back to Offer List</a></p>
</body>
</html>
