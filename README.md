# 🧹 Downloads Folder Clean-Up Bot

A fast, automated script written in **Python 3.12** that automatically scans your cluttered `Downloads` folder and neatly organizes files into sub-folders based on their extensions.

## ✨ Features
- Fully automatic: detects your operating system's standard Downloads directory.
- No external libraries required (uses built-in `pathlib` and `shutil`).
- Categorizes files into: `Images`, `Documents`, `Installers`, `Archives`, `Code`, and `Audio/Video`.
- Safe: Appends `copy_` to filenames if duplicates exist to prevent deleting data.

## 🚀 How to Run

1. Make sure you have [Python 3.12+](https://python.org) installed.
2. Clone this repository:
   ```bash
   git clone https://github.com
   ```
3. Run the script:
   ```bash
   python cleaner.py
   ```
"# downloads-cleaner-bot" 
