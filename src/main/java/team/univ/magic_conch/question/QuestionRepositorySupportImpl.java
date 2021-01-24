package team.univ.magic_conch.question;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import team.univ.magic_conch.tag.QTag;
import team.univ.magic_conch.user.QUser;

import java.util.List;

@RequiredArgsConstructor
public class QuestionRepositorySupportImpl implements QuestionRepositorySupport{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Question> findAllByTitleOrUsernameOrTagName(String title, String username, String tagName, Pageable pageable) {

        QQuestion question = QQuestion.question;
        QUser user = QUser.user;
        QTag tag = QTag.tag;

        QueryResults<Question> result = jpaQueryFactory
                .selectFrom(question)
                .join(question.user, user)
                .fetchJoin()
                .join(question.tag, tag)
                .fetchJoin()
                .where(
                        !StringUtils.isEmpty(title) ? question.title.contains(title) : null,
                        !StringUtils.isEmpty(username) ? user.username.contains(username) : null,
                        !StringUtils.isEmpty(tagName) ? tag.name.eq(tagName) : null
                )
                .orderBy(question.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
