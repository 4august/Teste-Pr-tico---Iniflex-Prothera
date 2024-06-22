package com.example.testeprothera.entities;

import com.example.testeprothera.DTO.FuncionarioDTO;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "funcionario")
@Table(name = "funcionario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Funcionario extends Pessoa{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private BigDecimal salario;
    @NotNull
    private String funcao;

    public Funcionario(FuncionarioDTO data){
        super(data.nome(), data.dataNascimento());

        this.salario = data.salario();
        this.funcao = data.funcao();
    }
}
