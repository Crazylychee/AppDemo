package com.crazylychee.mapper.exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crazylychee.domain.exam.Exam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppMapper extends BaseMapper<Exam> {
    String getShareExam(String id);
}
