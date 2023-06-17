import java.time.LocalDate;

public class Placas {
    private String nomeSinalizacao;
    private LocalDate date;
    private int mes;

    public Placas(String nome, LocalDate date, int mes){
        this.nomeSinalizacao = nome;
        this.date = date;
        this.mes = mes;
    }

    public String getNomeSinalizacao(){
        return nomeSinalizacao;
    }

    public LocalDate getDate(){
        return date;
    }

    public int getMes(){
        return mes;
    }
}
