/**
 * Essa é a classe principal da aplicacao "World of Zull".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.
 * 
 * Usuários podem caminhar em um cenário. E é tudo! Ele realmente precisa ser 
 * estendido para fazer algo interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o método "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os ambientes, 
 * cria o analisador e começa o jogo. Ela também avalia e  executa os comandos que 
 * o analisador retorna.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */

public class Jogo {
    // analisador de comandos do jogo
    private Analisador analisador;
    // ambiente onde se encontra o jogador
    private Ambiente ambienteAtual;
        
    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo()  {
        criarAmbientes();
        analisador = new Analisador();
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {
        Ambiente Salao, biblioteca, torre, jardins, masmorras;
      
        // cria os ambientes
        Salao = new Ambiente("em um grande salão abandonado, resquicio do que um dia foi a gloriosa tavola redonda");
        biblioteca = new Ambiente("em uma biblioteca escura, o odor envelhecido dos livros e traças impregna suas narinas");
        torre = new Ambiente("na antiga torre do relogio, as engrenagens enferrujadas ecoam o tempo perdido. Sombras dançantes testemunham seu inexorável tic-tac.");
        jardins = new Ambiente("nos antigos jardins do castelo, flores murchas sussurram segredos mortais entre vegetação retorcida. A aura de decadência domina a paisagem.");
        masmorras = new Ambiente("nas masmorras, os corredores sombrios ressoam gemidos antigos. Correntes enferrujadas seguram memórias de tormento.");
        
        // inicializa as saidas dos ambientes
        Salao.ajustarSaidas(null, biblioteca, jardins, torre);
        biblioteca.ajustarSaidas(null, null, null, Salao);
        torre.ajustarSaidas(null, Salao, null, null);
        jardins.ajustarSaidas(Salao, masmorras, null, null);
        masmorras.ajustarSaidas(null, null, null, jardins);

        ambienteAtual = Salao;  // o jogo comeca em frente ao Salao
    }

    /**
     *  Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar()  {
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nós repetidamente lemos comandos e 
        // os executamos até o jogo terminar.
                
        boolean terminado = false;
        while (! terminado) {   
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        System.out.println("Obrigado por jogar. Até mais!");
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        System.out.println();
        System.out.println("Bem-vindo ao Legends of Camelot!");
        System.out.println("Legends of Camelot eh um novo jogo de aventura.");
        System.out.println("Seu objetivo eh recuperar a espada perdida do rei Arthur");
        System.out.println("Digite 'ajuda' se voce precisar de ajuda.");
        System.out.println();
        
        getAmbienteAtual();
    
        System.out.print("Saidas: ");
        if(ambienteAtual.saidaNorte != null) {
            System.out.print("norte ");
        }
        if(ambienteAtual.saidaLeste != null) {
            System.out.print("leste ");
        }
        if(ambienteAtual.saidaSul != null) {
            System.out.print("sul ");
        }
        if(ambienteAtual.saidaOeste != null) {
            System.out.print("oeste ");
        }
        System.out.println();
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando)  {
        boolean querSair = false;

        if(comando.ehDesconhecido()) {
            System.out.println("Eu nao entendi o que voce disse...");
            return false;
        }

        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        }
        else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        }
        else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        }

        return querSair;
    }

    private void getAmbienteAtual()
    {
        System.out.println("Voce esta " + ambienteAtual.getDescricao());
    }

    /**
     * Exibe informações de ajuda.
     * Aqui nós imprimimos algo bobo e enigmático e a lista de  palavras de comando
     */
    private void imprimirAjuda()  {
        System.out.println("Você caminha pelo castelo abandonado, um sentimento de opressão se intensifica com cada passo que dá.");
        System.out.println("O ar úmido e impregnado de mofo parece sufocante, e o silêncio pesado é interrompido apenas pelo eco de seus próprios passos.");
        System.out.println("As sombras dançam nas paredes desgastadas e rachadas, dando vida a ilusões de movimento.");
        System.out.println("\n\n");
        System.out.println("Suas palavras de comando sao:");
        System.out.println("\nir\n sair\n ajuda\n");
    }

    /** 
     * Tenta ir em uma direcao. Se existe uma saída para lá entra no novo ambiente, 
     * caso contrário imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando)  {
        // se não há segunda palavra, não sabemos pra onde ir...
        if(!comando.temSegundaPalavra()) {            
            System.out.println("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = null;
        if(direcao.equals("norte")) {
            proximoAmbiente = ambienteAtual.saidaNorte;
        }
        if(direcao.equals("leste")) {
            proximoAmbiente = ambienteAtual.saidaLeste;
        }
        if(direcao.equals("sul")) {
            proximoAmbiente = ambienteAtual.saidaSul;
        }
        if(direcao.equals("oeste")) {
            proximoAmbiente = ambienteAtual.saidaOeste;
        }

        if (proximoAmbiente == null) {
            System.out.println("Nao ha passagem!");
        }
        else {
            ambienteAtual = proximoAmbiente;
            
            getAmbienteAtual();
            
            System.out.print("Saidas: ");
            if(ambienteAtual.saidaNorte != null) {
                System.out.print("norte ");
            }
            if(ambienteAtual.saidaLeste != null) {
                System.out.print("leste ");
            }
            if(ambienteAtual.saidaSul != null) {
                System.out.print("sul ");
            }
            if(ambienteAtual.saidaOeste != null) {
                System.out.print("oeste ");
            }
            System.out.println();
        }
    }

    /** 
     * "Sair" foi digitado. Verifica o resto do comando pra ver se nós queremos 
     * realmente sair do jogo.
     * @return true, se este comando sai do jogo, false, caso contrário.
     */
    private boolean sair(Comando comando)  {
        if(comando.temSegundaPalavra()) {
            System.out.println("Sair o que?");
            return false;
        }
        else {
            return true;  // sinaliza que nós realmente queremos sair
        }
    }
}
