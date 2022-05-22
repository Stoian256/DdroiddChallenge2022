# DdroiddChallenge2022
Cerinta:https://github.com/Ddroidd-Limited/SummerInternship2022-Backend  
Explicatii:  
repository->pentru oferte si produse lucrez cu fisiere ,iar pentru produsele din cos lucrez in memorie  
          ->fisierul offers.csv contine ofertele sub fomatul(Nume produs necesar in cos pentur a se aplica discount-ul,  
                                                              Cantitatea minima(stric;se face verificarea in serivce cu>) necesara pentru a se aplica discount-ul,  
                                                              Numele produsului pe care se aplica discount-ul,  
                                                              Discountul in sine)  
          ->fisierul products.csv contine produsele din stoc sub formatul(Id produs,Nume produs,Pret produs,Tara de livrare,Shipping rate,Greutate)  
Proiectul este stratificat avand:UI, Business, Repository, Domain, Exceptions, Tests  

Interactiune cu aplicatia:  
Meniul are 6 comenzi posibile  

0-opreste rularea aplicatiei  
1-afiseaza produsele din stoc (ID. Nume -$Pret)  
2-adaugarea unui produs in cos:-necesita:id-ul podusului de adaugat si cantitatea  
                               -dupa adaugare se afiseaza continutul cosului  
                               -daca exista in cos deja acelasi produs creste cantitatea comamdata  
3-eliminarea unui produs din cos:-necesita:id-ul produsului de eliminat  
                                 -se elima toata cantitatea nu cate unul  
4-afisarea continutului cosului  
5-calcularea si afisarea facturii  
6-calcularea si afisarea facturii cu discount-uri si VAT(aici ce nu am mai facut ca sa fie bine este sa se afiseze ce discount-uri se aplica,pur si simplu vezi direct costurile noi)  
