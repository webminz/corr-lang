package io.corrlang.domain.exceptions;

import java.io.IOException;

public sealed interface FailureTypes permits FailureTypes.TechSpaceMissingCapability, FailureTypes.NestedIOException, FailureTypes.IllegalState, FailureTypes.IllegalArgument, FailureTypes.NestedUnhandledCheckException {

    record TechSpaceMissingCapability(String techSpaceName) implements FailureTypes {}
    record NestedIOException(IOException e) implements FailureTypes {}
    record IllegalState() implements FailureTypes {}
    record IllegalArgument() implements FailureTypes {}
    record NestedUnhandledCheckException(Exception e) implements FailureTypes {}
}
