#!/usr/bin/env python3
import os
import subprocess
from pathlib import Path

def run(cmd, cwd=None):
    print(f"→ {cmd}")
    subprocess.run(cmd, cwd=cwd, shell=True, check=True)

def build_tint():
    # Path to Tint source directory
    tint_dir = Path("../cmake-build-debug/_deps/tint-src").resolve()

    # Make sure the directory exists
    tint_dir.mkdir(parents=True, exist_ok=True)

    # Initialize and fetch Tint repo
    run("git init", cwd=tint_dir)
    run("git remote remove origin || true", cwd=tint_dir)
    run("git remote add origin https://dawn.googlesource.com/tint", cwd=tint_dir)
    run("git fetch origin main", cwd=tint_dir)
    run("git checkout main", cwd=tint_dir)
    run("git submodule update --init --recursive", cwd=tint_dir)

    print("Tint built at: ", tint_dir)