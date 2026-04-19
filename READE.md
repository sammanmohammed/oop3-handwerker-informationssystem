# 🛠️ Handwerker-Informationssystem V2

Dieses Projekt wurde im Rahmen des Moduls **Objektorientierte Programmierung III** entwickelt.

## 📌 Beschreibung
Das Handwerker-Informationssystem ist eine Java-basierte Desktop-Anwendung zur Verwaltung von Kunden, Ressourcen und Aufträgen.  
Die Anwendung bietet eine grafische Benutzeroberfläche und nutzt verschiedene Technologien zur persistenten Datenhaltung.

## 🧩 Funktionen
- Kundenverwaltung
- Ressourcenverwaltung
- Auftragsverwaltung
- Verwaltung von Baustellenadressen
- Speicherung und Abruf von Daten aus einer Datenbank

## 🏗️ Architektur
Die Anwendung folgt einer **3-Schichten-Architektur**:

- Präsentationsschicht (JavaFX GUI)
- Anwendungskern (Geschäftslogik)
- Datenhaltungsschicht (JPA & JDBC)

Zusätzlich wird ein **Factory Pattern** zur Bereitstellung der Anwendungsfälle verwendet.

## 🧱 Projektstruktur
Das Projekt ist als **Maven Multi-Modul-Projekt** aufgebaut:

- `kundenvw` → Kundenverwaltung (JPA)
- `ressourceverwalter` → Ressourcenverwaltung (JPA)
- `auftragverwalter` → Auftragsverwaltung (JDBC)
- `gui` → JavaFX Benutzeroberfläche

## ⚙️ Technologien
- Java
- JavaFX
- JPA (EclipseLink)
- JDBC
- Maven
- SQL (Oracle / H2)

## ▶️ Anwendung starten

```bash
cd gui
mvn clean javafx:run