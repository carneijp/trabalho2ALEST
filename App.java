import java.io.BufferedReader;
// import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Para a aplicacao funcionar sem error, as placas presentes no csv nao podem possuir
 * datas de implantacao com dado vazio, deve-se corrigir o error no csv, 
 * tambem nao se pode ter dado que nao seja referente a uma data equivalente 
 * e corente.
 */


public class App {
    DoubleLinkedListOfRuas cidade;

    public App(){
        this.cidade = new DoubleLinkedListOfRuas();
    }

    public void executa(){
        leitor();
        imprimeRuaMaisPlacas();
        calculaMesComMaisPlacasInstaladas();
        menu();
    }

    public void leitor(){
        String linhas[] = new String[110000];
        int numLinhas = 0;

        Path filePath = Paths.get("dataEditado.csv");
        
        try ( BufferedReader reader = Files.newBufferedReader(filePath, Charset.defaultCharset())) {
            String line = reader.readLine();
            line = reader.readLine();
            while (line != null) {
                linhas[numLinhas] = line;
                numLinhas++;
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.err.format("Erro na leitura do arquivo: ", e.getMessage());
        }

        for (int i = 0; i < numLinhas; i++) {
            String[] campos = linhas[i].split(";");
// 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            LocalDateTime dateTime = LocalDateTime.parse(campos[0], formatter);
            int anoDataExtracao = dateTime.getYear();
            int mesDataExtracao = dateTime.getMonthValue();
            int diaDataExtracao = dateTime.getDayOfMonth();
            int horaDataExtracao = dateTime.getHour();
            int minDataExtracao = dateTime.getMinute();
// 
            // System.out.println("Data e hora extracao: " + diaDataExtracao + "/" + mesDataExtracao + "/" + anoDataExtracao + ", " + horaDataExtracao + ":" + minDataExtracao);

            String descricao = campos[1];
            String estado = campos[2];
            String complemento = campos[3];

            // System.out.println("Descricao: " + descricao);
            // System.out.println("Estado: " + estado + ", " + complemento);

            int anoImplantacao = 0;
            int mesImplantacao = 1;
            int diaImplantacao = 0;    
            LocalDate date = null;// = LocalDate.parse(campos[4]);        
            if(!campos[4].equals("")) {
                if(campos[4].contains("-"))
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                else
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(campos[4], formatter);
                anoImplantacao = date.getYear();
                mesImplantacao = date.getMonthValue();
                diaImplantacao = date.getDayOfMonth();
            }
            
            // System.out.println("Data implantacao: " + diaImplantacao + "/" + mesImplantacao + "/" + anoImplantacao);

            String logradouro = campos[5].split(" ", 2)[0];
            String nomeLog = campos[5].split(" ", 2)[1];
            // System.out.println("Logradouro: " + logradouro + " " + nomeLog);

            double numInicial;
            if(campos[6].equals(""))
                numInicial = 0;
            else
                numInicial = Double.parseDouble(campos[6]);
            
            double numFinal;
            if(campos[7].equals(""))
                numFinal = 0;
            else
                numFinal = Double.parseDouble(campos[7]);
            
            String defronte = campos[8];
            String cruzamento = campos[9];
            String lado = campos[10];
            String fluxo = "";
            if(campos.length>=12)
                fluxo = campos[11];
            String localInstalacao = "";
            if(campos.length>=13)
                localInstalacao = campos[12];

            // System.out.println("Num inicial e final: " + numInicial + ", " + numFinal + "; "
                    // + defronte + "; " + cruzamento + "; " + lado + "; " + fluxo + "; " + localInstalacao);
            // System.out.println("---------------------------------------> " + i);
            
            salvador(nomeLog, logradouro, descricao, date ,mesImplantacao);
        }

    }


    public void salvador(String nomeLogadouro, String tipoLogadouro, String nomeSinalizacao, LocalDate date, int mes ){
        Rua busca = new Rua(tipoLogadouro, nomeLogadouro);
        Placas novaPlaca = new Placas(nomeSinalizacao, date, mes);
        busca.placas.addOrdenado(novaPlaca);
        Rua aux = cidade.containsRua(busca);

        if(aux == null){//significa que n achou rua com mesmo nome
            cidade.orderedAdd(busca);
        }else{//achou uma rua
            aux.placas.addOrdenado(novaPlaca);
        }
    }

    public void imprimeRuaMaisPlacas(){
        Rua resultado = cidade.maisPlacas();
        System.out.println("Rua com mais placas instaladas");
        System.out.println("Nome da Rua: "+resultado.getTipo()+" "+resultado.getNome()+"\nNumero de Placas: "+resultado.placas.count);
    }

    public void calculaMesComMaisPlacasInstaladas(){
        int[] valorPorMes = new int[12];
        for(int i = 0; i < 12; i++){
            valorPorMes[i] = cidade.numeroPorMes(i+1);
        }
        
        int referencia = 0;
        int mes = 0;
        for(int i = 0; i < 12; i++){
            if(valorPorMes[i]>referencia){
                mes = i;
                referencia = valorPorMes[i];
            }
            System.out.println(valorPorMes[i]);
        }

        switch(mes){
            case 0:
                System.out.println("Maior mes de Janeiro com :"+valorPorMes[0]+" instalacoes de placas.");
                break;
            case 1:
                System.out.println("Maior mes de Fevereiro com :"+valorPorMes[1]+" instalacoes de placas.");
                break;
            case 2:
                System.out.println("Maior mes de Março com:"+valorPorMes[2]+" instalacoes de placas.");
                break;
            case 3:
                System.out.println("Maior mes de Abril com:"+valorPorMes[3]+" instalacoes de placas.");
                break;
            case 4:
                System.out.println("Maior mes de Maio com:"+valorPorMes[4]+" instalacoes de placas.");
                break;
            case 5:
                System.out.println("Maior mes de Junho com:"+valorPorMes[5]+" instalacoes de placas.");
                break;
            case 6:
                System.out.println("Maior mes de Julho com:"+valorPorMes[6]+" instalacoes de placas.");
                break;
            case 7:
                System.out.println("Maior mes de Agosto com:"+valorPorMes[7]+" instalacoes de placas.");
                break;
            case 8:
                System.out.println("Maior mes de Setembro com:"+valorPorMes[8]+" instalacoes de placas.");
                break;
            case 9:
                System.out.println("Maior mes de Outubro com:"+valorPorMes[9]+" instalacoes de placas.");
                break;
            case 10:
                System.out.println("Maior mes de Novembro com:"+valorPorMes[10]+" instalacoes de placas.");
                break;
            case 11:
                System.out.println("Maior mes de Dezembro com:"+valorPorMes[11]+" instalacoes de placas.");
                break;
            default:
                System.out.println("Nao foi possivel determinar um mes com instalacao");
                break;
        }
        
    }

    public void menu(){
        Scanner teclado = new Scanner(System.in);
        String escolha = "";
        do{
            System.out.println("Digite 5 para ver as informacoes das ruas\nDigite 0 para sair do menu");
            escolha = teclado.nextLine();
            if(escolha.equals("5")){
                cidade.reset();
                do{ 
                    Rua visivel = cidade.getCurrentRua();
                    System.out.println("\n\n\nRua: "+visivel.getNome()+"\nPlacas instaladas: "+visivel.placas.count+"\nPrimeiro elemento: "+visivel.placas.getPrimeiraPlaca().getNomeSinalizacao()+" "+visivel.placas.getPrimeiraPlaca().getDate()+"\nUltimo elemento: "+visivel.placas.getUltimaPlaca().getNomeSinalizacao()+" " + visivel.placas.getUltimaPlaca().getDate());
                    System.out.println("Digite 2 para ver as informacoes da proxima rua ou 1 para ver da rua anterior\nDigite 0 para sair do menu");
                    escolha = teclado.nextLine();
                    if(escolha.equals("2")){
                        cidade.next();
                    }else{
                        if(escolha.equals("1")){
                            cidade.preview();
                        }
                    }
                }while(!escolha.equals("0"));
            }
        }while(!escolha.equals("0"));




    }



}
