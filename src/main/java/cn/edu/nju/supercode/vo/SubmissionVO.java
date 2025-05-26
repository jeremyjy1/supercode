package cn.edu.nju.supercode.vo;

import cn.edu.nju.supercode.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SubmissionVO {
    private String lang;
    private String code;
    public PendingVO toPendingVO(String problemId, User user, LocalDateTime submissionTime){
        PendingVO pendingVO=new PendingVO();
        pendingVO.setProblemId(problemId);
        pendingVO.setLang(lang);
        pendingVO.setCode(code);
        pendingVO.setUser(user);
        pendingVO.setSubmitId(UUID.randomUUID().toString());
        pendingVO.setSubmissionTime(submissionTime);
        return pendingVO;
    }

}
