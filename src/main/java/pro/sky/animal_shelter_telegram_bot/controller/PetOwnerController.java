package pro.sky.animal_shelter_telegram_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.service.PetOwnerService;

import java.util.Collection;

import static pro.sky.animal_shelter_telegram_bot.controller.ConstantsOfControllers.HELLO_MESSAGE_OF_PET_OWNER_CONTROLLER;

@RestController
@RequestMapping("/pet-owner")
public class PetOwnerController {

    private final PetOwnerService petOwnerService;

    Logger logger = LoggerFactory.getLogger(PetOwnerController.class);

    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
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
            tags = "Pet owners"
    )
    @GetMapping
    public String helloMessage() {
        logger.info("Call helloMessage in PetOwnerController");
        return HELLO_MESSAGE_OF_PET_OWNER_CONTROLLER;
    }

    @Operation(
            summary = "Find pet owner by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found pet owner:",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Pet owners"
    )
    @GetMapping("{id}")
    public PetOwner findPetOwner(@Parameter(description = "Pet owner id", example = "1") @PathVariable Long id) {
        logger.info("Call findPetOwner in PetOwnerController");
        return petOwnerService.findPetOwner(id);
    }

    @Operation(
            summary = "Add information about new pet owner",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add information",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetOwner.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Add information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    )
            },
            tags = "Pet owners"
    )
    @PostMapping
    public PetOwner addPetOwner(@RequestBody PetOwner petOwner) {
        logger.info("Call addPetOwner in PetOwnerController");
        return petOwnerService.addPetOwner(petOwner);
    }

    @Operation(
            summary = "Update information about a pet owner",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Edit information about a pet owner",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetOwner.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "If pet owner not found, will be received bad request",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class))
                    )
            },
            tags = "Pet owners"
    )
    @PutMapping
    public PetOwner editPetOwner(@RequestBody PetOwner petOwner) {
        logger.info("Call editPetOwner in PetOwnerController");
        return petOwnerService.changePetOwner(petOwner);
    }

    @Operation(
            summary = "Delete information about pet owner",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet owner is delete from Database",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pets not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class))
                    )
            },
            tags = "Pet owners"
    )
    @DeleteMapping("{id}")
    public PetOwner deletePetOwner(@PathVariable Long id) {
        logger.info("Call deletePetOwner in PetOwnerController");
        return petOwnerService.deletePetOwner(id);
    }

    @Operation(
            summary = "Find pet owners with day of probation = 0",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found pet owners:",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )
            },
            tags = "Pet owners"
    )
    @GetMapping(path = "/zero-probation")
    public Collection<PetOwner> findPetOwnerZeroProbation() {
        logger.info("Call PetOwnerControllerZeroProbation in PetOwnerController");
        return petOwnerService.getPetOwnerWithZeroDayOfProbation();
    }

    @Operation(
            summary = "Update information about a pet owner",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PetOwner.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    )

            },
            tags = "Pet owners"
    )
    @PutMapping("{id}/probation-days")
    public PetOwner changeDayOfProbationInPetOwner(
            @PathVariable Long id,
            @Parameter(description = "Amount of extra day (could be negative)", example = "-2") @RequestParam("amount") Integer amountOfDays) {
        logger.info("Call changeDayOfProbationInPetOwner in PetOwnerController");
        return petOwnerService.setExtraDayOfProbation(id, amountOfDays);
    }

    @Operation(
            summary = "Say, that probation is over",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE)
                    )
            },
            tags = "Pet owners"
    )
    @PutMapping("{id}/probation-successfully")
    public String probationIsOver(@PathVariable Long id) {
        logger.info("Call probationIsOver in PetOwnerController");
        return petOwnerService.sayThatProbationIsOverSuccessfully(id);
    }

    @Operation(
            summary = "Say, that probation is over",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update information",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "If pet owner not found",
                            content = @Content(
                                    mediaType = MediaType.TEXT_PLAIN_VALUE
                            )
                    )

            },
            tags = "Pet owners"
    )
    @PutMapping("{id}/probation-unsuccessfully")
    public String probationIsOverUnsuccessfully(@PathVariable Long id) {
        logger.info("Call probationIsOverUnsuccessfully in PetOwnerController");
        return petOwnerService.sayThatProbationIsOverNotSuccessfully(id);
    }

    @Operation(
            summary = "Find all pet owners in pet shelter",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Finding pet owners",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Collection.class))
                    )
            },
            tags = "Pet owners"
    )
    @GetMapping("/all")
    public Collection<PetOwner> findAllPetOwners() {
        logger.info("Call findAllPetOwners in PetOwnerController");
        return petOwnerService.getAllPetOwners();
    }
}
