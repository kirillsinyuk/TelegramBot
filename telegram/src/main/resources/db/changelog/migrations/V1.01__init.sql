create table if not exists telegram_user
(
    id serial primary key,
    user_id bigint not null,
    chat_id bigint not null,
    core_user_id bigint,
    unique (user_id, chat_id)
);

create index user_chat_idx on telegram_user(user_id, chat_id);