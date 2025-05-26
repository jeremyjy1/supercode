package cn.edu.nju.supercode.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CMD {
    String command;
    List<String> args;
    String input;
    Config config;
}

