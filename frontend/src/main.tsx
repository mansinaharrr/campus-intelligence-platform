import React from 'react';
import ReactDOM from 'react-dom/client';
import './styles.css';

function App() {
  return (
    <main className="shell">
      <h1>Campus Intelligence</h1>
      <p>Phase 1 scaffold is ready. Frontend implementation begins in Phase 2.</p>
    </main>
  );
}

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode><App /></React.StrictMode>
);
