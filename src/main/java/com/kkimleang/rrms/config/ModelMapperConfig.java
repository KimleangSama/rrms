package com.kkimleang.rrms.config;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperConfig {
    private ModelMapperConfig() {
        throw new IllegalStateException("Utility class");
    }

    public static ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Condition<?, ?> skipNulls =
                context -> context.getSource() != null;
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(skipNulls);
        return modelMapper;
    }
}
