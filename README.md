# VideosDB - DESCRIERE

Platformă simplificată ce oferă informații despre filme și despre seriale.
Utilizatorii pot să primească recomandări personalizate pe baza preferințelor.

Platforma pe care o veți implementa simulează acțiuni pe care le pot face utilizatorii pe o platforma de vizualizare de filme: ratings, vizualizare film, căutări, recomandări etc. Entitățile modelate sunt:

**Video
De două tipuri: filme și seriale (shows). Diferența dintre ele este că serialele au sezoane.
In cazul serialelor, episoadele sezoanelor sunt nesemnificative in cerintele temei.
Toate videourile au în comun titlu, an lansare, unul sau mai multe genuri (e.g. comedie, thriller)
Sezoanele unui serial au asociat un număr, durata întregului sezon și un rating.
Filmele au durată și rating
User (utilizator)
Are două categorii: standard și premium
Are videouri favorite și videouri vizualizate
Datele pentru aceste entități sunt încărcate din fișierele JSON oferite ca intrare în teste. Ele sunt ținute într-un Repository.

**Comenzile
Acestea reprezintă abilitatea unui utilizator de a realiza acțiuni directe, fiind de 3 tipuri diferite.
Favorite - adaugă un video în lista de favorite videos ale acelui user, dacă a fost deja vizionat de user-ul respectiv.
View - vizualizează un video prin marcarea lui ca văzut. Dacă l-a mai văzut anterior, se incrementează numărul de vizualizări ale acelui video de către user. Atunci cand se vizualizeaza un serial, se vizualizeaza toate sezoanele.
Rating - oferă rating unui video care este deja văzut (la seriale se aplică la pentru fiecare sezon in parte(spre deosebire de vizionare, unde se face pe tot serialul)).
Ratingul diferă în funcție de tip - pentru seriale este pentru fiecare sezon in parte.
Ratingul poate fi dat o singură dată de către un utilizator. Pentru seriale poate fi o singură dată pe sezon.
Un serial are mai multe sezoane, fiecare putand primi cate o nota. Rating-ul se calculeaza ca o medie aritmetica a tuturor sezoanelor.

**Query-urile
Acestea reprezintă căutări globale efectuate de utilizatori după actori, video-uri și utilizator. Rezultatele acestor query-uri sunt afișate ca output al testului.
Un query contine tipul de informație căutată: actor/video/user, criteriul de sortare și diverși parametri. În secțiunea Implementare aveți exemple pentru toate felurile de queries. Query-urile conțin ca parametru și ordinea sortării, dacă sortarea să se facă crescător sau descrescător. Criteriul de sortare secundar este ordinea alfabetică descrescătoare/crescătoare în funcție de ordinea de la primul criteriu.
Pentru actori:
  Average - primii N actori (N dat în query) sortați după media ratingurilor filmelor și a serialelor în care au jucat. Media este aritmetica si se calculeaza pentru toate videoclipurile(cu ratingul total diferit de 0) in care a jucat.
  Awards - toți actorii cu premiile menționate în query. Atenție, trebuie ca acei actori să fi primit toate premiile cerute, nu doar pe unele din ele. Sortarea se va face după numărul total de premii al fiecărui actor, după ordinea precizată în input. De exemplu, daca un actor deține doua tipuri de premii: BEST_PERFORMANCE câștigat de 3 ori și BEST_SUPPORTING_ACTOR câștigat de 4 ori, numărul total de premii este 7. Acesta este numărul față de care vor fi făcute sortările.
  Filter Description - toți actorii în descrierea cărora apar toate keywords-urile (case insensitive) menționate în query. Sortarea se va face după ordinea alfabetică a numelor actorilor, după ordinea precizată în input.
Pentru video-uri:
  Rating - primele N video-uri sortate după rating. Pentru seriale rating-ul se calculează ca media tuturor sezoanelor, cu conditia ca cel putin un sezon sa aiba rating, cele fara rating avand 0. Nu se vor lua în considerare video-urile care nu au primit rating.
  Favorite - primele N video-uri sortate după numărul de apariții în listele de video-uri favorite ale utilizatorilor
  Longest - primele N video-uri sortate după durata lor. Pentru seriale se consideră suma duratelor sezoanelor.
  Most Viewed - primele N video-uri sortate după numărul de vizualizări. Fiecare utilizator are și o structură de date în care ține cont de câte ori a văzut un video. În cazul sezoanelor se ia întregul serial ca și număr de vizualizări, nu independent pe sezoane.
Pentru utilizatori:
  Number of ratings - primii N utilizatori sortați după numărul de ratings pe care le-au dat (practic cei mai activi utilizatori)
  Precizare: Daca numarul de raspunsuri este mai mic decat N, atunci se vor returna toate. Rezultatul este de forma: “Query result: [X]”, unde X este o lista de elemente, care poate sa contina 0 elemente!
  
**Recomandările
  Acestea reprezintă căutări după video-uri ale utilizatorilor. Ele sunt particularizate pe baza profilului acestora și au la bază 5 strategii.
Strategiile de recomandare:
  Pentru toți utilizatorii:
    Standard - întoarce primul video nevăzut de utilizator din baza de date, neexistand al doilea criteriu.
    Best unseen - întoarce cel mai bun video nevizualizat de acel utilizator. Toate video-urile sunt ordonate descrescător după rating și se alege primul din ele, al doilea criteriu fiind ordinea de aparitie din baza de date.
  Doar pentru utilizatorii premium:
    Popular - întoarce primul video nevizualizat din cel mai popular gen (video-urile se parcurg conform oridinii din baza de date). Popularitatea genului se calculează din numărul de vizualizări totale ale videoclipurile de acel gen. In cazul in care toate videoclipurile din cel mai popular gen sunt vizionate de utilizator, atunci se trece la urmatorul gen cel mai popular. Procesul se reia pana se gaseste primul videoclip care nu a fost vizionat din baza de date.
    Favorite - întoarce videoclipul care e cel mai des intalnit in lista de favorite (care să nu fie văzut de către utilizatorul pentru care se aplică recomandarea) a tuturor utilizatorilor, al doilea criteriu fiind ordinea de aparitie din baza de date. Atentie! Videoclipul trebuie sa existe in cel putin o lista de videoclipuri favorite ale userilor.
    Search - toate videoclipurile nevăzute de user dintr-un anumit gen, dat ca filtru în input. Sortate este in ordine crescatoare după rating, al doilea criteriu fiind numele.
În cadrul recomandărilor (fără Search Recommendation), al doilea criteriu de sortare este ordinea din baza de date (adică ordinea de apariţie în câmpul “movies”/“shows” din database).
Doar în cadrul Search Recommendation al doilea criteriu de sortare este numele.
Atunci când nu se poate întoarce niciun video se afiseaza “XRecommendation cannot be applied!”, altfel “XRecommendation result: ”, unde X este numele strategiei.

