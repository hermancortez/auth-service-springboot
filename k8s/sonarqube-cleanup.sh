#!/bin/bash

echo "🛑 Deteniendo y eliminando contenedor de SonarQube..."
docker compose down -v

echo "🧹 Eliminando volúmenes huérfanos (si los hay)..."
docker volume rm sonarqube_data sonarqube_logs sonarqube_extensions 2>/dev/null

echo "🔍 Verificando contenedores restantes..."
docker ps -a

echo "📦 Verificando volúmenes restantes..."
docker volume ls

echo "✅ SonarQube y recursos asociados eliminados completamente."