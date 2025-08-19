package com.todo.task.entity.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.task.entity.QTask;
import com.todo.task.entity.Task;
import com.todo.task.entity.TaskStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepositoryCustom{

    private final JPAQueryFactory qf;

    @Override
    public List<Task> search(Long memberId, Long categoryId, LocalDate from, LocalDate to, TaskStatus status) {
        QTask t = QTask.task;

        BooleanBuilder where = new BooleanBuilder().and(t.user.id.eq(memberId));

        if (categoryId != null) where.and(t.category.id.eq(categoryId));
        if (status != null)     where.and(t.status.eq(status));
        if (from != null)       where.and(t.startDate.goe(from));
        if (to != null)         where.and(t.endDate.loe(to));

        return qf.selectFrom(t)
                .leftJoin(t.category).fetchJoin()
                .where(where)
                .orderBy(t.startDate.asc(), t.id.desc())
                .fetch();
    }
}
