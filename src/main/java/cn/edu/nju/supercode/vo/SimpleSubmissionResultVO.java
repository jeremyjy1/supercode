package cn.edu.nju.supercode.vo;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SimpleSubmissionResultVO {
    private String submitId;
    private String problemId;
    private String lang;
    @Nullable
    private Integer time;
    @Nullable
    private Integer memory;
    private String result;
    private LocalDateTime submissionTime;
    @Nullable
    private Integer score;
}
