# Chess64

## Intro
Chess 64 is a monolithic fullstack chess application written in **Java** using **Spring Boot Data JPA**, **Spring Boot Web**, **Spring Boot Websocket** and **Typescript** using **Vite**, **React** and **Tailwind**, implementing **websocket** and **http** technologies. The database is **H2** for development purposes. Users enter their name and get paired with a random opponent. Then they play a chess game against each other. After the game is over, the user can convert it to *Portable Game Notation (PGN)* format.

⚠️ The project is work in progress and currently in a very early phase. ⚠️

## Usage
To run the frontend: 
```
cd frontend
npm install
npm run dev
```

To run the backend:
```
// Run it with IDE, or:

cd backend
mvn clean install -DskipTests
java -jar target/backend-chess64-0.0.1-SNAPSHOT.jar
```

## Demo
Two players paired against each other:
![screenshot of two screens, where the players are paired against each other](https://i.imgur.com/1s6tVCr.png)

## Features

### Implemented
- Rendered chessboard with fonts as pieces.
- Players and Game Rooms get created.
- The backend pairs players and assigns white or black randomly. The pairing is communicated to clients via websockets.

### Planned
- Improve pairing.
- Players can make moves (with websockets).
- The backend generates a list of valid moves for each new position.
- The backend can determine the result.
- Implement resignation. Handle quitting as resignation.
- After finishing a game, it can be converted to PGN format by the user.
- The frontend logs each move and it is possible to navigate in the history of the game.
