package com.example.testeprothera.Repository;
import com.example.testeprothera.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @Query("SELECT p, TIMESTAMPDIFF(YEAR, p.dataNascimento, CURDATE()) AS idade FROM pessoa p ORDER BY p.dataNascimento ASC LIMIT 1")
    Object findMaisVelho();

    @Query("SELECT p FROM pessoa p WHERE MONTH(p.dataNascimento) = 10 OR MONTH(p.dataNascimento ) = 12")
    List<Pessoa> findPessoaOutEDez();
    List<Pessoa> findAllByOrderByNomeAsc();

    Pessoa findByNome(String nome);
}
