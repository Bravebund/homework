package com.lagou.edu.controller;

import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    //@ResponseBody
    public ModelAndView queryAll(HttpServletRequest request) {
        List<Resume> resumes = resumeService.queryAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("resumes", resumes);
        modelAndView.setViewName("resume");
        request.setAttribute("resumes", resumes);
        /*HttpSession session = request.getSession();
        session.setAttribute("resumes", resumes);*/
        return modelAndView;
    }

    @RequestMapping(value = "/queryOne", method = RequestMethod.GET)
    public ModelAndView queryOne(HttpServletRequest request, String id) {
        Resume resume = resumeService.queryOne(id);
        request.setAttribute("resume", resume);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("update");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request, String id) {
        resumeService.deleteById(id);
        return "redirect:/resume/all";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, Resume resume) {
        resumeService.add(resume);
        return "redirect:/resume/all";
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(HttpServletRequest request, Resume resume) {
        resumeService.update(resume);
        return "redirect:/resume/all";
    }



}
