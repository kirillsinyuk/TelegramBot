package com.bot.service.entity;

import com.bot.model.dto.DynamicsDataDto;
import com.bot.model.dto.DynamicsDto;
import com.bot.model.entities.BotUser;
import com.bot.service.graphics.DataToDynamicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DynamicsDtoService {

    @Autowired
    private ProductService productService;
    @Autowired
    private DataToDynamicsService dataToDynamicsService;

    public DynamicsDto getDynamicsDto(Set<BotUser> users){
        BigDecimal totalSpend = totalSpend(users);
        Map<String, List<DynamicsDataDto>> dynData = getDynamicsDataUserMap(users);
        File graphic = totalSpend.equals(BigDecimal.ZERO) ? null : getDynamicsFile(dynData);

        return DynamicsDto.builder()
                .totalSpend(totalSpend)
                .monthSpendings(dynData)
                .message(getStaticticMsg(totalSpend))
                .dynamicsFile(graphic)
                .build();
    }

    private BigDecimal totalSpend(Set<BotUser> users){
        return users.stream().map(usr -> productService.getTotalSpendAllTime(usr)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, List<DynamicsDataDto>> getDynamicsDataUserMap(Set<BotUser> user){
        return user.stream()
                .map(usr -> new AbstractMap.SimpleImmutableEntry<>(usr.getUsername(), productService.getDynamicsDataDto(usr)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private File getDynamicsFile(Map<String, List<DynamicsDataDto>> dynData) {
        return dataToDynamicsService.convert(dynData);
    }

    private String getStaticticMsg(BigDecimal total) {
        return String.format("Всего потрачено %s руб.", total.intValue());
    }
}
