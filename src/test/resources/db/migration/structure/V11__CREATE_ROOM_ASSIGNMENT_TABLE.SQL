create table if not exists room_assignments
(
    id                uuid             not null
        primary key,
    created_at        timestamp(6) with time zone,
    created_by        uuid,
    deleted_at        timestamp(6) with time zone,
    deleted_by        uuid,
    updated_at        timestamp(6) with time zone,
    updated_by        uuid,
    assignment_date   timestamp(6)     not null,
    expected_end_date timestamp(6),
    remark            oid              not null,
    rental_price      double precision not null,
    room_id           uuid             not null
        constraint fkt96wkyclodjlrg52xftxyve1h
            references rooms,
    user_id           uuid
        constraint fks6xh6yp65mq50p2rlayu6gocp
            references users
);