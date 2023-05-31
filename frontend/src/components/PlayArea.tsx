import ChessBoard from "./ChessBoard";

function PlayArea({handleQuit}: { handleQuit: () => void }) {
    return (
        <div className={"border border-red-400 h-5/6 flex flex-col justify-between items-center"}>
            <ChessBoard/>
            <button onClick={handleQuit} className="bg-red-800 hover:bg-red-900 text-white font-semibold py-2 px-4
                rounded shadow">
                Quit
            </button>
        </div>
    );
}

export default PlayArea;
