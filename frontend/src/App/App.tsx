import { useEffect } from "react";
import "assets/scss/App.scss";
import { Route, Routes } from "react-router-dom";
import { Game } from "pages/GamePages/Game";
import { KakaoLogin } from "pages/LoginPages/KakaoLogin";
import { KakaoCallback } from "pages/LoginPages/KakaoCallback";
import { SignUp } from "pages/LoginPages/SignUp";
import { Main } from "pages/MainPages/Main";

export const App = () => {
  // 모바일 뷰포트 계산
  function setScreenSize() {
    const vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  useEffect(() => {
    setScreenSize();
  });

  return (
    <div className="App">
      <Routes>
        {/* 로그인 관련 */}
        <Route path="/" element={<KakaoLogin />} />
        <Route path="/KakaoCallback" element={<KakaoCallback />} />
        <Route path="/signup" element={<SignUp />} />

        <Route path="/main" element={<Main />} />
        <Route path="/game/*" element={<Game />} />
      </Routes>
    </div>
  );
};
