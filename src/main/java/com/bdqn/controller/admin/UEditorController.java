package com.bdqn.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ActionEnter;


@Controller
public class UEditorController {
    
    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath =ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/source/ueditor/jsp";
        try {
            response.setCharacterEncoding("UTF-8");
            String exec = new ActionEnter(request, rootPath).exec();
            System.out.println(exec);
            PrintWriter writer = response.getWriter();
            writer.write(new ActionEnter( request, rootPath ).exec());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
