package r93.configurableheadbob.client.menu;

import java.util.List;

import org.jspecify.annotations.NullMarked;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;

@NullMarked
public final class HeadbobOptionsList extends OptionsList {

    private final OptionsSubScreen owner;
    private final Entry firstRow;
    private final Entry lastRow;

    
    HeadbobOptionsList(Minecraft minecraft, int width, OptionsSubScreen owner, AbstractWidget globalToggle, AbstractWidget modeButton, AbstractWidget resetButton) {
        super(minecraft, width, owner);
        this.owner = owner;

        firstRow = Entry.small(globalToggle, modeButton, owner);

        resetButton.setWidth(310);
        lastRow = Entry.big(List.of(resetButton), owner);
    }

    public void resetToFirstRow() {
        setFocused(null);
        setDragging(false);
        clearEntries();

        addEntry(firstRow);
    }

    //Overload for addBig
    public void addBig(AbstractWidget theWidget) {
        theWidget.setWidth(310);
        addEntry(Entry.big(List.of(theWidget), owner));
    }

    public void addLastRow() {
        addEntry(lastRow);
        setScrollAmount(0.0);
    }
}