# Jinx

## Prequisites

Wir nutzen die Java JDK 17 und Gradle als build tool für unser Projekt.


## Source Code Documentation

### Main

Ausgehend von der Klasse Main in der sich die Main Methode befindet kann unser Projekt gestartet werden.
Von da an übernimmt der GameController im package game.

### GameController

Der GameController steuert über die start Methode den Fluss des Spiels indem er zum Beispiel erst den Startbildschirm zeigt 
und von da aus eine Runde vom Spieler gestartet werden kann

### Game

Die Klasse Game verfügt über mehrere Methoden die den Spielern das Spiel auf der Konsole ausgeben und Spielen lassen, 
zudem steuert sie den Spielfluss bei einzelnen Eingaben

### PlayerController

Der PlayerController kann Spieler registrieren, speichern und verwaltet gleichzeitig, welcher Spieler gerade am Zug ist.

### Player

Der Spieler wird durch die Klasse Player repräsentiert und speicher Attribute wie den Namen oder die Karten des Spielers

### HighScore 

HighScores der Spieler werden in der Highscore.txt gespeichert jedoch während das Programm läuft auch in Records names Highscore gespeichert

### Field

Die Klasse Field repräsentiert die Auslage der Karten von dem sich die Spieler nach den würfeln Karten nehmen können. 

### Dice

Die Dice Klasse fungiert als Würfel die eine zufällige Zahl von 1-6 mit ihrer Methode use generiert

### NumberCardStack

NumberCardStack dient als Repäsentation des Decks im Spiels. NumberCardStack erbt von der Datenstruktur Stack. 
Bei dem Aufruf des Konstruktors wird die generateDeck Methode auferufen die das Deck generiert.

### Card

Card ist eine abstrakte Klasse von den NumberCard und LuckyCard erben

### NumberCard

NumberCard erbt von Card undrepräsentiert die Zahlenkarten im Spiel Jinx. Sie führ die Attribute name und color

### LuckyCard

LuckyCard ist eine abstrakte Klasse und erbt wie NumberCard auch von Card. 
LuckyCard verfügt über eine abstrakte Methode Effekt die den Effekt einer LuckyCard ausübt.

### CardColor

CardColor ist ein Enum das die einzelnen Farben für NumberCard festlegt
