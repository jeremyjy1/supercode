package cn.edu.nju.supercode.serviceImpl;

import cn.edu.nju.supercode.exception.SuperCodeException;
import cn.edu.nju.supercode.po.Problem;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.repository.ProblemRepository;
import cn.edu.nju.supercode.repository.UserRepository;
import cn.edu.nju.supercode.service.ProblemService;
import cn.edu.nju.supercode.service.UserService;
import cn.edu.nju.supercode.util.TokenUtil;
import cn.edu.nju.supercode.vo.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemRepository problemRepository;

    @Override
    public void createProblem(ProblemVO problemVO) {
        Problem problem=problemVO.toPO();
        problemRepository.save(problem);
    }

    @Override
    public List<SimpleProblemVO> getProblems() {
        List<Problem>problems=problemRepository.findAll();
        List<SimpleProblemVO>simpleProblems=new ArrayList<>();
        for(Problem i:problems){
            simpleProblems.add(i.toSimpleVO());
        }
        return simpleProblems;
    }

    @Override
    public ProblemVO getProblemDetail(String uuid) {
        Problem problem=problemRepository.findById(uuid).orElseThrow(SuperCodeException::problemNotFound);
        return problem.toVO();
    }

    @Override
    public void deleteProblem(String uuid) {
        Problem problem=problemRepository.findById(uuid).orElseThrow(SuperCodeException::problemNotFound);
        problemRepository.delete(problem);
    }

    @Override
    public void updateProblem(ProblemVO problemVO) {
        if (problemVO.getUuid() != null) {
            if(problemRepository.findById(problemVO.getUuid()).isEmpty())
                throw SuperCodeException.problemNotFound();
            Problem problem=problemVO.toPO();
            problemRepository.save(problem);
        }
        else
            throw SuperCodeException.problemNotFound();
    }
}
