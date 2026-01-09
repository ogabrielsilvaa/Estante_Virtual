import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './context/authContext'
import { Login } from './pages/login';
import { Register } from './pages/register';
import { MyShelf } from './pages/myShelf';
import { BookDetails } from './pages/bookDetails';
import { Reviews } from './pages/reviews';
import { ReviewDetails } from './pages/reviewDetails';

function App() {

  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path='/' element={<Navigate to='/login'/>} />
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />

          <Route path='/myShelf' element={<MyShelf />} />
          <Route path='/bookDetails' element={<BookDetails />} />
          <Route path='/reviews' element={<Reviews />} />
          <Route path='/reviewDetails' element={<ReviewDetails />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
