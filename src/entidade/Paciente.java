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
    private boolean temCadastro;
    private LocalDate dataNascimento;
    private List<Condicao> historicoCondicoes;

    // Dados da visita atual
    private int id;
    private int scorePrioridade;
    private LocalDateTime chegada;
    private List<Condicao> condicoesAtuais;

    public Paciente(String cpf, String nome, int idade, char sexo, LocalDate dataNascimento, List<Condicao> historicoCondicoes) {
        // Paciente com cadastro
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.historicoCondicoes = historicoCondicoes;
        this.temCadastro = true;
    }

    public Paciente(int id, String nome, int idade, char sexo) {
        // Paciente temporário
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.temCadastro = false;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setScorePrioridade(int scorePrioridade) {
        this.scorePrioridade = scorePrioridade;
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
