
import { useContext, useEffect } from 'react';
import BookCreate from './components/BookCreate';
import BookList from './components/BookList';
import BooksContext from './context/book';
import { Provider } from './context/book';

function App() {
  const { fetchBooks } = useContext(BooksContext);

  useEffect(() => {
    fetchBooks();
  }, []);

  return (
    <Provider>
      <div className="app">
        <h1> Reading List </h1>
        <BookList />
        <BookCreate />
      </div>
    </Provider>
  );
}

export default App;
