# Feedback

## Introduction

Introduction un peu courte.

## Échéancier

Bon échéancier dans l'ensemble, mais un peu calqué du professeur.

## Glossaire 

Il manque des termes importants : "Code de la ville", "Notifications personnalisées", "Info Entraves et Travaux", "Planification participative" et "Statut du projet".

## Cas d'utilisation

Il manque "utilisateur général" comme acteur. 

Il manque "Envoyer une notification" et "Soumettre sa candidature" pour l'intervenant

Il manque "S'abonner à un projet" pour le résident.

Les utilisateurs doivent pouvoir "Modifier son profil" et "Consulter ses notifications".

Il manque des relations d'include pour l'authentification.

"Consulter les travaux en cours ou à avenir" devrait extend "Rechercher des travaux"

## Scénarios

Le système (MaVille) est l'environnement dans lequel l'acteur opère, il n'est pas un acteur.

Il y a un manque d'alternance entre acteur et système (max 2 étapes successives de la même source) pour certains scénarios.

Il manque le scénario "Signaler un problème à la ville".

### CU: Connexion

Les 2 scénarios alternatifs devraient être pour l'étape 2.

### CU: Soumettre un nouveau projet

3a- Devrait être pour l'étape 4.

### CU: Mettre à jour les informations de chantier

1- Pas besoin à cause de la précondition

### CU: Gestion des notifications

Selon les CUs, l'intervenant ne devrait pas être un acteur.

3a- Ne devrait pas être là, on cherche à gérer ses notifications pas à savoir si la notification s'affiche ou pas.

### CU: Consulter la liste des requêtes de travail

1- Pas besoin à cause de la précondition.

### CU: Modifier son profil utilisateur

1- Pas besoin à cause de la précondition.

### CU: Faire une planification participative (Avant les travaux - Préférences d'horaires)

1- Pas besoin à cause de la précondition.

2a- N'est pas pertinent avec l'étape 2 c'est plutôt pour l'étape 4.

### CU: Permettre une planification participative (Après les travaux - Avis sur les travaux)

2a- N'est pas pertinent avec l'étape 2.

### CU: Consulter les préférences des autres résidents du quartier

1- Pas besoin à cause de la précondition.

### CU: S'inscrire comme résident

Les 2 scénarios alternatifs devraient être pour l'étape 5.

## Diagramme d'activités

Il manque le diagramme d'activités principal.

Il manque des descriptions pour les noeuds de décision.

On peut boucler à l'infini dans plusieurs diagrammes.

Il manque le diagramme pour "Signaler un problème à la ville".

### Diagramme d'activité pour : Personaliser ses notifications

"Vérifier les paramètres de son appareil" n'est pas pertinent, on n'est plus dans l'application.

### Diagramme d'activité pour : Donner son avis sur le déroulement des travaux

Il devrait avoir un noeud avant "enregistrement"

### Diagramme d'activité pour : Inscription en tant qu'intervenant

La flèche de partant de "recommencer" pointe au mauvais endroit.

## Analyse

### Risques

On demandait 5 risques minimum avec leurs description, justification et une solution pour mitiger le risque.

### Besoins non fonctionnels

Manque de description, caractérisation par un critère de qualité et fustification par le contexte.

### Besoins matériels

Sur quoi va-t-on développer l'application et combien tout le matériel va coûter?

### Solution de stockage

Quel est le type de données et doit-on sécurisé ces données?

### Solution d'intégration

Bonne solution.

## Prototype

Fichier JAR fonctionnel.

## Git 

README et Release présents.

3/3 membres ont fait au moins 1 commit.