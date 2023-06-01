import classConstants from "../constants/classConstants";

function ChessSquare({piece, dark, handleSquareClick}: {
    piece: string, dark: boolean, handleSquareClick: (sq: Element) => void
}) {
    const bgClass: string = dark ? classConstants.bgDark: classConstants.bgLight;

    return (
        <div
            className={`h-[4.3vmax] w-[4.3vmax] flex justify-center items-center cursor-pointer ${bgClass}`}
            onMouseDown={e => e.preventDefault()}
            onContextMenu={e => e.preventDefault()}
            onClick={e => handleSquareClick(e.target as Element)}>
            {piece !== '0' && piece}
        </div>
    );
}

export default ChessSquare;