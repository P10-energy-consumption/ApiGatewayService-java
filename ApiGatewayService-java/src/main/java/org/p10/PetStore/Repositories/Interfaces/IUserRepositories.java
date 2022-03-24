package org.p10.PetStore.Repositories.Interfaces;

import org.p10.PetStore.Models.User;

public interface IUserRepositories {
    int insertUser(String request);
    User getUser(String userName);
    User updateUser(String request);
    String deleteUser(String userName);
}
