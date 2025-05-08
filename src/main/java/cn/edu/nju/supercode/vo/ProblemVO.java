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
public class ProblemVO {
    @Nullable
    private String uuid;
    private String title;
    private String description;
    private List<SampleVO> samples;
    public Problem toPO(){
        Problem problem=new Problem();
        if(uuid!=null)
            problem.setUuid(uuid);
        problem.setTitle(title);
        problem.setDescription(description);
        problem.setSamples(samples);
        return problem;
    }
}
