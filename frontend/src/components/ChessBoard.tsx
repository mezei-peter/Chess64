import ChessSquare from "./ChessSquare";

function ChessBoard({board, handleSquareClick}: { board: string[], handleSquareClick: (sq: Element) => void }) {
    return (
        <div className={"chess-board border grid grid-cols-8 grid-rows-8 text-center text-[5vmax]"}>
            {board.map((e, i) => (
                <ChessSquare key={i} piece={e} dark={Math.floor(i / 8) % 2 !== 0 ? i % 2 === 0 : i % 2 !== 0}
                             handleSquareClick={handleSquareClick}/>
            ))}
        </div>
    );
}

export default ChessBoard;
