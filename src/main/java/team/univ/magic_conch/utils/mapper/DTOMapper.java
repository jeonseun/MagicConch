package team.univ.magic_conch.utils.mapper;

import lombok.Getter;

import java.util.function.Function;
@Getter
public class DTOMapper<T, DTO> {

    private Function<T, DTO> convertFunc;

    public DTOMapper(Function<T, DTO> convertFunc) {
        this.convertFunc = convertFunc;
    }

    public DTO mapping(T t) {
        return convertFunc.apply(t);
    }
}
