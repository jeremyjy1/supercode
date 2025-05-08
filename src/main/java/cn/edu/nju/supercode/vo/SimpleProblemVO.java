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
public class SimpleProblemVO {
    private String uuid;
    private String title;
}
