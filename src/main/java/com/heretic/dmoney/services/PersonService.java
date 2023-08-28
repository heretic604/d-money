package com.heretic.dmoney.services;

import com.heretic.dmoney.dto.requests.PersonRequest;
import com.heretic.dmoney.dto.responses.PersonResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service processes users data.
 * Contains CRUD methods
 */
@Service
public interface PersonService {

    /**
     * Add new user to database
     *
     * @param personRequest user's data from controller
     * @return information about saved user
     */
    PersonResponse savePerson(PersonRequest personRequest);

    /**
     * Get user by ID
     *
     * @param id user ID
     * @return information about user from database
     */
    PersonResponse getPerson(UUID id);

    /**
     * Get user by username
     *
     * @param username username from controller
     * @return information about user from database
     */
    PersonResponse getPerson(String username);

    /**
     * Get information about all users in database
     *
     * @return List with information about all users
     */
    List<PersonResponse> getPersons();

    /**
     * Update user info in database
     *
     * @param personRequest data to update
     * @param id            user's ID to update
     * @return info about updated user
     */
    PersonResponse updatePerson(PersonRequest personRequest, UUID id);

    /**
     * Delete user from database
     *
     * @param id userID to delete
     * @return result of deletion
     */
    boolean deletePerson(UUID id);
}