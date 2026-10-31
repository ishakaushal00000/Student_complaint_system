
package com.studentcomplaint.servlet;

import com.studentcomplaint.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/saveComplaint")
public class SaveComplaintServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String studentName = request.getParameter("studentName");
        String rollNumber = request.getParameter("rollNumber");
        String category = request.getParameter("category");
        String department = request.getParameter("department");
        String description = request.getParameter("description");

        if (studentName == null || studentName.isBlank()
                || rollNumber == null || rollNumber.isBlank()
                || category == null || category.isBlank()
                || department == null || department.isBlank()
                || description == null || description.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Please fill in all fields.\"}"
            );
            return;
        }

        String sql = "INSERT INTO complaints " +
                "(student_name, roll_number, category, department, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, studentName.trim());
            ps.setString(2, rollNumber.trim());
            ps.setString(3, category);
            ps.setString(4, department);
            ps.setString(5, description.trim());

            ps.executeUpdate();

            String complaintId = "";

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    complaintId = "CMP-" + keys.getInt(1);
                }
            }

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(
                    "{\"success\":true,\"message\":\"Complaint saved successfully.\","
                            + "\"complaintId\":\"" + complaintId + "\"}"
            );

        } catch (Exception e) {
            getServletContext().log("Error saving complaint", e);
            e.printStackTrace();
            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
            response.getWriter().write(
                    "{\"success\":false,\"message\":\"Unable to save complaint. Please try again.\"}"
            );
        }
    }
}
