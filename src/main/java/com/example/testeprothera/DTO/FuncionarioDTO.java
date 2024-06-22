package com.example.testeprothera.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FuncionarioDTO(
        String nome,
        LocalDate dataNascimento,
        BigDecimal salario,
        String funcao
) {
    public String getFuncao(){
        return this.funcao;
    }
}
