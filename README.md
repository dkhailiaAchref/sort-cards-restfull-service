<table>
<thead>
<tr>
<th align="center">Description</th>
</tr>
<br></br>
<tr><td align="left">This project usually is a simple combination of existing technologies. The following sample applications expose Api RestFull by (Spring-boot (data-jpa/web/test)
,junit,mockito, swagger2) to test (sort cartes functionality) </td>
</tr>
</thead>
<tbody>
<tr>
<td colspan="2"><strong>Java</strong></td>
</tr>
<br></br>
<tr>
<td><b>REST Api with , spring-boot(web/test/data-jpa) , junit,mockito, swagger2, to test (sort cartes functionality)  </b>
<br></br>
 <br>* config programmatically ( endpoint REST)
 <br>* with swagger2 feature , for documentation api
 <br></br>
 * (aprés avoir  demarré le serveur embarqué de spring-web , par spring-boot:run )
 , vous pourrez tester les apis suivants :
  <br>* Api GET, getAllCards:</br>
   <a href="https://localhost:8443/getAllCards">https://localhost:8443/getAllCards </a>
 <br>* Api GET, (sortCardsGame):</br>
  <a href="https://localhost:8443/sortCardsGame"> https://localhost:8443/sortCardsGame</a>
  <br>* Api POST, (testCardsGame):<br>
 <br>* tester avec requette 'POST' sur (Postman ou swagger) , avec le body json, ecrit dans le fichier (sortedCardsGame.json)
  ,retrouver cette liste dans   "data" : { "cards" : [ { .......}]}
  <br>*
   <a href="https://localhost:8443/testGame/exerciceId"> https://localhost:8443/testGame/exerciceId</a>
 <br>* Swagger :</br>
  <a href="https://localhost:8443/swagger-ui.html#">  https://localhost:8443/swagger-ui.html#</a>
  <br>
  <br> * description d"taillée du besoin :
 <br> On souhaite développer un jeu de carte.

 <br> Dans ce jeu, chaque joueur recevra une main de 10 cartes tirées de manière aléatoire.

 <br> On récupère ces cartes via un service qui se trouve à cette url :

 <br>  --> https://recrutement.local-trust.com/test/cards/57187b7c975adeb8520a283c(GET)

  <br> Chaque carte possède une couleur ("Carreaux", par exemple) et une valeur ("10", par exemple).

  <br> On vous demande de présenter une main "triée" à l'utilisateur. C'est-à-dire que vous devez classer les cartes par couleur et valeur.

  <br> L'ordre des couleurs est, par exemple, le suivant :

  <br>  --> Carreaux, Coeur, Pique, Trèfle

  <br> L'ordre des valeurs est, par exemple, le suivant :

  <br> --> AS, 2, 3, 4, 5, 6, 7, 8, 9, 10, Valet, Dame, Roi

 <br> Le service qui renvoie une main de 10 cartes précise cet ordre (qui peut changer).

 <br> Vous présenterez une solution qui tourne sur Java 1.7 ou 1.8.

  <br> Vous pouvez utiliser un serveur d'application pour présenter la main de l'utilisateur, ou simplement la sortie console.

 <br> L'objectif de cet exercice est de discuter autour de la solution technique que vous aurez élaboré pour traiter ce problème.

  <br> Chaque appel au service vous donnera un id "exerciceId". Vous pouvez tester votre réponse en appelant le service suivant :

  <br> Méthode : --> POST URL : --> https://recrutement.local-trust.com/test/{exerciceId}

  <br> En-tête : --> Content-Type application/json

  <br> Exemple de corps de la requête :

  <br>  --> {"cards":[{"category":"DIAMOND","value":"TEN"},{"category":"DIAMOND","value":"QUEEN"},{"category":"DIAMOND","value":"KING"},{"category":"HEART","value":"EIGHT"},{"category":"HEART","value":"NINE"},{"category":"SPADE","value":"FIVE"},{"category":"SPADE","value":"SEVEN"},{"category":"SPADE","value":"KING"},{"category":"CLUB","value":"ACE"},{"category":"CLUB","value":"TWO"}],"categoryOrder":["DIAMOND","HEART","SPADE","CLUB"],"valueOrder":["ACE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE","TEN","JACK","QUEEN","KING"]}

 <br>  Ce deuxième service renverra le code 200 si votre réponse est bonne. Sinon il renverra le code 406 et la solution de l'exercice.
</td>
</tr>
</tbody>
</table>