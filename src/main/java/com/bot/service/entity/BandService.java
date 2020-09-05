package com.bot.service.entity;

import com.bot.model.entities.Band;
import com.bot.model.entities.BotUser;
import com.bot.model.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;


@Service
@Transactional
public class BandService {

    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private CategoryService categoryService;

    public Band createNewBand(User user){
        Band newBand = new Band();
        newBand.setSingle(true);
        newBand.setAdminId(user.getId());
        return bandRepository.save(newBand);
    }

    public Band createNewBand(BotUser user){
        Band newBand = new Band();
        newBand.setSingle(true);
        newBand.setAdminId(user.getUserId());
        return bandRepository.save(newBand);
    }

    public void deleteBandWithCategories(Band band){
        band.setBandUsers(null);
        categoryService.getCategoriesByBand(band)
                .forEach(category -> categoryService.deleteCategory(category));
        bandRepository.delete(band);
    }

    public void saveBand(Band band){
        bandRepository.save(band);
    }

    public void isNotSingleAnymore(Band band){
        band.setSingle(false);
        bandRepository.save(band);
    }

    public void setSingleIfNeeded(Band band){
        if(band.getBandUsers().size() == 1){
            band.setSingle(true);
            bandRepository.save(band);
        }
    }
}
