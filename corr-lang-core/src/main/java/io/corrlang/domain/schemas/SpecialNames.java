package io.corrlang.domain.schemas;

import no.hvl.past.names.Name;

/**
 * No-constructor class whose sole purpose is to be container
 * for constants. In this case constants that represent "magic" names in the CorrLang
 * DSL.
 */
public class SpecialNames {

    public static final Name RESULT_SUCCESS = Name.identifier("§out");
    public static final Name RESULT_FAILURE = Name.identifier("§err");
    public static final Name ROOT_CONTAINER = Name.identifier("§root");
    public static final Name ID_FIELD = Name.identifier("§id");
    public static final Name CREATE_METHOD = Name.identifier("§create");
    public static final Name READ_ALL_METHOD = Name.identifier("§all");
    public static final Name GET_BY_ID_METHOD = Name.identifier("§get");
    public static final Name UPDATE_METHOD = Name.identifier("§update");
    public static final Name DELETE_METHOD = Name.identifier("§delete");
    public static final Name GLOBAL_STRING_NAME = Name.identifier("String");
    public static final Name GLOBAL_INT_NAME = Name.identifier("Integer");
    public static final Name GLOBAL_FLOAT_NAME = Name.identifier("Float");
    public static final Name GLOBAL_BOOL_NAME = Name.identifier("Bool");

    private SpecialNames() {}
}
