import "./App.css";

import React from "react";
import AppRouter from "./AppRouter";
import Navbar from "./components/Common/Navbar";
import Footer from "./components/Common/Footer";
function App() {
  return (
    <div className="App">
      <Navbar />

      <AppRouter />
      <Footer />
    </div>
  );
}

export default App;
