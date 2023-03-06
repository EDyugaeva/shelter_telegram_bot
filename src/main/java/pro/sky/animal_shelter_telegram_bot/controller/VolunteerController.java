package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animal_shelter_telegram_bot.model.Volunteer;
import pro.sky.animal_shelter_telegram_bot.service.VolunteerService;

import java.util.Collection;

import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_VOLUNTEER_CONTROLLER;

@RestController
@RequestMapping("volunteer")
@Slf4j
public class VolunteerController {

    private final VolunteerService volunteerService;


    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Welcome message",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Send a welcome message",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    examples = @ExampleObject(value = "string"))
                    )
            },
            tags = "Volunteers"
    )
    @GetMapping
    public String helloMessage() {
        log.info("Call helloMessage in VolunteerController");
        return HELLO_MESSAGE_VOLUNTEER_CONTROLLER;
    }

    @Operation(
            summary = "Find volunteer by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found volunteer:",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If volunteer not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Volunteers"
    )
    @GetMapping("{id}")
    public Volunteer findVolunteer(@Parameter(description = "Volunteer id", example = "1") @PathVariable Long id) {
        log.info("Call findVolunteer in VolunteerController");
        return volunteerService.findVolunteer(id);
    }

    @Operation(
            summary = "Add information about new volunteer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add information",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))
                    )
            },
            tags = "Volunteers"
    )
    @PostMapping
    public Volunteer addVolunteer(@RequestBody Volunteer volunteer) {
        log.info("Call addVolunteer in VolunteerController");
        return volunteerService.addVolunteer(volunteer);
    }

    @Operation(
            summary = "Update information about a volunteer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Edit information about a volunteer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Volunteer.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If volunteer not found, will be received bad request",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Volunteers"
    )
    @PutMapping
    public Volunteer editVolunteer(@RequestBody Volunteer volunteer) {
        log.info("Call editVolunteer in VolunteerController");
        return volunteerService.changeVolunteer(volunteer);
    }

    @Operation(
            summary = "Delete information about volunteer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Volunteer is delete from Database",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If volunteer not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Volunteers"
    )
    @DeleteMapping("{id}")
    public Volunteer deleteVolunteer(@PathVariable Long id) {
        log.info("Call deleteVolunteer in VolunteerController");
        return volunteerService.deleteVolunteer(id);
    }

    @Operation(
            summary = "Find all volunteer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of volunteers",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Collection.class)
                            )
                    )
            },
            tags = "Volunteers"
    )
    @GetMapping(path = "/all")
    public Collection<Volunteer> findAllVolunteers() {
        log.info("Call findAllVolunteers in VolunteerController");
        return volunteerService.findAllVolunteers();
    }

    @Operation(
            summary = "Set phone number (if it was changed)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Volunteer",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If volunteer not found, will be received bad request",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Volunteers"
    )
    @PutMapping(path = "{id}/phone-number")
    public Volunteer editPhoneNumberOfVolunteer(
            @PathVariable Long id,
            @Parameter(description = "Phone number", example = "+79554478895") @RequestParam("phone") String phoneNumber) {
        log.info("Call editPhoneNumberOfVolunteer in VolunteerController");
//        if (id == null || phoneNumber == null || volunteerService.findVolunteer(id) == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }

        Volunteer editVolunteer = volunteerService.findVolunteer(id);
        volunteerService.setPhoneNumberOfVolunteer(editVolunteer, phoneNumber);
        return volunteerService.changeVolunteer(editVolunteer);
    }
}
