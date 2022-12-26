package com.bot.model.repository;

import com.bot.model.entities.BotUser;
import com.bot.model.entities.Band;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BotUserRepository extends CrudRepository<BotUser, Long> {

    @Query(value = "SELECT * FROM bot_user WHERE bot_user.user_id=?1", nativeQuery = true)
    BotUser getByUserId(Integer userId);

    Set<BotUser> findByBand(Band band);
}
