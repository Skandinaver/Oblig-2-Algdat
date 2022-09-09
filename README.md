[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-f059dc9a6f8d3a56e377f745f24479a46679e63a5d9fe6f495e02850cd0d8118.svg)](https://classroom.github.com/online_ide?assignment_repo_id=463192&assignment_repo_type=GroupAssignmentRepo)
# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Petter Kreutz Sundh, s356095, s356095@oslomet.no
* Trygve Reisæter Kristoffersen, s354579, s354579@oslomet.no

# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Petter har hatt hovedansvar for oppgave 1, 3, 4, 6 og 7. 
* Trygve har hatt hovedansvar for oppgave 2, 5, og 8. 


# Oppgavebeskrivelse

I oppgave 1 så begynte vi med å lage metoden "Antall". Her brukte vi en støtte node kalt "peker" og en while-løkke, så lenge peker != null er det en ny node og antall må økes. Deretter opprettet vi metoden "Tom", denne var relativt lett da vi bare sjekker om hode peker til en node eller ikke. Etter dette lagde vi konstruktøren for lister, her kaster vi en nullpointerexception om a = null, vi bruker så en for-løkke for å gå gjennom hver verdi i a og lage en node av dem om de ikke er null. Vi har også en støtte variabel "første" som sjekker om noden som opprettes er den første noden, hode blir linket til denne om det er tilfelle og "første" settes så til false. Støtte noden "prev" brukes for å holde styr på den forrige noden vi opprettet slik at vi kan linke opp den nye noden og den forrige.


I oppgave 2a så begynte vi med å sjekke om listen er tom, ved å kalle på metoden 'tom()' om den er det så returneres to tomme firkant parenteser som streng.
Instansierte så en stringbuilder for å lage en utstreng, denne starter vi med å legge på en firkant parentes, hvor vi så går videre med å instansiere node og sette verdi til 'hode'. Videre legges verdi av 'node' inn i utstrengen vår. Deretter går vi videre til neste node. Vi kjører så en while-løkke som legger inn verdiene til 'node' ved bruk av '.append', for å gå videre sier vi 'node = node.neste'. Løkken kjører så lenge 'node' ikke er null. Til slutt avslutter vi strengen med firkant parentes, og returnerer Stringbuilder som en streng. 
Metoden 'omvendtString' bruker samme fremgangsmåte, men her bruker vi naturligvis 'hale' siden vi skal gå gjennom listen omvendt.

i oppgave 2b starter vi med å bruke en 'requireNonNull' for å sjekke at verdi ikke er en nullverdi. Vi sjekker så om listen er tom ved å bruke en if, og sjekke at 'hode' og 'hale' er null, hvis det er tilfellet, oppretter vi så en ny node og setter 'hode' og 'hale' verdier. Hvis den går inn i else lager vi en ny node og setter 'forrige' peker til forrige 'hale', og sørger for at forrige node peker til den nye noden. og inkrementerer 'antall' og 'endringer'.

I oppgave 3a laget vi metoden finnNode, her implementerte vi først metoden indekskontroll slik obligen ba om. Denne sjekker at indeksen som skal fjernes er valid for listen vi jobber i. Vi lager så en "pointer" som kan brukes for å sjekke nodene mens vi løper gjennom listen. vi lager så en if-setning som sjekker om valgt indeks kommer i første eller andre halvpart av listen og velger utifra dette om vi begyner løpet vårt fra listens hode eller hale. Listen løpes gjennom med en for-løkke som går frem til eller tilbake mot indeks avhengig av hvor indeks befinner seg.

I oppgave 3b implementerte vi subliste metoden, som lager en egen liste ut av en seksjon fra hovedlisten vår. Her bruker vi metoden fratilkontroll som oppgaven ga oss for å sjekke at ønsket intervall er valid. Vi opretter så en ny instans av klassen dobbeltlenket liste og bruker en for løkke som løper gjennom intervallet og gjører "legg inn" funksjonen fra oppgave 2 på hver indeks.

I oppgave 4 skulle vi først implentere metoden indeksTil(T verdi), som returnerer indeksen til en gitt verdi om den finnes i listen ellers returnerer vi -1.
Først lager vi en if-setning som sjekker om gitt verdi er null, om dette er tilfelle returnerer vi -1 da null aldri finnes i listen vår. Vi erklœrer så en støttenode for å fungere som peker og lager en for-løkke som løper gjennom listen til pointer.verdi er det samme som ønsket verdi. Når vi finner den ønsket verdien returnerer vi nåvœrende indeks. Om vi aldri finner en lik verdi returnerer vi -1.

Oppgave 4 ba også om en metode som sjekker om listen inneholder en gitt verdi. Her brukte vi et enkelt return statement som returnerer true om indeksTil(verdi) ikke er det samme som -1.

I oppgave 5 startet vi med å sjekke om verdi er null. Kastes exception dersom den er det. Kaller så på indekskontroll som sjekker indeks. Videre håndterer vi ulike caser ved if-statements. Grunnet dårlig tid har jeg ikke rukket å kommentere denne ferdig. Denne feiler på test 5g.

I oppgave 6 har vi laget to fjern metoder. Den ene fjerner noden på ønsket indeks, den andre fjerner gitt verdi fra listen. Metoden fjern(indeks) bruker metodekallene finnNode for å lete seg frem til noden vi skal fjerne og indekskontroll for å sjekke om indeks er valid. Når vi har funnet noden vi vil fjerne sjekker vi om denne er eneste element i listen, ligger først, ligger sist, eller om den ligger midt i mellom to andre elementer. Avhengig av nodens plassering setter vi pekere slik at noden er isolert og blir plukket opp av trashcollector.
For å implementere fjern(verdi) laget vi en ny støtte metode nodeTil(verdi), denne fungerer som indeksTil(verdi) men returner en node istedenfor indeks. Vi bruker så samme metodikken som i fjern(indeks) for å sette pekere og fjerne noden. Om verdien ikke eksisterer i listen returnerer vi false.


I oppgave 7 ble vi bedt om å lage en metode som nullstiller listen, vi ble gitt to forslag og bedt om å velge den mest effektive. Istedenfor å benytte meg av disse forslagene har jeg valgt å bruke en helt annen metode der vi setter hode og hale til null. Listen blir da selv refererende og utilgjengelig, trashcollecter kommer da og fjerner den. I språk som C/C# etc. ville dette ledet til memory leak men i java fungerer det fint. Dette er desidert raskeste måten å tømme listen på.

I oppgave 8a sjekker vi om 'endringer' ikke er like med 'iteratorendringer', dersom dette er tilfellet kastes en 'concurrentModificationException'. Videre sjekker vi hva metoden 'hasNext()' returnerer. Dersom denne returnerer false kaster vi en 'NoSuchElementException()'. Etter dette legges verdi av 'denne' inn i 'temp', som skal brukes for å returnere verdien. Vi går så til neste node. Og setter til slutt 'fjernOk' til true.

I oppgave 8b returneres en instans av iteratorklassen,ved å kalle på 'DobbeltLenketListeIterator', slik oppgaven ber om.

I oppgave 8c starter vi ved å kalle på metoden 'finnNode()', ved hjelp av metoden settes 'denne' til node i korrekt indeks. Vi setter så 'fjernOk' til false, og til slutt setter vi 'iteratorendringer' lik 'endringer'.

I oppgave 8d bruker vi metoden 'indekskontroll()' for å sjekke at indeks er valid. Returnerer så en instans av iteratorklassen.
