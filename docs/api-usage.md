# 📘 Uso del Microservicio de Autenticación - Auth Service

Este documento describe cómo interactuar con el microservicio de autenticación construido con Spring Boot y JWT.

---

## ✅ Autenticación de Usuario

### 🔒 Endpoint: `/auth/login`
- **Método**: `POST`
- **Descripción**: Autentica al usuario y devuelve cookies seguras (`accessToken`, `refreshToken`).

### 🧾 Request (JSON)
```json
{
  "username": "usuario_demo",
  "password": "clave123"
}
```

### 🧾 Respuesta
- **Código HTTP**: `200 OK`
- **Cookies Set-Cookie**:
    - `accessToken`: JWT válido por 1 hora, enviado en cada petición
    - `refreshToken`: JWT válido por 7 días, usado para renovar el `accessToken`

---

## 🔄 Refrescar Token

### 🔁 Endpoint: `/auth/refresh`
- **Método**: `POST`
- **Descripción**: Usa el `refreshToken` en la cookie para emitir un nuevo `accessToken`.

### 🧾 Requisitos
- La cookie `refreshToken` debe estar presente y válida.

### 🧾 Respuesta
- **Código HTTP**: `200 OK`
- Nuevas cookies `accessToken` y `refreshToken`

---

## 🔐 Obtener Usuario Actual

### 👤 Endpoint: `/auth/me`
- **Método**: `GET`
- **Descripción**: Devuelve los datos del usuario autenticado, si el `accessToken` es válido.

### 🧾 Requisitos
- La cookie `accessToken` debe estar presente.

### 🧾 Respuesta
```json
{
  "username": "usuario_demo",
  "roles": ["USER"]
}
```
- **Códigos HTTP**:
    - `200 OK`: Usuario válido
    - `401 Unauthorized`: Token inválido o expirado

---

## 🚪 Logout

### 🧼 Endpoint: `/auth/logout`
- **Método**: `POST`
- **Descripción**: Revoca el token actual y elimina las cookies del navegador.

### 🧾 Respuesta
- **Código HTTP**: `204 No Content`
- Elimina cookies `accessToken` y `refreshToken`

---

## 🧪 Cómo probar con `curl`

### Login:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "usuario_demo", "password": "clave123"}' -i
```

### Me (usando cookies):
```bash
curl -X GET http://localhost:8080/auth/me \
  --cookie "accessToken=eyJhbGci..." -i
```

### Logout:
```bash
curl -X POST http://localhost:8080/auth/logout \
  --cookie "accessToken=eyJhbGci..." -i
```

---

## 📫 Cómo probar con Postman

1. **Login**
    - Método: `POST`, URL: `http://localhost:8080/auth/login`
    - Body: JSON con `username` y `password`
    - Activa "Retain Cookies" en la pestaña Cookies

2. **/auth/me** y **/auth/refresh**
    - Usar cookies automáticamente guardadas en Postman

3. **Logout**
    - `POST` a `/auth/logout`, se revocarán y eliminarán las cookies

---

## 🛑 Status Codes Esperados
| Endpoint         | 200 | 204 | 400 | 401 | 403 |
|------------------|-----|-----|-----|-----|-----|
| /auth/login      | ✅  |     | ✅  | ✅  |     |
| /auth/refresh    | ✅  |     | ✅  | ✅  |     |
| /auth/me         | ✅  |     |     | ✅  |     |
| /auth/logout     |     | ✅  |     | ✅  |     |

---

## 📌 Notas
- Todos los tokens están configurados como HTTP-only, `Secure`, y `SameSite=Strict`.
- No es necesario enviar `Authorization: Bearer`, ya que el backend lee los tokens desde cookies automáticamente.

---

¿Tienes dudas o deseas agregar más endpoints? Actualiza este archivo o consulta al equipo DevOps. 💬

