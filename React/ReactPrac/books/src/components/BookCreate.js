import { useContext, useState } from 'react';
import BooksContext from '../context/book';

function BookCreate() {
    const { createBook } = useContext(BooksContext); // importing createBook function from BooksContext object. 
    const [title, setTitle] = useState(''); // title object

    const handleChange = (event) => {
        setTitle(event.target.value); // look into what user entered into event object & update title
    };

    const handleSubmit = (event) => { // handle submit form
        event.preventDefault(); // Stops the reloading of the page on submit
        createBook(title); // creates book w/ updated title
        setTitle('');  // refresh title to blank state
    };

 return (
 <div className="book-create"> 
    <h3> Add a book </h3>
    <form onSubmit={handleSubmit}> 
        <label> Title </label>
        <input className="input" value={title} onChange={handleChange} />
        <button className="button"> Create! </button>
    </form>
</div>
 ); 
}

export default BookCreate;