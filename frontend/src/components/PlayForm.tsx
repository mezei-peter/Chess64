import React from "react";

const maxNameLength = 15;

function PlayForm({playerName, setPlayerName, handleSubmit}: {
    playerName: string, setPlayerName: React.Dispatch<React.SetStateAction<string>>,
    handleSubmit: () => void
}) {
    return (
        <form className={"h-5/6 flex flex-col justify-center items-center"}
              onSubmit={(e) => {
                  e.preventDefault();
                  handleSubmit();
              }}>
            <input
                className="shadow appearance-none border rounded py-2 px-3 text-gray-700
                leading-tight focus:outline-none focus:shadow-outline w-64 relative bottom-4 text-center"
                id="username" type="text" placeholder="Name" value={playerName}
                onChange={event => {
                    if (event.target.value.length <= maxNameLength) {
                        setPlayerName(event.target.value)
                    }
                }}/>
            <button
                className="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4
                border border-gray-400 rounded shadow">
                Play
            </button>
        </form>
    );
}

export default PlayForm;
