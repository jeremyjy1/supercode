package cn.edu.nju.supercode.po;

import cn.edu.nju.supercode.converter.StdioConverter;
import cn.edu.nju.supercode.stream.Config;
import cn.edu.nju.supercode.vo.FullProblemVO;
import cn.edu.nju.supercode.vo.ProblemVO;
import cn.edu.nju.supercode.vo.SimpleProblemVO;
import cn.edu.nju.supercode.vo.StdioVO;
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
    @Column(name = "description", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Basic
    @Column(name = "time_limit", nullable = false)
    private Integer timeLimit;

    @Basic
    @Column(name = "time_reserved", nullable = false)
    private Integer timeReserved;

    @Basic
    @Column(name = "memory_limit", nullable = false)
    private Integer memoryLimit;

    @Basic
    @Column(name = "memory_reserved", nullable = false)
    private Integer memoryReserved;

    @Basic
    @Column(name = "large_stack", nullable = false)
    private Boolean largeStack;

    @Basic
    @Column(name = "output_limit", nullable = false)
    private Integer outputLimit;

    @Basic
    @Column(name = "process_limit", nullable = false)
    private Integer processLimit;

    @Basic
    @Column(name = "example_stdio", nullable = false, columnDefinition = "LONGTEXT")
    @Convert(converter = StdioConverter.class)
    private List<StdioVO> exampleStdio;

    @Basic
    @Column(name = "stdio", nullable = false, columnDefinition = "LONGTEXT")
    @Convert(converter = StdioConverter.class)
    private List<StdioVO> stdio;

    public ProblemVO toVO() {
        ProblemVO problemVO = new ProblemVO();
        problemVO.setUuid(uuid);
        problemVO.setTitle(title);
        problemVO.setDescription(description);
        problemVO.setTimeLimit(timeLimit);
        problemVO.setMemoryLimit(memoryLimit);
        problemVO.setExampleStdio(exampleStdio);
        return problemVO;
    }

    public FullProblemVO toFullVO() {
        FullProblemVO fullProblemVO = new FullProblemVO();
        fullProblemVO.setUuid(uuid);
        fullProblemVO.setTitle(title);
        fullProblemVO.setDescription(description);
        fullProblemVO.setTimeLimit(timeLimit);
        fullProblemVO.setTimeReserved(timeReserved);
        fullProblemVO.setMemoryLimit(memoryLimit);
        fullProblemVO.setMemoryReserved(memoryReserved);
        fullProblemVO.setLargeStack(largeStack);
        fullProblemVO.setProcessLimit(processLimit);
        fullProblemVO.setExampleStdio(exampleStdio);
        fullProblemVO.setStdio(stdio);
        return fullProblemVO;
    }

    public SimpleProblemVO toSimpleVO() {
        SimpleProblemVO simpleProblemVO = new SimpleProblemVO();
        simpleProblemVO.setUuid(uuid);
        simpleProblemVO.setTitle(title);
        return simpleProblemVO;
    }

    public Config toConfig() {
        return new Config((timeLimit + 1000 - 1) / 1000, timeReserved, memoryLimit, memoryReserved, largeStack, outputLimit, processLimit);
    }
}
