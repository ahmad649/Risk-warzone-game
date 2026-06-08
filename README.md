# Risk — Turn-Based Strategy Game

> A Java implementation of the classic Risk board game with a turn-based command interface, modular game engine, and a custom map editor with graph-validation. Built by a team of 5 over a 4-month academic project at Concordia University.

![Java](https://img.shields.io/badge/java-100%25-blue) ![Build](https://img.shields.io/badge/builds-3%20releases-green) ![License](https://img.shields.io/badge/license-MIT-blue)

---

## Overview

This project is a faithful, extensible Java implementation of the Risk-style strategy game. Players take turns reinforcing, attacking, and fortifying territories across a configurable world map, with a turn engine that enforces the classic Risk rule set and a command parser that lets players issue moves through a clean text interface.

The project was developed iteratively across three release builds, with full architectural documentation, JUnit tests, and a CI pipeline running on GitHub Actions.


## Features

- **Turn-based game engine** enforcing the canonical Risk rules (reinforcement, attack, fortify phases)
- **Command parser** for text-based interaction with the game state
- **Map editor** with on-the-fly validation — uses depth-first search to confirm that every territory graph is fully connected before it can be loaded into the engine
- **Multiple themed maps** included:
  - Custom World
  - Game of Thrones
  - The Witcher
- **CI/CD** pipeline via GitHub Actions
- **3 release builds** tagged across the project lifecycle, demonstrating iterative delivery

## Tech Stack

`Java` · `Maven` · `JUnit` · `GitHub Actions (CI/CD)` · Standard OOP design patterns

## Repository Structure

```
.
├── src/                          # Main Java source code
│   ├── (game engine, command parser, map editor, etc.)
├── lib/                          # Project libraries
├── docs/                         # Architectural documentation
├── logs/                         # Game session logs
├── .github/workflows/            # CI/CD pipeline
├── pom.xml                       # Maven build
├── LICENSE                       # MIT
└── README.md
```

## Map File Format

Each map file (e.g. `custom_world.txt`) follows a structured format containing:

- **Map information** — author, image reference, etc.
- **Continents** — continent names with reinforcement bonuses
- **Territories** — territory definitions including positions and adjacency edges (defining the graph used for movement and connectivity validation)

## How to Run

```bash
# Clone the repo
git clone https://github.com/ahmad649/Risk-warzone-game.git
cd Risk-warzone-game

# Build with Maven
mvn clean install

# Run the game
mvn exec:java
```

Or grab a pre-built JAR from the [Releases](https://github.com/ahmad649/Risk-warzone-game/releases) page.

## Releases

| Version | Date | Highlights |
|---|---|---|
| **Build 3.0.0** | Final release with full feature set, themed maps, polished CLI |
| Build 2 | Extended game engine, additional commands |
| Build 1 | Initial playable prototype |


## License

MIT
