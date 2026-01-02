import './App.css'
import { AuthProvider } from './context/authContext'

function App() {

  return (
    <AuthProvider>
      <div>
        <h1>Estante Virtual</h1>
        <p>Projeto rodando!</p>
      </div>
    </AuthProvider>
  );
}

export default App
