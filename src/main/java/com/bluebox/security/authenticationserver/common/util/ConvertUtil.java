package com.bluebox.security.authenticationserver.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author kamran
 */
public class ConvertUtil {
    private static ModelMapper modelMapper = new ModelMapper();

    private ConvertUtil() {
    }

    public static <S, D> D to(S s, Class<D> clazz) {
        if (s == null)
            return null;
        return modelMapper.map(s, clazz);
    }

    public static <S, D> D to(S s, Class<D> clazz, PropertyMap<S, D> config) {
        if (s == null)
            return null;
        modelMapper.addMappings(config);
        return modelMapper.map(s, clazz);
    }

    public static <S, D> D to(PropertyMap<?, ?> config, S s, Class<D> clazz) {
        if (s == null)
            return null;
        modelMapper.addMappings(config);
        return modelMapper.map(s, clazz);
    }

    public static <S, D> List<D> to(Collection<S> list, Class<D> clazz) {
        if (list == null)
            return new ArrayList<>();
        return list.stream().map(s -> ConvertUtil.map(modelMapper, s, clazz)).collect(Collectors.toList());
    }

    public static <S, D> List<D> to(Iterable<S> list, Class<D> clazz) {
        if (list == null)
            return new ArrayList<>();
        return StreamSupport.stream(list.spliterator(), false).map(s -> ConvertUtil.map(modelMapper, s, clazz)).collect(Collectors.toList());
    }

    public static <S> String toString(S s) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDefaultPrettyPrinter(new MinimalPrettyPrinter());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(s);
    }

    private static <D> D map(ModelMapper modelMapper, Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }


}
