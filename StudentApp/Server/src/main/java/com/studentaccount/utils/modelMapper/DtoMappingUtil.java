package com.studentaccount.utils.modelMapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public final class DtoMappingUtil {
    private static ModelMapper mapper = new ModelMapper();

    private DtoMappingUtil() { }

    public static <S, D> D convert(S source, Class<D> destClass) {
        return mapper.map(source, destClass);
    }

    public static <S, D> List<D> convertCollection (Iterable<S> sources, Class<D> destClass) {
        List<D> resultList = new ArrayList<>();
        for (S source : sources) {
            D d = convert(source, destClass);
            resultList.add(d);
        }
        return resultList;
    }
}
