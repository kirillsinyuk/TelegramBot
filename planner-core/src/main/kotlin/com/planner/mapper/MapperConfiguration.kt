package com.planner.mapper

import org.mapstruct.CollectionMappingStrategy
import org.mapstruct.InjectionStrategy
import org.mapstruct.MapperConfig
import org.mapstruct.MappingConstants.ComponentModel
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.NullValuePropertyMappingStrategy

@MapperConfig(
    componentModel = ComponentModel.SPRING,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
interface MapperConfiguration
