package jinwoo.basetown.dto;

public class MemberDto {

    private Long id;
    private String name;
    private String username;
    private String teamName;

    //소속 팀이 있을 시
    public MemberDto(Long id, String username, String name, String teamName) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.teamName = teamName;
    }

    //team이 null인 경우 사용
    public MemberDto(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }
}
