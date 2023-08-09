package live.dgrr.domain.member.controller;

import live.dgrr.domain.member.dto.request.MemberRequestDto;
import live.dgrr.domain.member.dto.response.MemberInfoResponseDto;
import live.dgrr.domain.member.service.MemberService;
import live.dgrr.domain.member.entity.Member;
import live.dgrr.global.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@CrossOrigin(exposedHeaders = "Authorization")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/kakao-callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        String responses;
        String token = memberService.getKakaoAccessToken(code);
        String id = memberService.createKakaoMember(token);
        Member member = memberService.getMemberByKakaoId(id);
        HashMap<String, Object> map = new HashMap<>();
        if(member == null) { // 멤버가 없으면 회원가입
            responses = "signUp";
            map.put("key", responses);
            map.put("id", id);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else { // 멤버가 있다면 로그인
            responses = "login";
            map.put("key", responses);
            map.put("member", member);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping({"/", ""})
    public Member addMember(@RequestBody Member member) {

        return memberService.addMember(member);
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestParam("kakaoId") String kakaoId) {
        String token = memberService.createToken(memberService.getMemberByKakaoId(kakaoId));
        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtProperties.TOKEN_PREFIX + token);
        map.put("member", memberService.getMemberByKakaoId(kakaoId));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    // member 확인 kakao id로
    @GetMapping("/kakao-id")
    public ResponseEntity<?> searchMemberByKakaoId(@RequestParam String kakaoId) {
        Member member = memberService.getMemberByKakaoId(kakaoId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    // nickname 중복 처리
    @GetMapping("/nickname-check")
    public ResponseEntity<?> searchMemberByNickname(@RequestParam(value="nickname") String nickname) {
        Map<String, String> result = new HashMap<>();
        String nicknameExists;
        String message;
        boolean isThereNickname = memberService.findMemberByNickname(nickname);
        message = "NICKNAME '" + nickname + "' DOES NOT EXIST";
        nicknameExists = "false";
        if(isThereNickname) {
            message = "NICKNAME '" + nickname + "' ALREADY EXISTS";
            nicknameExists = "true";
        }
        result.put("nicknameExists", nicknameExists);
        result.put("message", message);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //mypage
    @GetMapping("/member-id")
    public ResponseEntity<?> mypage(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, "");
        Long memberId = memberService.getIdFromToken(token);
        MemberInfoResponseDto memberInfoDto = memberService.getMemberInfoWithRatingAndBattleDetail(memberId);
        return new ResponseEntity<>(memberInfoDto,HttpStatus.OK);
    }

    //mypage-update
    @PutMapping
    public ResponseEntity<?> updateMember(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateByMember(memberRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
