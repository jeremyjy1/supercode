package cn.edu.nju.supercode.vo;

import cn.edu.nju.supercode.po.Problem;
import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FullProblemVO {
    @Nullable
    private String uuid;
    private String title;
    private String description;
    private Integer timeLimit;
    private Integer timeReserved;
    private Integer memoryLimit;
    private Integer memoryReserved;
    private Boolean largeStack;
    private Integer outputLimit;
    private Integer processLimit;
    private List<StdioVO> exampleStdio;
    private List<StdioVO> stdio;

    public Problem toPO() {
        Problem problem = new Problem();
        if (uuid != null)
            problem.setUuid(uuid);
        problem.setTitle(title);
        problem.setDescription(description);
        problem.setTimeLimit(timeLimit);
        problem.setTimeReserved(timeReserved);
        problem.setMemoryLimit(memoryLimit);
        problem.setMemoryReserved(memoryReserved);
        problem.setLargeStack(largeStack);
        problem.setOutputLimit(outputLimit);
        problem.setProcessLimit(processLimit);
        problem.setExampleStdio(exampleStdio);
        problem.setStdio(stdio);
        return problem;
    }
}
