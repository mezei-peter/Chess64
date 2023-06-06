import PlayArea from "./components/PlayArea";
import PlayForm from "./components/PlayForm";
import {useState} from "react";
import PairingLoader from "./components/PairingLoader";


function App() {
    const [freshStart, setFreshStart] = useState(true);
    const [waiting, setWaiting] = useState(false);
    const [playing, setPlaying] = useState(false);
    const [playerId, setPlayerId] = useState("");

    function startPlaying() {
        setPlaying(true);
    }

    function startPairing() {
        setFreshStart(false);
        setWaiting(true);
    }

    function quitPlaying() {
        setPlaying(false);
    }

    return (
        <div className={"flex flex-col justify-between h-screen w-screen"}>
            <header className={"text-center text-5xl text-gray-800"}>Chess64</header>
            {freshStart && <PlayForm handleSubmit={startPairing}/>}
            {waiting && <PairingLoader/>}
            {playing && <PlayArea handleQuit={quitPlaying}/>}
            <footer className={""}>🄯 2023 MPB. All rights reversed.</footer>
        </div>
    )
}

export default App
