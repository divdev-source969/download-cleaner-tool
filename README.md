# 🧹 Downloads Folder Cleaner Bot (Java 21)

A fast, automated desktop utility written in **Java 21** that scans your system `Downloads` folder and instantly organizes loose files into clean, categorized sub-folders based on their extensions.

This repository is designed as a developer source code space and a downloadable, user-friendly automation package.

---

## 📂 Project Directory Structure

The repository and release ZIP folder contain the following 7 files:

- `.gitignore`      - Tells Git to keep temporary build artifacts out of your clean source control branch.
- `Cleaner.java`     - The complete Java 21 source code file using modern file stream handling.
- `Cleaner.class`    - The compiled Java bytecode execution file.
- `cleaner.jar`     - The standalone, executable Java binary archive.
- `RunCleaner.bat`   - The double-clickable Windows desktop shortcut launcher script.
- `readme.txt`      - The simple plain text manual included in the ZIP file for casual users.
- `README.md`       - This documentation homepage page and tutorial file.

---

## 📋 System Requirements

Before running the automated batch shortcut script, ensure your system environment satisfies this single condition:
* **Java Runtime Engine (JRE) or JDK 21 (or higher)** must be installed on your machine.
* To verify if you have it, open Command Prompt, type `java -version`, and press Enter. It must display version 21 or greater.

---

## 🚀 How to Run the App

### Option A: The Double-Click Shortcut (For Users)
If you downloaded the compressed release ZIP:
1. Extract the folder to your computer (e.g., your D: drive).
2. Double-click **`RunCleaner.bat`** on your desktop to run the tool instantly without typing terminal paths.

### Option B: Compiling from Source (For Developers)
If you cloned the source directory files and want to rebuild the application binaries yourself, execute this inside your Command Prompt:
```bash
javac Cleaner.java && jar cfe cleaner.jar Cleaner Cleaner.class
```

---

## 🛑 How `.gitignore` Protects the Repository
The local `.gitignore` is specifically tracking internal execution files to ensure binary logs and hidden OS junk files don't clutter up the raw code view:
```text
*.class
.DS_Store
Thumbs.db
```

---
⭐ Created by a 13-year-old developer. If this automation utility helped keep your workspace cleaner, give it a star!
