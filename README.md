# Post-Contenido 2 Unidad 12

![CI/CD Status](https://github.com/Alejafarfan/farfan-post2-u12-/actions/workflows/ci.yml/badge.svg)

API REST de productos construida con Spring Boot, contenedorizada con Docker
mediante un Dockerfile multi-stage, con pipeline CI/CD automatizado con GitHub Actions
y publicada en Docker Hub.

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.4
- Spring Data JPA
- PostgreSQL 16
- Docker (multi-stage build)
- Docker Compose
- GitHub Actions (CI/CD)
- Docker Hub
- Railway (plataforma de despliegue)
- Maven
- JaCoCo (cobertura de pruebas)

## Estructura del proyecto

```
productos-api/
├── .github/
│   └── workflows/
│       └── ci.yml
├── src/
│   └── main/
│       ├── java/com/universidad/productosapi/
│       │   ├── controller/ProductoController.java
│       │   ├── model/Producto.java
│       │   └── repository/ProductoRepository.java
│       └── resources/
│           ├── application.properties
│           └── application-prod.properties
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
└── pom.xml
```

## URL de la aplicación en Railway
https://farfan-post1-u12-production.up.railway.app

## Imagen Docker en Docker Hub
```bash
docker pull alejaf13/productos-api:latest
```

## Endpoints disponibles

| Método | Endpoint               | Descripción                  |
|--------|------------------------|------------------------------|
| GET    | /api/productos         | Listar todos los productos   |
| GET    | /api/productos/{id}    | Buscar producto por ID       |
| POST   | /api/productos         | Crear un nuevo producto      |
| PUT    | /api/productos/{id}    | Actualizar un producto       |
| DELETE | /api/productos/{id}    | Eliminar un producto         |
| GET    | /actuator/health       | Estado de la aplicación      |

## Variables de entorno requeridas

| Variable               | Descripción                              | Ejemplo                              |
|------------------------|------------------------------------------|--------------------------------------|
| SPRING_PROFILES_ACTIVE | Perfil activo de Spring Boot             | prod                                 |
| DATABASE_URL           | URL JDBC completa de PostgreSQL          | jdbc:postgresql://host:5432/appdb    |
| DB_USER                | Usuario de la base de datos              | appuser                              |
| DB_PASS                | Contraseña de la base de datos           | apppass                              |

## GitHub Secrets requeridos

| Secret               | Descripción                                      |
|----------------------|--------------------------------------------------|
| `DOCKERHUB_USERNAME` | Usuario de Docker Hub (ej: alejaf13)             |
| `DOCKERHUB_TOKEN`    | Access Token generado en Docker Hub              |

### Cómo configurar los Secrets
1. Ir al repositorio en GitHub
2. **Settings** → **Secrets and variables** → **Actions**
3. Clic en **New repository secret**
4. Crear `DOCKERHUB_USERNAME` con tu usuario de Docker Hub
5. Crear `DOCKERHUB_TOKEN` con el token generado en hub.docker.com → Account Settings → Personal access tokens

## Pipeline CI/CD

El pipeline se activa automáticamente en cada push a `main` y realiza:

1. **build-and-test** — Compilación con Maven, ejecución de pruebas unitarias y generación de reporte de cobertura JaCoCo (disponible como artefacto descargable)
2. **docker-publish** — Construcción de imagen Docker con multi-stage build y publicación en Docker Hub con tags `latest` y `sha-<commit>`

### Archivo del workflow
`.github/workflows/ci.yml`

### Tags generados en Docker Hub
- `latest` — siempre apunta a la última versión
- `sha-<commit>` — tag inmutable por cada commit

## Ejecutar localmente con Docker

### Requisitos previos
- Docker Desktop instalado y corriendo
- Git instalado

### Pasos para ejecutar

**1. Clonar el repositorio:**
```bash
git clone https://github.com/Alejafarfan/farfan-post2-u12-.git
cd farfan-post2-u12-
```

**2. Construir y levantar los servicios:**
```bash
docker compose up -d --build
```
Esto levanta dos contenedores:
- `productos-api-app-1` — la aplicación Spring Boot en el puerto 8080
- `productos-api-db-1` — PostgreSQL 16

**3. Verificar que la aplicación está corriendo:**
```bash
docker compose ps
```

**4. Probar el health check:**
```
http://localhost:8080/actuator/health
```
Respuesta esperada:
```json
{"status":"UP"}
```

**5. Detener la aplicación:**
```bash
docker compose down
```

### Ejecutar desde Docker Hub
```bash
docker pull alejaf13/productos-api:latest

docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=jdbc:postgresql://host:5432/appdb \
  -e DB_USER=appuser \
  -e DB_PASS=apppass \
  alejaf13/productos-api:latest
```

## Dockerfile multi-stage

El Dockerfile usa dos etapas:
- **Etapa builder:** imagen JDK + Maven para compilar el proyecto
- **Etapa producción:** imagen JRE liviana con usuario no root para ejecutar

Esto reduce el tamaño de la imagen final y elimina herramientas de desarrollo innecesarias en producción.

## Figura 1. Historial de ejecuciones en GitHub Actions — pipeline verde
[]![CI/CD Status](capturas/ci_status.png)
## Figura 2. Artefacto jacoco-report disponible para descarga  
[]![JaCoCo Report](capturas/jacoco_report.png)
## Figura 3. Imagen alejaf13/productos-api publicada en Docker Hub
[]![Docker Hub](capturas/docker_hub.png)
## Figura 4. Tags de la imagen en Docker Hub — latest y sha-xxxxxxx
[]![Docker Tags](capturas/docker_tags.png)
## Figura 5. Página principal del repositorio con badge CI/CD verde
[]![Repo Badge](capturas/repo_badge.png)
