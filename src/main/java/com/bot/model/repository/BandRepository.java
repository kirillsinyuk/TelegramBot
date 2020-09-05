package com.bot.model.repository;

import com.bot.model.entities.Band;
import com.bot.model.entities.BotUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandRepository extends CrudRepository<Band, Long> {

    @Query("From Band g where :user member g.bandUsers")
    Band getBandByMember(BotUser user);
}
