package team.univ.magic_conch.question;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import team.univ.magic_conch.bundle.QBundle;
import team.univ.magic_conch.follow.QFollow;
import team.univ.magic_conch.question.dto.QuestionSearchDTO;
import team.univ.magic_conch.tag.QTag;
import team.univ.magic_conch.user.QUser;
import team.univ.magic_conch.utils.page.PageRequestDTO;

import java.util.List;

@RequiredArgsConstructor
public class QuestionRepositorySupportImpl implements QuestionRepositorySupport{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Question> findAllByTitleOrUsernameOrTagName(QuestionSearchDTO questionSearchDTO) {

        QQuestion question = QQuestion.question;
        QUser user = QUser.user;
        QTag tag = QTag.tag;

        String title = questionSearchDTO.getTitle();
        String username = questionSearchDTO.getUsername();
        String tagName = questionSearchDTO.getTagName();
        Pageable pageable = new PageRequestDTO(questionSearchDTO.getPageNo()).getPageable();

        QueryResults<Question> result = jpaQueryFactory
                .selectFrom(question)
                .join(question.user, user).fetchJoin()
                .join(question.tag, tag).fetchJoin()
                .where(
                        !StringUtils.isEmpty(title) ? question.title.contains(title) : null,
                        !StringUtils.isEmpty(username) ? user.username.eq(username) : null,
                        !StringUtils.isEmpty(tagName) ? tag.name.eq(tagName) : null
                )
                .orderBy(question.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<Question> findAllByFollowUsername(String myname, QuestionSearchDTO questionSearchDTO) {

        QQuestion question = QQuestion.question;
        QUser user = QUser.user;
        QFollow follow = QFollow.follow;
        QTag tag = QTag.tag;

        String title = questionSearchDTO.getTitle();
        String username = questionSearchDTO.getUsername();
        String tagName = questionSearchDTO.getTagName();
        Pageable pageable = new PageRequestDTO(questionSearchDTO.getPageNo()).getPageable();

        QueryResults<Question> result = jpaQueryFactory
                .selectFrom(question)
                .join(question.user, user).fetchJoin()
                .join(question.tag, tag).fetchJoin()
                .where(user.in(
                        JPAExpressions
                            .select(follow.userTo)
                            .from(follow)
                            .join(follow.userTo, user)
                            .join(follow.userFrom, user)
                            .where(follow.userFrom.username.eq(myname))),
                        question.status.eq(QuestionStatus.ING),
                        !StringUtils.isEmpty(title) ? question.title.contains(title) : null,
                        !StringUtils.isEmpty(username) ? user.username.eq(username) : null,
                        !StringUtils.isEmpty(tagName) ? tag.name.eq(tagName) : null)
                .orderBy(question.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
