# Auth Service - Microservicio de Autenticación con Cookies Seguras

Este microservicio es parte de una arquitectura basada en Spring Boot orientada a microservicios, cuya misión es manejar la autenticación de usuarios mediante tokens JWT enviados exclusivamente en **cookies HTTP-only**, con los más altos estándares de seguridad.

## 📆 Características

- Autenticación basada en JWT (Access + Refresh tokens).
- Uso de cookies seguras (`HttpOnly`, `Secure`, `SameSite=Strict`).
- Protección contra ataques CSRF y XSS.
- Integración con SonarQube para asegurar calidad del software.
- Empaquetado en Docker y listo para desplegar en Kubernetes.

---

## 🔐 Endpoints Disponibles

| Endpoint        | Método | Protegido | Descripción                                               |
| --------------- | ------ | --------- | --------------------------------------------------------- |
| `/auth/login`   | POST   | No        | Login de usuario y seteo de cookies.                      |
| `/auth/refresh` | POST   | No        | Renueva el `accessToken` usando `refreshToken` en cookie. |
| `/auth/logout`  | POST   | Sí        | Elimina cookies y revoca el token.                        |
| `/auth/me`      | GET    | Sí        | Obtiene información del usuario actual.                   |

---

## 🚀 Instrucciones Rápidas

### Clonar y construir

```bash
# Clonar
https://github.com/tuusuario/auth-service.git
cd auth-service

# Compilar
mvn clean install
```

### Docker

```bash
# Construir imagen
docker build -t tuusuario/auth-service:lastest .

# Ejecutar
docker run -p 8080:8080 tuusuario/auth-service:lastest
```

### Kubernetes (minikube/dev)

```bash
kubectl apply -f configmap.yaml
kubectl apply -f deployment-dev.yaml
kubectl apply -f service.yaml
```

---

## 📈 Integración con SonarQube

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=auth-service \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=xxxxxxxxxxxxxxxxxxx
```

### Métricas evaluadas:

- ✅ Cobertura de tests (Jacoco)
- 🚨 Bugs y vulnerabilidades (OWASP rules)
- 🧼 Code smells y duplicación
- 🔹 Complejidad ciclomática
- 📜 Convenciones y licencias

---

## 🌐 Recomendaciones Frontend

En React / Angular usar `credentials: 'include'` para que las cookies se envíen:

```ts
fetch('/auth/me', { method: 'GET', credentials: 'include' });
```

---

## 🔧 Requisitos

- Java 17+
- Maven 3.8+
- Docker (para empaquetado)
- Kubernetes o Minikube (para despliegue)

---

## 🚪 Seguridad

- Los tokens **no están accesibles por JS** (`HttpOnly`).
- Solo se transmiten por HTTPS (`Secure`).
- `SameSite=Strict` evita CSRF.
- Tokens revocados se invalidan desde backend (lista).

---

## 🚀 Futuras mejoras

- Agregar roles y permisos personalizados.
- Soporte a autenticación multifactor (MFA).
- Log de auditoría.
- Integración con Keycloak u otros IDPs opcional.

---

> Diseñado para empresas que requieren máxima seguridad, rendimiento y calidad de código en sus soluciones de autenticación.

