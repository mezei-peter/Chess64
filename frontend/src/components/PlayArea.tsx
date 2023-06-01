import ChessBoard from "./ChessBoard";
import {useState} from "react";
import classConstants from "../constants/classConstants";

const whitePieces = ['♖', '♘', '♗', '♕', '♔', '♙'];
const blackPieces = ['♜', '♞', '♝', '♛', '♚', '♟︎'];

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
    const [whiteTurn, _setWhiteTurn] = useState(true);
    const [selectedSquare, setSelectedSquare] = useState(Element.prototype);

    function handleSquareClick(square: Element) {
        const piece: string | null = square.textContent;
        if (piece === null) {
            return;
        }
        if ((whiteTurn && whitePieces.includes(piece)) || (!whiteTurn && blackPieces.includes(piece))) {
            if (selectedSquare !== Element.prototype) {
                selectedSquare.classList.remove(classConstants.bgHighlight);
            }
            square.classList.add(classConstants.bgHighlight);
            setSelectedSquare(square);
        }
    }

    return (
        <div className={"h-5/6 flex flex-col justify-between items-center"}>
            <ChessBoard board={board} handleSquareClick={handleSquareClick}/>
            <button onClick={handleQuit} className="bg-red-800 hover:bg-red-900 text-white font-semibold py-2 px-4
                rounded shadow">
                Quit
            </button>
        </div>
    );
}

export default PlayArea;
