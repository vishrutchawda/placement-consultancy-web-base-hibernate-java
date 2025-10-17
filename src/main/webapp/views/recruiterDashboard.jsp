<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.placement.model.User" %>
<%@ page import="com.placement.service.RecruiterService" %>
<%@ page import="com.placement.model.Recruiter" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"recruiter".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }


    RecruiterService recruiterService = new RecruiterService();
    Recruiter recruiter = recruiterService.getRecruiterByUserId(user.getId());


    if (recruiter == null) {
        recruiter = new Recruiter();
        recruiter.setId(java.util.UUID.randomUUID().toString());
        recruiter.setUserId(user.getId());
        recruiter.setCompanyName(user.getCompany());
        recruiter.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        recruiter.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        recruiterService.addRecruiter(recruiter);
    }

    String recruiterId = recruiter.getId();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Recruiter Dashboard - Placement Consultancy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-5 pt-4">
    <div class="card p-4 mb-4">
        <h2 class="mb-4">
            <img src="${pageContext.request.contextPath}/assets/images/dashboard-icon.png" alt="Dashboard Icon" />
            Recruiter Dashboard
        </h2>
        <!-- Filter Form -->
        <form method="GET" action="${pageContext.request.contextPath}/recruiter" class="mb-4 p-3 rounded" style="background-color: #eff6ff;">
    <div class="row g-3">
        <div class="col-md-4">
            <label for="min_marks" class="form-label">Min Marks</label>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-graduation-cap"></i></span>
                <!-- Change name from "min_marks" to "minmarks" to match servlet parameter -->
                <input type="number" step="0.01" class="form-control" id="min_marks" name="minmarks"
                       value="${param.minmarks != null ? param.minmarks : ''}" />
            </div>
        </div>
        <div class="col-md-4">
            <label for="qualification" class="form-label">Qualification</label>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-book"></i></span>
                <select class="form-select" id="qualification" name="qualification">
                    <option value="All" ${param.qualification == 'All' ? 'selected' : ''}>All</option>
                    <option value="B.Tech" ${param.qualification == 'B.Tech' ? 'selected' : ''}>B.Tech</option>
                    <option value="M.Tech" ${param.qualification == 'M.Tech' ? 'selected' : ''}>M.Tech</option>
                    <option value="BE" ${param.qualification == 'BE' ? 'selected' : ''}>BE</option>
                    <option value="Other" ${param.qualification == 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </div>
        </div>
        <div class="col-md-4 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100">
                <img src="${pageContext.request.contextPath}/assets/images/filter-icon.png" alt="Filter Icon" style="height:22px; width:auto;" /> Apply Filter
            </button>
        </div>
    </div>
</form>

        <!-- Candidates Table -->
        <h3 class="mb-3">Candidates</h3>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Marks</th>
                    <th>Qualification</th>
                    <th>CV</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty candidates}">
                        <tr><td colspan="5" class="text-center">No candidates found.</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="candidate" items="${candidates}">
    <tr>
        <td><c:out value="${candidate.userName}" /></td>
        <td><c:out value="${candidate.marks}" /></td>
        <td><c:out value="${candidate.qualification != null ? candidate.qualification : 'N/A'}" /></td>
        <td>
            <c:if test="${not empty candidate.cvUrl}">
                <a href="${pageContext.request.contextPath}/download-cv?file=${candidate.cvUrl}" target="_blank" class="btn btn-info btn-sm">
    <i class="fas fa-file-pdf"></i> View CV
</a>

            </c:if>
            <c:if test="${empty candidate.cvUrl}">
                <span class="text-muted">No CV</span>
            </c:if>
        </td>
        <td>
            <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#offerModal"
                    onclick="setCandidateId('${candidate.id}')">
                <img src="${pageContext.request.contextPath}/assets/images/hire-icon.png" alt="Offer Icon" /> Send Offer
            </button>
        </td>
    </tr>
