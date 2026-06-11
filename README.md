# AstroTalk — Astrology Consultation Platform

Connect with professional astrologers via chat, voice, and video calls.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Spring Boot 3.2.5, Java 17 |
| Frontend | React 19, Vite 8, Tailwind CSS 4 |
| Database | PostgreSQL 15 |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security 6 + JWT |
| Real-time | STOMP WebSocket + SockJS |
| Payments | Razorpay |
| API Docs | SpringDoc OpenAPI |

## Project Structure

```
astroTalk-backend/   Spring Boot REST API (port 8080)
frontend/            React + Vite client (port 5173)
```

## Quick Start

### Backend

```bash
cd astroTalk-backend

# Option A: Development (H2 in-memory DB, no PostgreSQL needed)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Option B: Production (PostgreSQL required)
# Ensure PostgreSQL has database 'astrotalk' created
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

- Backend: http://localhost:8080
- Frontend: http://localhost:5173
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console (dev): http://localhost:8080/h2-console

---

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/register/astrologer` | Register a new astrologer |
| POST | `/api/auth/login` | Login and get JWT token |

### Astrologers
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/astrologers` | Search/filter astrologers |
| GET | `/api/astrologers/top` | Top 6 by rating |
| GET | `/api/astrologers/{id}` | Get profile |
| GET | `/api/astrologers/{id}/availability` | Check online status |
| PUT | `/api/astrologers/profile` | Update own profile |
| PUT | `/api/astrologers/status/toggle` | Toggle online/offline |

### Consultations
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/consultations/start` | Start a consultation |
| POST | `/api/consultations/end/{id}` | End + final billing |
| GET | `/api/consultations/active` | Get active consultation |
| GET | `/api/consultations/history` | Consultation history |

### Wallet & Payments
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/wallet/balance` | Get balance |
| GET | `/api/wallet/transactions` | Transaction history |
| POST | `/api/wallet/add` | Add money |
| POST | `/api/payments/create-order` | Create Razorpay order |
| POST | `/api/payments/verify` | Verify payment |

### Chat
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/chat/{id}/messages` | Message history |
| POST | `/api/chat/{id}/read` | Mark messages as read |
| GET | `/api/chat/{id}/unread-count` | Unread count |

### Reviews
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/reviews` | Submit a review |
| GET | `/api/reviews/astrologer/{id}` | Astrologer reviews |

### Notifications
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/notifications` | Get notifications |
| PUT | `/api/notifications/{id}/read` | Mark as read |
| PUT | `/api/notifications/read-all` | Mark all as read |

### Admin
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/dashboard` | Dashboard stats |
| GET | `/api/admin/users` | List users |
| GET | `/api/admin/astrologers` | List astrologers |
| PUT | `/api/admin/astrologers/{id}/verify` | Verify astrologer |
| PUT | `/api/admin/astrologers/{id}/reject` | Reject astrologer |
| GET | `/api/admin/revenue` | Revenue report |
| POST | `/api/admin/wallet/adjust` | Adjust wallet |

## WebSocket Topics

| Topic | Purpose |
|-------|---------|
| `/ws` | STOMP endpoint (SockJS) |
| `/topic/consultation/{id}` | Live chat messages |
| `/topic/consultation/{id}/signal` | WebRTC signaling |
| `/topic/consultation/{id}/typing` | Typing indicators |
| `/topic/user/{id}/call` | Incoming call invites |
| `/topic/user/{id}/notifications` | Push notifications |

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_URL` | `jdbc:postgresql://localhost:5432/astrotalk` | Database URL |
| `DB_USER` | `postgres` | Database user |
| `DB_PASS` | `postgres` | Database password |
| `JWT_SECRET` | (embedded default) | JWT signing key |
| `JWT_EXPIRATION` | `86400000` | JWT expiry (24h) |
| `RAZORPAY_KEY_ID` | — | Razorpay key |
| `RAZORPAY_KEY_SECRET` | — | Razorpay secret |
