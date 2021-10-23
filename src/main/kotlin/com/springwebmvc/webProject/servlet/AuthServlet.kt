package com.springwebmvc.webProject.servlet

import java.time.Instant
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "AuthServlet", urlPatterns = ["/login"])
class AuthServlet: HttpServlet() {
    private val username = "admin"
    private val password = "password"

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        if (username == req?.getParameter("username") &&
            password == req?.getParameter("password")){

            val cookie = Cookie("auth", Instant.now().toString())
            resp!!.addCookie(cookie)
            resp.sendRedirect("/menu.html")
        }
        else{
            resp!!.sendRedirect("/notAuth.html")
        }
    }

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req!!.getRequestDispatcher("/auth.html").forward(req, resp)
    }
}