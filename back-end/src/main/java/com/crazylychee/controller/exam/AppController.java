package com.crazylychee.controller.exam;


import com.crazylychee.domain.exam.Exam;
import com.crazylychee.service.exam.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Autowired
    private AppService appService;

    /**
     * 获取他人分享的题单
     */
    @GetMapping("/getShareExam")
    public String getShareExam(@RequestParam String id){
        return appService.getShareExam(id);
    }

    /**
     * 添加分享的题单
     */
    @PostMapping("/addShareExam")
    public String addShareExam(Exam exam){
        return appService.addShareExam(exam);
    }

}
