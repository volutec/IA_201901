/*
• Independência. 
				Um conjunto de objetos de ﬂuxo ativos sem restrições de execução. 

• Dependência.
				Um conjunto de objetos de ﬂuxo ativos com uma dependência de execução temporal entre eles. 
				Pode ser de dois tipos: Dependência estrita e Dependência Circunstancial. 
				Na Dependência estrita, se um objeto de ﬂuxo depender de outro objeto de ﬂuxo, a execução do primeiro ocorre só após a execução do segundo. 
				Na Dependência Circunstancial, se o objeto de ﬂuxo y depende do objeto de ﬂuxo x, 
												então y pode ser executado em um ﬂuxo onde x foi executado antes 
												ou, em um ﬂuxo onde x não é executado.

• Não-coexistência. 
					Onde um conjunto de objetos de ﬂuxo se relacionam de forma a não-coexistir no mesmo ﬂuxo de execução.

• União. 
		Um conjunto de objetos de ﬂuxo onde eles podem coexisitr no mesmo ﬂuxo ou separadamente.

A situação de não-coexistência ou união é denominado Conjunto de escolha ("Choice Set").

Dúvidas:
:: Como identificar sub-fluxos para poder verificar a Não-coexistência no sub-fluxo?

Buffer (memória de informações soltas):
CH: Formato: <Tarefa A> inicia.
CH: Formato: <Tarefa Z> finaliza.

*/


