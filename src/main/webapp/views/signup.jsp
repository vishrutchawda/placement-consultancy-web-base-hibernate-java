<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - Placement Consultancy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center justify-content-center min-vh-100">
    <div class="card p-4" style="max-width: 400px; width: 100%;">
        <div class="text-center mb-4">
            <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="PlacementPro Logo" style="height: 40px;">
            <h2 class="mt-2">Create Your Account</h2>
        </div>
        <form method="post" action="${pageContext.request.contextPath}/signup" novalidate onsubmit="return validateForm()">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                    <input type="text" class="form-control" id="name" name="name" required value="${param.name != null ? param.name : ''}">
                </div>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <img src="${pageContext.request.contextPath}/assets/images/login-icon.png" alt="Email Icon">
                    </span>
                    <input type="email" class="form-control" id="email" name="email" required value="${param.email != null ? param.email : ''}">
                </div>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-lock"></i></span>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
            </div>
            <div class="mb-3">
                <label for="confirmpassword" class="form-label">Confirm Password</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-lock"></i></span>
                    <input type="password" class="form-control" id="confirmpassword" name="confirmpassword" required>
                </div>
            </div>
            <div class="mb-3">
                <label for="role" class="form-label">Role</label>
                <div class="input-group">
                    <span class="input-group-text">
                        <img src="${pageContext.request.contextPath}/assets/images/recruiter-icon.png" alt="Role Icon"/>
                    </span>
                    <select class="form-select" id="role" name="role" onchange="toggleCompanyField()">
                        <option value="candidate" ${param.role == 'candidate' ? 'selected' : ''}>Candidate</option>
                        <option value="recruiter" ${param.role == 'recruiter' ? 'selected' : ''}>Recruiter</option>
                    </select>
                </div>
            </div>
            <div class="mb-3" id="companyfield" style="display:none;">
                <label for="company" class="form-label">Company Name</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-building"></i></span>
                    <input type="text" class="form-control" id="company" name="company" value="${param.company != null ? param.company : ''}">
                </div>
            </div>
            <button type="submit" class="btn btn-primary w-100">
                <img src="${pageContext.request.contextPath}/assets/images/signup-icon.png" alt="Signup Icon" /> Sign Up
            </button>
        </form>
        <div class="text-center mt-3">
            Already have an account? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Log In</a>
        </div>
    </div>
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <c:if test="${not empty error}">
            <div class="toast align-items-center text-white bg-danger border-0" role="alert" data-bs-autohide="true" data-bs-delay="3000">
                <div class="d-flex">
                    <div class="toast-body">${error}</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="toast align-items-center text-white bg-success border-0" role="alert" data-bs-autohide="true" data-bs-delay="3000">
                <div class="d-flex">
                    <div class="toast-body">${success}</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        </c:if>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function toggleCompanyField() {
            var role = document.getElementById('role').value;
            var companyField = document.getElementById('companyfield');
            companyField.style.display = role === 'recruiter' ? 'block' : 'none';
            document.getElementById('company').required = role === 'recruiter';
        }
        window.onload = function() {
            toggleCompanyField();
            document.querySelectorAll('.toast').forEach(toast => {
                new bootstrap.Toast(toast).show();
            });
        }
        function validateForm() { return true; } // Optional client-side validation
    </script>
</body>
</html>
