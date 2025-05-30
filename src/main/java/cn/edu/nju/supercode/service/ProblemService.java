package cn.edu.nju.supercode.service;

import cn.edu.nju.supercode.vo.FullProblemVO;
import cn.edu.nju.supercode.vo.ProblemVO;
import cn.edu.nju.supercode.vo.SimpleProblemVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProblemService {
    void createProblem(FullProblemVO fullProblemVO);

    List<SimpleProblemVO> getProblems();

    ProblemVO getProblemDetail(String uuid) throws Exception;

    FullProblemVO getFullProblemDetail(String uuid) throws Exception;


    void deleteProblem(String uuid) throws Exception;

    void updateProblem(FullProblemVO fullProblemVO) throws Exception;
}
