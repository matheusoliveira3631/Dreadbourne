import java.util.HashMap;

public class Jogador
{
    private String nome;
    private  HashMap<String, Item> mochila;

    public Jogador(String nome)
    {
        this.nome = nome;
        mochila = new HashMap<String, Item>();
    }

    public String getNome()
    {
        return nome;
    }

    public void guardaItem(Item item)
    {
        mochila.put(item.getNome(), item);
    }

    public Item removeItem(String nomeItem)
    {
        Item item = mochila.get(nomeItem);
        mochila.remove(nomeItem);
        return item;
    }

    public String inventario()
    {
        String itens = "";
        for (String nomeItem : mochila.keySet()) {
            itens = itens + "\n"+nomeItem;  
        }
        return itens;
    }
}