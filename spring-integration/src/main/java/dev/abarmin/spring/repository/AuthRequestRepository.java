package dev.abarmin.spring.repository;

import dev.abarmin.spring.model.AuthoriseRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRequestRepository {
    private final DSLContext dslContext;

    public AuthoriseRequest save(AuthoriseRequest request) {
        final int affected = dslContext.insertInto(DSL.table("auth_requests"))
                .set(DSL.field("from_id"), request.fromId())
                .set(DSL.field("to_id"), request.toId())
                .set(DSL.field("amount_currency"), request.amount().currency())
                .set(DSL.field("amount_value"), request.amount().amount())
                .execute();

        return request;
    }
}
