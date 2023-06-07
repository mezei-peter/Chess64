import SockJS from "sockjs-client";
import Stomp from "stompjs";
import sockConstants from "../constants/sockConstants";
import {useEffect, useRef} from "react";
import GameRoom from "../types/gameRoom";

function PairingLoader({playerId, handleGameRoom, handleCancel}: {
    playerId: string,
    handleGameRoom: (gr: GameRoom) => void,
    handleCancel: () => void,
}) {
    const sock = useRef(new SockJS(`${sockConstants.root}/${sockConstants.chess64}`));
    const client = useRef(Stomp.over(sock.current));

    useEffect(() => {
        client.current.connect({},
            () => {
                console.log("Connected to Chess64");
                client.current.send(`/${sockConstants.appPair}/${playerId}`);
                client.current.subscribe(`/${sockConstants.topicPairings}`, msg => onMessage(msg));
            },
            () => console.error("Connection to Chess64 failed"));
    }, []);

    function onMessage(msg: Stomp.Message) {
        client.current.disconnect(() => 0);
        const gameRoom: GameRoom = JSON.parse(msg.body);
        handleGameRoom(gameRoom);
    }

    return (
        <div className={"h-3/4 flex flex-col justify-between items-center text-2xl"}>
            <span/>
            <span className={"animate-pulse text-gray-800 p-2"}>Looking for an opponent . . .</span>
            <button onClick={handleCancel} className="bg-red-800 hover:bg-red-900 text-white font-semibold py-2 px-4
                rounded shadow">
                Cancel
            </button>
        </div>
    );
}

export default PairingLoader;
