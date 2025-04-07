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
```

---

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
```

---

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

