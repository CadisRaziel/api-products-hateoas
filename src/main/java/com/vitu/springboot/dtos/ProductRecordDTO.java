package com.vitu.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRecordDTO(

        @NotBlank //-> Nao poder se null e nao pode ser "" uma string vazia
        String name,

        @NotNull
        BigDecimal value) {
    //Record -> ele é tipo um Lombok, ele ja traz construtor, getters, hashcode sem que eu precise criar
}
