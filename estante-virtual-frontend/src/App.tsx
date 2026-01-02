import { AuthProvider } from './context/authContext'
import { MyShelf } from './pages/myShelf/myShelf';

function App() {

  return (
    <AuthProvider>
      <MyShelf />

      <div>
        <h1>Testando meu app!</h1>
      </div>

    </AuthProvider>
  );
}

export default App
