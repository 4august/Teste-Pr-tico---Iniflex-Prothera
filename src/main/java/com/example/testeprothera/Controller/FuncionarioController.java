package com.example.testeprothera.Controller;

import com.example.testeprothera.DTO.FuncionarioDTO;
import com.example.testeprothera.Repository.FuncionarioRepository;
import com.example.testeprothera.Repository.PessoaRepository;
import com.example.testeprothera.entities.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    /*3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
    • informação de data deve ser exibido no formato dd/mm/aaaa;
    • informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.*/
    @GetMapping("/listar-todos")
    public ResponseEntity listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody FuncionarioDTO data) {
        return ResponseEntity.ok(repository.save(new Funcionario(data)));
    }

    //3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
    @PostMapping("/salvar-varios")
    public ResponseEntity salvarVarios(@RequestBody FuncionarioDTO[] data) {
        for (FuncionarioDTO funcionario : data) {
            ResponseEntity.ok(repository.save(new Funcionario(funcionario)));
        }
        return ResponseEntity.ok(data);
    }

    //3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
    @PatchMapping("/atualizar-todos-salarios")
    public ResponseEntity atualizarTodosSalarios() {
        var funcionarios = repository.findAll();

        for (Funcionario funcionario : funcionarios) {
            Optional<Funcionario> func = repository.findById(funcionario.getId());

            var novoSalario = func.get().getSalario().doubleValue() + func.get().getSalario().doubleValue() * 0.10;
            var salarioFormated = new BigDecimal(novoSalario).setScale(2, BigDecimal.ROUND_HALF_EVEN);

            func.get().setSalario(salarioFormated);

            repository.save(func.get());
        }

        return ResponseEntity.ok(listarTodos());
    }

    //3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
    @GetMapping("/mais-velho")
    public ResponseEntity funcionarioMaisVelho() {
        return ResponseEntity.ok(pessoaRepository.findMaisVelho());
    }

    //3.10 – Imprimir a lista de funcionários por ordem alfabética.
    @GetMapping("/ordem-alfabetica")
    public ResponseEntity listarOrdemAlfabetica() {
        return ResponseEntity.ok(pessoaRepository.findAllByOrderByNomeAsc());
    }

    //3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
    @GetMapping("/nascidos-out-dez")
    public ResponseEntity nascidosDezEOut() {
        return ResponseEntity.ok(pessoaRepository.findPessoaOutEDez());
    }

    //3.11 – Imprimir o total dos salários dos funcionários.
    @GetMapping("/soma-salarios")
    public ResponseEntity somaSalaraios() {
        return ResponseEntity.ok("O total dos salarios dos funcionarios eh de: R$" + repository.somaDosSalarios());
    }

    //3.2 – Remover o funcionário “João” da lista
    @DeleteMapping("/deleta-joao")
    public ResponseEntity deletaJoao() {
        var jao = pessoaRepository.findByNome("João");
        repository.deleteById(jao.getId());

        return ResponseEntity.ok("jao deletado");
    }
    //3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
    @GetMapping("/salarios-minimos")
    public ResponseEntity salariosMinimosFunc() {
        return ResponseEntity.ok(repository.salariosMinFuncionarios());
    }

    //3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
    @GetMapping("/listar-todos-funcao")
    public ResponseEntity listarFuncionariosFuncao() {
        var funcionarios = repository.findAll();

        Map<String, List<FuncionarioDTO>> funcPorFuncao =
                funcionarios.stream()
                        .map(funcionario -> new FuncionarioDTO(
                                funcionario.getNome(),
                                funcionario.getDataNascimento(),
                                funcionario.getSalario(),
                                funcionario.getFuncao()
                        ))
                        .collect(Collectors.groupingBy(FuncionarioDTO::getFuncao));

        return ResponseEntity.ok(funcPorFuncao);
    }

    //3.6 – Imprimir os funcionários, agrupados por função.
    @GetMapping("/listar-funcao/{funcao}")
    public ResponseEntity listarFuncionariosPorFuncao(@PathVariable String funcao) {
        return ResponseEntity.ok(repository.findByFuncao(funcao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("deletado");
    }
}
