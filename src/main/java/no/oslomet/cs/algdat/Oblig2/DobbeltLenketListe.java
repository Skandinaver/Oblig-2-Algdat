package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = null;
        hale = null;

        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {

        hode = null;                                                            //Setter hode og hale
        hale = null;

        antall = 0;                                                             //Setter tellere for fremtidig bruk
        endringer = 0;

        Node<T> prev = null;                                                    //Setter en støtte node for å holde styr på forrige lagde node
        boolean first = true;                                                   //Setter en boolean for å holde styr på første i listen

        if(a == null){
            throw new NullPointerException("a er null!");                       //Kaster null-pointer exception om listen a er null
        }
        for(int i = 0;i < a.length;i++){                                        //For-løkke itererer over a
            if(a[i]!=null){                                                     //om verdien i a er null legges den ikke til some node
                Node<T> node = new Node<T>(a[i],prev, null);                    //Lager ny node
                if(first){                                                      //Sjekker om dette er første node som blir lagt til
                    hode = node;                                                //Hode peker nå til første node
                    first = false;                                              //setter first til false så hode ikke endres igjen
                }
                if(prev != null){                                               //Om forrige node laget ikke er null så settes dens
                    prev.neste = node;                                          //peker til neste node
                }                                                               
                prev = node;                                                    //setter prev til den nye noden
                antall++;                                                       //teller en ekstra verdi i listen
            }
        }
        hale = prev;                                                            //Setter Hale til siste node lagt til
        
    }
    
    public static void fratilKontroll(int antall, int fra, int til){            //implementerer fratilKontrol slik oppgaven ba om.

        if (fra < 0){                                  // fra er negativ
            throw new IndexOutOfBoundsException
          ("fra(" + fra + ") er negativ!");
        }
      if (til > antall){                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
          ("til(" + til + ") > antall(" + antall + ")");
        }
  
      if (fra > til){                                // fra er større enn til
            throw new IllegalArgumentException
          ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }
    
    public Node<T> nodeTil(T verdi) {                                           //støttemetode for oppg6, lik som indeksTil() men returnerer hele noden.

        Objects.requireNonNull(verdi);
        Node<T> pointer = hode;                                                

        for(int i = 0; i < antall; i++){                                        
            if(pointer.verdi.equals(verdi)){                                    
                return pointer;
            }
            pointer = pointer.neste;                                            
        }
        return null;                                                              
    }
    

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);                                       //kjører fratilKontrol for input validering
        DobbeltLenketListe<T> a = new DobbeltLenketListe<T>();                  //lager en ny,tom, instans av dobbellenket liste
        for(int i= fra; i < til; i++){                                          //Kjører gjennom de relevante indeksene i "a" og legger dem inn i den nye listen
            a.leggInn(hent(i));
        }
        return a;                                                               //Returnerer den nye listen.
    }

    @Override
    public int antall() {
        antall = 0;
        Node<T> peker = hode;                                                    //Lager en peker som starter ved hode
        while(peker != null){                                                    //Så lenge det er en peker til en ny node
            antall++;                                                            //øker vi antallet verdier i listen
            peker = peker.neste;                                                 //Peker forflytter seg så en node.
        }
        return antall;                                                           //Returnerer antall.
    }

    @Override
    public boolean tom() {
        return hode == null;                                                     //Returnerer om hode peker til en node eller ikke.
    }

    @Override
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi);                                           //Sjekker at verdi ikker er en null-verdi.
        if(hode == null && hale == null){                                        //Sjekker om listen er tom
            Node<T> node = new Node<T>(verdi,null,null);                         //Lager ny node
            hode = node;                                                         //Setter hode og hale
            hale = node;
            antall++;
            endringer++;
        }
        else{
            Node<T> node = new Node<T>(verdi,hale,null);                        //Lager ny node og setter forrige-peker til forrige hale
            hale.neste = node;                                                  //Sørger for at forrige node peker til ny node
            hale = node;                                                        //Setter ny hale.
            antall++;
            endringer++;
        }
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        
        Objects.requireNonNull(verdi);       //Kaster exception dersom verdi er NULL
        indeksKontroll(indeks, true);        //Kaller på indeksKontroll for å validere indeks.
        Node<T> node = finnNode(indeks);     
        Node<T> newNode = new Node<T>(verdi, null, null);

        if(node.forrige == null){
            node.forrige = newNode;
            hode = newNode;
            newNode.neste = node;
            antall++;
            endringer++;
        }
        else if(node.neste == null) {
            node.neste = newNode;
            hale = newNode;
            newNode.forrige = node;
            antall++;
            endringer++;
        }
        else if(hode == null && hale == null){
            hode = newNode;
            hale = newNode;
            antall++;
            endringer++;
        }
        else {
            Node<T> forrigeNode = node.forrige;

            forrigeNode.neste = newNode;
            newNode.forrige = forrigeNode;
            node.forrige = newNode;
            newNode.neste = node;
            antall++;
            endringer++;

        }


        
    }
    
    @Override
    public boolean inneholder(T verdi) {

        return indeksTil(verdi) != -1;                                          //Retrunerer sann om vi får en retur annen enn -1
    }

    private Node<T> finnNode(int indeks){                                       //Metoden finn node
        indeksKontroll(indeks, false); 
        Node<T> pointer;                                                        //Erklærer støtte noder   
        if(indeks < (antall/2)){                                                //Sjekker om vi skal begyne fra hode eller hale
            pointer = hode;                                                     //Om indeks er nærmest start setter vi pointer til hode
            for(int i=0; i < indeks; i++){                                      //For-løkken kjører gjennom nodene til vi når indeksen vår
                pointer = pointer.neste;                                        
            }
        }
        else{                                                                   //Det samme skjer om vi er nærmest hale, bare fra motsatt ende
            pointer = hale;                                                         
            for(int i=antall -1; i > indeks; i--){ 
                pointer = pointer.forrige;                    
            }
        }
        return pointer;                                      
    }

    @Override
    public T hent(int indeks) {
        
        return finnNode(indeks).verdi;                                          //Returnerer verdien til ønsket indeks
    }

    @Override
    public int indeksTil(T verdi) {
        Node<T> pointer = hode;                                                 //erklœrer støtte-node

        if(Objects.equals(verdi, null)){                                        //Om det bes om en null verdi returnerer vi -1                                
            return -1;
        }
        for(int i = 0; i < antall; i++){                                        //Kjører gjennom listen fra hode med en for-løkke
            if(pointer.verdi.equals(verdi)){                                    //Om vi finner ønsket verdi returnerer vi nåvœrende ineks.
                return i;
            }
            pointer = pointer.neste;                                            //flytter pointer til neste node om vi ikke finner verdi.
        }
        return -1;                                                              //returnerer -1 om vi ikke finner verdien.
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {

        Objects.requireNonNull(nyverdi);                                        //Sjekker at verdien vi endrer til ikke er null
        T formerValue = hent(indeks);                                           //Holder styr på verdien før oppdatering
        finnNode(indeks).verdi = nyverdi;                                       //Finner Noden man vil oppdatere og endrer dens verdi
        endringer++;
        return formerValue;                                                     //Returnerer verdien før endring.
    }

    @Override
    public boolean fjern(T verdi) {

        if(verdi == null){                                                      //Returnerer false om verdi er null.
            return false;
        }    
        Node<T> rNode = nodeTil(verdi);                                         //Bruker støttmetode for finne noden som skal fjernes
        if(rNode == null){
            return false;                                                       //Om noden vi får i retur er null returner vi false da verdien ikke finnes i listen.
        }
        if(rNode.forrige == null && rNode.neste == null){                       //samme metodikk som i fjern(indeks) metoden
            hode = null;
            hale = null;
            antall--;
            endringer++;
        }
        else if(rNode.forrige == null){
            Node<T> nesteNode = rNode.neste;
            nesteNode.forrige = null;
            hode = nesteNode;
            antall--;
            endringer++;
        } 
        else if(rNode.neste == null){
            Node<T> forrigeNode = rNode.forrige;
            forrigeNode.neste = null;
            hale = forrigeNode;
            antall--;
            endringer++;
        }
        else{
            Node<T> forrigeNode = rNode.forrige;                            
            Node<T> nesteNode = rNode.neste;   
            forrigeNode.neste = nesteNode;                                  
            nesteNode.forrige = forrigeNode;                                
            antall--;
            endringer++;    
        }
        return true;

        // int returIndeks = indeksTil(verdi);                                 //Å gjøre metoden sånn her går raskere 
        // if(returIndeks == -1){                                              //i mine tester på egen maskin, å teste metoden mot tidsbruk 
        //     return false;                                                   //måler kun hvor mye prosessorkraft er tilgjengelig.      
        // }
        // fjern(returIndeks);                                                
        // return true;                                                        
        

    }

    @Override
    public T fjern(int indeks) {
        
       
        Objects.requireNonNull(indeks);                                     //Sjekker at indeks ikke er null
        indeksKontroll(indeks, false);                                      //Sjekker at indeks er en valid input
        Node<T> rNode = finnNode(indeks);                                   //Støttenode for å holde styr på node som ønskes fjernet
        T rVerdi = rNode.verdi;                                             //Støttevariabel for å holde styr på verdien som fjernes

        if(rNode.forrige == null && rNode.neste == null){                   //If-løkke sjekker om rNode er eneste i listen
            hode = null;                                                    //er dette tilfelle fjerner vi alle pekere til rNode
            hale = null;                                                    //da fjernes den av trashcollector
            antall--;
            endringer++;
        }
        else if(rNode.forrige == null){                                     //Sjekker om rNode er første i listen
            Node<T> nesteNode = rNode.neste;                                //lager så støttenode for å holde styr på neste i listen
            nesteNode.forrige = null;                                       //nuller peker mot rNode
            hode = nesteNode;                                               //Setter hode til nye start i listen.
            antall--;
            endringer++;
        }
        else if(rNode.neste == null){                                       //Sjekker om rNode er siste i listen
            Node<T> forrigeNode = rNode.forrige;                            //lager støttenode for å holde styr på forrige node
            forrigeNode.neste = null;                                       //nuller peker mot rNode
            hale = forrigeNode;                                             //setter hale til nye ende på listen
            antall--;
            endringer++;
        }
        else{                                                               //Ellers er Node et sted midt i listen
            Node<T> forrigeNode = rNode.forrige;                            //støttenoder for å holde styr på neste og forrige node
            Node<T> nesteNode = rNode.neste;   
            forrigeNode.neste = nesteNode;                                  //setter pekere til nye indekser slik at rNode er isolert
            nesteNode.forrige = forrigeNode;                                //rNode fjernes da av trashcollector
            antall--;
            endringer++;    
        }
        return rVerdi;                                                      //Returnerer verdien i fjernet node.
    }

    @Override
    public void nullstill() {
        hode = null;                                                        //Har for oppgave 8 valgt en helt annen løsning en gitt i oblig.
        hale = null;                                                        //Java trashcollector fjerner alle selvreferrenede elementer
        antall = 0;                                                         //som ellers er utilgjengelige og man kan derfor bare sette hode og hale 
        endringer++;                                                        //til null. I språk som C#/C++/C etc. ville dette gitt memory leak, men ikke i Java.
    }

    @Override
    public String toString() {
        if (tom() == true){             //Kaller på metode tom() og sjekker om den returnerer "true", altså om listen er tom, isåfall returneres "[]".
            return "[]";
        }
        StringBuilder utString = new StringBuilder();   //Instansierer Stringbuilder.
        utString.append("[");                           //Legger på "[" i starten av strengen.

        Node<T> node = hode;                            //Instansierer node og setter verdi til "hode".
        
    
        utString.append(node.verdi);                    //Legger inn verdi av "node".
        node = node.neste;                              //Endrer verdi av "node" til neste node.
            
        while (node != null) {               //Så lenge node ikke er NULL, legger vi til ", " og verdi av node.
            utString.append(", ").append(node.verdi);
            node = node.neste;            //Endrer verdi av "node" til neste node.
        } 
        utString.append("]");             //Avslutter strengen ved å legge inn "]" på slutten.
        return utString.toString();       //Returnerer Stringbuilder som streng.
    }

    public String omvendtString() {
        if (tom() == true) {            //Kaller på metode tom() og sjekker om den returnerer "true", altså om listen er tom, isåfall returneres "[]".
            return "[]";
        }
        StringBuilder revertStreng = new StringBuilder();       //Instansierer Stringbuilder.
        revertStreng.append("[");                               //Legger på "[" i starten av strengen.

        Node<T> node = hale;                                    //Instansierer node og setter verdi til "hale"

                            
        revertStreng.append(node.verdi);                        //Legger inn verdi av "node".
        node = node.forrige;                                    //Endrer verdi av "node" til forrige node.

            while (node != null){                               //Så lenge node ikke er NULL, legger vi til ", " og verdi av node.
                revertStreng.append(", ").append(node.verdi);   
                node = node.forrige;                            //Endrer verdi av "node" til forrige node.
            }
        revertStreng.append("]");                               //Avslutter strengen ved å legge inn "]" på slutten.
        return revertStreng.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();        //Returnerer instans av iteratorklassen
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);                  //Kaller på 'indeksKontroll' for å sjekke om indeks ikke er "OOB".
        return new DobbeltLenketListeIterator(indeks);  //Returnerer instans av iteratorklassen.
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            denne = finnNode(indeks);                   //Setter 'denne' til node i ønsket indeks.
            fjernOK = false;                            //Setter 'fjernOk' til false.
            iteratorendringer = endringer;              //Teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if(endringer != iteratorendringer){         //Sjekker om endringer ikke er like 'iteratorendringer', hvis dette er tilfelle kastes det en 'ConcurrentModificationException'.
                throw new ConcurrentModificationException();
            }

            if(!hasNext()){                             //Sjekker om metoden 'hasNext()' er false, hvis den er det så kastes det en 'NoSuchElementException'.
                throw new NoSuchElementException();
            }
            
            T temp = denne.verdi;                       //Selvforklarende, legger verdi av 'denne' inn i 'T temp'. 'temp' skal brukes for å returnere verdien.
            denne = denne.neste;                        //går til neste node
            fjernOK = true;                             //'fjernOK' settes til true

            return temp;                                //Returnerer 'temp', som inneholder verdi av 'denne'.
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


