# Gestion de projet

Au cours de ce projet nous allons essayer de mettre en place une approche agile, je vous propose donc l'adaptation de ces méthode dans ce document.

Comme nous nous trouvons dans un contexte agile nous allons travailler en cycle court, la durée des sprints est fixé à **deux semaines**.

Au cours de chaque cycle nous allons retrouver les éléments suivants :

## Réunion de début de sprint

Nous nous réunirons au début de chaque cycle pour discuter du cycle passé, ce sera aussi l'occasion de plannifier les fonctionnalités à développer dans le prochain cycle. Les éventuels problèmes techniques ne devrons pas être évoqué lors de cette réunion.

C'est l'occasion de faire un poker planning pour définir la difficulté de chaque fonctionnalité, c'est aussi lors de cette réunion que les fonctionnalités seront attribués aux développeurs.
C'est ici que nous pourrons définir les tests fonctionnels que les modules doivent passés afin de valider leur intégration (features).
Nous discuterons des fonctionnalités à développer pour le cycle à venir et chaque développeur choisira celle qu'il souhaite développer lors du sprint à venir.

Chaque développeur sera en charge de la fonctionnalité entière (voir section "Codage" pour le travail à faire).


## Réunion technique (mêlée générale)

Théoriquement cette réunion devrais avoir lieu tous les jours et durer environs 15min, le but de celle-ci est que tous le monde aborde les questions suivantes :
 * Qu'est-ce que j'ai fait depuis le dernier meeting ?
 * Qu'est-ce que je prévois de faire ?
 * Où est-ce que je rencontre des problèmes ?

**Attention :** Ce n'est pas le bon moment pour résoudre les problèmes techniques, mais pour les identifier. Les problèmes rencontrés sont identifiés lors de cette réunion et sont résolus ensuite (parfois il n'y a pas besoin de toutes l'équipe pour résoudre un problème).

Je suis conscient que tous le monde ne sera pas disponible tous les jours à la même heure, je propose donc de faire cette réunion uniquement tous les **3 jours** (à part si vous vous sentez de la faire tous les jours).

Cette réunion peut se faire sur discord il n'est pas nécessaire de tous se déplacer pour un briefing de 15min

## Codage

Chaque développeur aura à sa charge la fonctionnalité entière qui lui sera affecté (écriture du service, intégration de celui-ci dans le controller et création du composant web associé).
Les tests unitaires doivent être écrits en même temps que le code.
Pour chaque fonctionnalité il est d'usage de créer une nouvelle branche sur le github. Lorsque vous avez terminé de développer votre fonctionnalité et que celle-ci passe les tests la branche en question sera merge sur le master.

Toutes les fonctions implémentées devront être commentées avec le style JavaDoc, et les informations suivantes devrons être présentes au début du fichier source : 
~~~
/**
* @author
* @date 
* description of the class/src file
*/
~~~
