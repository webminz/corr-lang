package no.hvl.past.di;

import no.hvl.past.graph.*;
import no.hvl.past.graph.predicates.*;
import no.hvl.past.graph.trees.QueryHandler;
import no.hvl.past.names.Name;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.server.WebserviceRequestHandler;
import no.hvl.past.systems.Data;
import no.hvl.past.systems.Sys;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceDirective;
import no.hvl.past.techspace.TechSpaceException;
import no.hvl.past.util.ShouldNotHappenException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TestTechSpaceAdapter implements TechSpaceAdapter<TestTechSpace>, TechSpaceDirective {

    public static final String SALES = "Sales";
    public static final String SALES_PURCHASE_NODE = "Purchase";
    public static final String SALES_CUSTOMER_NODE = "Customer";
    public static final String SALES_PURCHASE2CCUSTOMER_EDGE = "byCustomer";
    public static final String SALES_CUSTOMER2PURCHASE_EDGE = "allPurchases";
    public static final String SALES_PURCHASE_AMOUNT_ATT = "amount";
    public static final String SALES_CUSTOMER_ID_ATT = "id";
    public static final String SALES_CUSTOMER_FULLNAME_ATT = "fullName";

    public static final String INVOICES = "Invoices";
    public static final String INVOICES_INVOICE_NODE = "Invoice";
    public static final String INVOICES_CLIENT_NODE = "Client";
    public static final String INVOICES_INVOICE2CLIENT_EDGE = "ofClient";
    public static final String INVOICES_INVOICE_DUE_ATT = "dueAt";
    public static final String INVOICES_CLIENT_ID_ATT = "id";
    public static final String INVOICES_DATE_TYPE = "Date";
    public static final String HR_EMPLOYEE_NODE = "Employee";
    public static final String HR_EMPLOYEE_FIRSTNAME_ATT = "firstname";
    public static final String HR_EMPLOYEE_LASTNAME_ATT = "lastname";
    public static final String HR_EMPLOYEE_SALARY_ATT = "salary";
    public static final String HR = "HR";

    public static final String TREE = "Tree";
    public static final String TREE_ROOT = "Root";
    public static final String TREE_NODE = "Node";
    public static final String TREE_NODE_ID_ATT = "id";
    public static final String TREE_NODE_CONTENT_ATT = "content";
    public static final String TREE_NODE2NODE_EDGE = "children";
    public static final String GRAPHS_GRAPH = "Graph";
    public static final String GRAPHS_ARC = "Arc";
    public static final String GRAPHS_VERTEX = "Vertex";
    public static final String GRAPHS_GRAPH2ARC_EDGE = "arcs";
    public static final String GRAPHS_GRAPH2VERTEX_EDGE = "vertices";
    public static final String GRAPHS_ARC_NAME = "name";
    public static final String GRAPHS_EDGE_KEY = "key";
    public static final String GRAPHS_ARCSRC_EDGE = "source";
    public static final String GRAPHS_ARCTRG_EDGE = "target";
    public static final String GRAPHS = "Graphs";
    public static final String SIG_SIGNATURE = "Signature";
    public static final String SIG_SORT = "Sort";
    public static final String SIG_OP = "Operation";
    public static final String SIG_ARG = "Argument";
    public static final String SIG_SIGNATURE2SORT_EDGE = "sorts";
    public static final String SIG_SORT2OP_EDGE = "operations";
    public static final String SIG_OP2SORT_EDGE = "return";
    public static final String SIG_OP2ARG_EDGE = "arguments";
    public static final String SIG_ARG2SORT_EDGE = "type";
    public static final String SIG_SORTID_EDGE = "sid";
    public static final String SIG_SORTNAME_ATT = "sortName";
    public static final String SIG_OPNAME_ATT = "operationName";
    public static final String SIG_ARGNAME_ATT = "argumentName";
    public static final String SIG_ARGORDER_ATT = "order";
    public static final String SIG = "Sig";


    private final Universe universe;
    private final TechSpace techSpace;
    private String stringTypeName;
    private String intTypeName;
    private String floatTypeName;
    private String boolTypeName;
    private Sketch salesSchema;
    private Sketch invoicesSchema;
    private Sketch hrSchema;
    private Sketch treeSchema;
    private Sketch graphSchema;
    private Sketch signatureSchema;

    public TestTechSpaceAdapter(TestTechSpace techSpace,
                                Universe universe,
                                String stringTypeName,
                                String intTypeName,
                                String floatTypeName,
                                String boolTypeName) {
        this.universe = universe;
        this.techSpace = techSpace;
        this.stringTypeName = stringTypeName;
        this.intTypeName = intTypeName;
        this.floatTypeName = floatTypeName;
        this.boolTypeName = boolTypeName;

        try {
            salesSchema = buildSalesSchema(universe,stringTypeName,floatTypeName,intTypeName);
            invoicesSchema = createInvoicesSchema(universe,intTypeName,floatTypeName);
            hrSchema = createHrSchema(universe,stringTypeName,floatTypeName);
            treeSchema = createTreeSchema(universe, stringTypeName, intTypeName);
            graphSchema = createGraphSchema(universe, stringTypeName, intTypeName);
            signatureSchema = createSignatureSchema(universe, stringTypeName, intTypeName);

        } catch (GraphError error) {
            throw new ShouldNotHappenException(getClass(), error);
        }
    }

    private Sketch createSignatureSchema(Universe universe, String stringTypeName, String intTypeName) throws GraphError {
        return new GraphBuilders(universe, true, false)
                .node(SIG_SIGNATURE)
                .node(SIG_SORT)
                .node(SIG_OP)
                .node(SIG_ARG)
                .edge(SIG_SIGNATURE, SIG_SIGNATURE2SORT_EDGE, SIG_SORT)
                .edge(SIG_SORT, SIG_SORT2OP_EDGE, SIG_OP)
                .edge(SIG_OP, SIG_OP2SORT_EDGE, SIG_SORT)
                .edge(SIG_OP, SIG_OP2ARG_EDGE, SIG_ARG)
                .edge(SIG_SORT, SIG_SORTID_EDGE, intTypeName)
                .edge(SIG_SORT, SIG_SORTNAME_ATT, stringTypeName)
                .edge(SIG_OP, SIG_OPNAME_ATT, stringTypeName)
                .edge(SIG_ARG, SIG_ARGNAME_ATT, stringTypeName)
                .edge(SIG_ARG, SIG_ARG2SORT_EDGE, SIG_SORT)
                .edge(SIG_ARG, SIG_ARGORDER_ATT, intTypeName)
                .graph(Name.identifier(SIG).absolute())
                .startDiagram(Singleton.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(SIG_SIGNATURE))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(stringTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(IntDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SIG_OP))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SIG_OP2SORT_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(SIG_SORT))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SIG_ARG))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SIG_ARG2SORT_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(SIG_SORT))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SIG_ARG))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SIG_ARGORDER_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SIG_SORT))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SIG_SORTID_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .sketch(Name.identifier(SIG))
                .getResult(Sketch.class);

    }

    private Sketch createGraphSchema(Universe universe, String stringTypeName, String intTypeName) throws GraphError {
        return new GraphBuilders(universe, true, false)
                .node(GRAPHS_GRAPH)
                .node(GRAPHS_ARC)
                .node(GRAPHS_VERTEX)
                .edge(GRAPHS_GRAPH, GRAPHS_GRAPH2ARC_EDGE, GRAPHS_ARC)
                .edge(GRAPHS_GRAPH, GRAPHS_GRAPH2VERTEX_EDGE, GRAPHS_VERTEX)
                .edge(GRAPHS_ARC, GRAPHS_ARCSRC_EDGE, GRAPHS_VERTEX)
                .edge(GRAPHS_ARC, GRAPHS_ARCTRG_EDGE, GRAPHS_VERTEX)
                .edge(GRAPHS_ARC, GRAPHS_ARC_NAME, stringTypeName)
                .edge(GRAPHS_VERTEX, GRAPHS_EDGE_KEY, intTypeName)
                .graph(Name.identifier(GRAPHS).absolute())
                .startDiagram(Singleton.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(GRAPHS_GRAPH))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(stringTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(IntDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(GRAPHS_ARC))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(GRAPHS_ARCSRC_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(GRAPHS_VERTEX))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(GRAPHS_ARC))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(GRAPHS_ARCTRG_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(GRAPHS_VERTEX))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(GRAPHS_VERTEX))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(GRAPHS_EDGE_KEY))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(GRAPHS_ARC))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(GRAPHS_ARC_NAME))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(stringTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(SourceMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(GRAPHS_VERTEX))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(GRAPHS_EDGE_KEY))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .sketch(Name.identifier(GRAPHS))
                .getResult(Sketch.class);

    }

    private Sketch createTreeSchema(Universe universe, String stringTypeName, String intTypeName) throws GraphError {
        return new GraphBuilders(universe, true, false)
                .node(TREE_ROOT)
                .node(TREE_NODE)
                .edge(TREE_NODE, TREE_NODE_ID_ATT, intTypeName)
                .edge(TREE_NODE, TREE_NODE_CONTENT_ATT, stringTypeName)
                .edge(TREE_NODE, TREE_NODE2NODE_EDGE, TREE_NODE)
                .graph(Name.identifier(TREE).absolute())
                .startDiagram(Singleton.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(TREE_ROOT))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(stringTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(IntDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(TREE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(TREE_NODE_ID_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(TREE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(TREE_NODE_CONTENT_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(stringTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(SourceMultiplicity.getInstance(0, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(TREE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(TREE_NODE_ID_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(SourceMultiplicity.getInstance(1, 1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(TREE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(TREE_NODE2NODE_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(TREE_NODE))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(Acyclicity.getInstance())
                .map(Universe.LOOP_THE_LOOP.getSource(), Name.identifier(TREE_NODE))
                .map(Universe.LOOP_THE_LOOP.getLabel(), Name.identifier(TREE_NODE2NODE_EDGE))
                .endDiagram(Name.anonymousIdentifier())
                .sketch(TREE)
                .getResult(Sketch.class);
    }

    private Sketch createHrSchema(Universe universe, String stringType, String flaotType) throws GraphError {
        return new GraphBuilders(universe, true, false)
                .node(HR_EMPLOYEE_NODE)
                .edge(HR_EMPLOYEE_NODE, HR_EMPLOYEE_FIRSTNAME_ATT, stringType)
                .edge(HR_EMPLOYEE_NODE, HR_EMPLOYEE_LASTNAME_ATT, stringType)
                .edge(HR_EMPLOYEE_NODE, HR_EMPLOYEE_SALARY_ATT, flaotType)
                .graph(Name.anonymousIdentifier())
                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(stringType))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(flaotType))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(HR_EMPLOYEE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(HR_EMPLOYEE_FIRSTNAME_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(stringType))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(HR_EMPLOYEE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(HR_EMPLOYEE_LASTNAME_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(stringType))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(HR_EMPLOYEE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(HR_EMPLOYEE_SALARY_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(flaotType))
                .endDiagram(Name.anonymousIdentifier())
                .sketch(Name.identifier(HR))
                .getResult(Sketch.class);
    }

    private Sketch createInvoicesSchema(Universe universe, String intTypeName, String floatTypeName) throws GraphError {
        return new GraphBuilders(universe, true, false)
                .node(INVOICES_INVOICE_NODE)
                .node(INVOICES_CLIENT_NODE)
                .node(INVOICES_DATE_TYPE)
                .edge(INVOICES_INVOICE_NODE, INVOICES_INVOICE2CLIENT_EDGE, INVOICES_CLIENT_NODE)
                .edge(INVOICES_INVOICE_NODE, INVOICES_INVOICE_DUE_ATT, INVOICES_DATE_TYPE)
                .edge(INVOICES_INVOICE_NODE, SALES_PURCHASE_AMOUNT_ATT, floatTypeName)
                .edge(INVOICES_CLIENT_NODE, INVOICES_CLIENT_ID_ATT, intTypeName)
                .graph(Name.anonymousIdentifier())
                .startDiagram(IntDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(FloatDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(floatTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(DataTypePredicate.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(INVOICES_DATE_TYPE))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(INVOICES_INVOICE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(INVOICES_INVOICE2CLIENT_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(INVOICES_CLIENT_NODE))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(INVOICES_CLIENT_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(INVOICES_CLIENT_ID_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(INVOICES_INVOICE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SALES_PURCHASE_AMOUNT_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(floatTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(0,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(INVOICES_INVOICE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(INVOICES_INVOICE_DUE_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(INVOICES_DATE_TYPE))
                .endDiagram(Name.anonymousIdentifier())
                .sketch(Name.identifier(INVOICES))
                .getResult(Sketch.class);
    }

    private Sketch buildSalesSchema(Universe universe, String stringTypeName, String floatTypeName, String intTypeName) throws GraphError {
        return new GraphBuilders(universe, true, false)
                .node(SALES_PURCHASE_NODE)
                .node(SALES_CUSTOMER_NODE)
                .edge(SALES_PURCHASE_NODE, SALES_PURCHASE2CCUSTOMER_EDGE, SALES_CUSTOMER_NODE)
                .edge(SALES_CUSTOMER_NODE, SALES_CUSTOMER2PURCHASE_EDGE, SALES_PURCHASE_NODE)
                .edge(SALES_PURCHASE_NODE, SALES_PURCHASE_AMOUNT_ATT, floatTypeName)
                .edge(SALES_CUSTOMER_NODE, SALES_CUSTOMER_ID_ATT, intTypeName)
                .edge(SALES_CUSTOMER_NODE, SALES_CUSTOMER_FULLNAME_ATT, stringTypeName)
                .graph(Name.anonymousIdentifier())
                .startDiagram(StringDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(stringTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(IntDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(FloatDT.getInstance())
                .map(Universe.ONE_NODE_THE_NODE, Name.identifier(floatTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SALES_PURCHASE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SALES_PURCHASE2CCUSTOMER_EDGE))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(SALES_CUSTOMER_NODE))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SALES_PURCHASE_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SALES_PURCHASE_AMOUNT_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(floatTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SALES_CUSTOMER_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SALES_CUSTOMER_ID_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(intTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .startDiagram(TargetMultiplicity.getInstance(1,1))
                .map(Universe.ARROW_SRC_NAME, Name.identifier(SALES_CUSTOMER_NODE))
                .map(Universe.ARROW_LBL_NAME, Name.identifier(SALES_CUSTOMER_FULLNAME_ATT))
                .map(Universe.ARROW_TRG_NAME, Name.identifier(stringTypeName))
                .endDiagram(Name.anonymousIdentifier())
                .sketch(Name.identifier(SALES))
                .getResult(Sketch.class);
    }


    @Override
    public Sys parseSchema(Name schemaName, String schemaLocationURI) throws TechSpaceException, UnsupportedFeatureException {
        if (schemaName.equals(Name.identifier(SALES))) {
            return new TestSystem(schemaLocationURI, salesSchema);
        }
        if (schemaName.equals(Name.identifier(INVOICES))) {
            return new TestSystem(schemaLocationURI, invoicesSchema);
        }
        if (schemaName.equals(Name.identifier(HR))) {
            return new TestSystem(schemaLocationURI, hrSchema);
        }
        if (schemaName.equals(Name.identifier(TREE))) {
            return new TestSystem(schemaLocationURI, treeSchema);
        }
        if (schemaName.equals(Name.identifier(GRAPHS))) {
            return new TestSystem(schemaLocationURI, graphSchema);
        }
        if (schemaName.equals(Name.identifier(SIG))) {
            return new TestSystem(schemaLocationURI, signatureSchema);
        }
        throw new TechSpaceException("Schema at location " + schemaLocationURI + " not found", techSpace);
    }

    @Override
    public void writeSchema(Sys sys, OutputStream outputStream) throws TechSpaceException, UnsupportedFeatureException {
        throw new UnsupportedFeatureException();
    }

    @Override
    public TechSpaceDirective directives() {
        return this;
    }



    @Override
    public QueryHandler queryHandler(Sys system) throws TechSpaceException, UnsupportedFeatureException {
        throw new UnsupportedFeatureException();
    }

    @Override
    public Data readInstance(Sys system, InputStream inputStream) throws TechSpaceException, UnsupportedFeatureException {
        return null;
    }

    @Override
    public void writeInstance(Sys system, GraphMorphism instance, OutputStream outputStream) throws TechSpaceException, UnsupportedFeatureException {

    }



    @Override
    public Optional<Name> stringDataType() {
        return Optional.ofNullable(stringTypeName).map(Name::identifier);
    }

    @Override
    public Optional<Name> boolDataType() {
        return Optional.ofNullable(boolTypeName).map(Name::identifier);
    }

    @Override
    public Optional<Name> integerDataType() {
        return Optional.ofNullable(intTypeName).map(Name::identifier);
    }

    @Override
    public Optional<Name> floatingPointDataType() {
        return Optional.ofNullable(floatTypeName).map(Name::identifier);
    }

    @Override
    public Stream<Name> implicitTypeIdentities() {
        return Stream.empty();
    }
}
