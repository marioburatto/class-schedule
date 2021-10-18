package com.philips.classschedule.controller.mapper;

public interface DtoMapperInterface<D, T> {
    T domainToDto(D domain);

    D dtoToDomain(T dto);
}
