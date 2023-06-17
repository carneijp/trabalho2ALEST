
public class DoubleLinkedListOfRuas {
    private class Node {
        public Rua element;
        public Node next;
        public Node prev;
        public Node(Rua element) {
            this.element = element;
            next = null;
        }
    }

    private Node header;
    private Node trailer;
    private Node current;      
    private int count;

    public DoubleLinkedListOfRuas() {
        header = new Node(null);
        trailer = new Node(null);
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }
    
    public boolean isEmpty() {
        return (count == 0);
    }

    public int size() {
        return count;
    }

    public void clear() {
        header.next = trailer;
        trailer.prev = header;
        count = 0;
    }    

    public void orderedAdd (Rua element)  { 
        // boolean inseriu = false;
        Node aux = containsElement(element); // verifica se ja contem element para não inserir duplicado
        if (aux == null) {  // se nao contem element, insere
            Node n = new Node(element);

            if (header.next == trailer) { 
                // se a lista está vazia
                n.prev = header;
                n.next = trailer;
                trailer.prev = n;
                header.next = n;

            } 
            else if (element.getNome().compareTo(header.next.element.getNome())<0) { 
                // se for menor que o primeiro, insere no inicio
                n.next = header.next;
                n.prev = header;
                header.next = n;
                n.next.prev = n;
            }
            else if (element.getNome().compareTo(trailer.prev.element.getNome())>0) {
                // se for maior que o ultimo, insere no final
                n.next = trailer;
                n.prev = trailer.prev;
                trailer.prev.next = n;
                trailer.prev = n;
            }
            else {
                // senao procura a posicao correta para insercao
                aux = header.next;
                boolean inseriu=false;
                while (aux!=trailer && !inseriu) {
                    if (element.getNome().compareTo(aux.element.getNome())<0) {
                        inseriu = true;
                        n.next = aux;
                        n.prev=aux.prev;
                        aux.prev.next = n;
                        aux.prev = n;
                    }
                    aux = aux.next;
                }
            }
            count++;
        }
        // return inseriu;
    }
    
    private Node containsElement(Rua element) {
        Node aux = header.next;
        
        while (aux != trailer) {
            if (aux.element.getNome().equals(element.getNome())) {
                return aux;
            }
            aux = aux.next;
        }
        
        return null;
    }   
    public Rua containsRua(Rua element) {
        Node aux = header.next;
        
        while (aux != trailer) {
            if (aux.element.getNome().equals(element.getNome())) {
                return aux.element;
            }
            aux = aux.next;
        }
        
        return null;
    }     
    public Rua getCurrentRua(){
        return current.element;
    }
    public Rua get(int index) { 
        if ((index < 0) || (index >= count)) {
            throw new IndexOutOfBoundsException();
        }
        Node aux = getNodeIndex(index);
        return aux.element;
    }
    
    private Node getNodeIndex(int index) {
        Node aux = null;
        if (index < count/2) { // caminha do inicio para o meio
            aux = header.next;
            for(int i=0; i<index; i++)
                aux = aux.next;
        }
        else { // caminha do fim para o meio
            aux = trailer.prev;
            for(int i=count-1; i>index; i--)
                aux = aux.prev;
        }
        return aux;
    }
    
    public void reset() {
        current = header.next;
    }

    public Rua next() {
        if (current != trailer) {
            Rua rua = current.element;
            current = current.next;
            return rua;
        }
        return null;
    }
    
    public Rua preview(){
        if (current != header) {
            Rua rua = current.element;
            current = current.prev;
            return rua;
        }
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node aux = header.next;
        for (int i = 0; i < count; i++) {
            s.append(aux.element);
            s.append("\n");
            aux = aux.next;
        }
        return s.toString();
    }    
    
    public Rua maisPlacas(){
        Node aux = header.next;
        Rua maisPlacas = null;
        int referencia = 0;
        while (aux != trailer) {
            int auxInt = aux.element.placas.count;
            Rua auxRua = aux.element;
            if(auxInt > referencia){
                referencia = auxInt;
                maisPlacas = auxRua;
            }
            aux = aux.next;
        }
        return maisPlacas;
    }

    public int numeroPorMes(int mes){
        int resultado = 0;
        Node aux = header.next;
        while(aux != trailer){
            resultado += aux.element.placas.numeroPorMes(mes);
            aux = aux.next;
        }
        return resultado;
    }
}
