function ChessSquare({piece, dark}: {
    piece: string, dark: boolean,
}) {
    const bgClass: string = dark ? "bg-gray-200" : "bg-gray-100";
    const isEmpty: boolean = piece === '0';

    function handleClick(e: React.MouseEvent) {
        const classList: DOMTokenList = (e.target as Element).classList;
        const bgHighlight: string = dark ? "bg-red-200" : "bg-red-100";
        if (classList.contains(bgClass)) {
            if (!isEmpty) {
                classList.remove(bgClass);
                classList.add(bgHighlight);
            }
        } else {
            classList.remove(bgHighlight);
            classList.add(bgClass);
        }
    }

    return (
        <div
            className={`h-[4.3vmax] w-[4.3vmax] flex justify-center items-center cursor-pointer ${bgClass}`}
            onMouseDown={e => e.preventDefault()}
            onContextMenu={e => e.preventDefault()}
            onClick={e => handleClick(e)}>
            {piece !== '0' && piece}
        </div>
    );
}

export default ChessSquare;