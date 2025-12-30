# Platform Backend - Game Platform API

A Domain-Driven Design microservices backend built with **Spring Boot 3**, featuring two bounded contexts with event-driven architecture, CQRS pattern, and centralized read model. This project serves as a game platform with player management, achievements, lobbies, and friendships.

## Architecture Overview

### Design Patterns
- **Hexagonal Architecture**: Clear separation between domain, application, and infrastructure layers
- **Domain-Driven Design (DDD)**: Two autonomous bounded contexts with independent databases
- **CQRS**: Write and read operations separated into distinct models
- **Event-Driven Communication**: RabbitMQ for asynchronous inter-service messaging
- **Event Sourcing**: Domain events tracked in aggregate roots

### Bounded Contexts

#### 1. **Content Context** (Write Model)
Manages game creation, platform achievements, and player statistics.

**Aggregates:**
- `PlatformAchievement`: Global achievements with configurable unlock criteria (score-based, win-based, etc.)
- `Game`: Game entities with review states and player statistics tracking
- `PlayerStatistics`: In-game performance metrics per player

**Key Features:**
- Achievement type-based evaluation (`PlatformAchievementType`)
- Game review state transitions
- Player statistics projection for content queries

#### 2. **Player Context** (Write Model)
Manages player profiles, friendships, and lobby matchmaking.

**Aggregates:**
- `Player`: Player profile with unlocked achievements, favorite game tracking, and profile customization
- `Friendship`: Bidirectional friendship relationships with state management (REQUESTED → FRIENDS/DECLINED)
- `Lobby`: Matchmaking lobbies supporting three modes:
    - **Strangers**: Both players must accept to start
    - **Friends**: Immediate start with known players
    - **AI**: Single-player against AI opponent

**Key Features:**
- Achievement unlock tracking (both platform and game-specific)
- Friendship state transitions with proper validation
- GameReference projections for cross-context lookups


### Read Model (Query Layer)
Centralized denormalized read database (`read_model_schema`) for optimized GET queries:
- `PlayerModel`: Complete player statistics and metadata
- `GameModel`: Game information and review state
- `FriendshipModel`: Friendship data with player details
- `LobbyModel`: Lobby state and participant information
- `AchievementModel` / `PlatformAchievementModel`: Achievement data

**Projection Strategy:**
Domain events trigger event handlers that update read models asynchronously, ensuring consistency without blocking write operations.

## Technology Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Persistence | JPA/Hibernate + PostgreSQL |
| Message Queue | RabbitMQ |
| Authentication | Keycloak (OAuth2/JWT) |
| Build Tool | Gradle |
| Containerization | Docker |

## API Security

All endpoints are protected via **Keycloak JWT** except:
- `GET /games` - Public game browsing
- `POST /games` - Public game creation

**Authentication**: Include JWT token in `Authorization: Bearer <token>` header.

**CORS**: Enabled for all origins, methods, and headers (configurable in `SecurityConfig`).

## Message Queue Architecture

### RabbitMQ Topology

**Exchanges & Queues:**
- `platform.achievements`: Achievement-related events
- `platform.friendships`: Friendship state changes
- `platform.players`: Player profile updates
- `platform.games`: Game lifecycle events
- `platform.lobbies`: Lobby creation and state transitions

**Message Format**: JSON via `Jackson2JsonMessageConverter`

**Listeners**: Event handlers consume messages and update the read model with `@RabbitListener`.