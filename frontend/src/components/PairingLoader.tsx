import SockJS from "sockjs-client";
import Stomp from "stompjs";
import sockConstants from "../constants/sockConstants";
import {useEffect, useRef} from "react";

function PairingLoader() {
    const sock = useRef(new SockJS(`${sockConstants.root}/${sockConstants.chess64}`));
    const client = useRef(Stomp.over(sock.current));

    useEffect(() => {
        client.current.connect({}, () => console.log("Connected to Chess64"),
            () => console.error("Connection to Chess64 failed"));
    }, []);

    return (
        <div className={"animate-pulse m-auto text-gray-800 text-2xl p-2"}>
            Looking for an opponent . . .
        </div>
    );
}

export default PairingLoader;
