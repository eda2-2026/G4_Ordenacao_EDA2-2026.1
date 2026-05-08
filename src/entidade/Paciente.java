package entidade;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class Paciente {
    private String nome,
                   cpf;
    private char sexo;
    private int id,
                idade,
                gravidade;
    private boolean temCadastroCompleto;
    private LocalDate dataNascimento;
    private LocalDateTime chegada;
    private List<Condicao> condicoes;

    // No banco de dados
    public Paciente(String cpf, String nome, int idade, char sexo, LocalDate dataNascimento, int gravidade, List<Condicao> condicoes, boolean temCadastroCompleto) {
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.gravidade = gravidade;
        this.condicoes = condicoes;
        this.temCadastroCompleto = temCadastroCompleto;
    }

    public void adicionarCondicoes(List<Condicao> novasCondicoes) {
        this.condicoes.addAll(novasCondicoes);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getGravidade() {
        return gravidade;
    }

    public void setGravidade(int gravidade) {
        this.gravidade = gravidade;
    }

    public boolean isTemCadastroCompleto() {
        return temCadastroCompleto;
    }

    public void setTemCadastroCompleto(boolean temCadastroCompleto) {
        this.temCadastroCompleto = temCadastroCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public void setChegada(LocalDateTime chegada) {
        this.chegada = chegada;
    }

    public List<Condicao> getCondicoes() {
        return condicoes;
    }

    public void setCondicoes(List<Condicao> condicoes) {
        this.condicoes = condicoes;
    }
}
