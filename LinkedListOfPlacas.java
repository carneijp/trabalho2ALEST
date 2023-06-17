public class LinkedListOfPlacas {
    private class Node{
        Placas element;
        Node next;
        public Node(Placas placas){
            this.element = placas;
            this.next = null;
        }
    }
    Node head;
    Node tail;
    int count;

    public LinkedListOfPlacas(){
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    public void addOrdenado(Placas placa){
        Node aux = new Node(placa);
        if(head == null && tail == null){
            head = aux;
            tail = aux;
        }else{
            if(placa.getDate().compareTo(head.element.getDate())<=0){
                aux.next = head;
                head = aux;
            }else{
                if(placa.getDate().compareTo(tail.element.getDate())>0){
                    tail.next = aux;
                    tail = aux;
                }else{
                    Node proximo = head.next;
                    Node anterior = head;
                    boolean inseriu=false;
                    while (proximo!=tail && !inseriu) {
                        if (placa.getDate().compareTo(head.element.getDate())<0) {
                            inseriu = true;
                            aux.next = proximo;
                            anterior.next = aux;
                        }
                        anterior = anterior.next;
                        proximo = proximo.next;
                    }
                }
            }
        }
        count++;
    }


    public int numeroPorMes(int mes){
        int resultado = 0;
        Node aux = head;
        while(aux != tail.next){
            if(aux.element.getMes() == mes){
                resultado ++;
            } 
            aux = aux.next; 
        }
        return resultado;
    }

    public Placas getPrimeiraPlaca(){
        return head.element;
    }

    public Placas getUltimaPlaca(){
        return tail.element;
    }

}
