package cn.edu.nju.supercode.repository;

import cn.edu.nju.supercode.po.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, String> {
}