package cn.edu.nju.supercode.service;

import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubmissionService {

    void submit(User user, String problemId,SubmissionVO submissionVO);

    SubmissionResultVO getSingleSubmission(User user,String submitId);

    List<SimpleSubmissionResultVO> getResultsOfUser(User user);
}
