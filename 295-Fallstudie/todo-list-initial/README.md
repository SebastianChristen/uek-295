# Webentwicklung Backend / ük 295


```shell
durchfuehrung="BA23"

```

## Wichtige Ordner

| Ordner       | Beschreibung                                                |
|--------------|-------------------------------------------------------------|
| **database** | Schema und SQL-Files mit MySQLWorkbench erzeugt             |
| **docs**     | Aktuelle für die Entwicklung relevante Dokumente und Bilder |
| **src**      | Java-Source-Code                                            |

## Applikation starten

1. Lokal eine MySQL Instanz installieren
2. `database/create-database.sql` ausführen (erzeugt Datenbank inkl. User)
    * alternativ das Script `database/create-database-with-data.sql` ausführen, um Testdaten zu erhalten
3. Im Terminal mit ```gradlew bootRun``` oder über das Gradle-Tab in der IDE die App starten
4. Applikation kann über ```http://localhost:8080``` angesprochen werden
5. Wurde DB mit Daten geladen, existiert der Benutzer `user` mit dem Passwort `test`
6. Zum Einloggen POST-Request an ```http://localhost:8080/auth/signin``` schicken
   mit ```{"username": "user", "password": "test"}``` im Body
7. Im Body der Response des Logins sind die Informationen für den Token vorhanden. (`tokenType accessToken`,
   z.B. `Bearer exJh...`) Diese müssen bei jedem künftigen Request im Header als value zum Attribut `Authorization`
   gesetzt werden.

## Nützliche urls

* Api-Docs und Swagger
    * [http://localhost:8080/v3/api-docs/](http://localhost:8080/v3/api-docs/)
    * [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* API-Endpunkte
    * [http://localhost:8080/persons](http://localhost:8080/persons)
    * [http://localhost:8080/items](http://localhost:8080/items)
    * [http://localhost:8080/tags](http://localhost:8080/tags)
