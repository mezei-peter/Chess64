import PlayArea from "./components/PlayArea";
import PlayForm from "./components/PlayForm";
import {useEffect, useState} from "react";
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
    const [playerId, setPlayerId] = useState("");
    const [isWhite, setIsWhite] = useState(true);
    const [opponentName, setOpponentName] = useState("");

    useEffect(() => {
        window.onbeforeunload = () => {
            if (playerId !== "") {
                return "Your data will be lost if you reload.";
            }
        }
    }, [playerId]);

    function startPlaying(room: GameRoom) {
        const white: boolean = room.whitePlayer.playerId === playerId;
        setIsWhite(white);
        setOpponentName(white ? room.blackPlayer.name : room.whitePlayer.name);
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
