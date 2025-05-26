package cn.edu.nju.supercode.converter;

import cn.edu.nju.supercode.vo.StdioVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class StdioConverter implements AttributeConverter<List<StdioVO>,String> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<StdioVO> stdioVOList) {
        try {
            return objectMapper.writeValueAsString(stdioVOList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StdioVO> convertToEntityAttribute(String s) {
        try {
            StdioVO[] stdioVOS=objectMapper.readValue(s, StdioVO[].class);
            return Arrays.asList(stdioVOS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
