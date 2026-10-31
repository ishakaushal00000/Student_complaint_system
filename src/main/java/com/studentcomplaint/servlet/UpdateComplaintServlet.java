
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
import java.util.Map;

@WebServlet("/updateComplaint")
public class UpdateComplaintServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            int complaintId = Integer.parseInt(
                    request.getParameter("complaintId"));

            String assignedStaff = request.getParameter("assignedStaff");
            String status = request.getParameter("status");
            String resolution = request.getParameter("resolution");

            if (assignedStaff == null || status == null ||
                    !(status.equals("Pending") ||
                            status.equals("In Progress") ||
                            status.equals("Resolved"))) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(response.getWriter(),
                        Map.of("message", "Invalid complaint details"));
                return;
            }

            if (!assignedStaff.equals("Not Assigned") &&
                    assignedStaff.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(response.getWriter(),
                        Map.of("message", "Invalid staff assignment"));
                return;
            }

            if (!status.equals("Pending") &&
                    assignedStaff.equals("Not Assigned")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(response.getWriter(),
                        Map.of("message", "Assign staff before starting or resolving"));
                return;
            }

            if (status.equals("Resolved") &&
                    (resolution == null || resolution.trim().isEmpty())) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                mapper.writeValue(response.getWriter(),
                        Map.of("message", "Resolution note is required"));
                return;
            }

            if (resolution == null) {
                resolution = "";
            }

            String sql = "UPDATE complaints SET assigned_staff = ?, " +
                    "status = ?, resolution = ? WHERE complaint_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, assignedStaff);
                ps.setString(2, status);
                ps.setString(3, resolution);
                ps.setInt(4, complaintId);

                int updated = ps.executeUpdate();

                if (updated == 0) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    mapper.writeValue(response.getWriter(),
                            Map.of("message", "Complaint not found"));
                    return;
                }
            }

            mapper.writeValue(response.getWriter(),
                    Map.of("message", "Complaint updated successfully"));

        } catch (Exception e) {
            getServletContext().log("Error updating complaint", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            mapper.writeValue(response.getWriter(),
                    Map.of("message", "Unable to update complaint"));
        }
    }
}
