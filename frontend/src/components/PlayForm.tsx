function PlayForm({handleSubmit}: { handleSubmit: () => void }) {
    return (
        <form className={"h-5/6 flex flex-col justify-center items-center"}
              onSubmit={(e) => {
                  e.preventDefault();
                  handleSubmit();
              }}>
            <input
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700
                leading-tight focus:outline-none focus:shadow-outline w-2/4 relative bottom-4"
                id="username" type="text" placeholder="Name"/>
            <button
                className="bg-white hover:bg-gray-100 text-gray-800 font-semibold py-2 px-4
                border border-gray-400 rounded shadow">
                Play
            </button>
        </form>
    );
}

export default PlayForm;
