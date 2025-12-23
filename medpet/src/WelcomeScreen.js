import './WelcomeScreen.css';
import Clouds from './assets/Clouds.mp4'

function WelcomeScreen(props) {
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

  //Button handling
  const handleClick = () => {
    props.setLoggedIn(false);
  };

  // One Return Statement
  const returnStatement = (

    <div>
        <div className = "background"> 

            <video src={Clouds} autoPlay loop muted />

            <h2> {day} {time} </h2>
            <div class="container">
                <h1>{message}</h1>
                    <input type="text" placeholder="Enter name here..."/>
                    <button className="continue-button" onClick={handleClick}> Continue </button>
            </div>
        </div>
    </div>
  );

  // Return
  return returnStatement;
}

export default WelcomeScreen;