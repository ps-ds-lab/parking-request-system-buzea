package ro.utcluj.sd.parking.command;

import ro.utcluj.sd.parking.viewmodel.MyViewModel;

public class AddElementCommand implements Command<Void> {

    private final MyViewModel vm;

    public AddElementCommand(MyViewModel vm) {
        this.vm = vm;
    }

    @Override
    public Void execute() {
        vm.addNewElement();
        return null;
    }

    @Override
    public Void undo() {
        vm.removeElement();
        return null;
    }
}
