function App() {
  const date = new Date();
  const time = date.toLocaleTimeString();
  const fulldate = date.toLocaleDateString();

  // Lesson 1
  let message = "Welcome to MedPet";
  if (Math.random() > 0.5) {
    message = "Hello, Greetings!";
  }

  const lesson1 = (
    <div>
      <h2>Lesson 1</h2>
      <p>
        Time: {time} Date: {fulldate}
      </p>
      <h1>{message}</h1>{" "}
    </div>
  );

  //Lesson 2
  // Strings wrapped in "" while numbers,objects,arrays,& variables are wrapped in {}
  // Objects can not be returned but they can be used as properties
  const inputType = "number";
  const minValue = 5;
  const borderStyle = { border: "3px solid red" };
  const lesson2 = (
    <div>
      <h2>Lesson 2 </h2>
      <h3>How to create an input (takes characters and number)</h3>
      <input />

      <h3>How to create an input that takes ONLY numbers</h3>
      <input style={{ border: "3px solid blue" }} type="number" min={5} />

      <h3>Creating an input (takes numbers) using variables</h3>
      <input style={borderStyle} type={inputType} min={minValue} />
    </div>
  );

  //Lesson 3
  //Converting HTML into JSX

  /* In HTML: <textarea autofocus></textarea> */
  /* In JSX: use autoFocus, not autofocus  
    In JSX all prop names follow camelCase*/
  /* In HTML: <input maxlength="5"/> 
    In JSX <input maxLength={5}/>
    In JSX attributes meant to be numbers should be provided as numbers
    with the curly braces) */
  /* In HTML <div class="item" />
    In JSX <div className="item" />
    In JSX the 'class' attribute is written as 'className'
    */
  /* In HTML <a stle="text-decoration: 'none'; padding: 5px';" />
     In JSX <div style={{textDecoration:'none',padding: '5px'}} />
     In JSX, in-line styles are provided as OBJECTS */
  const lesson3 = (
    <div>
      <h2>Lesson 3</h2>
      <h3>Converting HTML into JSX</h3>
      <textarea autoFocus={true} />
    </div>
  );

  return (
    <div>
      <h1> My Learning Journey </h1>
      {lesson1}
      {lesson2}
      {lesson3}
    </div>
  );
}

export default App;
