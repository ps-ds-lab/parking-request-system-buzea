package ro.utcluj.sd.parking.command;

public interface Command<T> {

    T execute();

    T undo();
}
