import { useState } from 'react';
import AnimalShow from './AnimalShow';
import './App.css';


function getRandomAnimal() {
  const animals = ['bird', 'cat', 'cow', 'dog', 'gator', 'horse']; // List of Animals

  return animals[Math.floor(Math.random() * animals.length)] // returns random string in list
}

console.log(getRandomAnimal());

function App() {
  const [animals, setAnimals] = useState([]); /* creates an array with a value of animals and a function to change the state of the value */
 
  const handleClick = () => { // on click
    setAnimals([...animals, getRandomAnimal()]); /* in list of animals, getRandomAnimal */
  };

  const renderedAnimals = animals.map((animal, index) => {       /* handles conversion of string to type*/
    return <AnimalShow type={animal} key={index} />
  });
  
  return (
  <div className="app"> 
    <button onClick={handleClick}> Add Animal </button>
    <div className="animal-list">
      {renderedAnimals}
    </div>
  </div>);
}

export default App;