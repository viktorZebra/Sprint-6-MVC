package com.springwebmvc.webProject.filter

import java.time.Instant
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["/api/*", "/app/*"])
class AuthFilter : HttpFilter() {

    private lateinit var context: ServletContext

    override fun init(filterConfig: FilterConfig) {
        context = filterConfig.servletContext
        context.log("AuthFilter is initialized")
    }

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        val cookies = request!!.cookies

        if (cookies == null){
            context.log("cookies not found")
            response!!.sendRedirect("/login")
        }
        else{
            var flag = false // флаг того, что было найдено куки с именем auth

            for (cookie in cookies){
                if (cookie.name == "auth"){
                    flag = true
                    val currTime = Instant.now().toString()
                    if (cookie.value < currTime){
                        chain!!.doFilter(request, response)
                    }
                    else{
                        context.log("wrong cookie value: cookie val - ${cookie.value}, current val - $currTime")
                        response!!.sendRedirect("/login")
                    }
                }
            }

            if (!flag){
                context.log("cookie not found or wrong cookie name")
                response!!.sendRedirect("/login")
            }
        }
    }

}