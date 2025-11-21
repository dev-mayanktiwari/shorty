#!/bin/bash
#
# Setup script to install Git hooks
# This script copies the hook templates from hooks/ to .git/hooks/
#

set -e

# Detect shell and set compatibility
if [ -n "$ZSH_VERSION" ]; then
    # zsh
    setopt shwordsplit
fi

# Colors for output (using printf for better compatibility)
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Get the project root directory (where this script is located)
# Works in both bash and zsh
if [ -n "$ZSH_VERSION" ]; then
    # zsh
    SCRIPT_DIR="$(cd "$(dirname "${(%):-%x}")" && pwd)"
else
    # bash
    SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
fi
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
HOOKS_SOURCE_DIR="$PROJECT_ROOT/hooks"
GIT_HOOKS_DIR="$PROJECT_ROOT/.git/hooks"

printf "${BLUE}========================================${NC}\n"
printf "${BLUE}  Git Hooks Setup${NC}\n"
printf "${BLUE}========================================${NC}\n"
printf "\n"

# Check if .git directory exists
if [ ! -d "$PROJECT_ROOT/.git" ]; then
    printf "${RED}[ERROR]${NC} .git directory not found!\n"
    printf "${YELLOW}This script must be run from a Git repository root.${NC}\n"
    exit 1
fi

# Check if hooks source directory exists
if [ ! -d "$HOOKS_SOURCE_DIR" ]; then
    printf "${RED}[ERROR]${NC} Hooks source directory not found: $HOOKS_SOURCE_DIR\n"
    exit 1
fi

# Create .git/hooks directory if it doesn't exist
if [ ! -d "$GIT_HOOKS_DIR" ]; then
    printf "${YELLOW}Creating .git/hooks directory...${NC}\n"
    mkdir -p "$GIT_HOOKS_DIR"
fi

# List of hooks to install
hooks=("pre-commit" "commit-msg" "pre-push")

printf "${YELLOW}Installing Git hooks...${NC}\n"
printf "\n"

# Install each hook
for hook in "${hooks[@]}"; do
    hook_source="$HOOKS_SOURCE_DIR/$hook"
    hook_dest="$GIT_HOOKS_DIR/$hook"
    
    if [ ! -f "$hook_source" ]; then
        printf "${RED}[ERROR]${NC} Hook template not found: $hook_source\n"
        exit 1
    fi
    
    # Copy the hook
    cp "$hook_source" "$hook_dest"
    
    # Make it executable
    chmod +x "$hook_dest"
    
    printf "${GREEN}[OK]${NC} Installed: $hook\n"
done

printf "\n"
printf "${GREEN}========================================${NC}\n"
printf "${GREEN}  All hooks installed successfully!${NC}\n"
printf "${GREEN}========================================${NC}\n"
printf "\n"
printf "${BLUE}Installed hooks:${NC}\n"
printf "  ${GREEN}pre-commit${NC}  - Checks code formatting with Spotless\n"
printf "  ${GREEN}commit-msg${NC}   - Validates conventional commit format\n"
printf "  ${GREEN}pre-push${NC}     - Runs build check before pushing\n"
printf "\n"
printf "${YELLOW}Note: Hooks will run automatically on commit/push.${NC}\n"

