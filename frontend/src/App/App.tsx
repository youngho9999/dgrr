import React from "react";
import { Route, Routes } from "react-router-dom";
import { Game } from "pages/GamePages/Game";
import { KakaoLogin } from "pages/LoginPages/KakaoLogin";
import { KakaoCallback } from "pages/LoginPages/KakaoCallback";

export const App = () => {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<KakaoLogin />} />
        <Route path="/KakaoCallback" element={<KakaoCallback />} />
        <Route path="/game" element={<Game />} />
      </Routes>
    </div>
  );
};
