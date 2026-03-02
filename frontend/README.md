# TaskMaster Frontend Updates

This directory contains the **complete updated Angular source files** for the
[taskmaster-frontend](https://github.com/codezenithshashwat/taskmaster-frontend) repository,
supporting the new backend features: **JWT authentication**, **task priority**, **task status tracking**, and **due dates**.

---

## 🚀 Automated Sync (GitHub Actions)

A GitHub Actions workflow (`.github/workflows/sync-frontend.yml`) is configured to **automatically sync** these frontend files to the `taskmaster-frontend` repo whenever changes are pushed to `main`.

### One-Time Setup (Required)

1. **Create a Personal Access Token (PAT):**
   - Go to [GitHub Settings → Developer settings → Personal access tokens → Fine-grained tokens](https://github.com/settings/tokens?type=beta)
   - Click **"Generate new token"**
   - Name: `frontend-sync`
   - Repository access: Select **"Only select repositories"** → choose `taskmaster-frontend`
   - Permissions → Repository permissions → **Contents**: Read and write
   - Click **"Generate token"** and copy the token

2. **Add the token as a repository secret:**
   - Go to [taskmaster repo Settings → Secrets and variables → Actions](https://github.com/codezenithshashwat/taskmaster/settings/secrets/actions)
   - Click **"New repository secret"**
   - Name: `FRONTEND_SYNC_TOKEN`
   - Value: paste the token from step 1
   - Click **"Add secret"**

3. **Trigger the initial sync:**
   - Go to [Actions → Sync Frontend to taskmaster-frontend](https://github.com/codezenithshashwat/taskmaster/actions/workflows/sync-frontend.yml)
   - Click **"Run workflow"** → **"Run workflow"**
   - This pushes all frontend files to `taskmaster-frontend`, and Vercel auto-deploys

After this one-time setup, any future changes to `frontend/src/` will automatically sync to the frontend repo on push to `main`.

### ⚠️ Important: Set CORS_ALLOWED_ORIGINS on Render

In your **Render dashboard**, set the environment variable:
```
CORS_ALLOWED_ORIGINS=https://your-app.vercel.app
```
Replace `https://your-app.vercel.app` with your actual Vercel frontend URL. This allows the frontend to make API calls to the backend.

---

## 🔧 Manual Sync (Alternative)

If you prefer to sync manually instead of using GitHub Actions:

```bash
# Clone both repos side by side (skip if already cloned)
git clone https://github.com/codezenithshashwat/taskmaster.git
git clone https://github.com/codezenithshashwat/taskmaster-frontend.git

# Run the automation script
cd taskmaster
chmod +x frontend/apply-to-frontend-repo.sh
./frontend/apply-to-frontend-repo.sh ../taskmaster-frontend

# Build, test, and push
cd ../taskmaster-frontend
npm install
npm run build
npm start          # Verify at http://localhost:4200 that login page appears
git add -A
git commit -m "Add JWT auth, priority system, status tracking, due dates"
git push origin main
```

---

## 📦 Deployment Flow

### Frontend (Vercel) — ✅ Auto-deploys on push to `taskmaster-frontend`
- Vercel detects the push → runs `ng build --configuration production` → deploys
- Production build uses `environment.prod.ts` (points to Render backend URL)

### Backend (Render) — ✅ Auto-deploys on push to `taskmaster` main
- Render detects the push → builds using `Dockerfile` → deploys

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
