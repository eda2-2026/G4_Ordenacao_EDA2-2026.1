package entidade;
import util.DataUtil;

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

    public Paciente(String cpf, String nome, char sexo, LocalDate dataNascimento, List<Condicao> historicoCondicoes) {
        // Paciente com cadastro
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
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

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScorePrioridade() {
        return scorePrioridade;
    }

    public void setScorePrioridade(int scorePrioridade) {
        this.scorePrioridade = scorePrioridade;
    }

    public LocalDateTime getChegada() {
        return chegada;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        if (temCadastro)
            return DataUtil.calcularIdade(this.dataNascimento);
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean temCadastro() {
        return temCadastro;
    }

    public void setTemCadastro(boolean temCadastro) {
        this.temCadastro = temCadastro;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Condicao> getHistoricoCondicoes() {
        return historicoCondicoes;
    }

    public void setHistoricoCondicoes(List<Condicao> historicoCondicoes) {
        this.historicoCondicoes = historicoCondicoes;
    }
}