package estrutura;

import entidade.Paciente;
import java.util.List;

public class InsertionSort {

    private InsertionSort() {}

    public static void ordenar(List<Paciente> lista) {
        int n = lista.size();
        for (int i = 1; i < n; i++) {
            Paciente atual = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j).getCpf().compareTo(atual.getCpf()) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, atual);
        }
    }

    public static void inserirOrdenado(List<Paciente> lista, Paciente paciente) {
        lista.add(paciente);
        int i = lista.size() - 1;

        while (i > 0) {
            Paciente atual = lista.get(i);
            Paciente anterior = lista.get(i - 1);

            if (atual.getCpf().compareTo(anterior.getCpf()) < 0) {
                lista.set(i, anterior);
                lista.set(i - 1, atual);
                i--;
            } else {
                break;
            }
        }
    }
}