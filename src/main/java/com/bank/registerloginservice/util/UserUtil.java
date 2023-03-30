package com.bank.registerloginservice.util;

import org.springframework.stereotype.Component;

import com.bank.registerloginservice.dto.UserDetails;
import com.bank.registerloginservice.entity.AppUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class UserUtil {

    public UserDetails toUserDetails(AppUser user){
        UserDetails desired=new UserDetails();
        desired.setId(user.getId());
        desired.setUsername(user.getUsername());
        desired.setPassword(user.getPassword());
        return desired;
    }

    public List<UserDetails>toUserDetailsList(Collection<AppUser> users){
        List<UserDetails>desired=new ArrayList<>();
        for (AppUser user:users){
            UserDetails details=toUserDetails(user);
            desired.add(details);
        }
        return desired;
    }

}
