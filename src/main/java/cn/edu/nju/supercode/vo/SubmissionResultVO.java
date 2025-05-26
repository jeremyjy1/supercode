package cn.edu.nju.supercode.vo;

import cn.edu.nju.supercode.po.User;
import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SubmissionResultVO {
    private String submitId;
    private String problemId;
    private String lang;
    private String code;
    @Nullable
    private Integer time;
    @Nullable
    private Integer memory;
    private String result;
    private LocalDateTime submissionTime;
    @Nullable
    private Integer score;
}
