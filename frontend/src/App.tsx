import PlayArea from "./components/PlayArea";
import PlayForm from "./components/PlayForm";
import {MutableRefObject, useEffect, useRef, useState} from "react";
import PairingLoader from "./components/PairingLoader";
import GameRoom from "./types/gameRoom";

async function createUser(name: string): Promise<string> {
    const res = await fetch(`/api/player/${name}`, {method: "POST"});
    return await res.text();
}


function App() {
    const [freshStart, setFreshStart] = useState(true);
    const [waiting, setWaiting] = useState(false);
    const [playing, setPlaying] = useState(false);
    const [playerName, setPlayerName] = useState("");
    const [playerId, setPlayerId] = useState(localStorage.getItem("playerId"));
    const [isWhite, setIsWhite] = useState(true);
    const [opponentName, setOpponentName] = useState("");
    const room = useRef<GameRoom>(null) as MutableRefObject<GameRoom>;

    useEffect(() => {
        const roomId = localStorage.getItem("roomId");
        if (roomId) {
            fetch("/api/room/" + roomId)
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                })
                .then((data: GameRoom) => room.current = data);
        }
    }, []);

    useEffect(() => {
        if (playerId) {
            window.localStorage.setItem("playerId", playerId);
        }
    }, [playerId]);

    useEffect(() => {
        if (room.current) {
            window.localStorage.setItem("roomId", room.current.roomId);
        }
    }, [room.current]);

    function startPlaying(gameRoom: GameRoom) {
        room.current = gameRoom;
        const white: boolean = gameRoom.whitePlayer.playerId === playerId;
        setIsWhite(white);
        setOpponentName(white ? gameRoom.blackPlayer.name : gameRoom.whitePlayer.name);
        setWaiting(false);
        setPlaying(true);
    }

    async function startPairing() {
        const id = await createUser(playerName);
        setPlayerId(id);
        setFreshStart(false);
        setWaiting(true);
    }

    function quitPlaying() {
        setPlaying(false);
        setPlayerId("");
        setFreshStart(true);
    }

    return (
        <div className={"flex flex-col justify-between h-screen w-screen"}>
            <header className={"text-center text-5xl text-gray-800"}>Chess64</header>
            {freshStart &&
                <PlayForm playerName={playerName} setPlayerName={setPlayerName} handleSubmit={startPairing}/>}
            {waiting && <PairingLoader playerId={playerId} handleGameRoom={startPlaying}/>}
            {playing && <PlayArea isWhite={isWhite} opponentName={opponentName} playerName={playerName}
                                  handleQuit={quitPlaying}/>}
            <footer className={""}>🄯 2023 MPB. All rights reversed.</footer>
        </div>
    )
}

export default App
