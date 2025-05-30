package cn.edu.nju.supercode.stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Sent {
    List<CMD> commands;
    String image;
    String submit_id;
}

