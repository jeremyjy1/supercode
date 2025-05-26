package cn.edu.nju.supercode.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Config {
    Integer time_limit;
    Integer time_reserved;
    Integer memory_limit;
    Integer memory_reserved;
    Boolean large_stack;
    Integer output_limit;
    Integer process_limit;
    public Config(){
        time_limit=5;
        time_reserved=0;
        memory_limit=256000;
        memory_reserved=6291456;
        large_stack=false;
        output_limit=0;
        process_limit=0;
    }
}

