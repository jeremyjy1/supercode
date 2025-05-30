package cn.edu.nju.supercode.po;

import cn.edu.nju.supercode.converter.StdioConverter;
import cn.edu.nju.supercode.vo.SimpleSubmissionResultVO;
import cn.edu.nju.supercode.vo.StdioVO;
import cn.edu.nju.supercode.vo.SubmissionResultVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Submission {
    @Id
    @Column(name = "submission_id")
    private String submissionId;

    @Basic
    @Column(name = "problemId", nullable = false)
    private String problemId;

    @Basic
    @Column(name = "userId", nullable = false)
    private String userId;

    @Basic
    @Column(name = "code", nullable = false,columnDefinition = "LONGTEXT")
    private String code;

    @Basic
    @Column(name = "score", nullable = false)
    private Integer score;

    @Basic
    @Column(name = "lang", nullable = false)
    private String lang;

    @Basic
    @Column(name = "time", nullable = false)
    private Integer time;

    @Basic
    @Column(name = "memory", nullable = false)
    private Integer memory;

    @Basic
    @Column(name = "result", nullable = false)
    private String result;

    @Basic
    @Column(name="stdio",nullable = false,columnDefinition = "LONGTEXT")
    @Convert(converter= StdioConverter.class)
    private List<StdioVO> stdio;

    @Basic
    @Column(name = "submission_time", nullable = false)
    private LocalDateTime submissionTime;

    public SubmissionResultVO toSubmissionResultVO(){
        SubmissionResultVO submissionResultVO=new SubmissionResultVO();
        submissionResultVO.setSubmitId(submissionId);
        submissionResultVO.setProblemId(problemId);
        submissionResultVO.setCode(code);
        submissionResultVO.setScore(score);
        submissionResultVO.setLang(lang);
        submissionResultVO.setTime(time);
        submissionResultVO.setMemory(memory);
        submissionResultVO.setResult(result);
        submissionResultVO.setSubmissionTime(submissionTime);
        return submissionResultVO;
    }

    public SimpleSubmissionResultVO toSimpleSubmissionResultVO(){
        SimpleSubmissionResultVO simpleSubmissionResultVO=new SimpleSubmissionResultVO();
        simpleSubmissionResultVO.setSubmitId(submissionId);
        simpleSubmissionResultVO.setProblemId(problemId);
        simpleSubmissionResultVO.setScore(score);
        simpleSubmissionResultVO.setLang(lang);
        simpleSubmissionResultVO.setTime(time);
        simpleSubmissionResultVO.setMemory(memory);
        simpleSubmissionResultVO.setResult(result);
        simpleSubmissionResultVO.setSubmissionTime(submissionTime);
        return simpleSubmissionResultVO;
    }
}
