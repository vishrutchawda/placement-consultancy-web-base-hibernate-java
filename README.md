# Placement Consultancy Java Web System [![Java](https://img.shields.io/badge/Java-17%2B-orange)](https://www.oracle.com/java/) [![JakartaEE](https://img.shields.io/badge/JakartaEE-10-blue)](https://jakarta.ee/) [![Hibernate](https://img.shields.io/badge/Hibernate-6%2B-red)](https://hibernate.org/) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

An enterprise-grade web application for placement consultancies, leveraging Servlets and JSP for dynamic pages, Hibernate ORM for MySQL persistence, and BCrypt for secure auth. This project illustrates full-stack Java web development with role-based access, file uploads, and CRUD via sessions, perfect for demonstrating ORM and servlet patterns in academic settings.[file:3]

## Table of Contents
- [About the Project](#about-the-project)
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Security Considerations](#security-considerations)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## About the Project
This app facilitates candidate-recruiter interactions in job placements, with candidates updating profiles (CV PDFs), responding to offers, and recruiters filtering/searching/sending proposals. Using Jakarta EE servlets for handling, JSP for views with JSTL, and Hibernate for entity mapping (e.g., @Entity User with unique email), it focuses on scalable web backend without frameworks like Spring. Motivation: Teach servlet lifecycle, ORM queries (HQL), and file handling in a real-world consultancy scenario, solving inefficiencies in manual systems.[file:3]

Key elements:
- Session-based auth with role checks (candidate/recruiter).
- Hibernate auto-schema via hbm2ddl.auto=update.
- Bootstrap UI with modals/toasts for UX.

## Features
- **Authentication Module**: Login/signup with BCrypt hashing (AuthService.hashPassword), forgot password reset; AuthFilter blocks unauth access except login/signup.
- **Role-Based Access**: Candidate dashboard (view/accept/reject offers via OfferServlet); Recruiter (filter candidates by marks/qual, send offers modal with salary); Admin stub (extendable via UserDAO.deleteUser).
- **Profile Management**: Edit marks/qualification, upload CV to /uploads/ (MultipartConfig maxFileSize=16MB), download via FileDownloadServlet.
- **Offer System**: Create/update offers (UUID id, Status ENUM PENDING/ACCEPTED/REJECTED, BigDecimal salary); Filter/display with candidate names fetched via services.
- **UI/UX**: Responsive Bootstrap 5.3 tables/forms, Font Awesome icons, JS for modals/toasts/auto-focus.[file:3]

## Architecture
MVC via Servlets/JSP:
- **Backend**: Servlets (e.g., CandidateServlet doPost for upload/validate, OfferServlet for status update); DAOs (Hibernate Session for HQL queries like "from Candidate where userId = :userId"); Services (business logic, e.g., CandidateService.getAllCandidates).
- **Database**: MySQL 8+ with entities (User @Table("users"), Offer with @Enumerated(EnumType.STRING)); hibernate.cfg.xml for connection (jdbcmysql://localhost:3306/pcs, dialect MySQL8Dialect).
- **Frontend**: JSP with <c:forEach> for tables, <fmt:formatDate> for timestamps; CSS animations (gradient background); Filter chain for auth.
- **Flow**: Request → AuthFilter → Servlet (get/post) → Service/DAO (Hibernate tx) → JSP forward with attributes → Response.[file:3]

Data flow: Form submit → Multipart/params → Hibernate save/update → Session success/error → Redirect/view.

## Tech Stack
- **Backend**: Java 17+ (Jakarta Servlet 5.0, JSP 3.0, Hibernate 6+ ORM for annotations/mappings).
- **Database**: MySQL 8+ (InnoDB, connector-j for driver); HQL queries in DAOs.
- **Security**: BCrypt (jbcrypt lib for hashing/verification), session attributes (userId, user), Multipart for secure uploads.
- **Frontend**: Bootstrap 5.3 (grids/modals), JSTL (core/fmt), JavaScript (vanilla for Enter submit, toast show), Font Awesome 6.4.
- **Server**: Apache Tomcat 10+ (for Jakarta EE 10); Build: Maven (pom.xml with dependencies: hibernate-core, jakarta.servlet-api:provided, mysql-connector-j).
- **Dependencies**: jbcrypt, jstl-api (compile scope).[file:3]

## Prerequisites
- Java 17+ JDK.
- MySQL 8+ server (DB: pcs, user: root, pass: vishrutcoderpro—or edit hibernate.cfg.xml).
- Apache Tomcat 10+ (for servlet container).
- IDE: Eclipse/IntelliJ with Jakarta EE support; Maven for deps.
- Download MySQL connector JAR if not via Maven.

## Installation
1. **Clone or Download**:
git clone https://github.com/vishrutchawda/placement-consultancy-javaweb-hibernate.git
cd placement-consultancy-javaweb-hibernate

2. **Database Setup**:
- Create DB: `CREATE DATABASE pcs;`.
- Hibernate auto-creates tables on first run (hbm2ddl.auto=update); Or manual schema from entities (users, candidates, recruiters, offers with foreign keys via relations if added).
- Update hibernate.cfg.xml: url=jdbcmysql://localhost:3306/pcs?..., username/root, password/yourpass.

3. **Build & Deploy**:
- Maven: `mvn clean package` → target/*.war.
- Or IDE: Import as Maven project, add Tomcat server, deploy WAR to webapps/.
- Tomcat: Copy WAR to webapps/, start server (localhost:8080/placement-consultancy-javaweb-hibernate).
- Create /uploads/ in webapps/ root: `mkdir uploads && chmod 755 uploads` (for CVs).

4. **Run & Test**:
- Access http://localhost:8080/placement-consultancy-javaweb-hibernate/login.
- Signup (candidate/recruiter), test offers, CV upload/download.[file:3]

## Usage
- **Candidate**: Login → Dashboard (offers table, accept/reject links) → Edit Profile (form post to servlet).
- **Recruiter**: Login → Dashboard (filter form, candidates table with Send Offer modal) → View sent offers.
- **General**: Logout invalidates session; Forgot password emails not implemented (extend AuthService).[file:3]
Example: Offer creation: POST to OfferServlet with candidateId/recruiterId/salary → Hibernate save → Success toast.

## Security Considerations
- Passwords hashed with BCrypt (12-round salt) in AuthService; Verified via checkpw().
- Sessions via HttpSession (userId/user/role); AuthFilter redirects non-logged to login.
- Uploads: MultipartConfig limits size, file to /uploads/ with unique names (timestamp + UUID); Download checks existence.
- SQL/XSS: Hibernate prepared statements mitigate injection; JSP inputs via params (escape if needed); No CSRF (add tokens for prod).
- Vulnerabilities: Weak pass policy (add validation); Company field only for recruiters (JS toggle).[file:3]

## Roadmap
- Add email notifications (JavaMailSender for offers).
- Implement admin dashboard (user deletion via servlet).
- Pagination for candidate lists (HQL with setFirstResult/setMaxResults).
- Frontend: AJAX for async offer updates (jQuery optional).
- Testing: JUnit for DAOs/services; Integration tests with Testcontainers.
- Migration: Spring Boot for modern DI/REST.

## Contributing
Fork, branch (`git checkout -b feature/AddAdmin`), commit (`git commit -m 'Add admin servlet'`), PR. Adhere to Jakarta conventions, Hibernate best practices; Test on Tomcat 10+; No schema-breaking changes.[file:3]

1. Fork the Project
2. Create Feature Branch
3. Commit Changes
4. Push Branch
5. Open PR

## License
MIT License - See LICENSE.

## Contact
Vishrut Chawda - CS Student @ A.V Parekh Technical Institute, Rajkot  
Email: vishrutchawda@gmail.com  
LinkedIn: [www.linkedin.com/in/gp-avpti-comp-vishrut-chawda-s236020307230](https://www.linkedin.com/in/gp-avpti-comp-vishrut-chawda-s236020307230)  
Project: [https://github.com/vishrutchawda/placement-consultancy-javaweb-hibernate](https://github.com/vishrutchawda/placement-consultancy-javaweb-hibernate)[file:3]
