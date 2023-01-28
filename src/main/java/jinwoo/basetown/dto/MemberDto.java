package jinwoo.basetown.dto;

public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    //소속 팀이 있을 시
    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    //team이 null인 경우 사용
    public MemberDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
