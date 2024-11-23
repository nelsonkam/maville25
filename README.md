# MaVille


## Qu'est-ce que MaVille?
MaVille est une application permettant de faciliter la gestion et la communication entre les résidents et intervenants en ce qui concerne les travaux réalisés sur le territoire de la Ville de Montréal.

Un rapport en format web est disponible sur le répertoire GitHub pour plus de détails sur le projet.

## GIT
Pour télécharger la dernière version du répertoire GitHub, utiliser la commande suivante:

```
    git clone https://github.com/sidaliterkmane/maville25.git
```



## Prototype
Un prototype écrit en Java est disponible. Il démontre l'interface du côté des résidents et du côté des intervenants. Le cas d'utilisation "Consulter les travaux en cours" est démontré.

Pour exécuter le fichier .jar, utiliser la commande suivante dans un terminal:
```
java -jar MaVille.jar
```

```
    ===== Accès à l'interface résident =====
    Utilisateur: citoyen
    Mot de passe: maVille25
```

```
    ===== Accès à l'interface intervenant ======
    Utilisateur: intervenant
    Mot de passe: maVille25
```
### Residents Table
| Nom            | Email | Date de naissanece | Telephone    | Adresse                               |
|----------------|--------|--------------------|--------------|---------------------------------------|
| Jean Tremblay  | jean.tremblay@email.com | 1980-05-15         | 514-555-0101 | 123 Rue Saint-Denis, Montréal         |
| Marie Lavoie   | marie.lavoie@email.com | 1992-08-22         | 514-555-0102 | 456 Boulevard Saint-Laurent, Montréal |
| Pierre Gagnon  | pierre.gagnon@email.com | 1975-11-30         | 514-555-0103 | 789 Avenue Mont-Royal, Montréal       |
| Sophie Dubois  | sophie.dubois@email.com | 1988-03-25         | 514-555-0104 | 321 Rue Sherbrooke, Montréal          |
| Lucas Bergeron | lucas.bergeron@email.com | 1995-07-12         | 514-555-0105 | 654 Avenue du Parc, Montréal          |

### Intervenants
| Nom                        | Email | Type | ID       |
|----------------------------|--------|------|----------|
| Construction Québec Inc.   | info@constructionqc.com | Entrepreneur privé | 12345678 |
| Ville de Montréal - Voirie | voirie@montreal.ca | Entreprise publique | 87654321 |
| Aqueduc Expert             | contact@aqueducexpert.com | Entrepreneur privé | 23456789 |
| Signalisation Montréal     | ops@signalmtl.com | Entrepreneur privé | 34567890 |
| Services Municipaux MTL    | services@mtl.ca | Entreprise publique | 45678901 |

### Requete de travail
| Titre                 | Description | Type             | Date       | Statut      | Email du resident       |
|-----------------------|-------------|------------------|------------|-------------|-------------------------|
| Réparation trottoir   | Trottoir endommagé devant le 123 Rue Saint-Denis | Voirie           | 2024-05-01 | PENDING     | jean.tremblay@email.com |
| Fuite d'eau           | Fuite d'eau importante sur Boulevard Saint-Laurent | Aqueduc          | 2024-04-15 | IN_PROGRESS | marie.lavoie@email.com  |
| Lampadaire défectueux | Lampadaire non fonctionnel depuis 2 semaines | Éclairage public | 2024-04-20 | PENDING     | pierre.gagnon@email.com |
| Panneau stop manquant | Panneau stop disparu après accident | Signalisation    | 2024-04-10 | ASSIGNED    | marie.lavoie@email.com  |
| Égout bouché          | Égout bouché causant des refoulements | Égouts           | 2024-05-15 | PENDING     | jean.tremblay@email.com |

