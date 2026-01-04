# CDD Java Framework - Spring Boot Integration
Ce projet démontre l'utilisation du framework Cyberattack-Driven Development (CDD) pour sécuriser une application Spring Boot. Il permet d'automatiser des audits de sécurité réels (et non simulés) directement via votre pipeline de tests.

## Installation
### 1. Ajouter la dépendance Maven
Ajoutez la bibliothèque cdd-java à votre fichier pom.xml :

```
<dependency>
    <groupId>io.github.cddframework</groupId>
    <artifactId>cdd-java</artifactId>
    <version>0.5.0-alpha.3</version>
    <scope>test</scope>
</dependency>
```



### 2. Initialisation du Workspace
Au premier lancement de vos tests (ou via un bean de startup), CDD détecte la structure Java et génère les fichiers nécessaires :

Binaire Ratel : Extrait dans ~/.ratel/bin/ratel.exe.

Scénario : Injecté dans src/test/resources/ratel/security.ratel.

## Configuration du Scénario d'Audit
Le fichier .ratel utilise un DSL (Domain Specific Language) pour définir les tests de sécurité :


```
SCENARIO "Access audit"
TARGET "http://localhost:8080"
WITH_SCOPE KERNEL

STEP "Secure transport verification"
    ATTACK secure_headers
    CHECK header "Strict-Transport-Security" EXISTS
    CHECK response.status BE 200
```

## Exécution et Résultats
Une fois l'audit lancé par le framework, les résultats sont consolidés dans un rapport JSON structuré. Voici un exemple de sortie obtenue avec la version 0.5.6 du binaire :

```
{
  "name": "Access audit",
  "target": "http://localhost:8080",
  "scope": "KERNEL",
  "steps": [
    {
      "title": "Secure transport verification",
      "results": [
        {
          "kind": "ATTACK",
          "value": "secure_headers",
          "status": "SUCCESS",
          "message": "Security headers audit completed successfully"
        },
        {
          "kind": "CHECK",
          "value": "CHECK header \"Strict-Transport-Security\" EXISTS",
          "status": "SUCCESS",
          "message": "Condition satisfied: Header 'strict-transport-security' is present"
        }
      ]
    }
  ],
  "executed_at": "2026-01-04T14:46:35Z"
}
```

## Architecture "Binary Pivot"
Le framework repose sur une architecture robuste en trois couches :

CDD-Java : Le wrapper qui pilote l'exécution.

Ratel CLI (v0.5.6) : Le binaire natif qui parse le DSL.

CDD-Core (v0.5.3) : La bibliothèque Rust qui effectue les requêtes réseau réelles via reqwest.

_Notes importantes pour Windows_
TLS : Le binaire est compilé avec native-tls pour assurer la compatibilité avec Windows sans dépendances externes complexes.

Intégrité : Toute modification manuelle du fichier .ratel sans utiliser la commande certify bloquera l'audit pour garantir la traçabilité des tests d'experts.