🏦 Banking Ledger API – Spring Boot + PostgreSQL (Relational Data Model)

Production-grade reference implementation for Part 1 of the series: 5 Data Models, 5 Use Cases, 5 Vendors.

Use case: Banking ledger with ACID guarantees, transactional safety, and reporting-friendly schema.
Stack: Spring Boot, PostgreSQL, Flyway migrations, Docker Compose.

🔧 Features

Relational-first modeling with normalized schema (accounts, transactions, balances).

Strong ACID guarantees (transactions, rollbacks, constraints).

Clean Spring Boot project structure (REST controllers, services, repositories).

Flyway migrations for schema evolution.

Docker Compose with PostgreSQL + pgAdmin UI.

Health checks and actuator endpoints.

🚀 Quickstart
cp .env.example .env
# tweak DB credentials/ports if needed

docker compose up --build -d
# API → http://localhost:8080/swagger-ui.html
# pgAdmin → http://localhost:5050


Seed the DB (optional):

docker exec -it postgres psql -U ledger_user -d ledger_db \
  -c "\i seed/seed.sql"

🧱 API Endpoints

Health
GET /actuator/health – liveness probe

Accounts
POST /accounts – create account
GET /accounts/{id} – fetch account details
GET /accounts – list accounts

Transactions
POST /transactions – create debit/credit transaction (ACID-safe)
GET /transactions/{id} – fetch transaction
GET /transactions?accountId=... – list transactions by account

Balance
GET /accounts/{id}/balance – current balance

🗄️ Schema & Indexes

Created via Flyway migrations on startup:

Tables

accounts(id, name, created_at)

transactions(id, account_id, amount, type, created_at)

Indexes

PK on accounts.id

FK + index on transactions.account_id

B-Tree index on transactions.created_at (for statements/queries)

🧪 Local Smoke Tests
./mvnw test

🏗️ Design Notes (Why Relational Model Here?)

Ledger requires strict consistency, durability, and transaction safety.

PostgreSQL provides ACID compliance, ensuring balances always match.

Normalized schema supports auditing, compliance, and regulatory reporting.

Easy to enforce constraints (e.g., no negative balances).

👉 When NOT to use RDBMS here:

Extremely flexible/variable attributes (better with document DB).

Real-time recommendation graphs (better with graph DB).

High-velocity sensor/IoT inserts (better with time-series DB).

🔒 Production Tips

Enable SSL/TLS for Postgres connections.

Configure connection pooling (HikariCP defaults).

Add rate limiting and strong input validation on public APIs.

Use logical replication for read scaling or analytics offload.

Set up backups + PITR (point-in-time recovery).

🧭 Next Steps in the Series

Part 1: Relational – Banking Ledger (PostgreSQL) ← this repo

Part 2: Document – E-commerce Catalog (MongoDB)

Part 3: Graph – Recommendations & Fraud Rings (Neo4j)

Part 4: Time-Series – Metrics/IoT (InfluxDB)

Part 5: Key-Value – Caching & Sessions (Redis)
