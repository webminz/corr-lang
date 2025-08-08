package io.corrlang.domain.exceptions;

import io.corrlang.techspaces.TechSpaceCapability;
import org.checkerframework.checker.units.qual.C;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Supertype for all CorrLang related exceptions,
 * wrapped as RuntimeExceptions to make them more convenient.
 */
public class CorrLangException extends RuntimeException {

    @Nullable
    private final String details;
    @Nullable
    private final Throwable nestedException;

    private final FailureTypes type;

    private CorrLangException(@Nullable String details, @Nullable Throwable nestedException, FailureTypes type) {
        this.details = details;
        this.nestedException = nestedException;
        this.type = type;
    }

    public static CorrLangException io(IOException nested) {
        return new CorrLangException(null, nested, FailureTypes.NESTED_IO_EXCEPTION);
    }

    public static CorrLangException missingTechSpaceCapability(String techSpace, Class<? extends TechSpaceCapability> capability) {
        return new CorrLangException("%s is missing '%s'".formatted(techSpace, capability.getName()), null, FailureTypes.TECH_SPACE_MISSING_CAPABILITY);
    }

}
