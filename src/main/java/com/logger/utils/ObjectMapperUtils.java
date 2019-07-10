package com.logger.utils;

import com.logger.session.entity.Session;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

public class ObjectMapperUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        TypeMap<Session, Session> typeMap = modelMapper.createTypeMap(Session.class, Session.class);
    }

    public static <S, D> D map(S source, Class<D> outClass) {
        return modelMapper.map(source, outClass);
    }

    public static <S, D> void map(S source, D destination) {
        modelMapper.map(source, destination);
    }
}

