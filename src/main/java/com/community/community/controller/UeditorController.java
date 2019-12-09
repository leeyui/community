package com.community.community.controller;


import com.community.community.domain.Ueditor;
import com.community.community.util.PublicMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class UeditorController {
    private static final String PATH = "E:\\Code\\community\\src\\main\\resources\\static\\img\\";
    private static final String URL = "http://localhost:8080/";

    @RequestMapping(value="/ueditor")
    @ResponseBody
    public String ueditor(HttpServletRequest request) {

        return PublicMsg.UEDITOR_CONFIG;
    }

    @RequestMapping(value="/imgUpload")
    @ResponseBody
    public Ueditor imgUpload(MultipartFile upfile) {
        Ueditor ueditor = new Ueditor();
        if (!upfile.isEmpty()) {
            String fileName = upfile.getOriginalFilename();
            try {
                upfile.transferTo(new File(PATH + fileName));
                ueditor.setState("SUCCESS");
                ueditor.setUrl(URL + "img/" + fileName);
            } catch (IOException e) {
                ueditor.setState("fail");
            }
        }else {
            ueditor.setState("fail");
        }
        return ueditor;
    }



}
