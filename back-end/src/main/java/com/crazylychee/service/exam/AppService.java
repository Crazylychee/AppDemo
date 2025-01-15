package com.crazylychee.service.exam;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crazylychee.domain.exam.Exam;
import com.crazylychee.mapper.exam.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService extends ServiceImpl<AppMapper, Exam> {

    @Autowired
    private AppMapper appMapper;

    public String getShareExam(String id){

        //执行查询
        String exam = appMapper.getShareExam(id);
        if(exam != null){
            return exam;
        }
        return null;
    }

    public String addShareExam(Exam exam){
        LambdaQueryWrapper<Exam> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Exam::getShareId, exam.getShareId());
// 执行查询
        Exam examfound = appMapper.selectOne(lambdaQueryWrapper);
        if (examfound != null){
            examfound.setShareId(examfound.getShareId());
            examfound.setQuestionText(exam.getQuestionText());
            appMapper.updateById(examfound);
        }else{
            appMapper.insert(exam);
        }
        return "success";
    }
}
