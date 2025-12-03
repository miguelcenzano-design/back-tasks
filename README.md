# Tasks API - Sistema de Gestión de Tareas

API RESTful desarrollada con Spring Boot para la gestión de tareas, permitiendo crear, listar, actualizar y eliminar tareas con diferentes estados.

## 📋 Características

- ✅ CRUD completo de tareas
- 🔒 Validación de nombres únicos
- 📊 Estados de tareas (Pendiente, En Proceso, Completado)
- ⏰ Timestamps automáticos (creación y actualización)
- 🛡️ Manejo global de excepciones
- ✔️ Validaciones de entrada con Bean Validation
- 🔄 Actualización parcial de tareas (PATCH)

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.5.8**
- **Spring Data JPA**
- **MySQL** - Base de datos
- **Maven** - Gestión de dependencias
- **Bean Validation** - Validación de datos
- **Spring Dotenv** - Gestión de variables de entorno

## 📦 Dependencias Principales

```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Environment Variables -->
    <dependency>
        <groupId>me.paulschwarz</groupId>
        <artifactId>spring-dotenv</artifactId>
        <version>3.0.0</version>
    </dependency>
</dependencies>
```

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+

### Configuración de Base de Datos

1. Crea una base de datos MySQL:
```sql
CREATE DATABASE tasks_db;
```

2. Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:
```env
DB_URL=jdbc:mysql://localhost:3306/tasks_db
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
```

### Instalación

1. Clona el repositorio:
```bash
git clone <url-del-repositorio>
cd back-tasks
```

2. Instala las dependencias:
```bash
./mvnw clean install
```

3. Ejecuta la aplicación:
```bash
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`

## 📚 API Endpoints

### Base URL
```
http://localhost:8080/api/tasks
```

### Endpoints Disponibles

#### 1. Listar todas las tareas
```http
GET /api/tasks
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Implementar autenticación",
    "description": "Agregar JWT al sistema",
    "status": "PENDING",
    "createdAt": "2025-12-03T10:30:00",
    "updatedAt": null
  }
]
```

#### 2. Obtener una tarea por ID
```http
GET /api/tasks/{id}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "name": "Implementar autenticación",
  "description": "Agregar JWT al sistema",
  "status": "PENDING",
  "createdAt": "2025-12-03T10:30:00",
  "updatedAt": null
}
```

**Error (404 Not Found):**
```json
{
  "message": "Tarea con ID 1 no encontrada"
}
```

#### 3. Crear una nueva tarea
```http
POST /api/tasks
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Implementar autenticación",
  "description": "Agregar JWT al sistema",
  "status": "PENDING"
}
```

**Respuesta exitosa (201 Created):**
```json
{
  "id": 1,
  "name": "Implementar autenticación",
  "description": "Agregar JWT al sistema",
  "status": "PENDING",
  "createdAt": "2025-12-03T10:30:00",
  "updatedAt": null
}
```

**Error - Nombre duplicado (409 Conflict):**
```json
{
  "message": "Ya existe una tarea con el nombre: Implementar autenticación"
}
```

**Error - Validación (400 Bad Request):**
```json
{
  "name": "El nombre debe tener entre 3 y 100 caracteres"
}
```

#### 4. Actualizar parcialmente una tarea
```http
PATCH /api/tasks/{id}
Content-Type: application/json
```

**Body (campos opcionales):**
```json
{
  "status": "IN_PROGRESS"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "name": "Implementar autenticación",
  "description": "Agregar JWT al sistema",
  "status": "IN_PROGRESS",
  "createdAt": "2025-12-03T10:30:00",
  "updatedAt": "2025-12-03T11:00:00"
}
```

#### 5. Eliminar una tarea
```http
DELETE /api/tasks/{id}
```

**Respuesta exitosa (204 No Content)**

## 📊 Modelo de Datos

### Entidad Task

| Campo | Tipo | Descripción | Restricciones |
|-------|------|-------------|---------------|
| `id` | Long | Identificador único | Auto-generado |
| `name` | String | Nombre de la tarea | Único, 3-100 caracteres, no nulo |
| `description` | String | Descripción detallada | Opcional, tipo TEXT |
| `status` | TaskStatus | Estado de la tarea | Enum: PENDING, IN_PROGRESS, COMPLETED |
| `createdAt` | LocalDateTime | Fecha de creación | Auto-generado, no actualizable |
| `updatedAt` | LocalDateTime | Fecha de última actualización | Auto-actualizado |

### Estados de Tarea (TaskStatus)

```java
public enum TaskStatus {
    PENDING,      // Pendiente
    IN_PROGRESS,  // En proceso
    COMPLETED     // Completado
}
```

## 🏗️ Estructura del Proyecto

```
src/main/java/com/miguel/tasks/
├── controllers/
│   └── TaskController.java          # Endpoints REST
├── entities/
│   ├── Task.java                     # Entidad JPA
│   └── TaskStatus.java               # Enum de estados
├── exceptions/
│   ├── DuplicateTaskNameException.java
│   ├── TaskNotFoundException.java
│   └── GlobalExceptionHandler.java   # Manejo global de errores
├── repositories/
│   └── TaskRepository.java           # Interfaz JPA Repository
├── services/
│   └── TaskService.java              # Lógica de negocio
└── TasksApplication.java             # Clase principal
```

## 🔒 Validaciones

### Validaciones de Entrada
- **Nombre**: 
  - Mínimo 3 caracteres
  - Máximo 100 caracteres
  - No puede ser nulo
  - Debe ser único en la base de datos

### Validaciones de Negocio
- No se permiten nombres duplicados
- Solo se pueden actualizar los campos: `name`, `description`, `status`
- La tarea debe existir para actualizar o eliminar

## 🛡️ Manejo de Errores

La API implementa un manejo global de excepciones que retorna respuestas consistentes:

| Código | Excepción | Descripción |
|--------|-----------|-------------|
| 400 | MethodArgumentNotValidException | Error de validación de entrada |
| 404 | TaskNotFoundException | Tarea no encontrada |
| 409 | DuplicateTaskNameException | Nombre de tarea duplicado |
| 500 | Exception | Error interno del servidor |

## 🧪 Testing

Ejecutar tests:
```bash
./mvnw test
```

## 📝 Ejemplos de Uso

### Crear una tarea
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Revisar código",
    "description": "Code review del PR #123",
    "status": "PENDING"
  }'
```

### Actualizar estado de una tarea
```bash
curl -X PATCH http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED"
  }'
```

### Eliminar una tarea
```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

## 🔄 Características Técnicas

### Actualización Automática de Timestamps
- `createdAt`: Se establece automáticamente al crear la tarea (`@PrePersist`)
- `updatedAt`: Se actualiza automáticamente al modificar la tarea (`@PreUpdate`)

### Actualización Parcial (PATCH)
El endpoint PATCH permite actualizar solo los campos necesarios sin enviar toda la entidad.

### Validación de Unicidad
Se implementa validación a nivel de base de datos (constraint) y a nivel de aplicación (service layer).

## 📄 Licencia

Este proyecto es de código abierto.

## 👤 Autor

Miguel Cenzano

---

**Nota**: Este proyecto es una demostración de una API REST con Spring Boot para gestión de tareas.
