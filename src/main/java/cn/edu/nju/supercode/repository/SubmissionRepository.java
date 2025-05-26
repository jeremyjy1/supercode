package cn.edu.nju.supercode.repository;

import cn.edu.nju.supercode.po.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, String> {
    List<Submission> findByProblemIdAndUserId(String problemId, String userId);

    List<Submission> findByProblemId(String problemId);

    List<Submission> findByUserId(String userId);
}