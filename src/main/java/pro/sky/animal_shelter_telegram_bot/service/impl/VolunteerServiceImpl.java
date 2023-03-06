package pro.sky.animal_shelter_telegram_bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.repository.VolunteerRepository;
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service for working with repository VolunteerRepository
 */
@Service
@Slf4j
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    private final PetOwnerService petOwnerService;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository, PetOwnerServiceImpl petOwnerService) {
        this.volunteerRepository = volunteerRepository;
        this.petOwnerService = petOwnerService;
    }

    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        Volunteer addingVolunteer = setPhoneNumberOfVolunteer(volunteer, volunteer.getPhoneNumber());
        volunteerRepository.save(addingVolunteer);
        log.info("Volunteer {} is saved", addingVolunteer);
        return addingVolunteer;
    }

    @Override
    public Volunteer deleteVolunteer(Long id) {
        Volunteer deleteVolunteer = volunteerRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Volunteer with id = %d is not found", id)));
        volunteerRepository.deleteById(id);
        log.info("Volunteer with id {} is deleted", id);
        return deleteVolunteer;
    }

    @Override
    public Volunteer findVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Volunteer with id = %d is not found", id)));
        log.info("Volunteer with id {} is found", id);
        return volunteer;
    }


    @Override
    public Volunteer changeVolunteer(Volunteer volunteer) {
        if (volunteerRepository.findById(volunteer.getId()).isEmpty()) {
            log.info("Volunteer with id {} is not found", volunteer.getId());
            throw new  NoSuchElementException(String.format("Volunteer with id = %d is not found", volunteer.getId()));
        }
        Volunteer changingVolunteer = volunteerRepository.save(volunteer);
        log.info("Volunteer with id {} is saved", volunteer);
        return changingVolunteer;
    }

    /**
     * add phone number to database. If this phone number was in pet-owner table, chat id will be saved to this table
     *
     * @param phoneNumber - phone number from swagger
     * @param volunteer   - volunteer
     */
    @Override
    public Volunteer setPhoneNumberOfVolunteer(Volunteer volunteer, String phoneNumber) {
        if (phoneNumber.isEmpty() || phoneNumber.isBlank()) {
            throw new NullPointerException("Phone number is empty");
        }

        volunteer.setPhoneNumber(phoneNumber);
        try {
            volunteer.setChatId(petOwnerService.getPetOwnerChatIdByPhoneNumber(phoneNumber));
        } catch (NullPointerException e) {
            log.info("Error in setPhoneNumberOfVolunteer. Method petOwnerService.getPetOwnerChatIdByPhoneNumber(phoneNumber)");
        }
        log.info("Volunteer {} is changed. Phone number {} is added.", volunteer, phoneNumber);
        return volunteerRepository.save(volunteer);
    }

    /**
     * find volunteer in database
     *
     * @param phoneNumber - phone number in format +7....
     * @return volunteer - volunteer from database
     */
    @Override
    public Volunteer findVolunteerByPhoneNumber(String phoneNumber) {
        return volunteerRepository.findVolunteerByPhoneNumber(phoneNumber);
    }

    /**
     * find volunteers in database
     *
     * @return list of volunteers
     */
    @Override
    public List<Volunteer> findAllVolunteers() {
        log.info("Was invoked method for getAllVolunteers");
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        if (volunteerList.isEmpty()) {
            log.error("Volunteer list is empty");
            throw new NotFoundException("Volunteers are empty");
        }
        return volunteerList;
    }


}
