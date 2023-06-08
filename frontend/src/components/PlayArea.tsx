import ChessBoard from "./ChessBoard";
import {useEffect, useRef, useState} from "react";
import classConstants from "../constants/classConstants";
import SockJS from "sockjs-client";
import sockConstants from "../constants/sockConstants";
import Stomp from "stompjs";
import GameRoom from "../types/gameRoom";

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

function PlayArea({isWhite, handleQuit, opponentName, playerName, room, playerId}: {
    isWhite: boolean,
    handleQuit: () => void,
    opponentName: string,
    playerName: string,
    room: GameRoom,
    playerId: string,
}) {
    const [board, _setBoard] = useState(isWhite ? initBoard() : initBoard().reverse());
    const [whiteTurn, _setWhiteTurn] = useState(true);
    const [selectedSquare, setSelectedSquare] = useState(Element.prototype);
    const sock = useRef(new SockJS(`/ws/${sockConstants.chess64}`));
    const client = useRef(Stomp.over(sock.current));

    useEffect(() => {
        client.current.connect({},
            () => {
                console.log("Connected to Chess64");
                client.current.subscribe(`/${sockConstants.topicRoomPings}/${playerId}`, msg => onMessage(msg));
                fetch(`/api/room/ping/${room.roomId}`, {method: "POST"}).then();
            },
            () => console.error("Connection to Chess64 failed"));
    }, []);

    function onMessage(msg: Stomp.Message) {
        console.log(msg.body);
    }

    function validateMove(_fromSquare: Element, _toSquare: Element): boolean {
        // TODO
        return true;
    }

    function handleSquareClick(square: Element) {
        const piece: string | null = square.textContent;
        if (piece === null) {
            return;
        }
        if (piece === '') {
            if (selectedSquare === Element.prototype) {
                return;
            }
            if (validateMove(selectedSquare, square)) {
                square.textContent = selectedSquare.textContent;
                selectedSquare.textContent = '';
                selectedSquare.classList.remove(classConstants.bgHighlight);
                setSelectedSquare(Element.prototype);
            }
            return;
        }
        if ((whiteTurn && whitePieces.includes(piece as string))
            || (!whiteTurn && blackPieces.includes(piece as string))) {
            if (selectedSquare !== Element.prototype) {
                selectedSquare.classList.remove(classConstants.bgHighlight);
            }
            square.classList.add(classConstants.bgHighlight);
            setSelectedSquare(square);
        }
    }

    return (
        <div className={"h-5/6 flex flex-col justify-between items-center"}>
            <div className={"flex flex-col items-start"}>
                <div className={"font-bold text-xl text-gray-800 p-2"}>{opponentName}</div>
                <ChessBoard board={board} handleSquareClick={handleSquareClick}/>
                <div className={"font-bold text-xl text-gray-800 p-2"}>{playerName}</div>
            </div>
            <button onClick={handleQuit} className="bg-red-800 hover:bg-red-900 text-white font-semibold py-2 px-4
                rounded shadow">
                Quit
            </button>
        </div>
    );
}

export default PlayArea;
