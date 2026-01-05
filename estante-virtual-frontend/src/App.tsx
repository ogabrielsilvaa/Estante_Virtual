import { AuthProvider } from './context/authContext'
import { Login } from './pages/login';

function App() {

  return (
    <AuthProvider>
      <Login />
    </AuthProvider>
  );
}

export default App
