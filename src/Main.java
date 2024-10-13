import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) throws IOException {
		
        String caminhoDoArquivo = "D:\\Trabalhos\\Trabalhos 2024\\2 semestre\\Organização e Arquitetura de Computadores\\trabGA\\TrabGa_Aqr_Org\\Instrucoes.txt";
        List<String> linhas = Files.readAllLines(Paths.get(caminhoDoArquivo));
        String[] instrucoes = linhas.toArray(new String[0]);
        
        Processador processador = new Processador(instrucoes);
        Scanner scanner = new Scanner(System.in);
        
        while(processador.acabou == 0) {
            // Espera o usuário pressionar Enter para continuar
            System.out.print("Pressione Enter para continuar...");
            scanner.nextLine(); // Aguarda a entrada do usuário
        	processador.wrightback();
        	processador.memoria();
        	processador.execucao();
        	processador.decodificacao();
            processador.busca();
            //processador.verificarDone();
            processador.imprimirEstado();
        }
        scanner.close();
        System.out.println("Instruções Finalizadas!");
    }
}