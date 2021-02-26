package team.univ.magic_conch.visibility;

public interface VisibilityChecker {

    /**
     * 번들(게시물)의 가시성과 유저간 관계를 받아 열람 가능한지 확인
     * @param visibility
     * @param relation
     * @return true : 열람 가능 / false : 열람 불가능
     */
    boolean isVisible(Visibility visibility, UserRelation relation);
}
