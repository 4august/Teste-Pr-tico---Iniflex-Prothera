package com.example.testeprothera.Repository;

import com.example.testeprothera.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("SELECT SUM(salario) from funcionario")
    BigDecimal somaDosSalarios();

    @Query("SELECT f,  f.salario / 1212 AS qtd_salario_min FROM funcionario f")
    List<Object[]> salariosMinFuncionarios();

    List<Funcionario> findByFuncao(String funcao);
}
