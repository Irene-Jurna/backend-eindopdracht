# Pluk! Recepten API 🌿

Een RESTful API ter ondersteuning van de leden en boeren van de zelfoogsttuin **Pluk! Groenten van West** in Amsterdam Nieuw-West. Deze API helpt oogsters om recepten te vinden op basis van hun seizoensgebonden oogst – waaronder ook vergeten groenten en wilde planten. Voor de boeren kan het een systeem worden om de wekelijkse oogstlijst te delen met de gemeenschap. Deze API is een MVP, het is gemaakt als onderdeel van het HBO vak backend development **@NOVI Hogeschool**.

## 🌱 Over het project

Aan de rand van het fluisterbos in Amsterdam Nieuw-West ligt de biologische zelfoogsttuin *Pluk! Groenten van West*. Hier kunnen leden wekelijks hun eigen seizoensgroenten en kruiden oogsten. Dit vraagt om een andere manier van koken: wat je mee naar huis neemt, is niet wat je gewend bent uit de supermarkt. Veel mensen weten niet wat ze met bladmosterd, koolrabi of raapstelen moeten doen. Deze API helpt met kookinspiratie.

## 🍽️ Wat kun je met deze API?

- ✅ Recepten zoeken op basis van:
    - Type keuken: vegetarisch, veganistisch, wildpluk, ayurvedisch
    - Type gerecht: soep, hoofdgerecht, bijgerecht, zoet, drinken
    - Ingrediënten: bijv. andijvie, daslook, colakruid
    - Maand of seizoen

- ✅ Zelf recepten toevoegen met:
    - Naam, ingrediënten, bereidingswijze
    - (optioneel) een foto

- 🔜 Toekomstige functies:
    - Upload & weergeven van foto's
    - Favorieten opslaan
    - Account/login-systeem

## 💚 Waarom dit belangrijk is

Deze API wil bijdragen aan een duurzamer voedselsysteem door lokaal, biologisch en seizoensgebonden koken makkelijker te maken. Door het delen van recepten en ervaringen kunnen leden elkaar inspireren en ondersteunen in hun kookproces. Zo bouwen we samen aan:

- Betere aansluiting bij lokale boeren
- Minder voedselverspilling
- Meer verbinding met natuur en seizoen
- Een sterker gemeenschapsgevoel

## ⚙️ Technologieën

- Java met Spring Boot
- Maven voor dependency management
- REST API structuur
- PostgreSQL database (maar H2 of andere relationele databases werken ook)
- JPA / Hibernate voor data persistence
- JUnit voor integratietests

## 🛠️ Project lokaal draaien

### Vereisten

- Java 17+ met JDK
- Maven 
- PostgreSQL (of de database van jouw voorkeur)
- Postman 
- IntelliJ of andere IDE

Testgebruikers en wachtwoorden vind je in de data.sql, dus je kan direct aan de slag via Postman. Vergeet niet jouw database gebruikersnaam en wachtwoord in te vullen. Het template voor de applications.properties zit al in de project-code. Er is een volledige installatiehandleiding beschikbaar op aanvraag (inclusief Postman collecties, stappenplan installatiehandleiding en REST-endpoints).

## ✍️ Auteur

Dit project is gemaakt door **Irene Jurna**  
🌍 Gemaakt vanuit mijn werkkamer in Amsterdam, vakantiehuis in Noordwolde en op diverse plekken in de trein  
🔁 Carrièreswitch gemaakt via NOVI: van communicatieadviseur naar software ontwikkelaar   
🍃 Enthousiaste oogster bij Pluk! Groenten van West  


