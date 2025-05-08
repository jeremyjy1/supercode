package cn.edu.nju.supercode.converter;

import cn.edu.nju.supercode.vo.SampleVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class SampleConverter implements AttributeConverter<List<SampleVO>,String> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<SampleVO> sampleVOList) {
        try {
            return objectMapper.writeValueAsString(sampleVOList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SampleVO> convertToEntityAttribute(String s) {
        try {
            SampleVO[] samples=objectMapper.readValue(s,SampleVO[].class);
            return Arrays.asList(samples);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
