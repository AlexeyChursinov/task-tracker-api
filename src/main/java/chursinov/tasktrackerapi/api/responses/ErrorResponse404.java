package chursinov.tasktrackerapi.api.responses;

import chursinov.tasktrackerapi.api.exceptions.ErrorDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ApiResponse(responseCode = "404", description = "Not Found",
        content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorDto.class)))
public @interface ErrorResponse404 {
}


