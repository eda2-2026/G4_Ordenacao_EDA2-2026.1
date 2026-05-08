package estrutura;
import entidade.Paciente;
import java.util.List;

public class MaxHeap {

    private MaxHeap(){

    }

    private static void swap(int i, int j, List<Paciente> lista) {
        Paciente temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
    }

    private static void shiftUp(int index, List<Paciente> lista){
        if(index == 0){
            return; // Condição de parada
        }

        int pai = (index-1)/2;

        if(lista.get(index).getScorePrioridade() > lista.get(pai).getScorePrioridade()){
            swap(index, pai, lista);
            shiftUp(pai, lista);
        }
    }

    private static void shiftDown(int index, List<Paciente> lista){
        if((index*2 + 1) >= lista.size()){
            return; // Verifica existência de algum filho (Esquerdo)
        }
        int maiorfilho;
        int filhoe = index * 2 + 1;

        if((index*2 + 2) < lista.size()){ // Se filho direito existe
            int filhod = index * 2 + 2; //Pode nem sempre existir

            if(lista.get(filhod).getScorePrioridade() > lista.get(filhoe).getScorePrioridade()){ // Qual maior filho?
                maiorfilho = filhod;
            }else{
                maiorfilho = filhoe;
            }
        }else{
            maiorfilho = filhoe;
        }

        if(lista.get(maiorfilho).getScorePrioridade() > lista.get(index).getScorePrioridade()){
            swap(index, maiorfilho, lista);
            shiftDown(maiorfilho, lista);
        }
    }


    public static void inserirPacienteFilaPrioridade(Paciente paciente, List<Paciente> lista){
        lista.add(paciente); // Índice -> size-1 (Última Posição)
        int index = lista.size() - 1;
        shiftUp(index, lista);
    }

    public static Paciente removerPacienteFilaPrioridade(List<Paciente> lista){
        if(lista.isEmpty()){
            throw new RuntimeException("A fila está vazia!");
        }
        int ultimo = lista.size() - 1;
        Paciente removido = lista.getFirst();
        swap(0, ultimo, lista);
        lista.remove(ultimo);
        shiftDown(0, lista);
        return removido;
    }

}