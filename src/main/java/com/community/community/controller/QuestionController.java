package com.community.community.controller;

import com.community.community.dto.QuestionDto;
import com.community.community.dto.UserDto;
import com.community.community.service.QuestionService;
import com.community.community.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    public static final int SIZE = 5;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           HttpServletRequest request,
                           Model model) {
        QuestionDto questionDto = questionService.findById(id);
        model.addAttribute("question",questionDto);
        System.out.println(request.getSession().getAttribute("user"));
        return "question";
    }
    @GetMapping("/question/edit/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       HttpServletRequest request,
                       Model model) {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:/";
        }
        QuestionDto questionDto = questionService.findById(id);
        model.addAttribute("question",questionDto);
        return "edit";
    }
    @PostMapping("/question/edit/{id}")
    public String postEdit(@RequestParam(value = "title") String title,
                           @RequestParam(value = "info") String info,
                           @RequestParam(value = "tag") String tag,
                           @PathVariable(name = "id") Integer id,
                              HttpServletRequest request,
                              Model model) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if (title == null || title.equals("")) {
            model.addAttribute("reslut","标题不能为空");
            return "/edit";
        }
        model.addAttribute("title",title);
        if (info == null || info.equals("")) {
            model.addAttribute("reslut","内容不能为空");
            return "/edit";
        }
        model.addAttribute("info",info);
        if (tag == null || tag.equals("")) {
            model.addAttribute("reslut","标签不能为空");
            return "/edit";
        }
        model.addAttribute("tag",tag);
        questionService.modifyById(title,info,tag,id);
        QuestionDto questionDto = questionService.findById(id);
        model.addAttribute("reslut","修改成功");
        model.addAttribute("question",questionDto);
        return "edit";
    }
    @GetMapping("myquestion")
    public String myQuestion(@RequestParam(name = "page",defaultValue = "0") Integer page,
                             @RequestParam(name = "condition" ,required = false) String condition,
                             HttpServletRequest request,
                             Model model) {
        if (condition == null) {
            return "redirect:/";
        }
        Page page1 = null;
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        int id = user.getId();
        if (condition == null || condition.equals("")) {
            page1 = questionService.listByCreator(page, SIZE, id);
        } else {
            page1 = questionService.listByCreatorAndCondition(page, SIZE, id,condition);
        }
        model.addAttribute("page",page1);
        request.getSession().setAttribute("pagepath","myquestion");
        return "myquestion";
    }
    @RequestMapping("/conditionquestion")
    public String condition(@RequestParam(name = "page",defaultValue = "0") Integer page,
                            @RequestParam(name = "condition" , required = false) String condition,
                            HttpServletRequest request,
                            Model model) {
        if (condition == null) {
            return "redirect:/";
        }
        Page<QuestionDto> page1 = questionService.listByCondition(page, SIZE, condition);
        model.addAttribute("page",page1);
        request.getSession().setAttribute("pagepath","conditionquestion");
        return "index";
    }
}
