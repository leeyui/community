package com.community.community.controller;

import com.community.community.service.QuestionService;
import com.community.community.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    public static final int SIZE = 5;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/")
    public String index(@RequestParam(name = "page",defaultValue = "0") Integer page,
                        Model model) {
        Page page1 = questionService.list(page,SIZE);
        model.addAttribute("page",page1);
        return "index";
    }@RequestMapping(value = "/logout")
    public String logout(@RequestParam(name = "page",defaultValue = "0") Integer page,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model) {
        Page page1 = questionService.list(page,SIZE);
        model.addAttribute("page",page1);
        request.getSession().removeAttribute("user");
        response.addCookie(new Cookie("token",null));
        return "redirect:/";
    }
}
