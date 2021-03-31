package no.hvl.past.corrlang.execution.goals;

import no.hvl.past.corrlang.domainmodel.CorrSpec;
import no.hvl.past.plugin.UnsupportedFeatureException;
import no.hvl.past.techspace.TechSpace;
import no.hvl.past.techspace.TechSpaceAdapter;
import no.hvl.past.techspace.TechSpaceException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileGoal extends LanguageGoal {

    private File file;


    public void setFile(File file, boolean overwrite) throws GoalException {
        this.file = file;
        if (!this.file.getParentFile().exists()) {
            this.file.mkdirs();
        }
        if (file.exists() && !overwrite) {
            throw new GoalException("Cannot execute '" + getKey() + "' because " + file.getAbsolutePath() + " already exists and overwrite = false");
        }
        if (file.exists() && overwrite) {
            this.file.delete();
        }
    }



    public FileGoal() {
        super("FILE");
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
    protected void executeSchema(CorrSpec correspondence, TechSpaceAdapter<? extends TechSpace> techSpace) throws IOException, TechSpaceException, UnsupportedFeatureException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        techSpace.writeSchema(correspondence.getComprSys(), fileOutputStream);
        fileOutputStream.close();
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
