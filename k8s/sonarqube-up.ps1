Write-Host "🚀 Iniciando SonarQube usando Docker Compose..."

docker compose up -d

Write-Host "`n✅ SonarQube debería estar disponible en: http://localhost:9000"
Write-Host "🔐 Usuario: admin | Contraseña: admin (primera vez)"