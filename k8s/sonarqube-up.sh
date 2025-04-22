#!/bin/bash

echo "🚀 Iniciando SonarQube usando Docker Compose..."

docker compose up -d

echo
echo "✅ SonarQube debería estar disponible en: http://localhost:9000"
echo "🔐 Usuario: admin | Contraseña: admin (primera vez)"