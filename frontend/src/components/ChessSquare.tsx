function ChessSquare({piece, dark}: { piece: string, dark: boolean }) {
    return (
        <div
            className={
                dark
                    ? `h-[4.3vmax] w-[4.3vmax] flex justify-center items-center bg-gray-200`
                    : `h-[4.3vmax] w-[4.3vmax] flex justify-center items-center bg-gray-100`
            }>
            {piece !== '0' && piece}
        </div>
    );
}

export default ChessSquare;