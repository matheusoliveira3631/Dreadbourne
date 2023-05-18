import java.util.HashMap;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe é parte da aplicação "World of Zuul".
 * "World of Zuul" é um jogo de aventura muito simples, baseado em texto.  
 *
 * Um "Ambiente" representa uma localização no cenário do jogo. Ele é conectado aos 
 * outros ambientes através de saídas. As saídas são nomeadas como norte, sul, leste 
 * e oeste. Para cada direção, o ambiente guarda uma referência para o ambiente vizinho, 
 * ou null se não há saída naquela direção.
 * 
 * @author  Michael Kölling and David J. Barnes (traduzido e adaptado por Julio César Alves)
 */
public class Ambiente  {
    // descrição do ambiente
    private String descricao;
    private String descricaoCompleta;
    // ambientes vizinhos de acordo com a direção
    private HashMap<String, Ambiente> saidas;

    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele não tem saidas. 
     * "descricao" eh algo como "uma cozinha" ou "um jardim aberto".
     * @param descricao A descrição do ambiente.
     */
    public Ambiente(String descricao)  {
        this.descricao = descricao;
        this.descricaoCompleta="";
        this.saidas= new HashMap<String, Ambiente>();
    }

    /**
     * Define as saídas do ambiente. Cada direção ou leva a um outro ambiente ou é null 
     * (indicando que não tem nenhuma saída para lá).
     * @param norte A saída norte.
     * @param leste A saída leste.
     * @param sul A saída sul.
     * @param oeste A saída oeste.
     */
    public void ajustarSaida(String dir, Ambiente ambiente)  {
        this.saidas.put(dir, ambiente);
    }
    
    public Ambiente getSaida(String dir) {
        return saidas.get(dir);
    }

    public String direcoesDeSaida() {    	
    	String textoSaidas = "";
    	for (String direcao : saidas.keySet()) {
    		if(saidas.get(direcao)!=null)
                textoSaidas = textoSaidas + "\n"+direcao;  
    	}        
	return textoSaidas;
}


    /**
     * @return A descrição do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }


    public String getDescricaoCompleta()
    {
        return getDescricao() + descricaoCompleta;
    }

    public void setDescricaoCompleta(String descricaoCompleta) {
        this.descricaoCompleta = descricaoCompleta;
    }

}
