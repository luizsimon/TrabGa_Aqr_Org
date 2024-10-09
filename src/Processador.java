import java.util.ArrayList;

public class Processador {
	public String instrucoesexecutando[], instrucoes[];
	public int[] registradores, instrucaoPronta, memoria, instrucoesDecodificadas;
	public int pc, semiAcabou, acabou;

	public Processador(String instr[]) {
		this.instrucoesexecutando = new String[5];
		this.instrucoesDecodificadas = new int[5];
		this.registradores = new int[32];
		this.memoria = new int[128];
		this.instrucaoPronta = new int[4];
		this.instrucoes = instr;
		this.pc = 0;
		this.acabou = 0;
		this.semiAcabou = 0;
	}

	public void busca() {
		instrucoesexecutando[1] = instrucoesexecutando[0];
		instrucoesexecutando[0] = instrucoes[pc];
		instrucoesDecodificadas[1] = instrucoesDecodificadas[0];
		pc++;
	}

	public void decodificacao() {
		String[] instrucoesQuebradas = instrucoesexecutando[1].split(" ");
		switch (instrucoesQuebradas[0]) {
			case "lw": {
				instrucaoPronta[0] = 0;
				instrucaoPronta[1] = Integer.parseInt(instrucoesQuebradas[1]);
				instrucaoPronta[2] = Integer.parseInt(instrucoesQuebradas[2]);
				instrucaoPronta[3] = Integer.parseInt(instrucoesQuebradas[3]);
				break;
			}
			case "sw": {
				instrucaoPronta[0] = 1;
				instrucaoPronta[1] = Integer.parseInt(instrucoesQuebradas[1]);
				instrucaoPronta[2] = Integer.parseInt(instrucoesQuebradas[2]);
				instrucaoPronta[3] = Integer.parseInt(instrucoesQuebradas[3]);
				break;
			}
			case "add": {
				instrucaoPronta[0] = 2;
				instrucaoPronta[1] = Integer.parseInt(instrucoesQuebradas[1]);
				instrucaoPronta[2] = Integer.parseInt(instrucoesQuebradas[2]);
				instrucaoPronta[3] = Integer.parseInt(instrucoesQuebradas[3]);
				break;
			}
			case "sub": {
				instrucaoPronta[0] = 3;
				instrucaoPronta[1] = Integer.parseInt(instrucoesQuebradas[1]);
				instrucaoPronta[2] = Integer.parseInt(instrucoesQuebradas[2]);
				instrucaoPronta[3] = Integer.parseInt(instrucoesQuebradas[3]);
				break;
			}
			case "beq": {
				instrucaoPronta[0] = 4;
				instrucaoPronta[1] = Integer.parseInt(instrucoesQuebradas[1]);
				instrucaoPronta[2] = Integer.parseInt(instrucoesQuebradas[2]);
				instrucaoPronta[3] = Integer.parseInt(instrucoesQuebradas[3]);
				break;
			}
			case "noop": {
				instrucaoPronta[0] = 5;
				break;
			}
			case "halt": {
				instrucaoPronta[0] = 6;
				break;
			}
			default: 
				throw new IllegalArgumentException("Instrução incorreta: " + instrucoesQuebradas[0]);
		}
		
		instrucoesexecutando[2] = instrucoesexecutando[1];
		instrucoesDecodificadas[2] = instrucoesDecodificadas[1];
	}

	public void execucao() {
		switch(instrucaoPronta[0]) {
			case(0): {//lw
				int posicao = 0;
				posicao = registradores[instrucaoPronta[1]] + registradores[instrucaoPronta[2]];
				registradores[posicao] = instrucaoPronta[3];
				break;
			}
			case(1): {//sw
				break;
			}
			case(2): {//add
				int soma = 0;
				//registrador
				soma = registradores[instrucaoPronta[1]] + registradores[instrucaoPronta[2]];
				registradores[instrucaoPronta[3]] = soma;
				break;
			}
			case(3): {//sub
				int sub = 0;
				sub = registradores[instrucaoPronta[1]] - registradores[instrucaoPronta[2]];
				registradores[instrucaoPronta[3]] = sub;
				break;
			}
			case(4): {//beq
				if(instrucaoPronta[1] == instrucaoPronta[2])
					pc = registradores[instrucaoPronta[3]];
				break;
			}
			case(5)://noop
				break;
			case(6)://halt
				break;
		}
		instrucoesexecutando[3] = instrucoesexecutando[2];
		instrucoesDecodificadas[3] = instrucoesDecodificadas[2];
	}

	public void memoria() {
		instrucoesexecutando[4] = instrucoesexecutando[3];
	}

	public void wrightback() {
		if(semiAcabou == 1)
			acabou = 1;
	}
}