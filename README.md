# MQRouter - Application de Routage des Messages de Paiement

## Description
L'application **MQRouter** est responsable du traitement des messages de paiement reçus par le département de paiement de la banque. Ces messages sont envoyés par les applications **Back Office** via une **file IBM MQ Series**. L'application de routage reçoit ces messages et les redirige vers différentes destinations selon des règles de routage définies.

Les utilisateurs ont également la possibilité d'ajouter des **partenaires** pour configurer les files MQ, facilitant ainsi l'intégration et la gestion des échanges entre les différentes parties prenantes.

## Fonctionnalités
- **Réception des messages de paiement** depuis les applications Back Office via IBM MQ Series.
- **Routage des messages** vers des destinations spécifiques selon les configurations définies.
- **Gestion des partenaires** pour configurer les files MQ et personnaliser les routes des messages.

## Documentation swagger
http://localhost:8080/swagger-ui/index.html

## Base de données utilisée 
H2