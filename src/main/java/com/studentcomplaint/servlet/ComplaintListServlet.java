
package com.studentcomplaint.servlet;

import com.studentcomplaint.util.DBConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/complaints")
public class ComplaintListServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String sql = "SELECT complaint_id, student_name, roll_number, " +
                "category, department, description, status, " +
                "assigned_staff, resolution " +
                "FROM complaints ORDER BY complaint_id DESC";

        List<Map<String, Object>> complaints = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();

                item.put("id", "CMP-" + rs.getInt("complaint_id"));
                item.put("complaintId", rs.getInt("complaint_id"));
                item.put("studentName", rs.getString("student_name"));
                item.put("rollNumber", rs.getString("roll_number"));
                item.put("category", rs.getString("category"));
                item.put("department", rs.getString("department"));
                item.put("description", rs.getString("description"));
                item.put("status", rs.getString("status"));
                item.put("assignedStaff", rs.getString("assigned_staff"));
                item.put("resolution", rs.getString("resolution"));

                complaints.add(item);
            }

            mapper.writeValue(response.getWriter(), complaints);

        } catch (Exception e) {
            getServletContext().log("Error loading complaints", e);
            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            mapper.writeValue(response.getWriter(),
                    Map.of("message", "Unable to load complaints"));
        }
    }
}
