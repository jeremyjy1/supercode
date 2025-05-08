package cn.edu.nju.supercode.po;

import cn.edu.nju.supercode.converter.SampleConverter;
import cn.edu.nju.supercode.vo.ProblemVO;
import cn.edu.nju.supercode.vo.SampleVO;
import cn.edu.nju.supercode.vo.SimpleProblemVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Problem {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "description", nullable = false,columnDefinition = "LONGTEXT")
    private String description;

    @Basic
    @Column(name="samples",nullable = false,columnDefinition = "LONGTEXT")
    @Convert(converter= SampleConverter.class)
    private List<SampleVO>samples;

    public ProblemVO toVO(){
        ProblemVO problemVO=new ProblemVO();
        problemVO.setUuid(uuid);
        problemVO.setTitle(title);
        problemVO.setDescription(description);
        problemVO.setSamples(samples);
        return problemVO;
    }

    public SimpleProblemVO toSimpleVO(){
        SimpleProblemVO simpleProblemVO=new SimpleProblemVO();
        simpleProblemVO.setUuid(uuid);
        simpleProblemVO.setTitle(title);
        return simpleProblemVO;
    }
}
