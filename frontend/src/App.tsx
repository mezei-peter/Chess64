import PlayArea from "./components/PlayArea";
import PlayForm from "./components/PlayForm";
import {useState} from "react";
import PairingLoader from "./components/PairingLoader";

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

    function startPlaying() {
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
    }

    return (
        <div className={"flex flex-col justify-between h-screen w-screen"}>
            <header className={"text-center text-5xl text-gray-800"}>Chess64</header>
            {freshStart &&
                <PlayForm playerName={playerName} setPlayerName={setPlayerName} handleSubmit={startPairing}/>}
            {waiting && <PairingLoader playerId={playerId}/>}
            {playing && <PlayArea handleQuit={quitPlaying}/>}
            <footer className={""}>🄯 2023 MPB. All rights reversed.</footer>
        </div>
    )
}

export default App
