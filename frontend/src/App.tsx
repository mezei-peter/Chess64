import PlayArea from "./components/PlayArea";
import PlayForm from "./components/PlayForm";
import {useEffect, useState} from "react";
import PairingLoader from "./components/PairingLoader";
import GameRoom from "./types/gameRoom";

async function createUser(name: string): Promise<string> {
    const res = await fetch(`/api/player/${name}`, {method: "POST"});
    return await res.text();
}


async function deleteUser(playerId: string) {
    fetch("/api/player/delete/" + playerId, {method: "DELETE"})
        .then(() => console.log("player deleted"))
        .catch(() => console.error("Unable to delete player: " + playerId));
}

function App() {
    const [freshStart, setFreshStart] = useState(true);
    const [waiting, setWaiting] = useState(false);
    const [playing, setPlaying] = useState(false);
    const [playerName, setPlayerName] = useState("");
    const [playerId, setPlayerId] = useState(localStorage.getItem("playerId"));
    const [isWhite, setIsWhite] = useState(true);
    const [opponentName, setOpponentName] = useState("");
    const [room, setRoom] = useState<GameRoom | null>(null);

    useEffect(() => {
        const roomId = localStorage.getItem("roomId");
        if (roomId) {
            fetch("/api/room/" + roomId)
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                })
                .then((data: GameRoom) => {
                    setRoom(data);
                });
        }
    }, []);

    useEffect(() => {
        if (playerId) {
            window.localStorage.setItem("playerId", playerId);
            if (!room) {
                setFreshStart(false);
                setPlaying(false);
                setWaiting(true);
            }
        }
    }, [playerId]);

    useEffect(() => {
        if (room) {
            window.localStorage.setItem("roomId", room.roomId);
            startPlaying(room);
        }
    }, [room]);

    function startPlaying(gameRoom: GameRoom) {
        if (room !== gameRoom) {
            setRoom(gameRoom);
        }
        const white: boolean = gameRoom.whitePlayer.playerId === playerId;
        setIsWhite(white);
        setOpponentName(white ? gameRoom.blackPlayer.name : gameRoom.whitePlayer.name);
        setPlayerName(!white ? gameRoom.blackPlayer.name : gameRoom.whitePlayer.name);
        setFreshStart(false);
        setWaiting(false);
        setPlaying(true);
    }

    async function startPairing() {
        const id = await createUser(playerName);
        setPlayerId(id);
        setFreshStart(false);
        setPlaying(false);
        setWaiting(true);
    }

    async function quitPlaying() {
        if (waiting) {
            await deleteUser(playerId as string);
        }
        setPlaying(false);
        setWaiting(false);
        setPlayerId("");
        setRoom(null);
        window.localStorage.setItem("playerId", "");
        window.localStorage.setItem("roomId", "");
        setFreshStart(true);
    }

    return (
        <div className={"flex flex-col justify-between h-screen w-screen"}>
            <header className={"text-center text-5xl text-gray-800"}>Chess64</header>
            {freshStart && <PlayForm playerName={playerName} setPlayerName={setPlayerName}
                                     handleSubmit={startPairing}/>}
            {waiting && <PairingLoader playerId={playerId as string} handleGameRoom={startPlaying}
                                       handleCancel={quitPlaying}/>}
            {playing && <PlayArea isWhite={isWhite} opponentName={opponentName} playerName={playerName}
                                  handleQuit={quitPlaying} room={room as GameRoom} playerId={playerId as string}/>}
            <footer className={""}>🄯 2023 MPB. All rights reversed.</footer>
        </div>
    )
}

export default App