import java.io.*; 
import alice.tuprolog.*;
 
 public class Chatter { 
 
    public static void main (String[] args) { 
	
		//instancia o Prolog
		Prolog engine = new Prolog();
		Theory t;
		try{
			t = new Theory(new java.io.FileInputStream("modelo.pl"));
			engine.setTheory(t);
		} catch(Exception e){
			System.out.println("ERRO A: IO erro tentando ler respostaUsuario."); 
			e.printStackTrace();
			System.exit(1); 
		}

		//  pergunta o nome
		System.out.println("\u001B[33m \nCH: Olá, falaremos sobre a construção de um fluxo de processo."); 
		System.out.println("CH: Um objeto de ﬂuxo ativo é um elemento de processo que representa algo que acontece - evento, ou trabalho a ser formado - atividade."); 
		System.out.println("CH: O conjunto de objetos de ﬂuxo ativo formam um Domínio."); 
		System.out.println("CH: Um tipo de relação sobre objetos de ﬂuxo ativos descrevem uma Situação ('Situation')."); 
		System.out.println("CH: Para maiores detalhes, consulte o artigo 'COSTA, Mateus Barcellos; TAMZALIT, Dalila. Recommendation patterns for business process imperative modeling. In: ACM. Proceedings of the Symposium on Applied Computing. [S.l.], 2017. p. 735–742.' "); 
		System.out.println("CH: Qual seu nome? \u001B[0m"); 
		System.out.print("\u001B[0mUSU: "); 
 
		//  Abre o input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
 
		String respostaUsuario = null; 
 
		try { 
			respostaUsuario = br.readLine(); 
		} catch (Exception e) { 
			System.out.println("ERRO A: IO erro tentando ler respostaUsuario."); 
			e.printStackTrace();
			System.exit(1); 
		} 


		try {
			SolveInfo solution = engine.solve("asserta(nomeUsuario('" + respostaUsuario + "'))." );
		} catch (Exception e) 
		{
			System.out.println("ERRO B: " + e); e.printStackTrace();	
		}

		try{
			System.out.print("\u001B[32mSaida: ");
			SolveInfo info = engine.solve("nomeUsuario(X).");
			System.out.println(info.getSolution());
			System.out.println("\u001B[0m");

		} catch(Exception e){
			System.out.println("ERRO C: " + e);
			e.printStackTrace();
		};

/////////////////////////////////////////
		System.out.println("\u001B[33m \nCH: Qual o nome do processo?\u001B[0m"); 
		System.out.print("USU: "); 
 
		try { 
			respostaUsuario = br.readLine(); 
		} catch (Exception e) { 
			System.out.println("ERRO A: IO erro tentando ler respostaUsuario."); 
			e.printStackTrace();
			System.exit(1); 
		} 

		try {
			SolveInfo solution = engine.solve("asserta(nomeProcesso('" + respostaUsuario + "'))." );
		} catch (Exception e) 
		{
			System.out.println("ERRO B: " + e); e.printStackTrace();	
		}

		try{
			System.out.print("\u001B[32mSaida: ");
			SolveInfo info = engine.solve("nomeProcesso(X).");
			System.out.println(info.getSolution());
			System.out.println("\u001B[0m");

		} catch(Exception e){
			System.out.println("ERRO C: " + e);
			e.printStackTrace();
		};

 /////////////////////////////////////////
		System.out.println("\u001B[33m \nCH: Quais os objetos de fluxo do processo? Separe com virgulas.\u001B[0m"); 
		System.out.print("USU: "); 

		String dominio = "[";		
		try { 
			respostaUsuario = br.readLine(); 
			String[] objetosFluxoArray = respostaUsuario.split("\\s*,\\s*");
			for(String objetoFluxo : objetosFluxoArray){
				System.out.println(objetoFluxo);
			};

			for(String objetoFluxo : objetosFluxoArray){
				dominio += "'" + objetoFluxo + "',";
			};
			dominio += "]";
			dominio = dominio.replace(",]", "]"); //retirando a virgula final
			System.out.println("\u001B[32mDominio informado: " + dominio);
			System.out.print("\u001B[0m");
			
		} catch (Exception e) { 
			System.out.println("ERRO A: IO erro tentando ler respostaUsuario."); 
			e.printStackTrace();
			System.exit(1); 
		} 

		try {
			SolveInfo solution = engine.solve("asserta(dominio(" + dominio + "))." );
		} catch (Exception e) 
		{
			System.out.println("ERRO B: " + e); e.printStackTrace();	
		}

		try{
			System.out.print("\u001B[32mSaida: ");
			SolveInfo info = engine.solve("dominio(X).");
			System.out.println(info.getSolution());
			System.out.print("\u001B[0m");
			
		} catch(Exception e){
			System.out.println("ERRO C: " + e);
			e.printStackTrace();
		};

///////////////////////////////////////// Situations
		// Booleanos para invocar as clausulas para as Situations e montagem do Predicado Modelo.
		boolean regraDepEstrita = false;
		boolean regraDepCircunstancial = false;
		boolean regraNaoCoexiste = false;
		boolean regraIndepende = false;
		
		boolean respostaValida; 
		while (respostaUsuario.toLowerCase().equals("bye") == false) {
			
			respostaValida = false; 
			
			System.out.println("\u001B[33m \nCH: Falemos das relações entre os objetos de fluxo ativos.");
			System.out.println("CH: Como eles se relacionam?");
			System.out.println("CH: Formato: <Tarefa X> nao coexiste com <Tarefa Y>.");
			System.out.println("CH: Formato: <Tarefa X> independe de <Tarefa Y>.");
			System.out.println("CH: Formato: <Tarefa X> depende estritamente de <Tarefa Y>.");
			System.out.println("CH: Formato: <Tarefa X> depende circunstancialmente de <Tarefa Y>.");
			System.out.println("CH: Para ver o(s) modelo(s), digite 'modelo' sem aspas.");
			System.out.println("\nCH: Para terminar, digite 'bye'.\u001B[0m");

			System.out.print("USU: "); 

			try { 
				respostaUsuario = br.readLine(); 
				
				if (respostaUsuario.toLowerCase().equals("modelo")) { // Quero ver o(s) modelo(s)
					
					respostaValida = true;
					String x = "gerarModelo";

					//nome do usuario
					SolveInfo info2 = engine.solve("nomeUsuario(Nome_usuario)");
					System.out.print("\u001B[32m");
					System.out.println(info2.toString());  
					//nome do processo
					info2 = engine.solve("nomeProcesso(Nome_processo)");
					System.out.println(info2.toString());  

					// Chama as cláusulas para o Modelo baseado nas Situations invocadas
					if (regraDepEstrita == true) {
						x += "De";
					};
					if (regraDepCircunstancial == true) {
						x += "Dc";
					};
					if (regraNaoCoexiste == true) {
						x += "Nc";
					};
					if (regraIndepende == true) {
						x += "In";
					};
					
					//chama o predicado criado
					String modelo = x + "(" + dominio +", Modelo).";
					System.out.println("modelo a resolver: " + modelo);
					info2 = engine.solve(modelo);
					System.out.println(info2.getSolution()); // demais solucoes
					info2 = engine.solveNext();
					while (engine.hasOpenAlternatives()) {
						System.out.println(info2);
						info2 = engine.solveNext();
					}
					
					System.out.println("\u001B[0m");
				};

				if (respostaUsuario.toLowerCase().indexOf("coexiste") != -1) { // <Tarefa X> não coexiste com <Tarefa Y>
					try{
						respostaValida = true;
						regraNaoCoexiste = true;
						

						String objFluxo, strResposta;

						String[] objetosFluxoArray = respostaUsuario.split("\\s*nao coexiste com\\s*");
						
						objFluxo = "";
						for(String objetoFluxo : objetosFluxoArray){
							objFluxo += "'" + objetoFluxo + "',";
						};
						objFluxo += "]";
						objFluxo = objFluxo.replace(",]", ""); //retirando a virgula final
						
						// validar se ja existe
						SolveInfo info = engine.solve("naoCoexistir(" + objFluxo + ")." );
						strResposta = info.toString();
						if (strResposta.toLowerCase().equals("yes.")) { // Ja existe
							System.out.println("\u001B[33mCH: Esta regra ja esta cadastrada!\u001B[0m");
						} else { // inserir novo fato restricao
							info = engine.solve("asserta(naoCoexistir(" + objFluxo + ")).");
							System.out.println("\u001B[33mCH: OK, regra cadastrada.\u001B[0m");
						};

					} catch(Exception e){
						System.out.println("ERRO C: " + e);
						e.printStackTrace();
					};
				};

				if (respostaUsuario.toLowerCase().indexOf("independe") != -1) { // <Tarefa X> independe de <Tarefa Y>
					try{
						respostaValida = true;
						regraIndepende = true;

						System.out.println("Saida:");
						SolveInfo info = engine.solve("depende(X, Y).");
						System.out.println(info.getSolution());
					} catch(Exception e){
						System.out.println("ERRO C: " + e);
						e.printStackTrace();
					};
				};

				if (respostaUsuario.toLowerCase().indexOf("estritamente") != -1) { // <Tarefa X> depende estritamente de <Tarefa Y>
					try{
						respostaValida = true;
						regraDepEstrita = true;
						String objFluxo, strResposta;

						String[] objetosFluxoArray = respostaUsuario.split("\\s*depende estritamente de\\s*");
						
						objFluxo = "";
						for(String objetoFluxo : objetosFluxoArray){
							objFluxo += "'" + objetoFluxo + "',";
						};
						objFluxo += "]";
						objFluxo = objFluxo.replace(",]", ""); //retirando a virgula final
						
						// validar se ja existe
						SolveInfo info = engine.solve("depende(" + objFluxo + ")." );
						strResposta = info.toString();
						if (strResposta.toLowerCase().equals("yes.")) { // Ja existe
							System.out.println("\u001B[33mCH: Esta regra ja esta cadastrada!\u001B[0m");
						} else { // inserir novo fato restricao
							info = engine.solve("asserta(depende(" + objFluxo + ")).");
							System.out.println("\u001B[33mCH: OK, regra cadastrada.\u001B[0m");
						};
					} catch(Exception e){
						System.out.println("ERRO C: " + e);
						e.printStackTrace();
					};
				};

				if (respostaUsuario.toLowerCase().indexOf("circunstan") != -1) { // <Tarefa X> depende circunstancialmente de <Tarefa Y>
					try{
						respostaValida = true;
						regraDepCircunstancial = true;

						System.out.println("Saida:");
						SolveInfo info = engine.solve("depende(X, Y).");
						System.out.println(info.getSolution());
					} catch(Exception e){
						System.out.println("ERRO C: " + e);
						e.printStackTrace();
					};
				};

				if (respostaUsuario.toLowerCase().equals("bye")) { // bye
					respostaValida = true;
				};
				
				if (!respostaValida) {
					System.out.println("\u001B[33m \nCH: Nao entendi. Por favor, reformule sua resposta.\u001B[0m");
				};

				
			} catch (Exception e) { 
				System.out.println("ERRO Situations: " + e); 
				e.printStackTrace();
				System.exit(1); 
			}
		}
		
		System.out.println("\u001B[33m \nCH: Bye!\u001B[0m");

	} 
 
 }  
