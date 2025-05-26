package cn.edu.nju.supercode.component;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class LastSubmission {
    public static final Map<String, LocalDateTime> lastSubmission = new HashMap<>();
}
