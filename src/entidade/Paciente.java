package entidade;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

public class Paciente {
    // Dados permanentes
    private String nome,
                   cpf;
    private char sexo;
    private int idade;
    private boolean temCadastroCompleto;
    private LocalDate dataNascimento;
    private List<Condicao> historicoCondicoes;

    // Dados da visita atual
    private int id;
    private int gravidade;
    private LocalDateTime chegada;
    private List<Condicao> condicoesAtuais;

    // No banco de dados
    public Paciente(String cpf, String nome, int idade, char sexo, LocalDate dataNascimento, int gravidade, List<Condicao> historicoCondicoes, boolean temCadastroCompleto) {
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.gravidade = gravidade;
        this.historicoCondicoes = historicoCondicoes;
        this.temCadastroCompleto = temCadastroCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
    }

    public void setChegada(LocalDateTime chegada) {
        this.chegada = chegada;
    }

    public List<Condicao> getCondicoesAtuais() {
        return condicoesAtuais;
    }

    public void setCondicoesAtuais(List<Condicao> condicoesAtuais) {
        this.condicoesAtuais = condicoesAtuais;
    }
}
