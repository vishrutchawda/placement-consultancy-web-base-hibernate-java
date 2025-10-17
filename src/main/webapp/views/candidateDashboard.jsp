<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page import="com.placement.model.User" %>
<%@ page import="com.placement.service.OfferService" %>
<%@ page import="com.placement.service.CandidateService" %>
<%@ page import="com.placement.model.Candidate" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"candidate".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    // Fetch offers for the current candidate
    CandidateService candidateService = new CandidateService();
    Candidate candidate = candidateService.getCandidateByUserId(user.getId());

    java.util.List<com.placement.model.Offer> offers = java.util.Collections.emptyList();
    if (candidate != null) {
        OfferService offerService = new OfferService();
        offers = offerService.getAllOffers().stream()
            .filter(offer -> offer.getCandidateId() != null && offer.getCandidateId().equals(candidate.getId()))
            .collect(java.util.stream.Collectors.toList());
    }
    request.setAttribute("offers", offers);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Candidate Dashboard - Placement Consultancy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-5 pt-4">
    <div class="card p-4">
        <h2 class="mb-4">
            <img src="${pageContext.request.contextPath}/assets/images/dashboard-icon.png" alt="Dashboard Icon" />
            Welcome ${sessionScope.user.name} !!!
        </h2>
        <h3 class="mb-3">Your Offers</h3>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Company</th>
                        <th>Status</th>
                        <th>Estimated Salary</th>
                        <th>Sent Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty offers}">
                        <tr><td colspan="5" class="text-center">No offers available.</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="offer" items="${offers}">
                            <tr>
                                <td><c:out value="${offer.companyName}" /></td>
                                <td>
                                    <span class="badge bg-${offer.status == 'PENDING' ? 'warning' : offer.status == 'ACCEPTED' ? 'success' : 'danger'}">
                                        <c:out value="${offer.status}" />
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${offer.estimatedSalary != null}">
                                            $<fmt:formatNumber value="${offer.estimatedSalary}" pattern="#,##0.00" />
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Not specified</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatDate value="${offer.createdAt}" pattern="MMM dd, yyyy" />
                                </td>
                                <td>
                                    <c:if test="${offer.status == 'PENDING'}">
                                        <a href="${pageContext.request.contextPath}/offer/update?id=${offer.id}&status=ACCEPTED"
                                           class="btn btn-success btn-sm me-2"
                                           onclick="return confirm('Are you sure you want to accept this offer?')">
                                            <img src="${pageContext.request.contextPath}/assets/images/hire-icon.png" alt="Accept Icon" /> Accept
                                        </a>
                                        <a href="${pageContext.request.contextPath}/offer/update?id=${offer.id}&status=REJECTED"
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Are you sure you want to reject this offer?')">
                                            <img src="${pageContext.request.contextPath}/assets/images/reject.png" alt="Reject Icon" /> Reject
                                        </a>
                                    </c:if>
                                    <c:if test="${offer.status != 'PENDING'}">
                                        <span class="text-muted fst-italic">Decision made</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
        <div class="d-flex justify-content-center gap-3 mt-4 flex-wrap">
            <a href="${pageContext.request.contextPath}/candidate/edit" class="btn btn-primary btn-lg">
                <img src="${pageContext.request.contextPath}/assets/images/profile-icon.png" alt="Profile Icon" style="height:22px; width:auto;"/> Edit Profile
            </a>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-lg">
                <img src="${pageContext.request.contextPath}/assets/images/logout-icon.png" alt="Logout Icon" style="height:22px; width:auto;"/> Logout
            </a>
        </div>
    </div>
</div>

<!-- Toast Container -->
<div class="toast-container position-fixed top-0 end-0 p-3">
    <c:if test="${not empty success}">
        <div class="toast align-items-center bg-success border-0" role="alert" data-bs-autohide="true" data-bs-delay="5000">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="fas fa-check-circle me-2"></i> <c:out value="${success}" />
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
        <% session.removeAttribute("success"); %>
    </c:if>
    <c:if test="${not empty error}">
        <div class="toast align-items-center bg-danger border-0" role="alert" data-bs-autohide="true" data-bs-delay="5000">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="fas fa-exclamation-circle me-2"></i> <c:out value="${error}" />
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
        <% session.removeAttribute("error"); %>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.querySelectorAll('.toast').forEach(toastEl => {
        new bootstrap.Toast(toastEl).show();
    });

    // Auto-refresh the page if there are pending offers (optional)
    function checkForNewOffers() {
        const hasPendingOffers = document.querySelector('.badge.bg-warning') !== null;
        if (hasPendingOffers) {
            // Refresh every 30 seconds if there are pending offers
            setTimeout(() => {
                window.location.reload();
            }, 30000);
        }
    }

    // Start checking for new offers
    checkForNewOffers();
</script>
</body>
</html>