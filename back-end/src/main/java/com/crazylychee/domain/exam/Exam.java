package com.crazylychee.domain.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Exam {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField(value = "question_text")
    private String questionText;

    @TableField(value = "share_id")
    private String shareId;

    @Override
    public String toString() {
        return "Exam{" +
                "id='" + id + '\'' +
                ", questionText='" + questionText + '\'' +
                ", shareId='" + shareId + '\'' +
                '}';
    }


}
