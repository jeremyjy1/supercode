package cn.edu.nju.supercode.service;

import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProblemService {
    void createProblem(ProblemVO problemVO);

    List<SimpleProblemVO>getProblems();

    ProblemVO getProblemDetail(String uuid) throws Exception;

    void deleteProblem(String uuid) throws Exception;

    void updateProblem(ProblemVO problemVO) throws Exception;
}
