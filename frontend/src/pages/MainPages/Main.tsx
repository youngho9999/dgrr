import { useNavigate } from "react-router-dom"
import personIco from "assets/images/ico_person_24px.svg"
import menuIco from "assets/images/ico_menu_24px.svg"
import character from "assets/images/logo_character.svg"
import title from "assets/images/logo_title.png"
import "assets/scss/Main.scss"
import { Button } from "components/Game/Elements/Button/BasicButton"


export const Main = () => {
  const navigate = useNavigate();


  return (
    <div className="MainPage">
      <div className="MainNav">
        <img src={menuIco} alt="메뉴 아이콘" />
        <img src={personIco} alt="프로필 아이콘" onClick={()=>{navigate('/profile')}} />
      </div>
      <div className="MainBody">
        <img src={character} alt="캐릭터" id="character" />
        <img src={title} alt="타이틀" id="title" />
        <div className="MainBtns">
          <Button>코드 입력</Button>
          <Button>방 만들기</Button>
          <Button>랜덤 매칭</Button>
        </div>
      </div>
    </div>
  )
}