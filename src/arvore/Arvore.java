package arvore;

import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.Id3;
import weka.core.Instance;
import weka.core.Instances;

public class Arvore {
	
	
	
	public static void main(String[] args) throws Exception {
		
		//Declaração de variaveis
		String estacao;
		String cor;
		String estado;
		
		
		//Lendo o arquivo arff e embaralhando ele
		FileReader arquivo = new FileReader("guardaroupa.arff");
		Instances roupas = new Instances(arquivo);
		roupas.setClassIndex(3);
		roupas = roupas.resample(new Random());
		
		
		//Declarando o scanner pra leitura do teclado
		Scanner leitor = new Scanner(System.in);
		System.out.println("Bem vindo.");
		
		
		//Guarda a estação e testa por possiveis comandos invaldos
		do{
			System.out.println("Determine a estação.:");
			System.out.println("[1]Verão");
			System.out.println("[2]Outono");
			System.out.println("[3]Inverno");
			System.out.println("[4]Primavera");
			try{
				int temp = leitor.nextInt();
				
				switch(temp){
				case 1:
					estacao = "verao";
					break;
				case 2:
					estacao = "outono";
					break;
				case 3:
					estacao = "inverno";
					break;
				case 4:
					estacao = "primavera";
					break;
				default:
					System.out.println("Valor incorreto");
					continue;
				}
				break;
			}catch(Exception ex){
				System.out.println("Valor digitado incorreto");
			}
		}while(true);
		
		//Guarda a cor e testa por possiveis comandos invaldos
		do{
			System.out.println("---");
			System.out.println("Determine a coloração da roupa");
			System.out.println("[1]Clara");
			System.out.println("[2]Escura");
			try{
				int temp = leitor.nextInt();
				
				switch (temp){
				case 1:
					cor = "clara";
					break;
				case 2:
					cor = "escura";
					break;
				default:
					System.out.println("Valor incorreto");
					continue;
				}
				break;
			}catch(Exception ex){
				System.out.println("Valor digitado incorreto");
			}
		}while(true);
		
		//Guarda o estado e testa por possiveis comandos invaldos
		do{
			System.out.println("---");
			System.out.println("Determine o estado da roupa");
			System.out.println("[1]Nova");
			System.out.println("[2]Usada");
			try{
				int temp = leitor.nextInt();
				
				switch (temp){
				case 1:
					estado = "sim";
					break;
				case 2:
					estado = "nao";
					break;
				default:
					System.out.println("Valor incorreto");
					continue;
				}
				break;
			}catch(Exception ex){
				System.out.println("Valor digitado incorreto");
			}
		}while(true);
		
		
		int PARTICOES = roupas.numInstances();
		int ITERACOES = roupas.numInstances();
		
//		Declara o metodo a ser utilizado
		Id3 classArvore = new Id3();
		
//		Treina o algoritimo
		for (int i = 0; i < ITERACOES; i++){
			Instances roupaTreino = roupas.trainCV(PARTICOES, i);
			Instances roupaTeste = roupas.testCV(PARTICOES, i);
			
			classArvore.buildClassifier(roupaTreino);
			
			for (int j = 0; j < roupaTeste.numInstances(); j++) {

				// Testando
				Instance exemplo = roupaTeste.instance(j);
				exemplo.setClassMissing(); // removendo a classe
				double arvoreRes = classArvore.classifyInstance(exemplo);
			}
		}
		
//		Cria uma instance com os dados coletados
		Instance inst = new Instance(3);
		inst.setDataset(roupas);
		inst.setValue(0, estacao);
		inst.setValue(1, cor);
		inst.setValue(2, estado);
		
//		Classifica a instance
		double resu = classArvore.classifyInstance(inst);
		
//		Mostra na tela o resultado
		if (resu==0)System.out.println("A roupa tem o estilo CASUAL");
		else if (resu==1) System.out.println("A roupa tem o estilo FORMAL");
		else System.out.println("A roupa tem o estilo PUNK");
	}

}
