function App() {
  const Calendar = new Date();
  const time = Calendar.toLocaleTimeString();
  const day = Calendar.toLocaleDateString();
  // Welcome Message Logic
  let message = "Welcome To MedPet!";
  if (Math.random() <= 0.5) {
    message = "Greeting From MedPet!";
  }

  // Input Logic
  const style = { border: "3px solid red" };
  const inputType = "text";
  const input = <input style={style} type={inputType} />;

  // One Return Statement
  const returnStatement = (
    <div>
      <h1> {message} </h1>
      <div>
        <h3>
          {" "}
          {day} {time}
        </h3>
        <h2>{input}</h2>
      </div>
    </div>
  );

  // Return
  return returnStatement;
}

// export outside of App Function
export default App;
