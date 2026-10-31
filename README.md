# Student Complaint Management System

##  Project Overview
The Smart Student Complaint Management System is a Java-based web application designed to help students submit complaints and track their resolution. It provides separate interfaces for Students, Administrators, and Heads of Department (HODs) to support complaint management.

##  Objectives
- Provide an online platform for submitting student complaints.
- Allow authorized users to view complaint records.
- Support complaint status updates and resolution tracking.
- Store complaint data in a MySQL database.
- Improve the organization and transparency of complaint handling.

##  Features
- Student interface for complaint submission.
- Administrator interface for complaint management.
- HOD interface for viewing and managing complaints.
- Complaint listing and status update functionality.
- Database connectivity using JDBC.

##  Technologies Used
- **Language:** Java
- **Frontend:** HTML
- **Backend:** Java Servlets
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **Build Tool:** Maven
- **Application Server:** Apache Tomcat
- **Development Environment:** IntelliJ IDEA

## 🗂️ Project Structure
```text
Student_Complaint_System/
├── pom.xml
├── .gitignore
└── src/
    └── main/
        ├── java/
        │   └── com/studentcomplaint/
        │       ├── servlet/
        │       ├── test/
        │       └── util/
        └── webapp/
            ├── index.html
            ├── roles.html
            ├── student_login.html
            ├── admin_login.html
            └── HOD_login.html
```

##  Requirements
- Java Development Kit (JDK)
- Apache Maven
- MySQL Server
- Apache Tomcat compatible with the Jakarta Servlet API used by the project
- IntelliJ IDEA or another Java IDE

##  Setup Instructions
1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Configure the MySQL database and create the required database and tables.
4. Configure the database connection using the environment variable `DB_PASSWORD`.
5. Ensure the MySQL JDBC driver and required Maven dependencies are available.
6. Configure Apache Tomcat to deploy the web application.
7. Build the project using Maven and run it on Tomcat.
8. Open the application using the local URL configured for your Tomcat server.

##  Database
The application uses MySQL to store complaint information. JDBC is used to establish a connection between the Java application and the database.

Configure your own database credentials locally. Do not upload database passwords or other secrets to GitHub.

##  Project Purpose
This project was developed as an academic project to practice Java web application development, database connectivity, and complaint management workflows.

##  Future Enhancements
- Role-based authentication and authorization.
- Complaint assignment to staff members.
- Email notifications for complaint updates.
- Analytics dashboard for complaint statistics.
- Improved input validation and security.

##  License
This project is intended for educational purposes.
