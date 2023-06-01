import ChessSquare from "./ChessSquare";

function ChessBoard({board}: { board: string[] }) {
    return (
        <div className={"chess-board border grid grid-cols-8 grid-rows-8 text-center text-[5vmax]"}>
            {board.map((e, i) => (
                <ChessSquare key={i} piece={e} dark={Math.floor(i / 8) % 2 !== 0 ? i % 2 === 0 : i % 2 !== 0}
                />
            ))}
        </div>
    );
}

export default ChessBoard;
