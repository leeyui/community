package com.community.community.controller;

import com.community.community.dto.UserDto;
import com.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/publish")
    public String publish(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/";
        }
        return "publish";
    }

    @PostMapping(value = "/publish")
    public String postPublish(@RequestParam(value = "title") String title,
                              @RequestParam(value = "info") String info,
                              @RequestParam(value = "tag") String tag,
                              HttpServletRequest request,
                              Model model) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if (title == null || title.equals("")) {
            model.addAttribute("reslut","标题不能为空");
            return "/publish";
        }
        model.addAttribute("title",title);
        if (info == null || info.equals("")) {
            model.addAttribute("reslut","内容不能为空");
            return "/publish";
        }
        model.addAttribute("info",info);
        if (tag == null || tag.equals("")) {
            model.addAttribute("reslut","标签不能为空");
            return "/publish";
        }
        model.addAttribute("tag",tag);
        questionService.create(title,info,tag,user.getId());
        model.addAttribute("reslut","发布成功");
        return "publish";
    }
}
