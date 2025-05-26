package cn.edu.nju.supercode.vo;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProblemVO {
    @Nullable
    private String uuid;
    private String title;
    private String description;
    private Integer timeLimit;
    private Integer memoryLimit;
    private List<StdioVO> exampleStdio;
}
