# TaskMaster Frontend Updates

This directory contains the updated Angular source files for the `taskmaster-frontend` repository
to support the new backend features (JWT authentication, priority system, task status tracking).

## How to Apply These Changes

Copy the contents of `src/` from this directory into the `src/` directory of the
`codezenithshashwat/taskmaster-frontend` repository, replacing the existing files.

```bash
# From the taskmaster-frontend repo root:
cp -r <path-to-this>/frontend/src/ ./src/
```

## What Changed

### New Files
- `src/app/services/auth.ts` — Auth service (login, register, JWT token storage in localStorage)
- `src/app/interceptors/auth.interceptor.ts` — HTTP interceptor that adds `Authorization: Bearer <token>` to all requests
- `src/app/guards/auth.guard.ts` — Route guard that redirects unauthenticated users to `/login`
- `src/app/components/login/` — Login page component
- `src/app/components/register/` — Registration page component

### Modified Files
- `src/app/app.config.ts` — Now includes HTTP interceptor for JWT
- `src/app/app.routes.ts` — Routes for `/login`, `/register`, `/tasks` (protected)
- `src/app/app.ts` — Uses RouterOutlet instead of embedding TaskListComponent directly; shows auth-aware navbar
- `src/app/app.html` — Updated navbar with username display and logout button; uses `<router-outlet>`
- `src/app/services/task.ts` — Added TypeScript interfaces for Task/TaskRequest with priority & status; added `getTasksByStatus()` and `getTasksByPriority()` methods
- `src/app/components/task-list/task-list.ts` — Added priority selector, due date input, status filter buttons, color-coded badges
- `src/app/components/task-list/task-list.html` — Redesigned with priority badges, status badges, due date display, overdue indicators, filter bar

## Backend API Endpoints

### Auth (Public)
- `POST /api/v1/auth/register` — `{ username, password, email }` → `{ token, username, email }`
- `POST /api/v1/auth/login` — `{ username, password }` → `{ token, username, email }`

### Tasks (Requires `Authorization: Bearer <token>`)
- `POST /api/v1/tasks` — Create task with `{ title, description, dueDate, completed, priority, status }`
- `GET /api/v1/tasks` — List user's tasks (paginated)
- `GET /api/v1/tasks/{id}` — Get task by ID
- `PUT /api/v1/tasks/{id}` — Update task
- `DELETE /api/v1/tasks/{id}` — Delete task
- `GET /api/v1/tasks/search?keyword=...` — Search tasks
- `GET /api/v1/tasks/status/{status}` — Filter by status (PENDING, IN_PROGRESS, COMPLETED, OVERDUE)
- `GET /api/v1/tasks/priority/{priority}` — Filter by priority (LOW, MEDIUM, HIGH, URGENT)
