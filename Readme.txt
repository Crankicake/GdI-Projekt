# GdI-Projekt
Unsere Repository für das GdI Projekt

Also Leute, ich hab mal die 5 States noch als Basis hinzugefügt.
In ExceptionReason fügt ihr bitte alle Gründe hinzu, wegen welchen die Exception auftreten kann.
Falls ihr Anregungen bzgl. der Klasse GorillaException habt, dann teilt die mir bitte mit oder ändert die Klasse, aber bitte vermerken!
Zudem habe ich in Launcher die main-Methode platziert.
Projectile und Player sind atm nur so da.
----------------------------------------------------------------------------------------------
13.02. 
Fabian @ all:
Es gibt jetzt 3 Arten von Gebäuden & einen Hintergrund.
Das ganze ist unter assets/gorillas/background/ zu finden. Es gibt neue ExceptionReason.
In Gorillas.java hab ich eine if-Abfrage reingemacht, damit direkt das Gameplay startet. Gerne könnt ihr das zu euren Debugzwecken ändern.
Es gibt eine neue Klasse ThrowAttempt, die mit den verschiedenen Parametern erstellt wird und entweder den nächsten Punkt abhängig von der vergangenen Zeit berechnet,
oder eine Liste von der gesamten Flugbahn ausgibt, bis die Banane rechts, links oder unten rausfliegt.
Überschneidunug mit Projectile?
@ Christoph:
Lies die Kommentare^^


----------------------------------------------------------------------------------------------

15.02.

Christoph @ all:
Affe wird (zwar noch fehlerhaft) auf dem SPielfeld platziert, derzeit nur der linke.

----------------------------------------------------------------------------------------------
15.02.

Fabi:
Nachdem Christoph die ganze Zeit programmiert hat und alles blockiert hat, habe ich mich mal ausgelassen.
Habe viele Kommentare gelöscht, die nutzlos waren. Hab die Affen richtig platziert, die tanzen jetzt. Gibt jetzt einen Button und 2 Textfelder, allerdings noch ohne beschriftung.
Player erbt jetzt von Entity. 
@Christoph: Ich hab deine komische Klasse mal gelöscht <3