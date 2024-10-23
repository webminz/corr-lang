package io.corrlang.plugins.dm;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MultiPlotElement extends  PlotElement{

    private String name;
    private List<String> targets = new ArrayList<>();

    public MultiPlotElement(String name) {
        this.name = name;
    }

    public void add(String target) {
        this.targets.add(target);
    }

    @Override
    public void writePlantUML(Writer writer) throws IOException {
        writer.append("() ");
        writer.append(name);
        writer.append(" #lightblue;line.dashed:blue\n");
        for (String target : this.targets) {
            writer.append(name);
            writer.append("--# ");
            writer.append(target);
            writer.append(" #blue;line.dashed;text:blue : π\n");
        }
    }
}
