import java.util.Arrays;

public class Processador {
	public String instrucoesexecutando[], instrucoes[];
	public int[] registradores, instrucaoPronta, memoria, instrucoesDecodificadas;
	public int pc, semiAcabou, acabou, enderecoMemoria, resultadoOperacao;

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

		// Inicialização da memória
		this.memoria[0] = -1;
		this.memoria[1] = 10;
		this.memoria[2] = 1;

		// Inicialização R0
		this.registradores[0] = 0;
	}

	public void busca() {
		System.out.println("Buscando instrução no PC: " + pc);
    	System.out.println("Instrução buscada: " + instrucoes[pc]);
		instrucoesexecutando[1] = instrucoesexecutando[0];
		instrucoesexecutando[0] = instrucoes[pc];
		instrucoesDecodificadas[1] = instrucoesDecodificadas[0];
		pc++;
	}

	public void decodificacao() {
		if (instrucoesexecutando[1] == null) {

		} else {
			String[] instrucoesQuebradas = instrucoesexecutando[1].split(" ");
			System.out.println("Decodificando: " + instrucoesexecutando[1]);
			switch (instrucoesQuebradas[0]) {
				case "lw": {
					instrucaoPronta[0] = 0;
					instrucaoPronta[1] = Integer.parseInt(instrucoesQuebradas[1]); // R origem
					instrucaoPronta[2] = Integer.parseInt(instrucoesQuebradas[2]); // R destino
					instrucaoPronta[3] = Integer.parseInt(instrucoesQuebradas[3]); // offset
	
					//int enderecoMemoria = registradores[instrucaoPronta[1]] + instrucaoPronta[3];
					//registradores[instrucaoPronta[2]] = memoria[enderecoMemoria];
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
		}
		System.out.println("Instrução decodificada: " + Arrays.toString(instrucaoPronta));
		instrucoesexecutando[2] = instrucoesexecutando[1];
		instrucoesDecodificadas[2] = instrucoesDecodificadas[1];
	}

	public void execucao() {
			System.out.println("Executando instrução: " + Arrays.toString(instrucaoPronta));
			switch(instrucaoPronta[0]) {
				case(0): // lw
					enderecoMemoria = registradores[instrucaoPronta[1]] + instrucaoPronta[3];
					System.out.println("LW: Endereço de memória: " + enderecoMemoria);
					break;
				case(1): // sw
					enderecoMemoria = registradores[instrucaoPronta[1]] + instrucaoPronta[3];
					System.out.println("SW: Endereço de memória: " + enderecoMemoria + ", Valor: " + registradores[instrucaoPronta[2]]);
					break;
				case(2): // add
					resultadoOperacao = registradores[instrucaoPronta[1]] + registradores[instrucaoPronta[2]];
					System.out.println("ADD: Resultado: " + resultadoOperacao);
					break;
				case(3): // sub
					resultadoOperacao = registradores[instrucaoPronta[1]] - registradores[instrucaoPronta[2]];
					System.out.println("SUB: Resultado: " + resultadoOperacao);
					break;
				case(4): // beq
					if(registradores[instrucaoPronta[1]] == registradores[instrucaoPronta[2]]) {
						for (int i = 1; i < 4; i++) {
							instrucoesexecutando[i] = null;
						}

						pc = registradores[instrucaoPronta[3]] + 4;
						System.out.println("BEQ: Branch taken, PC set to: " + pc);
					} else {
						System.out.println("BEQ: Branch not taken");
					}
					break;
				case(5)://noop
					break;
				case(6)://halt
					break;
			}
		instrucoesexecutando[3] = instrucoesexecutando[2];
		instrucoesDecodificadas[3] = instrucoesDecodificadas[2];
	}

	public void memoria() {

			switch(instrucaoPronta[0]) {
				case 0: // lw
					if (instrucaoPronta[2] == 0) {

					} else {
						registradores[instrucaoPronta[2]] = memoria[enderecoMemoria];
						System.out.println("LW: Registrador[" + instrucaoPronta[2] + "] = " + memoria[enderecoMemoria]);
					}
					break;
				case 1: // sw
					memoria[enderecoMemoria] = registradores[instrucaoPronta[2]];
					System.out.println("SW: Memória[" + enderecoMemoria + "] = " + registradores[instrucaoPronta[2]]);
					break;
				default:
					break;
		}
		instrucoesexecutando[4] = instrucoesexecutando[3];
	}

	public void wrightback() {
		if (instrucoesexecutando[1] == null) {

		} else {

			switch(instrucaoPronta[0]) {
				case 2: // add
					registradores[instrucaoPronta[3]] = resultadoOperacao;
					System.out.println("Write Back: Registrador[" + instrucaoPronta[3] + "] = " + resultadoOperacao);
					break;
				case 3: // sub
					registradores[instrucaoPronta[3]] = resultadoOperacao;
					break;
				case 4: // beq
					// Não há necessidade de write back para beq
					break;
				case 5: // noop
					// Não há necessidade de write back para noop
					break;
				case 6: // halt
					System.out.println("Halt: Programa finalizado.");
					acabou = 1; // Finaliza o programa
					break;
				default:
					break;
			}
		}
	}

	public void imprimirEstado() {
		System.out.println("Estado dos Registradores: " + Arrays.toString(registradores));
		System.out.println("Estado da Memória: " + Arrays.toString(memoria));
	}

}