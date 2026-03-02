# TaskMaster Frontend Updates

This directory contains the **complete updated Angular source files** for the
[taskmaster-frontend](https://github.com/codezenithshashwat/taskmaster-frontend) repository,
supporting the new backend features: **JWT authentication**, **task priority**, **task status tracking**, and **due dates**.

> **Why is this here?** Copilot can only push to the repo it's working on (`taskmaster` backend).
> These files need to be applied to the separate `taskmaster-frontend` repo. Follow the steps below.

---

## 🚀 How to Apply These Changes (Step-by-Step)

### Option A: Automated Script (Recommended)

**Prerequisites:** Git and a terminal (macOS/Linux/WSL).

```bash
# Step 1: Clone both repos side by side (skip if already cloned)
git clone https://github.com/codezenithshashwat/taskmaster.git
git clone https://github.com/codezenithshashwat/taskmaster-frontend.git

# Step 2: Switch to the backend branch that has the frontend files
cd taskmaster
git checkout copilot/enhance-taskmaster-features

# Step 3: Run the automation script
chmod +x frontend/apply-to-frontend-repo.sh
./frontend/apply-to-frontend-repo.sh ../taskmaster-frontend

# Step 4: Go to the frontend repo, build, test, and push
cd ../taskmaster-frontend
npm install
npm run build
npm start          # Opens http://localhost:4200 — verify login/register/tasks work
git add -A
git commit -m "Add JWT auth, priority system, status tracking, due dates"
git push origin main
```

### Option B: Manual Copy (Windows or if script doesn't work)

```bash
# Step 1: Clone both repos (skip if already cloned)
git clone https://github.com/codezenithshashwat/taskmaster.git
git clone https://github.com/codezenithshashwat/taskmaster-frontend.git

# Step 2: Switch to the backend branch with frontend files
cd taskmaster
git checkout copilot/enhance-taskmaster-features

# Step 3: Copy the entire src/ folder over to the frontend repo
# On macOS/Linux:
cp -r frontend/src/* ../taskmaster-frontend/src/

# On Windows (PowerShell):
# Copy-Item -Recurse -Force frontend\src\* ..\taskmaster-frontend\src\

# Step 4: Go to frontend repo, install, build, test, push
cd ../taskmaster-frontend
npm install
npm run build
npm start
# Verify at http://localhost:4200 that login page appears
git add -A
git commit -m "Add JWT auth, priority system, status tracking, due dates"
git push origin main
```

---

## 📦 Will Changes Auto-Deploy?

### Frontend (Vercel) — ✅ YES, automatically
Your `taskmaster-frontend` is deployed on **Vercel**. Once you `git push` to the `main` branch:
- Vercel detects the push automatically
- Runs `npm run build` (production build with `environment.prod.ts`)
- Deploys the new version within 1-2 minutes
- **No manual action needed** — just push and wait

### Backend (Render) — ✅ YES, automatically (after merging this PR)
Your `taskmaster` backend is deployed on **Render**. Once this PR is merged to `main`:
- Render detects the push and auto-builds using the `Dockerfile`
- Deploys the new backend with JWT auth, priority, and status endpoints
- **No manual action needed** — just merge the PR

### ⚠️ Important: Deploy Backend FIRST
1. **First:** Merge this PR (backend) → Render auto-deploys the backend with auth endpoints
2. **Then:** Push the frontend changes → Vercel auto-deploys the frontend with login/register

If you deploy the frontend first, users will see a login page but the auth endpoints won't exist yet.

---

## 📋 What's New in the Frontend

### New Files (6)
| File | Purpose |
|------|---------|
| `src/app/services/auth.ts` | Auth service — login, register, JWT token management |
| `src/app/interceptors/auth.interceptor.ts` | HTTP interceptor — adds `Authorization: Bearer <token>` header |
| `src/app/guards/auth.guard.ts` | Route guard — redirects unauthenticated users to `/login` |
| `src/app/components/login/login.{ts,html,css}` | Login page with form and error handling |
| `src/app/components/register/register.{ts,html,css}` | Registration page with validation |

### Modified Files (7)
| File | What Changed |
|------|-------------|
| `src/app/app.config.ts` | Added JWT interceptor via `withInterceptors()` |
| `src/app/app.routes.ts` | Routes: `/login`, `/register`, `/tasks` (guarded) |
| `src/app/app.ts` | Auth-aware component with `RouterOutlet`, logout |
| `src/app/app.html` | Navbar with username display and logout button |
| `src/app/services/task.ts` | Typed Task/TaskRequest interfaces, priority & status filter methods |
| `src/app/components/task-list/task-list.ts` | Priority selector, due date, status filters |
| `src/app/components/task-list/task-list.html` | Priority/status badges, overdue indicators, filter bar |

### Files NOT Changed (kept as-is from the frontend repo)
- `package.json` — no new dependencies needed
- `angular.json` — build config stays the same
- `tsconfig.json` — TypeScript config stays the same
- `vercel.json` — deployment config stays the same

---

## 🔌 Backend API Endpoints

### Auth (Public — no token required)
| Method | Endpoint | Body | Response |
|--------|----------|------|----------|
| POST | `/api/v1/auth/register` | `{ username, password, email }` | `{ token, username, email }` |
| POST | `/api/v1/auth/login` | `{ username, password }` | `{ token, username, email }` |

### Tasks (Protected — requires `Authorization: Bearer <token>`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/tasks` | Create task: `{ title, description, dueDate, completed, priority, status }` |
| GET | `/api/v1/tasks` | List user's tasks (paginated) |
| GET | `/api/v1/tasks/{id}` | Get task by ID |
| PUT | `/api/v1/tasks/{id}` | Update task |
| DELETE | `/api/v1/tasks/{id}` | Delete task |
| GET | `/api/v1/tasks/search?keyword=...` | Search by title/description |
| GET | `/api/v1/tasks/status/{status}` | Filter: PENDING, IN_PROGRESS, COMPLETED, OVERDUE |
| GET | `/api/v1/tasks/priority/{priority}` | Filter: LOW, MEDIUM, HIGH, URGENT |
