package com.github.sdaniel;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/createAccount")
public class CreateAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String position = req.getParameter("position");

        if ((firstName != null) && (lastName != null) && (email != null) && (password != null) && (position != null)) {
            DataBase dBase = new DataBase();
            try {
                UserDao uDao = new UserDao(dBase.connect());
                uDao.insert(new User(0, firstName, lastName, email, password, position));
                resp.sendRedirect("/chronos.com/html/");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
