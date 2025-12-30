import { createContext, useState } from 'react';
import axios from 'axios';

const BooksContext = createContext();

function Provider({ children }) {
    const [books, setBooks] = useState([]);

    const fetchBooks = async () => {
        const response = await axios.get('http://localhost:3001/books');

        setBooks(response.data);
    };

    const editBookById = async (id, newTitle) => {  // update Book by Id
        const response = await axios.put(`http://localhost:3001/books/${id}`, {
        title: newTitle
        });

        console.log(response);
        const updatedBooks = books.map((book) => { // receieve each individual book
            if (book.id == id) { // if each bookid = the book id we want
            return { ...book, ...response.data}; // return the previous books + new book
        }

        return book; // otherwise return book
        });

        setBooks(updatedBooks);
    };

    const deleteBookById = async (id) => {
        await axios.delete(`http://localhost:3001/books/${id}`);

        const updatedBooks = books.filter((book) => { 
        return book.id !== id
        });

        setBooks(updatedBooks);
    };

    const createBook = async (title) => {
        const response = await axios.post('http://localhost:3001/books', {
        title
        });

        

        const updatedBooks = [...books, response.data]; // new array
        setBooks(updatedBooks);
    };
        

    const valueToShare = {
        books, 
        fetchBooks,
        deleteBookById,
        editBookById, 
        createBook,
    };

    return (<BooksContext.Provider value={valueToShare}>
        {children}
    </BooksContext.Provider>);
}

export { Provider };
export default BooksContext;
