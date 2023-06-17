public class Rua {
    private String tipo;
    private String nome;
    LinkedListOfPlacas placas;

    public Rua(String tipo, String nome){
        this.tipo = tipo;
        this.nome = nome;
        this.placas = new LinkedListOfPlacas();
    }

    public String getTipo(){
        return tipo;
    }

    public String getNome(){
        return nome;
    }
}
