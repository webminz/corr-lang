package no.hvl.past.corrlang.execution.goals;

import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceException;

public class CodegenGoal extends LanguageGoal {


    public CodegenGoal() {
        super("CODE_GEN");
    }

    @Override
    protected void executeTransformation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeSynchronize(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeVerify(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeSchema(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeMatch(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) {

    }

    @Override
    protected void executeFederation(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws TechSpaceException, UnsupportedFeatureException {

    }

    @Override
    public boolean isBatchGoal() {
        return false;
    }

    @Override
    public boolean isServerGoal() {
        return false;
    }

    @Override
    public boolean isCodegenGoal() {
        return false;
    }

    @Override
    public boolean isFileGoal() {
        return true;
    }
}
