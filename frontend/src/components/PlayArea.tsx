import ChessBoard from "./ChessBoard";
import {useState} from "react";

function initBoard(): string[] {
    return [
        '♜', '♞', '♝', '♛', '♚', '♝', '♞', '♜',
        '♟︎', '♟︎', '♟︎', '♟︎', '♟︎', '♟︎', '♟︎', '♟︎',
        '0', '0', '0', '0', '0', '0', '0', '0',
        '0', '0', '0', '0', '0', '0', '0', '0',
        '0', '0', '0', '0', '0', '0', '0', '0',
        '0', '0', '0', '0', '0', '0', '0', '0',
        '♙', '♙', '♙', '♙', '♙', '♙', '♙', '♙',
        '♖', '♘', '♗', '♕', '♔', '♗', '♘', '♖',
    ];
}

function PlayArea({handleQuit}: { handleQuit: () => void }) {
    const [board, _setBoard] = useState(initBoard());

    return (
        <div className={"border border-red-400 h-5/6 flex flex-col justify-between items-center"}>
            <ChessBoard board={board}/>
            <button onClick={handleQuit} className="bg-red-800 hover:bg-red-900 text-white font-semibold py-2 px-4
                rounded shadow">
                Quit
            </button>
        </div>
    );
}

export default PlayArea;
