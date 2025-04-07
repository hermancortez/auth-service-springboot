<<<<<<< HEAD
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
=======
# Autenticación con Cookies HTTP-only Seguras

Este documento describe el funcionamiento del API de autenticación basada en cookies HTTP-only seguras, como alternativa al enfoque común de `Authorization: Bearer`.

---

## 🔑 Seguridad

- Los tokens **access** y **refresh** se envían como **cookies HTTP-only**:
    - ❄️ No son accesibles por `JavaScript` (mitigación XSS).
    - ⛓ Solo se envían con peticiones seguras (HTTPS).
    - 🚫 Incluyen `SameSite=Strict` para prevenir ataques CSRF.

> ✅ Ideal para integración con frontend moderno y seguro.

---

## 📝 Endpoints

### POST `/auth/login`
**Body:**
```json
{
  "username": "admin",
  "password": "admin"
}
```
**Respuesta:**
- No tiene cuerpo (`204 OK` o `200 OK`).
- Las cookies `accessToken` y `refreshToken` se establecen en las cabeceras `Set-Cookie`.

---

### POST `/auth/refresh`
- Lee el `refreshToken` desde la cookie y genera un nuevo `accessToken` + `refreshToken`.

**Respuesta:**
- Cookies renovadas con nuevos tokens.
- Opcionalmente, se devuelve un JSON con los tiempos:
```json
{
  "value": "<nuevo_access_token>",
  "issuedAt": "...",
  "expiresAt": "...",
  "refresh": false
}
>>>>>>> fd961d5 (Implementación de autenticación con cookies seguras (HTTP-only, SameSite, Secure))
```

---

<<<<<<< HEAD
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
=======
### POST `/auth/logout`
- Lee `accessToken` desde la cookie y lo revoca.
- Devuelve cookies vacías con `Max-Age=0` para forzar expiración.

**Respuesta:**
```
204 No Content
```

---

### GET `/auth/me`
- Obtiene el usuario actual autenticado.
- **Requiere que `accessToken` esté presente como cookie**.

**Respuesta:**
```json
{
  "id": "1",
  "username": "admin",
  "roles": ["ROLE_ADMIN"]
}
```

---

## 🚀 Integración con Frontend (React / Angular)

### Login
```ts
fetch('/auth/login', {
  method: 'POST',
  credentials: 'include', // ⬆️ envía y guarda cookies
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ username: 'admin', password: 'admin' })
});
```

### Obtener usuario actual
```ts
fetch('/auth/me', {
  method: 'GET',
  credentials: 'include'
});
```

### Logout
```ts
fetch('/auth/logout', {
  method: 'POST',
  credentials: 'include'
});
```

### Refresh Token (opcional)
```ts
fetch('/auth/refresh', {
  method: 'POST',
  credentials: 'include'
});
>>>>>>> fd961d5 (Implementación de autenticación con cookies seguras (HTTP-only, SameSite, Secure))
```

---

<<<<<<< HEAD
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
=======
## 📊 Recomendaciones para pruebas en Postman
- En **Authorization** seleccionar: "No Auth".
- Ir a pestaña **Cookies** y asegurarse que `accessToken` y `refreshToken` se estén enviando.
- Activar **cookies automáticas** si usas `localhost`.

---

## 📃 Tecnología
- Spring Boot 3.4.x
- JWT (JJwt)
- Cookies HTTP-only + SameSite Strict + Secure

---

## ⚠️ Notas
- En ambiente de desarrollo sin HTTPS, puedes temporalmente desactivar `cookie.setSecure(true)`.
- Requiere que el frontend esté en el mismo dominio o bien que se configure correctamente CORS + cookies cross-origin.

---

> Este enfoque está alineado con las mejores prácticas modernas de seguridad web. Es ideal para aplicaciones que desean evitar exposición de tokens a JavaScript y minimizar el riesgo XSS/CSRF.
>>>>>>> fd961d5 (Implementación de autenticación con cookies seguras (HTTP-only, SameSite, Secure))

