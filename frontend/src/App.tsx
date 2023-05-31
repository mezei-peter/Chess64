import PlayArea from "./components/PlayArea";
import PlayForm from "./components/PlayForm";
import {useState} from "react";



function App() {
    const [playing, setPlaying] = useState(false);

    function startPlaying() {
        setPlaying(true);
    }

    function quitPlaying() {
        setPlaying(false);
    }

    return (
        <div className={"flex flex-col justify-between h-screen w-screen"}>
            <header className={"text-center text-5xl text-gray-800"}>Chess64</header>
            {!playing ? <PlayForm handleSubmit={startPlaying}/> : <PlayArea handleQuit={quitPlaying}/>}
            <footer className={""}>🄯 2023 MPB. All rights reversed.</footer>
        </div>
    )
}

export default App
