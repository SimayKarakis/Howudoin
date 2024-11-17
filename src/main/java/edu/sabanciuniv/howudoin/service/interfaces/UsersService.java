package edu.sabanciuniv.howudoin.service.interfaces;

import edu.sabanciuniv.howudoin.model.Users;
import java.util.List;

public interface UsersService
{
    public void registerUser(Users user) throws Exception;
    List<Users> searchCustomUser(String startDate, String endDate);
}