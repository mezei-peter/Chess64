import ChessSquare from "./ChessSquare";
import React, {Dispatch} from "react";

function ChessBoard({board, select}: { board: string[], select: Dispatch<React.SetStateAction<null>> }) {
    return (
        <div className={"chess-board border grid grid-cols-8 grid-rows-8 text-center text-[5vmax]"}>
            {board.map((e, i) => (
                <ChessSquare key={i} piece={e} dark={Math.floor(i / 8) % 2 !== 0 ? i % 2 === 0 : i % 2 !== 0}
                             select={select}/>
            ))}
        </div>
    );
}

export default ChessBoard;
