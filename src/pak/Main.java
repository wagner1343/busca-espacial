package pak;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Stack;

public class Main {
    static String estadoFinal = "000000000000123456";
    static String caminho = "";


    public static void main(String[] args) {
        ArrayList<String> estadosPassados = new ArrayList<>();
        Stack<Character>[] pilhas = new Stack[3];
        for(int x = 0; x < 3; x++){
            pilhas[x] = new Stack<>();
        }
        for(int x = 0; x < 6; x++){
            pilhas[0].push((char) ('0' + x + 1));
        }

        for(int x = 0; x < 6; x++){
            //pilhas[1].push('0');
        }

        for(int x = 0; x < 6; x++){
            //pilhas[2].push('0');
        }

        String altura = " | ";

        Stack<String> caminho = new Stack<>();

        boolean found = false;

        for(int x = 0; x < 3 && !found; x++){
            for(int y = 0; y < 3 && !found; y++){
                if(x != y){
                    System.out.println(altura + "movendo de " + x + " para " + y);
                    if(busca(estadosPassados, pilhas, x, y, altura, caminho) == 1){
                        found = true;
                    }
                }
            }
        }

        while(!caminho.empty()){
            System.out.println(caminho.pop());
        }
    }

    public static int busca(ArrayList<String> estadosPassados, Stack<Character>[] pilhas, int fonte, int destino, String altura, Stack<String> caminho){
        altura = altura.concat(" | ");
        System.out.println(altura + "Main.busca");
        System.out.println(altura + "fonte = [" + fonte + "], destino = [" + destino + "]" + " estado = [" + getEstado(pilhas) + "]");

        if(pilhas[fonte].empty() ) {
            System.out.println(altura + "Pilha fonte " + fonte + " vazia");
            return 0;
        }

        //System.out.println(altura + "pilhas[fonte].peek() = " + pilhas[fonte].peek()
          //      + " Pilhas[destino].peek() = " + (pilhas[destino].empty() ? "0" : pilhas[destino].peek()));

        pilhas[destino].push(pilhas[fonte].pop());
        String novoEstado = getEstado(pilhas);
        System.out.println(altura + "novoEstado = " + novoEstado);
        if(novoEstado.equals(estadoFinal)){
            System.out.println(altura + "Estado final achado");
            return 1;
        } else {
            if(estadosPassados.contains(novoEstado)){
                System.out.println(altura + "Estado ja percorrido");
                pilhas[fonte].push(pilhas[destino].pop());
                return 0;
            }

            estadosPassados.add(novoEstado);
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    if(x != y){
                        System.out.println(altura + "movendo de " + x + " para " + y);
                        if(busca(estadosPassados, pilhas, x, y, altura, caminho) == 1){
                            caminho.push(x + " - " + y);
                            return 1;
                        }
                    }
                }
            }

            pilhas[fonte].push(pilhas[destino].pop());
            return 0;
        }

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
