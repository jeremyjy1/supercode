package cn.edu.nju.supercode.serviceImpl;

import cn.edu.nju.supercode.exception.NotFoundException;
import cn.edu.nju.supercode.po.Problem;
import cn.edu.nju.supercode.repository.ProblemRepository;
import cn.edu.nju.supercode.service.ProblemService;
import cn.edu.nju.supercode.vo.FullProblemVO;
import cn.edu.nju.supercode.vo.ProblemVO;
import cn.edu.nju.supercode.vo.SimpleProblemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemRepository problemRepository;

    @Override
    public void createProblem(FullProblemVO fullProblemVO) {
        Problem problem = fullProblemVO.toPO();
        problemRepository.save(problem);
    }

    @Override
    public List<SimpleProblemVO> getProblems() {
        List<Problem> problems = problemRepository.findAll();
        List<SimpleProblemVO> simpleProblems = new ArrayList<>();
        for (Problem i : problems) {
            simpleProblems.add(i.toSimpleVO());
        }
        return simpleProblems;
    }

    @Override
    public ProblemVO getProblemDetail(String uuid) {
        Problem problem = problemRepository.findById(uuid).orElseThrow(NotFoundException::problemNotFound);
        return problem.toVO();
    }

    @Override
    public FullProblemVO getFullProblemDetail(String uuid) {
        Problem problem = problemRepository.findById(uuid).orElseThrow(NotFoundException::problemNotFound);
        return problem.toFullVO();
    }

    @Override
    public void deleteProblem(String uuid) {
        Problem problem = problemRepository.findById(uuid).orElseThrow(NotFoundException::problemNotFound);
        problemRepository.delete(problem);
    }

    @Override
    public void updateProblem(FullProblemVO fullProblemVO) {
        if (fullProblemVO.getUuid() != null) {
            if (problemRepository.findById(fullProblemVO.getUuid()).isEmpty())
                throw NotFoundException.problemNotFound();
            Problem problem = fullProblemVO.toPO();
            problemRepository.save(problem);
        } else
            throw NotFoundException.problemNotFound();
    }
}
