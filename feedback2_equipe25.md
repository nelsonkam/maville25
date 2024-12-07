# Feedback

## Révision

### Échéancier

Échéancier mis à jour

### diagrammes CU 

Il manque un nouvel acteur "API de la ville".

### Diagrammes d'activités 

Diagrammes d'activités mis à jour 

### Analyse

Analyse mise à jour

## Architecture

Les API externes ne font pas partie du système.

Il manque une frontière pour distinguer les composantes du système des dépendances externe

Les interactions entres les composantes ne sont pas assez explicites (HTTP, JSON) 

Pour la base de données précisez le type de fichiers.

## Diagramme de classe

La classe "Maville" a trop de responsabilités

Il manquerait une classe "base de données" pour stocker les objets au lieu de faire ça dans "Maville".

Les signatures et les paramètres des méthodes sont manquants.

Certains attributs pourraient être des enums ex: classe "TypeTravail"

Il manque des méthodes dans certaines classe ex: L'intervenant ne peut pas soumettre sa candidature

## Diagramme de séquence 

Il manque des descriptions pour certains fragments "alt".

Certaines méthodes appelées n'existent pas dans le diagramme de classe.

### Consulter les entraves

Comment on récupère la liste des entraves?

Pouquoi on envoie des notifications?

### Soumettre une requête de travail

On ne peut pas faire le suivi d'une requête de travail.

### Consulter la liste des requêtes de travail

Comment on récupère la liste des requêtes?

On n'a pas accès à une requète de travail.

L'intervenant ne peut pas soumettre sa candidature.

## Discussion design

Discussion du design absente.

## Implémentation

Fichier jar pour la remise du DM2 absent.

La page de connexion n'est pas partagée.

Belle architecture REST.

Le code est différent de la conception.

## Tests unitiaire

Tests exécutables et passent tous.