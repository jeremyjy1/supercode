package cn.edu.nju.supercode.component;

import cn.edu.nju.supercode.vo.PendingVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PendingSubmissions {
    public static final Map<String, PendingVO> pending = new HashMap<>();
}
