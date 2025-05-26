package cn.edu.nju.supercode.stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Received {
    List<Result> sandbox_results;
    String submit_id;
}

