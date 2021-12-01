package io.corrlang.plugins.techspace;

import no.hvl.past.names.Name;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Tech space directives allow to specify implicit
 * rules for aligning multiple models of the same tech-space
 */
public interface TechSpaceDirective {

    interface BaseTypeDescription {

        Name typeName();

        default boolean isDefault() {
            return true;
        }
    }

    enum IntTypeSizeRestriction {
        UNLIMITED,
        BIT_LENGTH_RESTRICTED,
        DECIMAL_LENGTH_RESTRICTED
    }

    interface IntTypeDescription extends BaseTypeDescription {

        int limit();

        IntTypeSizeRestriction restriction();

    }

    interface StringTypeDescription extends BaseTypeDescription {

        default Optional<Integer> limit() {
            return Optional.empty();
        }
    }


    interface FloatTypeDescription extends BaseTypeDescription {

    }

    interface IEEEFloatTypeDescription extends FloatTypeDescription {

        int bitSize();
    }

    interface DecimalFloatTypeDescription extends FloatTypeDescription {

        int length();

        int decimalPositions();
    }

    interface CustomBaseTypeDescription extends BaseTypeDescription {

        @Override
        default boolean isDefault() {
            return false;
        }
    }



    // TODO maybe the default data types must be changed into a collection ?!

    /**
     * The name of the string data type in this technology (if exists).
     */
    Stream<StringTypeDescription> stringDataType();

    /**
     * The name of the bool data type in this technology (if exists).
     */
    Stream<BaseTypeDescription> boolDataType();

    /**
     * The name of the integer data type in this technology (if exists).
     */
    Stream<IntTypeDescription> integerDataType();

    /**
     * The name of the floating point number data type in this technology (if exists).
     */
    Stream<FloatTypeDescription> floatingPointDataType();

    Stream<CustomBaseTypeDescription> otherDataTypes();

    /**
     * More names of types that always should be identified by their name.
     */
    void additionalTechnologySpecificRules(TechnologySpecificRules configure);

}
