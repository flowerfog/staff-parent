package org.flowerfog.controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseController {
    public HttpServletRequest request;
    public HttpServletResponse response;
    protected String companyId;
    protected String companyName;

    protected Claims claims;

    @ModelAttribute
    public void setResAnReq(HttpServletRequest request,HttpServletResponse response){
        this.request=request;
        this.response=response;

        Object obj = request.getAttribute("user_claims");
        if (obj != null){
            this.claims =(Claims) obj;
            //TODO
            this.companyId= (String) claims.get("companyId");
            this.companyName = (String) claims.get("companyName");
        }



    }
}
