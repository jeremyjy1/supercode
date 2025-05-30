package cn.edu.nju.supercode.stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Result {
    private String state;
    private String stdout;
    private String stderr;
    private Integer time;
    private Integer memory;
}