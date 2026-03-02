#!/bin/bash
# ============================================================================
# apply-to-frontend-repo.sh
# Automatically applies the updated frontend files to your taskmaster-frontend repo.
#
# Usage:
#   ./apply-to-frontend-repo.sh /path/to/taskmaster-frontend
#
# Example:
#   ./apply-to-frontend-repo.sh ~/projects/taskmaster-frontend
# ============================================================================

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if path argument is provided
if [ -z "$1" ]; then
    echo -e "${RED}Error: Please provide the path to your taskmaster-frontend repo.${NC}"
    echo ""
    echo "Usage: ./apply-to-frontend-repo.sh /path/to/taskmaster-frontend"
    echo ""
    echo "Example:"
    echo "  ./apply-to-frontend-repo.sh ~/projects/taskmaster-frontend"
    exit 1
fi

FRONTEND_REPO="$1"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SOURCE_DIR="$SCRIPT_DIR/src"

# Verify the frontend repo exists
if [ ! -d "$FRONTEND_REPO" ]; then
    echo -e "${RED}Error: Directory '$FRONTEND_REPO' does not exist.${NC}"
    echo ""
    echo "Make sure you clone the frontend repo first:"
    echo "  git clone https://github.com/codezenithshashwat/taskmaster-frontend.git"
    exit 1
fi

# Verify it looks like an Angular project
if [ ! -f "$FRONTEND_REPO/angular.json" ]; then
    echo -e "${RED}Error: '$FRONTEND_REPO' does not look like an Angular project (no angular.json found).${NC}"
    exit 1
fi

# Verify source files exist
if [ ! -d "$SOURCE_DIR" ]; then
    echo -e "${RED}Error: Source directory '$SOURCE_DIR' not found.${NC}"
    exit 1
fi

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}  TaskMaster Frontend Update Script${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "Source:      ${YELLOW}$SOURCE_DIR${NC}"
echo -e "Destination: ${YELLOW}$FRONTEND_REPO/src${NC}"
echo ""

# Step 1: Create new directories
echo -e "${YELLOW}Step 1: Creating new directories...${NC}"
mkdir -p "$FRONTEND_REPO/src/app/services"
mkdir -p "$FRONTEND_REPO/src/app/guards"
mkdir -p "$FRONTEND_REPO/src/app/interceptors"
mkdir -p "$FRONTEND_REPO/src/app/components/login"
mkdir -p "$FRONTEND_REPO/src/app/components/register"
mkdir -p "$FRONTEND_REPO/src/app/components/task-list"
mkdir -p "$FRONTEND_REPO/src/environments"
echo -e "${GREEN}  ✓ Directories created${NC}"

# Step 2: Copy all source files
echo -e "${YELLOW}Step 2: Copying source files...${NC}"
cp -v "$SOURCE_DIR/main.ts"                                         "$FRONTEND_REPO/src/main.ts"
cp -v "$SOURCE_DIR/index.html"                                      "$FRONTEND_REPO/src/index.html"
cp -v "$SOURCE_DIR/styles.css"                                      "$FRONTEND_REPO/src/styles.css"
cp -v "$SOURCE_DIR/environments/environment.ts"                     "$FRONTEND_REPO/src/environments/environment.ts"
cp -v "$SOURCE_DIR/environments/environment.prod.ts"                "$FRONTEND_REPO/src/environments/environment.prod.ts"
cp -v "$SOURCE_DIR/app/app.config.ts"                               "$FRONTEND_REPO/src/app/app.config.ts"
cp -v "$SOURCE_DIR/app/app.routes.ts"                               "$FRONTEND_REPO/src/app/app.routes.ts"
cp -v "$SOURCE_DIR/app/app.ts"                                      "$FRONTEND_REPO/src/app/app.ts"
cp -v "$SOURCE_DIR/app/app.html"                                    "$FRONTEND_REPO/src/app/app.html"
cp -v "$SOURCE_DIR/app/guards/auth.guard.ts"                        "$FRONTEND_REPO/src/app/guards/auth.guard.ts"
cp -v "$SOURCE_DIR/app/interceptors/auth.interceptor.ts"            "$FRONTEND_REPO/src/app/interceptors/auth.interceptor.ts"
cp -v "$SOURCE_DIR/app/services/auth.ts"                            "$FRONTEND_REPO/src/app/services/auth.ts"
cp -v "$SOURCE_DIR/app/services/task.ts"                            "$FRONTEND_REPO/src/app/services/task.ts"
cp -v "$SOURCE_DIR/app/components/login/login.ts"                   "$FRONTEND_REPO/src/app/components/login/login.ts"
cp -v "$SOURCE_DIR/app/components/login/login.html"                 "$FRONTEND_REPO/src/app/components/login/login.html"
cp -v "$SOURCE_DIR/app/components/login/login.css"                  "$FRONTEND_REPO/src/app/components/login/login.css"
cp -v "$SOURCE_DIR/app/components/register/register.ts"             "$FRONTEND_REPO/src/app/components/register/register.ts"
cp -v "$SOURCE_DIR/app/components/register/register.html"           "$FRONTEND_REPO/src/app/components/register/register.html"
cp -v "$SOURCE_DIR/app/components/register/register.css"            "$FRONTEND_REPO/src/app/components/register/register.css"
cp -v "$SOURCE_DIR/app/components/task-list/task-list.ts"           "$FRONTEND_REPO/src/app/components/task-list/task-list.ts"
cp -v "$SOURCE_DIR/app/components/task-list/task-list.html"         "$FRONTEND_REPO/src/app/components/task-list/task-list.html"
cp -v "$SOURCE_DIR/app/components/task-list/task-list.css"          "$FRONTEND_REPO/src/app/components/task-list/task-list.css"
echo -e "${GREEN}  ✓ All 22 files copied${NC}"

echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}  ✅ Files applied successfully!${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo -e "Next steps:"
echo -e "  1. cd $FRONTEND_REPO"
echo -e "  2. npm install     (install dependencies)"
echo -e "  3. npm run build   (verify it compiles)"
echo -e "  4. npm start       (test locally at http://localhost:4200)"
echo -e "  5. git add -A && git commit -m 'Add JWT auth, priority, status support'"
echo -e "  6. git push        (push to GitHub → auto-deploys to Vercel)"
echo ""
