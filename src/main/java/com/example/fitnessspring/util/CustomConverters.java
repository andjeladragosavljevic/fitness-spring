package com.example.fitnessspring.util;


import com.example.fitnessspring.models.entities.ImageEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.List;
import java.util.stream.Collectors;

public class CustomConverters {


    public static Converter<List<ImageEntity>, List<String>> imageEntityToStringConverter = new Converter<List<ImageEntity>, List<String>>() {
        @Override
        public List<String> convert(MappingContext<List<ImageEntity>, List<String>> context) {
            return context.getSource().stream().map(ImageEntity::getUrl).collect(Collectors.toList());
        }
    };

    public static ModelMapper configureModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(imageEntityToStringConverter);
        return modelMapper;
    }


}
