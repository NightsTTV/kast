import Button from "./Button";
function App() {
  return (
  <div> 
    <div>
      <Button secondary outline> Hello! </Button> 
    </div>
    <div> 
      <Button danger outline> Me AF</Button> 
    </div>
    <div> 
      <Button warning outline> Hello World </Button> 
    </div>
    <div> 
      <Button secondary outline> Weep Woop! </Button> 
    </div>
    <div> 
      <Button primary outline> Weep Woop! </Button> 
    </div>
  </div>);
}

export default App;