package com.github.sdaniel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/employeeTime")
public class EmployeeTimeSheetServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataBase dBase = new DataBase();
        User user = new User();
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = req.getSession(false);

        if (session.getAttribute("uname") != null) {
            try {
                TimeSheetDao tDao = new TimeSheetDao(dBase.connect());
                UserDao uDao = new UserDao(dBase.connect());
                List<TimeSheet> tSheets = new ArrayList<>();
                String uname = (String) session.getAttribute("uname");
                String pass = (String) session.getAttribute("upass");

                user = uDao.validateLogin(uname, pass);
                tSheets = tDao.getAll(user.getId());
                String jsonString = mapper.writeValueAsString(tSheets);
                resp.setContentType("application/json");
                resp.getWriter().println(jsonString);
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
