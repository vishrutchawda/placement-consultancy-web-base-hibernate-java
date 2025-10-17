<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Placement Consultancy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center justify-content-center min-vh-100">
    <div class="card p-4" style="max-width: 400px; width: 100%;">
        <div class="text-center mb-4">
            <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="PlacementPro Logo" style="height: 40px;">
            <h2 class="mt-2">Welcome Back</h2>
        </div>
        <form method="post" action="${pageContext.request.contextPath}/login" novalidate>
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
            <button type="submit" class="btn btn-primary w-100">
                <img src="${pageContext.request.contextPath}/assets/images/login-icon.png" alt="Login Icon" /> Login
            </button>
        </form>
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/forgotpassword" class="text-decoration-none">Forgot Password?</a>
        </div>
        <div class="text-center mt-2">
            Don't have an account? <a href="${pageContext.request.contextPath}/signup" class="text-decoration-none">Sign Up</a>
        </div>
    </div>

    <!-- Toast Container for Error Messages -->
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <c:if test="${not empty error}">
            <div class="toast align-items-center text-white bg-danger border-0" role="alert" data-bs-autohide="true" data-bs-delay="3000">
                <div class="d-flex">
                    <div class="toast-body">${error}</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        </c:if>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.querySelectorAll('.toast').forEach(toast => {
            new bootstrap.Toast(toast).show();
        });
    </script>
</body>
</html>
