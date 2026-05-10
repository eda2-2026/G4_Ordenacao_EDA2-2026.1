package entidade;
import util.DataUtil;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    // Dados permanentes
    private String nome, cpf;
    private char sexo;
    private int idade;
    private boolean temCadastro;
    private LocalDate dataNascimento;
    private List<HistoricoClinico> historicoClinico = new ArrayList<>();

    // Dados da visita atual
    private int id;
    private int scorePrioridade;
    private LocalDateTime chegada;
    private List<CondicaoAtual> condicoesAtuais = new ArrayList<>();

    /**
     * Construtor para Paciente com Cadastro Completo
     */
    public Paciente(String cpf, String nome, char sexo, LocalDate dataNascimento,
                    List<HistoricoClinico> historicoClinico) {
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;

        if (historicoClinico != null) this.historicoClinico = historicoClinico;

        this.temCadastro = true;
    }

    /**
     * Construtor para Paciente Temporário (Chegou na emergência sem docs)
     */
    public Paciente(int id, String nome, int idade, char sexo) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.idade = idade;
        this.temCadastro = false;
    }

    // GETTERS E SETTERS

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

    public List<HistoricoClinico> getHistoricoClinico() { return historicoClinico; }
    public void setHistoricoClinico(List<HistoricoClinico> historicoClinico) { this.historicoClinico = historicoClinico; }

    public List<CondicaoAtual> getCondicoesAtuais() { return condicoesAtuais; }
    public void setCondicoesAtuais(List<CondicaoAtual> condicoesAtuais) { this.condicoesAtuais = condicoesAtuais; }
}
