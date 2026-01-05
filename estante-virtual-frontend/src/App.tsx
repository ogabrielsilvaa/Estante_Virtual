import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './context/authContext'
import { Login } from './pages/login';
import { Register } from './pages/register';
import { MyShelf } from './pages/myShelf';

function App() {

  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route path='/' element={<Navigate to='/login'/>} />
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />

          <Route path='/myShelf' element={<MyShelf />} />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App
