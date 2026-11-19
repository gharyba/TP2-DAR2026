# TP2 - Calculs avec des sockets TCP

## Objectif

Envoyer des opérations à un serveur TCP et récupérer le résultat côté client.

## Exercices réalisés

- Activité 2-1 : échange d'une expression et du résultat sous forme de texte.
- Activité 2-2 : envoi d'un objet `Operation` sérialisé au serveur.

## Lancement

Dans PowerShell, depuis ce dossier, compiler les deux activités séparément car elles ont toutes les deux des classes `Client` et `Serveur` sans package :

```powershell
New-Item -ItemType Directory -Force bin/activite_2_1, bin/activite_2_2 | Out-Null
javac -encoding UTF-8 -d bin/activite_2_1 src/activite_2.1/Client.java src/activite_2.1/Serveur.java
javac -encoding UTF-8 -d bin/activite_2_2 src/activite_2_2/Operation.java src/activite_2_2/Client.java src/activite_2_2/Serveur.java
```

Ouvrir deux terminaux dans ce dossier. Pour l'activité 2-1, lancer le serveur puis le client :

```powershell
java -cp bin/activite_2_1 Serveur
java -cp bin/activite_2_1 Client
```

Pour l'activité 2-2, remplacer `bin/activite_2_1` par `bin/activite_2_2`. Les deux serveurs utilisent le port `5000` ; en lancer un seul à la fois.
