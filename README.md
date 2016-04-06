# Zelos CodeGenerator

**Zelos ist eine Desktop-Anwendung für die modellbasierte Code-Generierung. Primär für Java-Dateien und SQL-Skripte gedacht.**

Die Entitäten werden in einem **Modell** definiert. Dieses Modell wird als **XML-Datei** gespeichert und kann so zusammen
mit den Sourcen versioniert werden.
Zelos ist dreigeteilt: Modell, CodeGenerator und GUI. Die Entitäten können also komfortabel in einer Desktop-**GUI**
bearbeitet werden. Eine oder mehrere Entitäten können dann auf Knopfdruck generiert werden. Die Vorlagen
werden mit Apache Velocity definiert. Eine sog. Zelos-Command-Datei legt fest, welche Artefakte pro Entität generiert
werden sollen und wie die Dateinamen lauten.

Das Besondere an Zelos ist, dass man fachliche Datentypen angibt. Beim Modellieren muss man sich also nicht mehr Gedanken
um die technische Abbildung für Java und verschiedene DBMS (Oracle, HSQLDB, ...) machen.

## Struktur

Ein **Modelltyp** repräsentiert die Architektur und definiert **Vorlagen** und **Datentypen**.
Ein **Modell** hat einen Modelltypen und enthält die Entitäten.
**Entitäten** bestehen vor allem aus Feldern. Weiterhin enthält eine Entität Fremdschlüssel und Indizes.
Ein **Feld** hat einen Datentyp.

## Build

Nach dem Auschecken mit Eclipse muss die Ant-Datei zelos/build.xml (Default-Target eclipse) ausgeführt werden, damit alle
erforderlichen JARs geladen werden.

Build: Ant-Datei zelos/build.xml (Taget build) ausführen.

Tooling: Java 8 >=u40, GUI mit JavaFX, Build mit Gradle 2.10, Ant, IDE: Eclipse Mars.2, SCM: github

Lizenz: Apache 2.0
