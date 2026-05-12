package util;

import entidade.HistoricoClinico;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class GeradorPacientes {

    private static final Random random = new Random();

    private static final String[] nomesMasculinos = {
            "Lucas", "Miguel", "Arthur", "Davi", "Theo", "Gabriel", "Samuel", "Pedro", "João", "Rafael",
            "Mateus", "Enzo", "Bernardo", "Guilherme", "Gustavo", "Felipe", "Nicolas", "Henrique",
            "Murilo", "Eduardo", "Victor", "Cauã", "Antônio", "Vicente", "Daniel", "Thiago"
    };

    private static final String[] nomesFemininos = {
            "Maria", "Sophia", "Helena", "Laura", "Alice", "Valentina", "Julia", "Manuela", "Beatriz",
            "Camila", "Isabella", "Mariana", "Lara", "Letícia", "Luiza", "Cecília", "Lívia", "Isadora",
            "Lorena", "Ana", "Clara", "Giovanna", "Yasmin", "Melissa", "Marina"
    };

    private static final String[] sobrenomes = {
            "Silva", "Souza", "Oliveira", "Santos", "Lima", "Ferreira", "Rodrigues", "Almeida", "Cardoso",
            "Barreto", "Freitas", "Costa", "Carvalho", "Martins", "Araújo", "Melo", "Barbosa", "Ribeiro",
            "Alves", "Pinto", "Teixeira", "Cavalcanti", "Dias", "Castro", "Rocha", "Mendes", "Nunes"
    };

    private static final HistoricoClinico[] crianca = {
            HistoricoClinico.AUTISMO, HistoricoClinico.DEFICIENCIA_INTELECTUAL,
            HistoricoClinico.DOENCA_PULMONAR, HistoricoClinico.ALERGIA_GRAVE, HistoricoClinico.EPILEPSIA
    };

    private static final HistoricoClinico[] adulto = {
            HistoricoClinico.DOENCA_AUTOIMUNE, HistoricoClinico.INSUFICIENCIA_RENAL,
            HistoricoClinico.EPILEPSIA, HistoricoClinico.CIRURGIA_RECENTE,
            HistoricoClinico.DEFICIENCIA_FISICA, HistoricoClinico.DOENCA_PULMONAR
    };

    private static final HistoricoClinico[] idoso = {
            HistoricoClinico.INSUFICIENCIA_RENAL, HistoricoClinico.CANCER,
            HistoricoClinico.DEFICIENCIA_VISUAL, HistoricoClinico.DEFICIENCIA_AUDITIVA,
            HistoricoClinico.CIRURGIA_RECENTE, HistoricoClinico.INTERNACAO_RECENTE
    };

    private static class PacienteGerado implements Comparable<PacienteGerado> {
        String cpf;
        String nome;
        char sexo;
        LocalDate dataNascimento;
        String historicos;

        @Override
        public int compareTo(PacienteGerado outro) {
            return this.cpf.compareTo(outro.cpf);
        }
    }

    public static void main(String[] args) {
        int quantidadeGerar = 500;
        List<PacienteGerado> listaPacientes = new ArrayList<>();
        Set<String> cpfsUsados = new HashSet<>();

        System.out.println("Gerando " + quantidadeGerar + " pacientes com consistência médica e CPFs válidos...");

        for (int i = 0; i < quantidadeGerar; i++) {
            PacienteGerado p = new PacienteGerado();
            p.sexo = random.nextBoolean() ? 'M' : 'F';
            p.nome = gerarNome(p.sexo);
            p.dataNascimento = gerarDataNascimento();

            do {
                p.cpf = gerarCpfValido();
            } while (cpfsUsados.contains(p.cpf));
            cpfsUsados.add(p.cpf);

            int idade = Period.between(p.dataNascimento, LocalDate.now()).getYears();
            Set<HistoricoClinico> historicos = gerarHistoricos(idade);
            p.historicos = historicosToString(historicos);

            listaPacientes.add(p);
        }

        Collections.sort(listaPacientes);

        try (FileWriter writer = new FileWriter("pacientes.csv")) {
            writer.write("cpf,nome,sexo,dataNascimento,historicoClinico\n");
            for (PacienteGerado p : listaPacientes) {
                writer.write(p.cpf + "," + p.nome + "," + p.sexo + "," + p.dataNascimento + "," + p.historicos + "\n");
            }
            System.out.println("✅ Arquivo pacientes.csv gerado e ordenado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao escrever arquivo: " + e.getMessage());
        }
    }

    private static String gerarNome(char sexo) {
        String primeiroNome = (sexo == 'M')
                ? nomesMasculinos[random.nextInt(nomesMasculinos.length)]
                : nomesFemininos[random.nextInt(nomesFemininos.length)];

        String sobrenome1 = sobrenomes[random.nextInt(sobrenomes.length)];
        String sobrenome2;
        do {
            sobrenome2 = sobrenomes[random.nextInt(sobrenomes.length)];
        } while (sobrenome1.equals(sobrenome2));

        return primeiroNome + " " + sobrenome1 + " " + sobrenome2;
    }

    private static LocalDate gerarDataNascimento() {
        int anoAtual = LocalDate.now().getYear();
        int ano = (anoAtual - 95) + random.nextInt(95);
        int mes = 1 + random.nextInt(12);
        int dia = 1 + random.nextInt(28);
        return LocalDate.of(ano, mes, dia);
    }

    private static Set<HistoricoClinico> gerarHistoricos(int idade) {
        Set<HistoricoClinico> historicos = new HashSet<>();

        if (random.nextDouble() < 0.30) return historicos;

        if (idade >= 60) {
            if (random.nextDouble() < 0.65) historicos.add(HistoricoClinico.HIPERTENSAO);
            if (random.nextDouble() < 0.45) historicos.add(HistoricoClinico.DIABETES);
            if (random.nextDouble() < 0.30) historicos.add(HistoricoClinico.DOENCA_CARDIACA);
            if (random.nextDouble() < 0.15) historicos.add(HistoricoClinico.HISTORICO_INFARTO);
            if (random.nextDouble() < 0.10) historicos.add(HistoricoClinico.HISTORICO_AVC);
        } else if (idade >= 30) {
            if (random.nextDouble() < 0.25) historicos.add(HistoricoClinico.HIPERTENSAO);
            if (random.nextDouble() < 0.15) historicos.add(HistoricoClinico.DIABETES);
            if (random.nextDouble() < 0.10) historicos.add(HistoricoClinico.TRANSTORNO_PSIQUIATRICO);
        } else if (idade >= 13) {
            if (random.nextDouble() < 0.08) historicos.add(HistoricoClinico.USO_DROGAS);
            if (random.nextDouble() < 0.10) historicos.add(HistoricoClinico.TRANSTORNO_PSIQUIATRICO);
        } else {
            if (random.nextDouble() < 0.15) historicos.add(HistoricoClinico.ALERGIA_GRAVE);
            if (random.nextDouble() < 0.15) historicos.add(HistoricoClinico.DOENCA_PULMONAR);
            if (random.nextDouble() < 0.05) historicos.add(HistoricoClinico.AUTISMO);
        }

        HistoricoClinico[] pool;
        if (idade <= 12) pool = crianca;
        else if (idade < 60) pool = adulto;
        else pool = idoso;

        int doencasExtras = random.nextInt(3);
        for (int i = 0; i < doencasExtras; i++) {
            historicos.add(pool[random.nextInt(pool.length)]);
        }

        return historicos;
    }

    private static String historicosToString(Set<HistoricoClinico> historicos) {
        if (historicos.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (HistoricoClinico h : historicos) {
            sb.append(h.name()).append(";");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Gera um CPF matematicamente válido de acordo com as regras da Receita Federal.
     */
    private static String gerarCpfValido() {
        int[] cpf = new int[11];

        // 1. Gera os 9 primeiros dígitos aleatórios
        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        // 2. Calcula o 1º Dígito Verificador (posição 9)
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += cpf[i] * (10 - i);
        }
        int resto1 = soma1 % 11;
        cpf[9] = (resto1 < 2) ? 0 : (11 - resto1);

        // 3. Calcula o 2º Dígito Verificador (posição 10)
        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += cpf[i] * (11 - i);
        }
        int resto2 = soma2 % 11;
        cpf[10] = (resto2 < 2) ? 0 : (11 - resto2);

        // Converte o array para String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            sb.append(cpf[i]);
        }

        if (sb.toString().matches("(\\d)\\1{10}")) {
            return gerarCpfValido();
        }

        return sb.toString();
    }
}