</c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Offers Table -->
    <div class="card p-4">
        <h3 class="mb-3">Your Offers</h3>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Candidate</th>
                    <th>Status</th>
                    <th>Estimated Salary</th>
                    <th>Sent Date</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty offers}">
                        <tr><td colspan="4" class="text-center">No offers sent.</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="offer" items="${offers}">
                            <tr>
                                <td><c:out value="${offer.candidateName}" /></td>
                                <td>
                                    <span class="badge bg-${offer.status == 'PENDING' ? 'warning' : offer.status == 'ACCEPTED' ? 'success' : 'danger'}">
                                        <c:out value="${offer.status}" />
                                    </span>
                                </td>
                                <td>$<fmt:formatNumber value="${offer.estimatedSalary}" type="currency" currencySymbol="" minFractionDigits="2" maxFractionDigits="2"/></td>
                                <td><fmt:formatDate value="${offer.createdAt}" pattern="MMM dd, yyyy HH:mm" /></td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Logout Button -->
    <div class="d-flex justify-content-center gap-3 mt-4 flex-wrap">
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-lg">
            <img src="${pageContext.request.contextPath}/assets/images/logout-icon.png" alt="Logout Icon" style="height:22px; width:auto;"/> Logout
        </a>
    </div>
</div>

<!-- Offer Modal -->
<div class="modal fade" id="offerModal" tabindex="-1" aria-labelledby="offerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="POST" action="${pageContext.request.contextPath}/offer" id="offerForm">
                <div class="modal-header">
                    <h5 class="modal-title" id="offerModalLabel">Send Offer</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" id="candidate_id" name="candidateId" />
                    <!-- FIXED: Use the actual recruiter ID instead of user ID -->
                    <input type="hidden" name="recruiterId" value="<%= recruiterId %>" />
                    <input type="hidden" name="companyName" value="${sessionScope.user.company}" />
                    <input type="hidden" name="status" value="PENDING" />

                    <div class="mb-3">
                        <label for="estimated_salary" class="form-label">Estimated Salary *</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-dollar-sign"></i></span>
                            <input type="number" step="0.01" class="form-control" id="estimated_salary" name="estimatedSalary" required autofocus />
                        </div>
                        <div class="form-text">Press Enter to submit quickly</div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">
                        <img src="${pageContext.request.contextPath}/assets/images/hire-icon.png" alt="Send Icon" /> Send Offer
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Toasts -->
<div class="toast-container position-fixed top-0 end-0 p-3">
    <c:if test="${not empty success}">
        <div class="toast align-items-center bg-success border-0" role="alert" data-bs-autohide="true">
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
        <div class="toast align-items-center bg-danger border-0" role="alert" data-bs-autohide="true">
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
    function setCandidateId(id) {
        console.log("Setting candidate ID:", id);
        document.getElementById('candidate_id').value = id;

        // Set focus to salary field when modal opens
        setTimeout(() => {
            document.getElementById('estimated_salary').focus();
        }, 500);
    }

    // Handle Enter key in salary field
    document.addEventListener('DOMContentLoaded', function() {
        const salaryField = document.getElementById('estimated_salary');
        const offerForm = document.getElementById('offerForm');

        if (salaryField && offerForm) {
            salaryField.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    if (salaryField.value.trim() !== '') {
                        offerForm.submit();
                    }
                }
            });
        }
    });

    // Auto-show modal if there's a candidate ID in URL (optional feature)
    const urlParams = new URLSearchParams(window.location.search);
    const candidateIdFromUrl = urlParams.get('candidateId');
    if (candidateIdFromUrl) {
        setCandidateId(candidateIdFromUrl);
        const offerModal = new bootstrap.Modal(document.getElementById('offerModal'));
        offerModal.show();
    }

    document.querySelectorAll('.toast').forEach(toastEl => {
        new bootstrap.Toast(toastEl).show();
    });
</script>
</body>
</html>