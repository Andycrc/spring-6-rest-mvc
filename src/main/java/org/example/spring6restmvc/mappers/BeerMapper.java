package org.example.spring6restmvc.mappers;

import org.example.spring6restmvc.entities.Beer;
import org.example.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);

}
