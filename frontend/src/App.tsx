import PlayArea from "./components/PlayArea";

function App() {
    return (
        <div className={"flex flex-col justify-between h-screen"}>
            <header className={"text-center text-5xl"}>Chess64</header>
            <PlayArea/>
            <footer className={""}>🄯 2023 MPB. All rights reversed.</footer>
        </div>
    )
}

export default App
