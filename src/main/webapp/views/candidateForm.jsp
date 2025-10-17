<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.placement.model.Candidate" %>
<%
    Candidate candidate = (Candidate) request.getAttribute("candidate");
    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Edit Profile - Placement Consultancy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-5 pt-4">
    <div class="card p-4">
        <h2 class="mb-4">
            <img src="${pageContext.request.contextPath}/assets/images/profile-icon.png" alt="Profile Icon" style="height:22px; margin-right:8px;">
            Your Profile
        </h2>
        <form method="post" action="${pageContext.request.contextPath}/candidate" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${candidate != null ? candidate.id : ''}" />
            <div class="row g-3">
                <div class="col-md-6">
                    <label for="userId" class="form-label">User ID</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-user"></i></span>
                        <input type="text" class="form-control" id="userId" name="userId"
                               value="${candidate != null ? candidate.userId : ''}" readonly />
                    </div>
                </div>
                <div class="col-md-6">
                    <label for="marks" class="form-label">Marks</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-graduation-cap"></i></span>
                        <input type="number" step="0.01" class="form-control" id="marks" name="marks"
                               value="${candidate != null && candidate.marks != null ? candidate.marks : ''}" required />
                    </div>
                </div>
                <div class="col-md-6">
                    <label for="qualification" class="form-label">Qualification</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-book"></i></span>
                        <select class="form-select" id="qualification" name="qualification" required>
                            <option value="">Select Qualification</option>
                            <option value="B.Tech" ${candidate != null && candidate.qualification == 'B.Tech' ? 'selected' : ''}>B.Tech</option>
                            <option value="M.Tech" ${candidate != null && candidate.qualification == 'M.Tech' ? 'selected' : ''}>M.Tech</option>
                            <option value="BE" ${candidate != null && candidate.qualification == 'BE' ? 'selected' : ''}>BE</option>
                            <option value="Other" ${candidate != null && candidate.qualification == 'Other' ? 'selected' : ''}>Other</option>
                        </select>
                    </div>
                </div>
                <div class="col-12">
                    <label for="cvFile" class="form-label">Upload CV (PDF)</label>
                    <div class="input-group">
                        <span class="input-group-text">
                            <img src="${pageContext.request.contextPath}/assets/images/choose_file.png" alt="Upload Icon" />
                        </span>
                        <input type="file" class="form-control" id="cvFile" name="cvFile" accept=".pdf" />
                    </div>
                    <c:if test="${candidate != null && not empty candidate.cvUrl}">
                        <p class="mt-2">
                            Current CV:
                            <a href="${pageContext.request.contextPath}/download-cv?file=${candidate.cvUrl}" target="_blank" class="btn btn-outline-primary btn-sm">
    <i class="fas fa-file-pdf"></i> View CV
</a>

                        </p>
                    </c:if>
                </div>
            </div>
            <div class="d-flex justify-content-center gap-3 mt-4 flex-wrap">
                <button type="submit" class="btn btn-primary btn-lg">
                    <img src="${pageContext.request.contextPath}/assets/images/save-icon.png" alt="Save Icon" style="height:20px; margin-right:6px;" />
                    Save Profile
                </button>
                <a href="${pageContext.request.contextPath}/views/candidateDashboard.jsp"
   class="btn btn-secondary btn-lg">
                    <i class="fas fa-arrow-left"></i> Back to Candidates
                </a>
            </div>
        </form>
    </div>
</div>

<div class="toast-container position-fixed top-0 end-0 p-3" style="z-index: 1050;">
    <c:if test="${not empty error}">
        <div class="toast align-items-center bg-danger border-0 show" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="fas fa-exclamation-circle me-2"></i> ${error}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="toast align-items-center bg-success border-0 show" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="fas fa-check-circle me-2"></i> ${success}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.querySelectorAll('.toast').forEach(toastEl => {
        new bootstrap.Toast(toastEl).show();
    });
</script>

</body>
</html>
