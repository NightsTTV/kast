import React, { useState } from 'react';
import WelcomeScreen from "./WelcomeScreen";
import Dashboard from "./Dashboard";

function App() {
  const [isLoggedIn, setLoggedIn] = useState(true);

  return (
    <div className="App"> 
      {isLoggedIn ? (
        <WelcomeScreen setLoggedIn={setLoggedIn} />
      ) : (
        <Dashboard />
      )}
    </div>
  );
}

export default App;