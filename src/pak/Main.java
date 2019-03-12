package pak;

import javafx.util.Pair;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    static String estadoFinal = "000000000000123456";


    public static void main(String[] args) {
        ArrayList<String> estadosPassados = new ArrayList<>();

        ArrayList<Integer> numerosEmbaralhados = new ArrayList<>(6);
        for(int x = 0; x < 6; x++)
            numerosEmbaralhados.add(x);
        Collections.shuffle(numerosEmbaralhados);

        Stack<Character>[] pilhas = new Stack[3];
        for(int x = 0; x < 3; x++)
            pilhas[x] = new Stack<>();

        int s = numerosEmbaralhados.size();
        for(int x = 0; x < s; x++){
            pilhas[0].push((char) ('0' + numerosEmbaralhados.get(x) + 1));
        }

        Stack<String> caminho = new Stack<>();
        boolean found = false;

        System.out.println("Estado inicial: " + dividirEstado(getEstado(pilhas)));

        try {
            for (int x = 0; x < 3 && !found; x++) {
                for (int y = 0; y < 3 && !found; y++) {
                    if (x != y) {
                        if (busca(estadosPassados, pilhas, x, y, caminho, estadoFinal) == 1) {

                            found = true;
                        }
                    }
                }
            }
        } catch (StackOverflowError error){
            error.printStackTrace();
            System.out.println("StackOverflow: Aumente o tamanho da stack usando -Xss1024m como argumento pro java");
        }

        int passos = caminho.size();
        while(!caminho.empty()){
            System.out.println(caminho.pop());
        }

        System.out.println("Número de passos: " + passos);
    }



    public static int busca(ArrayList<String> estadosPassados, Stack<Character>[] pilhas, int fonte, int destino, Stack<String> caminho, String objetivo){
        // Condição: A pilha fonte não pode estar vazia
        if(pilhas[fonte].empty())
            return 0;

        pilhas[destino].push(pilhas[fonte].pop());
        String novoEstado = getEstado(pilhas);

        // Condição: Se atingir o objetivo retornar empilhando o caminho
        if(novoEstado.equals(objetivo))
            return 1;

        // Condição: Não avaliar estados previamente avaliados
        if(estadosPassados.contains(novoEstado)){
            pilhas[fonte].push(pilhas[destino].pop());
            return 0;
        } else {
            estadosPassados.add(novoEstado);
        }


        // Avaliar todas combinações possiveis de jogadas
        for(int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++)
                if (x != y)
                    if (busca(estadosPassados, pilhas, x, y, caminho, objetivo) == 1) {
                        caminho.push(x + " - " + y + ", " + dividirEstado(novoEstado));
                        return 1;
                    }
        }

        pilhas[fonte].push(pilhas[destino].pop());
        return 0;

    }

    public static String dividirEstado(String estado){
        return estado.substring(0, 6) + " "
                + estado.substring(6, 12) + " "
                + estado.substring(12, 18);
    }

    public static String getEstado(Stack<Character>[] pilhas){
        String estado = "";

        for(int x = 0; x < 3; x++){
            int l = 6 - pilhas[x].toArray().length;
            Object[] cp =  pilhas[x].toArray();

            for(int i = 0;i < l; i++){
                estado = estado.concat("0");
            }

            for(int i = cp.length -1; i >= 0; i--){
                estado = estado.concat(String.valueOf((Character) cp[i]));
            }
        }

        if(estado.length() != 18){
            System.out.println("Algo errado " + estado);
        }
        return estado;
    }
}