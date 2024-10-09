import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class Main {

	public static void main(String[] args) throws IOException {
		
        String caminhoDoArquivo = "C:/Users/augus/eclipse-workspace/TrabGa_Arq_Org/Instrucoes.txt";
        List<String> linhas = Files.readAllLines(Paths.get(caminhoDoArquivo));
        String[] instrucoes = linhas.toArray(new String[0]);
        
        Processador processador = new Processador(instrucoes);
        
        while(processador.acabou == 0) {
        	processador.wrightback();
        	processador.memoria();
        	processador.execucao();
        	processador.decodificacao();
        	processador.busca();
        }
        
        System.out.println("Instruções Finalizadas!");
    }
}